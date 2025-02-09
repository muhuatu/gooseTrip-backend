package com.example.goosetrip.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dao.AccommodationDao;
import com.example.goosetrip.dao.ChatroomMembersDao;
import com.example.goosetrip.dto.Accommodation;
import com.example.goosetrip.dto.Users;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.DeleteRoomDataReq;
import com.example.goosetrip.vo.GetHotelReq;
import com.example.goosetrip.vo.GetHotelRoomReq;
import com.example.goosetrip.vo.GetRoomDataRes;
import com.example.goosetrip.vo.HotelList;
import com.example.goosetrip.vo.HotelRoom;
import com.example.goosetrip.vo.RoomData;
import com.example.goosetrip.vo.SaveRoomDataReq;
import com.example.goosetrip.vo.SelectHotelRes;
import com.example.goosetrip.vo.UpdateRoomData;
import com.example.goosetrip.vo.UpdateRoomDataReq;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import io.github.bonigarcia.wdm.WebDriverManager;

@Service
public class AccommodationService {

	@Autowired
	private AccommodationDao accommodationDao;

	@Autowired
	private ChatroomMembersDao chatroomMembersDao;

	/**
	 * 取得booking網站的飯店資訊
	 * 
	 * @param reqDto
	 * @return
	 */
	public List<HotelList> selectHotel(GetHotelReq reqDto) {
		List<HotelList> resp = new ArrayList<>();
		if (!StringUtils.hasText(reqDto.getWebName())) {
			String booking = openPythonFile("bookingList.py", reqDto);
			String agoda = openPythonFile("agodaList.py", reqDto);
			if (StringUtils.hasText(booking)) {
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					List<HotelList> hotelDTO = objectMapper.readValue(booking, new TypeReference<List<HotelList>>() {
					});
					resp.addAll(hotelDTO);
				} catch (JsonProcessingException e) {
					return null;
				}
			}

			if (StringUtils.hasText(agoda)) {
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					List<HotelList> hotelDTO = objectMapper.readValue(agoda, new TypeReference<List<HotelList>>() {
					});
					resp.addAll(hotelDTO);
				} catch (Exception e) {
					return null;
				}
			}
			return resp;
		} else if ("booking".equals(reqDto.getWebName())) {
			String booking = openPythonFile("bookingList.py", reqDto);
			System.out.println(LocalDateTime.now() + ": " + booking);
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				List<HotelList> hotelDTO = objectMapper.readValue(booking, new TypeReference<List<HotelList>>() {
				});
				return hotelDTO;
			} catch (Exception e) {
				return null;
			}
		} else if ("agoda".equals(reqDto.getWebName())) {
			String agoda = openPythonFile("agodaList.py", reqDto);
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				List<HotelList> hotelDTO = objectMapper.readValue(agoda, new TypeReference<List<HotelList>>() {
				});
				return hotelDTO;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	private static WebDriver driver;

	/**
	 * 取得網站的房間資訊
	 * 
	 * @param reqDto
	 * @return
	 */
	public List<HotelRoom> getHotelRoom(GetHotelRoomReq reqDto) {
		String output = "";
		if ("booking".equals(reqDto.getWebName())) {
			output = openPythonFile("bookingFindRoom.py", reqDto);
		}
		if ("agoda".equals(reqDto.getWebName())) {
			output = openPythonFile("agodaFindRoom.py", reqDto);
		}
		if (output == null) {
			return null;
		}

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			List<HotelRoom> roomDTO = objectMapper.readValue(output, new TypeReference<List<HotelRoom>>() {
			});
			return roomDTO;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 儲存房間資訊
	 * 
	 * @param reqDto
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public BasicRes saveRoomData(SaveRoomDataReq reqDto, Users user) {
		// 檢查是否在這一個行程中
		List<Integer> userInJourneyId = chatroomMembersDao.findJourneyIdByUserMail(user.getUserMail());
		if (!userInJourneyId.contains(reqDto.getJourneyId())) {
			return new BasicRes(ResMessage.MEMBER_NOT_IN_JOURNEY.getCode(),
					ResMessage.MEMBER_NOT_IN_JOURNEY.getMessage());
		}

		// 日期檢查
		if (reqDto.getCheckinDate().isBefore(LocalDate.now())) {
			return new SelectHotelRes(ResMessage.CHECKIN_DAY_IS_ERROR.getCode(),
					ResMessage.CHECKIN_DAY_IS_ERROR.getMessage());
		}
		if (reqDto.getCheckoutDate().isBefore(reqDto.getCheckinDate())) {
			return new SelectHotelRes(ResMessage.CHECKOUT_DAY_IS_ERROR.getCode(),
					ResMessage.CHECKOUT_DAY_IS_ERROR.getMessage());
		}

		List<Accommodation> saveList = new ArrayList<>();
		for (RoomData item : reqDto.getRoomData()) {
			Accommodation vo = new Accommodation();
			vo.setJourneyId(reqDto.getJourneyId());
			vo.setCheckinDate(reqDto.getCheckinDate());
			vo.setCheckoutDate(reqDto.getCheckoutDate());
			vo.setHotelName(reqDto.getHotelName());
			vo.setHotelWeb(reqDto.getWebName());
			vo.setRoomType(item.getRoomType());
			vo.setBedType(item.getBedType().toString());
			vo.setPrice(item.getRoomCurrency() + item.getRoomPrice());
			vo.setNumber(item.getNumber());
			vo.setUrl(reqDto.getUrl());
			vo.setFinished(item.isFinished());
			vo.setUserMail(null);
			saveList.add(vo);
		}

		int result = accommodationDao.saveRoomData(saveList);
		if (result < 1) {
			return new BasicRes(ResMessage.INSERT_ROOM_FAILED.getCode(), ResMessage.INSERT_ROOM_FAILED.getMessage());
		}

		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	/**
	 * 取得相同行程的住宿資訊
	 * 
	 * @param journeyId
	 * @return
	 */
	public GetRoomDataRes getRoomData(int journeyId, Users user) {
		// 檢查是否在這一個行程中
		List<Integer> userInJourneyId = chatroomMembersDao.findJourneyIdByUserMail(user.getUserMail());
		if (!userInJourneyId.contains(journeyId)) {
			return new GetRoomDataRes(ResMessage.MEMBER_NOT_IN_JOURNEY.getCode(),
					ResMessage.MEMBER_NOT_IN_JOURNEY.getMessage());
		}

		List<Accommodation> result = accommodationDao.getRoomData(journeyId);
		List<List<Accommodation>> resList = new ArrayList<>();
		item: for (Accommodation item : result) {
			for (List<Accommodation> resVoList : resList) {
				if (resVoList.isEmpty()) {
					continue;
				}
				if (item.getCheckinDate().equals(resVoList.get(0).getCheckinDate())
						&& item.getCheckoutDate().equals(resVoList.get(0).getCheckoutDate())
						&& item.getHotelName().equals(resVoList.get(0).getHotelName())) {
					resVoList.add(item);
					continue item;
				}
			}
			List<Accommodation> temp = new ArrayList<>();
			temp.add(item);
			resList.add(temp);
		}
		return new GetRoomDataRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), resList);
	}

	/**
	 * 刪除住宿資訊
	 * 
	 * @param reqDto
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public BasicRes deleteRoomData(DeleteRoomDataReq reqDto, Users user) {
		// 檢查是否在這一個行程中
		List<Integer> userInJourneyId = chatroomMembersDao.findJourneyIdByUserMail(user.getUserMail());
		if (!userInJourneyId.contains(reqDto.getJourneyId())) {
			return new BasicRes(ResMessage.MEMBER_NOT_IN_JOURNEY.getCode(),
					ResMessage.MEMBER_NOT_IN_JOURNEY.getMessage());
		}

		accommodationDao.deleteRoomData(reqDto.getJourneyId(), reqDto.getAccommodationId());
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	/**
	 * 取得行程住宿資訊編輯權限
	 * 
	 * @param userMail
	 * @param journeyId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public BasicRes editAccommodation(String userMail, int journeyId) {
		// 檢查是否在這一個行程中
		List<Integer> userInJourneyId = chatroomMembersDao.findJourneyIdByUserMail(userMail);
		if (!userInJourneyId.contains(journeyId)) {
			return new BasicRes(ResMessage.MEMBER_NOT_IN_JOURNEY.getCode(),
					ResMessage.MEMBER_NOT_IN_JOURNEY.getMessage());
		}
		int result = accommodationDao.editAccommodation(userMail, journeyId);
		if (result < 1) {
			return new BasicRes(ResMessage.GET_EDIT_PURVIEW_FAILED.getCode(),
					ResMessage.GET_EDIT_PURVIEW_FAILED.getMessage());
		}
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	/**
	 * 更新住宿資訊、停止編輯權限
	 * 
	 * @param reqDto
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public BasicRes updateRoomData(UpdateRoomDataReq reqDto, String userMail) {
		List<Integer> Chatrooms = chatroomMembersDao.findJourneyIdByUserMail(userMail);
		if (!Chatrooms.contains(reqDto.getJourneyId())) {
			return new BasicRes(ResMessage.MEMBER_NOT_IN_JOURNEY.getCode(),
					ResMessage.MEMBER_NOT_IN_JOURNEY.getMessage());
		}
		// 補檢查這個人有沒有加入這個行程
		for (UpdateRoomData item : reqDto.getRoomDate()) {
			Accommodation vo = new Accommodation();
			vo.setAccommodationId(item.getAccommodationId());
			vo.setFinished(item.isFinished());
			accommodationDao.updateRoomData(vo);
		}
		accommodationDao.stopEditAccommodation(reqDto.getJourneyId());
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	private String openPythonFile(String pyFilePath, Object reqData) {

		String tempFilePath = "C:/Users/TEST/Desktop/Python_file/" + UUID.randomUUID().toString() + ".txt"; // 可以根據需要修改檔案路徑
		File tempPythonFile = null;

		try (BufferedWriter bufferedWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(tempFilePath), StandardCharsets.UTF_8))) {

			// 將 Map 轉換成 JSON 字串
			String json = new Gson().toJson(reqData);

			// 寫入到檔案
			bufferedWriter.write(json);
			bufferedWriter.flush();

			// 從 resources 複製 Python 腳本到臨時目錄
			ClassPathResource resource = new ClassPathResource(pyFilePath);
			tempPythonFile = File.createTempFile("script", ".py");
			try (InputStream inputStream = resource.getInputStream();
					FileOutputStream outputStream = new FileOutputStream(tempPythonFile)) {
				FileCopyUtils.copy(inputStream, outputStream);
			}

			// 執行 Python 腳本
			ProcessBuilder processBuilder = new ProcessBuilder("python", tempPythonFile.getAbsolutePath(),
					tempFilePath);
			Process process = processBuilder.start();

			// 讀取 Python 腳本輸出的結果
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder output = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line);
			}

			// 等待 Python 腳本執行結束
			int exitCode = process.waitFor();
			// 檢查腳本執行是否成功
			if (exitCode == 0) {
				return output.toString();
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			// 刪除臨時檔案
			try {
				Files.deleteIfExists(Paths.get(tempFilePath));
				if (tempPythonFile != null && tempPythonFile.exists()) {
					tempPythonFile.delete();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 取得booking網站的飯店資訊
	 * 
	 * @param reqDto
	 * @return
	 */
	public SelectHotelRes fingBookingList(GetHotelReq reqDto) {

		if (LocalDate.parse(reqDto.getCheckinDate()).isBefore(LocalDate.now())) {
			return new SelectHotelRes(ResMessage.CHECKIN_DAY_IS_ERROR.getCode(),
					ResMessage.CHECKIN_DAY_IS_ERROR.getMessage());
		}
		if (LocalDate.parse(reqDto.getCheckoutDate()).isBefore(LocalDate.parse(reqDto.getCheckinDate()))) {
			return new SelectHotelRes(ResMessage.CHECKOUT_DAY_IS_ERROR.getCode(),
					ResMessage.CHECKOUT_DAY_IS_ERROR.getMessage());
		}
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments(
				"--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
		options.addArguments("--disable-gpu");
		options.addArguments("--window-size=1920,1080");
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", false);

		driver = new ChromeDriver(options);
		driver.get(
				"https://www.booking.com/index.zh-tw.html?aid=2311236;label=zh-xt-tw-booking-desktop-EbyZWwQLwTW_IbH*0eHNCgS654267613595:pl:ta:p1:p2:ac:ap:neg:fi:tikwd-65526620:lp1012818:li:dec:dm;ws=&gad_source=1&gclid=CjwKCAiAmfq6BhAsEiwAX1jsZ_cjODVX18PabJdOk_jUBAsXEG0-JGMbFFdphMB-0brvLW50MfVqnhoChgQQAvD_BwE");
		try {
			WebElement closeButton = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='關閉登入的資訊。']")));
			closeButton.click();
		} catch (TimeoutException e) {
		}

		String returnUrl = "";
		try {
			WebElement inputBox = new WebDriverWait(driver, Duration.ofSeconds(1))
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[name='ss'].eb46370fe1")));
			inputBox.clear();
			inputBox.sendKeys(reqDto.getArea());

			Thread.sleep(1000); // Sleep for 1 second
			inputBox.sendKeys(Keys.ENTER);

			String url = driver.getCurrentUrl();
			returnUrl = url.split("group_adults")[0] + "checkin=" + reqDto.getCheckinDate() + "&checkout="
					+ reqDto.getCheckoutDate() + "&group_adults=" + reqDto.getAdults() + "&no_rooms="
					+ reqDto.getRooms() + "&group_children=" + reqDto.getChildren();

			for (int i = 0; i < Integer.parseInt(reqDto.getChildren()); i++) {
				returnUrl = returnUrl + "&age=10";
			}
			driver.get(returnUrl);

			inputBox = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[name='ss'].eb46370fe1")));

			String value = inputBox.getAttribute("value");
			while (!value.contains(reqDto.getArea())) {
				inputBox = new WebDriverWait(driver, Duration.ofSeconds(10))
						.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[name='ss'].eb46370fe1")));
				inputBox.clear();
				inputBox.sendKeys(reqDto.getArea());

				WebElement button = new WebDriverWait(driver, Duration.ofSeconds(10))
						.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
								"button.a83ed08757.c21c56c305.a4c1805887.f671049264.a2abacf76b.c082d89982.cceeb8986b.b9fd3c6b3c")));
				button.click();

				value = inputBox.getAttribute("value");
			}
			new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span.a8887b152e")));
			for (WebElement item : driver.findElements(By.cssSelector("span.a8887b152e"))) {
				if ("入住日期".equals(item.getText())) {
					return new SelectHotelRes(ResMessage.NOT_FIND_HOTEL.getCode(),
							ResMessage.NOT_FIND_HOTEL.getMessage());
				}
			}
		} catch (Exception e) {
		}

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
			WebElement closeButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='關閉登入的資訊。']")));
			closeButton.click();
		} catch (TimeoutException e) {
		}
		Set<String> nameSet = new HashSet<>();
		List<HotelList> allData = new ArrayList<>();

		long lastHeight = 0;
		boolean condition = true;
		if (reqDto.getNumber() != null) {
			condition = driver.findElements(By.cssSelector("div[data-testid='property-card']")).size() < Integer
					.valueOf(reqDto.getNumber());
		}

		while (condition) {
			try {
				Thread.sleep(1000);
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight-1000);");
				long newHeight = (long) jsExecutor.executeScript("return document.body.scrollHeight");
				if (newHeight == lastHeight) {
					break;
				}
				lastHeight = newHeight;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		while (condition) {
			try {
				// 等待按鈕可點擊
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
				WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
						"button[type='button'].a83ed08757.c21c56c305.bf0537ecb5.f671049264.af7297d90d.c0e0affd09")));

				// 使用 ActionChains 模擬滑鼠移動和點擊
				Actions actions = new Actions(driver);
				actions.moveToElement(button).perform();
				button.click();
			} catch (Exception e) {
				break;
			}
		}

		List<WebElement> cards = driver.findElements(By.cssSelector("div[data-testid='property-card']"));
		int returnSize = 0;
		if (reqDto.getNumber() != null) {
			returnSize = Math.min(cards.size(), reqDto.getNumber());
		} else {
			returnSize = cards.size();
		}

		for (int i = 0; i < returnSize; i++) {
			try {
				WebElement card = cards.get(i);
				WebElement nameElement = card.findElement(By.cssSelector("[data-testid='title']"));

				List<WebElement> priceElements = card.findElements(By.cssSelector(".f6431b446c.fbfd7c1165.e84eb96b1f"));
				String name = nameElement.getText();

				if (name != null && priceElements != null && !nameSet.contains(name)) {
					String url = card.findElement(By.cssSelector("a[data-testid='title-link']")).getAttribute("href");

					double opinion = 0.0f;
					try {

						Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
						Matcher matcher = pattern
								.matcher(card.findElement(By.cssSelector("div.a3b8729ab1.d86cee9b25")).getText());
						if (matcher.find()) {
							opinion = Double.valueOf(matcher.group());
						}

					} catch (Exception ex) {
						// 若沒有評價元素，則設置為0.0
					}

					String img = card.findElement(By.cssSelector("img[data-testid='image']")).getAttribute("src");

					HotelList data = new HotelList();
					data.setWebName("booking");
					data.setName(name);
					Matcher match = Pattern.compile("([A-Z]+)\\s([\\d,]+)").matcher(priceElements.get(0).getText());
					long day = ChronoUnit.DAYS.between(LocalDate.parse(reqDto.getCheckinDate()),
							LocalDate.parse(reqDto.getCheckoutDate()));
					if (match.find()) {
						data.setCurrency(match.group(1));
						int price = Integer.parseInt(match.group(2).replace(",", ""));
						data.setOneNightPrice((int) Math.ceil(price / (double) day));
						data.setAllPrice(price);
					}
					data.setUrl(url);
					data.setOpinion(opinion);
					data.setImg(img);

					nameSet.add(name);
					allData.add(data);
				}
			} catch (StaleElementReferenceException e) {
				continue; // 若元素不再可用，則跳過
			}
		}
		driver.quit();
		return new SelectHotelRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), allData);
	}

	/**
	 * 取得agoda網站的飯店資訊
	 * 
	 * @param reqDto
	 * @return
	 */
	public SelectHotelRes fingAgodaList(GetHotelReq reqDto) {
		if (LocalDate.parse(reqDto.getCheckinDate()).isBefore(LocalDate.now())) {
			return new SelectHotelRes(ResMessage.CHECKIN_DAY_IS_ERROR.getCode(),
					ResMessage.CHECKIN_DAY_IS_ERROR.getMessage());
		}
		if (LocalDate.parse(reqDto.getCheckoutDate()).isBefore(LocalDate.parse(reqDto.getCheckinDate()))) {
			return new SelectHotelRes(ResMessage.CHECKOUT_DAY_IS_ERROR.getCode(),
					ResMessage.CHECKOUT_DAY_IS_ERROR.getMessage());
		}
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments(
				"--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
		options.addArguments("--disable-gpu");
		options.addArguments("--window-size=1920,1080");
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", false);

		driver = new ChromeDriver(options);
		try {
			driver.get(
					"https://www.agoda.com/zh-tw/?site_id=1922895&tag=e9ea26c2-c046-468f-939d-97d11075d6e0&gad_source=1&device=c&network=g&adid=702678770791&rand=9547786221666415290&expid=&adpos=&aud=kwd-2230651387&gclid=EAIaIQobChMI_abB68LCigMVTcdMAh1hEzFZEAAYASAAEgK--fD_BwE&pslc=1&ds=Q8goOEZePfADNhzP");

			// 輸入地點
			while (true) {
				try {
					driver.findElement(By.id("textInput"));
					break;
				} catch (Exception e) {
				}
			}
			driver.findElement(By.id("textInput")).sendKeys(reqDto.getArea());
			Thread.sleep(500);

			// 解除 focus 狀態
			new Actions(driver).moveByOffset(100, 100).click().perform();

			// 點擊搜尋按鈕
			WebElement button = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
					ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-selenium='searchButton']")));

			button.click();

			while (true) {
				// 獲取所有窗口的句柄（視窗）
				List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
				if (windowHandles.size() > 1) {
					driver.switchTo().window(windowHandles.get(windowHandles.size() - 1));
					break;
				}
				if (windowHandles.size() == 1) {
					break;
				}
			}

			// 切換到新分頁
			List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
			driver.switchTo().window(windowHandles.get(windowHandles.size() - 1));

			// 取得新分頁的 URL
			String pageUrl = driver.getCurrentUrl();
			URI uri = new URI(pageUrl);
			String query = uri.getRawQuery();

			String[] pairs = query.split("&");
			Map<String, String> queryParams = new HashMap<>();
			for (String pair : pairs) {
				int idx = pair.indexOf("=");
				String key = URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8);
				String value = URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8);
				queryParams.put(key, value);
			}

			queryParams.put("checkIn", reqDto.getCheckinDate().toString());
			queryParams.put("checkOut", reqDto.getCheckoutDate().toString());
			queryParams.put("rooms", reqDto.getRooms());
			queryParams.put("adults", reqDto.getAdults());
			queryParams.put("children", reqDto.getChildren());

			// 添加 childages
			StringBuilder age = new StringBuilder();
			if (StringUtils.hasText(reqDto.getChildren())) {
				int childrenCount = Integer.parseInt(reqDto.getChildren());
				for (int i = 0; i < childrenCount; i++) {
					age.append("10");
					if (i < childrenCount - 1) {
						age.append(",");
					}
				}
				queryParams.put("childages", age.toString());
			}

			StringBuilder newQuery = new StringBuilder();
			for (Map.Entry<String, String> entry : queryParams.entrySet()) {
				if (newQuery.length() > 0) {
					newQuery.append("&");
				}
				newQuery.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8)).append("=")
						.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
			}

			URI newUri = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), newQuery.toString(),
					uri.getFragment());
			driver.get(newUri.toString());

			if (windowHandles.size() > 1) {

				try {
					Thread.sleep(500);
					if (driver.findElements(By.cssSelector("[data-element-name='no-search-results']")).size() != 0) {
						System.out.println("沒飯店拉");
						return new SelectHotelRes(ResMessage.NOT_FIND_HOTEL.getCode(),
								ResMessage.NOT_FIND_HOTEL.getMessage());
					}
				} catch (Exception e) {
				}
			} else {
				try {
					List<WebElement> test = driver.findElements(By.tagName("h4"));
					for (int i = 0; i < test.size(); i++) {
						String text = test.get(i).getText();
						if ("沒有您需要的選項?".equals(text)) {
							System.out.println("沒飯店拉2");
							return new SelectHotelRes(ResMessage.NOT_FIND_HOTEL.getCode(),
									ResMessage.NOT_FIND_HOTEL.getMessage());
						}
					}
				} catch (Exception e) {
				}
			}

			Set<String> nameSet = new HashSet<>();
			List<HotelList> allData = new ArrayList<>();
			List<HotelList> resp = new ArrayList<>();

			// 條件判斷
			int maxNumber = reqDto.getNumber() != null ? reqDto.getNumber() : 300;
			int startNumber = 0;

			while ((resp.size() + allData.size()) < maxNumber) {

				if (!allData.isEmpty() && startNumber == allData.size()) {
					try {
						// 點擊下一頁按鈕
						driver.findElement(By.cssSelector("button[id='paginationNext']")).click();

						// 等待新內容加載
						WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(250));
						wait.until(
								ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label='住宿欄位']")));
						// 將 allData 中的元素添加到 resp 列表
						resp.addAll(allData);

						// 清空 allData 列表
						allData.clear();
					} catch (Exception e) {
						break;
					}
				}

				startNumber = allData.size();

				List<WebElement> cards;
				int time = 0;
				while (true) {
					try {
						cards = driver.findElements(By.cssSelector("[aria-label='住宿欄位']"));

						if (cards.size() == 0) {
							time++;
							if (time == 20) {

								return null;
							}
							Thread.sleep(200);
							continue;
						}

						break;
					} catch (Exception e) {
						Thread.sleep(100);
						time++;
						System.out.println(e);
					}
				}
				for (int i = startNumber; i < cards.size(); i++) {
					try {
						cards = driver.findElements(By.cssSelector("[aria-label='住宿欄位']"));
						// 滑動卷軸到目標卡片
						WebElement card = cards.get(i);
						JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
						jsExecutor.executeScript("arguments[0].scrollIntoView();", card);
						String name = card.findElement(By.cssSelector("[data-selenium='hotel-name']")).getText();
						if (nameSet.contains(name)) {
							continue;
						}
						String priceDiv = card.findElement(By.cssSelector("div[data-element-name='final-price']"))
								.getText();
						String[] priceText = priceDiv.split("\n");
						String currency;
						int price;
						if (priceText.length > 1) {
							currency = priceText[0];
							price = Integer.parseInt(priceText[1].replace(",", ""));
						} else {
							currency = priceText[0].split(" ")[0];
							price = Integer.parseInt(priceText[0].split(" ")[1].replace(",", ""));
						}

						if ("NT$".equals(currency)) {
							currency = "TWD";
						}
						String img = card
								.findElement(By.cssSelector("button[data-element-name='ssrweb-mainphoto'] img"))
								.getAttribute("src");
						String url = card.findElement(By.cssSelector("a[data-element-name='property-card-content']"))
								.getAttribute("href");
						String opinion = card.findElement(By.cssSelector("[data-element-name='property-card-review']"))
								.getText().split("\n")[0];
						if (opinion.length() > 4) {
							Pattern r = Pattern.compile("\\d+\\.\\d+");
							Matcher m = r.matcher(opinion);
							if (m.find()) {
								opinion = m.group();
							}
						}
						nameSet.add(name);
						HotelList data = new HotelList();
						data.setWebName("agoda");
						data.setName(name);
						data.setCurrency(currency);
						data.setOneNightPrice(price);
						long day = ChronoUnit.DAYS.between(LocalDate.parse(reqDto.getCheckinDate()),
								LocalDate.parse(reqDto.getCheckoutDate()));
						data.setAllPrice(price * (int) day);
						data.setImg(img);
						data.setUrl(url);

						data.setOpinion(Double.valueOf(opinion));
						allData.add(data);

						if ((resp.size() + allData.size()) == maxNumber) {
							if (resp.addAll(allData)) {
								driver.quit();
								return new SelectHotelRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(),
										resp);
							}
							driver.quit();
							return new SelectHotelRes(ResMessage.NOT_FIND_HOTEL.getCode(),
									ResMessage.NOT_FIND_HOTEL.getMessage());

						}
					} catch (Exception e) {
						System.out.println(e);
						if (i == 0) {
							break;
						}
					}
					Thread.sleep(100);
				}
			}
			if (resp.addAll(allData)) {
				driver.quit();
				return new SelectHotelRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), resp);
			}
			driver.quit();
			return new SelectHotelRes(ResMessage.NOT_FIND_HOTEL.getCode(), ResMessage.NOT_FIND_HOTEL.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new SelectHotelRes(ResMessage.NOT_FIND_HOTEL.getCode(), ResMessage.NOT_FIND_HOTEL.getMessage());
		}
	}

	/**
	 * booking取得房間資訊
	 * 
	 * @param reqDto
	 * @return
	 */
	public List<HotelRoom> getBookingHotelRoom(GetHotelRoomReq reqDto) {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments(
				"--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
		options.addArguments("--disable-gpu");
		options.addArguments("--window-size=1920,1080");
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", false);

		driver = new ChromeDriver(options);
		driver.get(reqDto.getUrl());

		List<HotelRoom> allData = new ArrayList<>();
		List<WebElement> cards = null;

		List<WebElement> imgs = null;
		while (true) {
			try {
				new WebDriverWait(driver, Duration.ofSeconds(10))
						.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tr.js-rt-block-row")));
				cards = driver.findElements(By.cssSelector("tr.js-rt-block-row"));
				imgs = driver.findElements(By.className("hprt-roomtype-link"));
				break;
			} catch (Exception e) {
			}
		}
		Map<String, String> imgDict = new HashMap<>();

		for (WebElement img : imgs) {
			String imgLink = "";
			String roomName = "";
			try {
				img.click();
				while (true) {
					try {
						imgLink = driver.findElement(By.cssSelector("a.js-hotel-thumb img")).getAttribute("src")
								.replace("square60", "square600");
						break;
					} catch (Exception e) {

					}
				}

				while (true) {
					try {
						roomName = driver.findElements(By.cssSelector("h1#hp_rt_room_gallery_modal_room_name")).get(
								driver.findElements(By.cssSelector("h1#hp_rt_room_gallery_modal_room_name")).size() - 1)
								.getText();
						if (roomName.length() == 0) {
							continue;
						}
						break;
					} catch (Exception e) {

					}
				}
			} catch (Exception e) {

			} finally {
				if (!imgLink.isEmpty() && !roomName.isEmpty()) {
					imgDict.put(roomName, imgLink);
				}
			}

			try {
				WebElement imgClose = driver.findElement(By.xpath("//button[@class='modal-mask-closeBtn']"));
				imgClose.click();
			} catch (Exception e) {
				try {
					WebElement button = driver.findElement(
							By.cssSelector("button.a83ed08757.c21c56c305.f38b6daa18.d691166b09.ab98298258.f4552b6561"));
					new Actions(driver).moveToElement(button).click(button).perform();
				} catch (Exception ex) {

				}
			}
		}
		for (WebElement card : cards) {
			List<WebElement> roomTypeElements = card.findElements(By.cssSelector("span.hprt-roomtype-icon-link"));
			List<WebElement> bedTypeElements = card.findElements(By.cssSelector("li.art-bed-type spn"));

			if (bedTypeElements.isEmpty()) {
				bedTypeElements = card.findElements(By.cssSelector("li.bedroom_bed_type"));
			}
			if (bedTypeElements.isEmpty()) {
				bedTypeElements = card.findElements(By.cssSelector("li.rt-bed-type"));
			}

			List<WebElement> priceElements = card.findElements(By.cssSelector("span.prco-valign-middle-helper"));
			List<WebElement> highFloorElements = card.findElements(By.cssSelector("div.hprt-higher-floor"));
			List<WebElement> infantBedElements = card.findElements(By.cssSelector("span.hprt-roomtype-crib-label"));
			List<WebElement> notificationOneElements = card.findElements(By.cssSelector("div.bui-list__description"));
			List<WebElement> notificationTwoElements = card.findElements(
					By.cssSelector("span.bui-text.bui-text--variant-small_1.bui-text--color-constructive"));
			List<WebElement> maxMemberElements = card.findElements(By.cssSelector("span.bui-u-sr-only"));

			HotelRoom data = new HotelRoom();

			if (!roomTypeElements.isEmpty() && !bedTypeElements.isEmpty()) {
				data.setRoomType(roomTypeElements.get(0).getText());
				List<String> bedTypes = new ArrayList<>();
				for (WebElement bedTypeElement : bedTypeElements) {
					bedTypes.add(bedTypeElement.getText());
				}
				data.setBedType(bedTypes);
			} else if (!allData.isEmpty()) {
				HotelRoom lastData = allData.get(allData.size() - 1);
				data.setRoomType(lastData.getRoomType());
				data.setBedType(lastData.getBedType());
			} else {
				data.setRoomType(roomTypeElements.get(0).getText());
				List<String> bedTypes = new ArrayList<>();
				for (WebElement bedTypeElement : bedTypeElements) {
					bedTypes.add(bedTypeElement.getText());
				}
				data.setBedType(bedTypes);

			}

			if (imgDict.containsKey(data.getRoomType())) {
				data.setImg(imgDict.get(data.getRoomType()));
			} else {
				data.setImg("https://i.imgur.com/Y1GLBdW.png");
			}

			Pattern pattern = Pattern.compile("([A-Z]+)\\s([\\d,]+)");
			Matcher matcher = pattern.matcher(priceElements.get(0).getText());
			if (matcher.find()) {
				data.setCurrency(matcher.group(1));
				data.setPrice(Integer.parseInt(matcher.group(2).replace(",", "")));
			}
			data.setHightFloor(!highFloorElements.isEmpty());
			if (!infantBedElements.isEmpty()) {
				data.setInfantBed(infantBedElements.get(0).getText());
			}

			List<String> notificationList = new ArrayList<>();
			for (WebElement notificationElement : notificationOneElements) {
				notificationList.add(notificationElement.getText());
			}
			for (WebElement notificationElement : notificationTwoElements) {
				notificationList.add(notificationElement.getText());
			}

			String[] maxMemberText = maxMemberElements.get(0).getText().split("<br>");

			if (maxMemberText.length == 1 && maxMemberText[0].contains("成人")) {

				Matcher member = Pattern.compile("\\d+").matcher(maxMemberElements.get(0).getText());
				if (member.find()) {
					data.setMaxMemberAdults(Integer.parseInt(member.group()));
				} else {
					data.setMaxMemberAdults(0);
				}
				data.setMaxMemberChildren(0);
			}
			if (maxMemberText.length == 1 && maxMemberText[0].contains("兒童")) {
				Matcher member = Pattern.compile("\\d+").matcher(maxMemberElements.get(0).getText());
				if (member.find()) {
					data.setMaxMemberChildren(Integer.parseInt(member.group()));
				} else {
					data.setMaxMemberChildren(0);
				}
				data.setMaxMemberAdults(0);
			}
			if (maxMemberText.length > 1 && maxMemberText[0].contains("成人")) {

				Matcher member = Pattern.compile("\\d+").matcher(maxMemberElements.get(0).getText());
				if (member.find()) {
					data.setMaxMemberAdults(Integer.parseInt(member.group()));
				}
				if (member.find()) {
					data.setMaxMemberChildren(Integer.parseInt(member.group()));
				}
			}

			if (maxMemberText.length > 1 && maxMemberText[0].contains("兒童")) {
				Matcher member = Pattern.compile("\\d+").matcher(maxMemberElements.get(0).getText());
				if (member.find()) {
					data.setMaxMemberChildren(Integer.parseInt(member.group()));
				} else {
					data.setMaxMemberChildren(0);
				}
				if (member.find()) {
					data.setMaxMemberAdults(Integer.parseInt(member.group()));
				} else {
					data.setMaxMemberAdults(0);
				}
			}
			data.setNotificationList(notificationList);
			allData.add(data);
		}
		driver.quit();

		return allData;
	}

	/**
	 * Agoda取得房間資訊
	 * 
	 * @param reqDto
	 * @return
	 */
	public List<HotelRoom> getAgodaHotelRoom(GetHotelRoomReq reqDto) {
//		long startTime = System.currentTimeMillis();
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments(
				"--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
		options.addArguments("--disable-gpu");
		options.addArguments("--window-size=1920,1080");
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", false);

		driver = new ChromeDriver(options);
		driver.get(reqDto.getUrl());

		new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-selenium='MasterRoom']")));
		List<WebElement> cards = driver.findElements(By.cssSelector("[data-selenium='MasterRoom']"));

		List<HotelRoom> allData = new ArrayList<>();
		for (WebElement room : cards) {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("arguments[0].scrollIntoView();", room);
			String roomType = room.findElement(By.cssSelector("[data-selenium='masterroom-title-name']")).getText();
			String img = room.findElement(By.cssSelector("[data-selenium='MasterRoom-infoPhoto-image']"))
					.getAttribute("src").replace("208x117", "2560x1440");

			List<WebElement> bedTypeTemp = room.findElements(By.cssSelector("[data-testid='drone-box']"));
			String[] bedType = {};
			List<String> bedTypeList = new ArrayList<>();
			for (WebElement bed : bedTypeTemp) {
				if (bed.getText().contains("床")) {
					bedType = bed.getText().split("&");
					for (String item : bedType) {
						bedTypeList.add(item);
					}
					break;
				}
			}

			try {
				room.findElement(By.cssSelector("[data-selenium='MasterRoom-showMoreLessButton']")).click();
			} catch (Exception e) {

			}

			List<WebElement> roomDetails = room.findElements(By.cssSelector("[data-selenium='ChildRoomsList-room']"));
			detail: for (WebElement detail : roomDetails) {
				if (!detail.findElements(By.cssSelector("span.Capacity-iconGroup.Capacity-iconGroup--red")).isEmpty()) {
					continue;
				}
				int number = 0;
				try {
					List<WebElement> test = detail.findElements(By.cssSelector("[data-testid='text']"));
					for (WebElement element : test) {

						if (element.getText().contains("入住人數超過客房限制")) {
							continue detail;
						}
						if (element.getText().contains("位大人")) {
							number = Integer.valueOf(element.getText().substring(0, 1));
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				List<WebElement> peopleIcons = null;
				WebElement peopleIconsGroup;

				if (number == 0) {
					while (true) {
						try {
							peopleIconsGroup = detail.findElement(By.cssSelector("span.Capacity-iconGroup"));
							peopleIcons = peopleIconsGroup.findElements(By.cssSelector("[data-ppapi='occupancyIcon']"));
							number = peopleIcons.size();
							if (number == 0) {
								number = Integer.parseInt(detail
										.findElement(By.cssSelector("span.Capacity-iconGroup.Capacity-iconGroup--num"))
										.findElement(By.cssSelector("[data-ppapi='occupancy']")).getText());
							}
							break;
						} catch (Exception e) {

						}
					}
				}

				String currency = detail.findElement(By.cssSelector("[data-ppapi='room-price-currency']")).getText();
				String priceText = detail.findElement(By.cssSelector("[data-ppapi='room-price']")).getText()
						.replace(",", "");
				int price = Integer.parseInt(priceText);

				List<WebElement> notificationList = detail.findElements(By.cssSelector("[data-testid='text']"));
				List<String> notifications = new ArrayList<>();
				for (WebElement notification : notificationList) {
					if (notification.getText().contains("取消") || notification.getText().contains("扣款")
							|| notification.getText().contains("付款")) {
						notifications.add(notification.getText());
					}
				}

				HotelRoom data = new HotelRoom();
				data.setRoomType(roomType);
				data.setBedType(bedTypeList);
				data.setImg(img);
				data.setCurrency(currency);
				data.setPrice(price);
				data.setHightFloor(false);
				data.setMaxMemberAdults(number);
				data.setMaxMemberChildren(0);
				data.setNotificationList(notifications);

				allData.add(data);
			}
		}
		return allData;
	}

}
