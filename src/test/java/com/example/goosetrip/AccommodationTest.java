package com.example.goosetrip;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.goosetrip.service.AccommodationService;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.DeleteRoomDataReq;
import com.example.goosetrip.vo.GetHotelRoomReq;
import com.example.goosetrip.vo.HotelRoom;
import com.example.goosetrip.vo.RoomData;
import com.example.goosetrip.vo.SaveRoomDataReq;
import com.example.goosetrip.vo.UpdateRoomData;
import com.example.goosetrip.vo.UpdateRoomDataReq;

@SpringBootTest
//@Transactional
public class AccommodationTest {

	@Autowired
	private AccommodationService accommodationService;

//	@Test
//	public void fingBookingListTest() {
//		GetHotelReq reqDto = new GetHotelReq("booking", "台南中西區","2025-01-11", "2025-01-14", "4",
//				"1", "0");
//		long startTime = System.currentTimeMillis();
//		SelectHotelRes data = accommodationService.fingBookingList(reqDto);
//		System.out.println("執行時間: " + (System.currentTimeMillis() - startTime) + " 毫秒");
//		System.out.println(data.getCode());
//		System.out.println(data.getData().size());
////		System.out.println("執行時間: " + (System.currentTimeMillis() - startTime) + " 毫秒");
//
//	}
//
//	@Test
//	public void fingAgodaListTest() {
//		GetHotelReq reqDto = new GetHotelReq("agoda", "台北市","2025-01-20", "2025-01-23", "4",
//				"1", "0", 20);
//		long startTime = System.currentTimeMillis();
//		SelectHotelRes data = accommodationService.fingAgodaList(reqDto);
//		System.out.println("執行時間: " + (System.currentTimeMillis() - startTime) + " 毫秒");
//		System.out.println(data.getData().size());
//
//	}

	@Test
	public void getHotelRoomTest() {
		GetHotelRoomReq reqDto = new GetHotelRoomReq("booking",
				"https://www.booking.com/hotel/tw/en-vogue-taichung.zh-tw.html?label=zh-xt-tw-booking-desktop-EbyZWwQLwTW_IbH*0eHNCgS654267613595%3Apl%3Ata%3Ap1%3Ap2%3Aac%3Aap%3Aneg%3Afi%3Atikwd-65526620%3Alp1012818%3Ali%3Adec%3Adm&aid=2311236&ucfs=1&arphpl=1&checkin=2025-06-10&checkout=2025-06-11&dest_id=-2637824&dest_type=city&group_adults=2&req_adults=2&no_rooms=1&group_children=2&req_children=2&age=10&req_age=10&age=0&req_age=0&hpos=1&hapos=1&sr_order=popularity&srpvid=2769081c7f650130&srepoch=1734916157&all_sr_blocks=44572702_287965035_3_2_0&highlighted_blocks=44572702_287965035_3_2_0&matching_block_id=44572702_287965035_3_2_0&sr_pri_blocks=44572702_287965035_3_2_0__118800&from=searchresults");
		long startTime = System.currentTimeMillis();
		List<HotelRoom> res = accommodationService.getBookingHotelRoom(reqDto);
		System.out.println("執行時間: " + (System.currentTimeMillis() - startTime) + " 毫秒");
		System.out.println(res.size());

		startTime = System.currentTimeMillis();
		res = accommodationService.getHotelRoom(reqDto);
		System.out.println("執行時間: " + (System.currentTimeMillis() - startTime) + " 毫秒");
		System.out.println(res.size());
	}

	@Test
	public void selectHotelInAgodaTest() {
		long startTime = System.currentTimeMillis();
		GetHotelRoomReq reqDto = new GetHotelRoomReq("agoda",
				"https://www.agoda.com/zh-tw/caesar-park-hotel-banqiao/hotel/taipei-tw.html?finalPriceView=1&isShowMobileAppPrice=false&cid=1922895&numberOfBedrooms=&familyMode=false&adults=4&children=0&rooms=1&maxRooms=0&checkIn=2025-06-10&isCalendarCallout=false&childAges=&numberOfGuest=0&missingChildAges=false&travellerType=1&showReviewSubmissionEntry=false&currencyCode=TWD&isFreeOccSearch=false&tag=e9ea26c2-c046-468f-939d-97d11075d6e0&tspTypes=8,-1,-1&los=1&searchrequestid=aff609ef-58f2-4be9-9df2-7ce8bf9c432e");
		List<HotelRoom> res = accommodationService.getAgodaHotelRoom(reqDto);
		System.out.println("執行時間: " + (System.currentTimeMillis() - startTime) + " 毫秒");
		System.out.println(res.size());

		startTime = System.currentTimeMillis();
//		GetHotelRoomReq reqDto = new GetHotelRoomReq("agoda",
//				"https://www.agoda.com/zh-tw/caesar-park-hotel-banqiao/hotel/taipei-tw.html?finalPriceView=1&isShowMobileAppPrice=false&cid=1922895&numberOfBedrooms=&familyMode=false&adults=4&children=0&rooms=1&maxRooms=0&checkIn=2025-06-10&isCalendarCallout=false&childAges=&numberOfGuest=0&missingChildAges=false&travellerType=1&showReviewSubmissionEntry=false&currencyCode=TWD&isFreeOccSearch=false&tag=e9ea26c2-c046-468f-939d-97d11075d6e0&tspTypes=8,-1,-1&los=1&searchrequestid=aff609ef-58f2-4be9-9df2-7ce8bf9c432e");
		res = accommodationService.getHotelRoom(reqDto);
		System.out.println("執行時間: " + (System.currentTimeMillis() - startTime) + " 毫秒");
		System.out.println(res.size());
	}

	@Test
	public void getHotelRoomAgodaTest() {
		GetHotelRoomReq reqDto = new GetHotelRoomReq("agoda",
				"https://www.agoda.com/zh-tw/tainan-haian-art-apartment/hotel/tainan-tw.html?countryId=140&finalPriceView=1&isShowMobileAppPrice=false&cid=1922895&numberOfBedrooms=&familyMode=false&adults=4&children=0&rooms=1&maxRooms=0&checkIn=2025-06-10&isCalendarCallout=false&childAges=&numberOfGuest=0&missingChildAges=false&travellerType=1&showReviewSubmissionEntry=false&currencyCode=TWD&isFreeOccSearch=false&tag=e9ea26c2-c046-468f-939d-97d11075d6e0&tspTypes=9,-1&los=1&searchrequestid=fb676b42-99a9-43c9-ab91-d7bc3235d665");
		List<HotelRoom> res = accommodationService.getAgodaHotelRoom(reqDto);
		System.out.println(res.size());
	}

	@Test
	public void saveRoomDataTest() {
		List<RoomData> reqList = new ArrayList<>();
		reqList.add(new RoomData("經典四人房", List.of("2 張雙人床"), "TWD", 2559, 1, false));
		reqList.add(new RoomData("四人家庭房", List.of("2 張雙人床"), "TWD", 4802, 2, false));
		reqList.add(new RoomData("豪華家庭房", List.of("2 張加大雙人床"), "TWD", 3588, 3, false));
		SaveRoomDataReq reqDto = new SaveRoomDataReq(2, "booking", LocalDate.of(2025, 6, 10), LocalDate.of(2025, 6, 11),
				"雀客旅館新北蘆洲", reqList,
				"https://www.booking.com/hotel/tw/f-hoteltai-bei-lu-zhou-guan.zh-tw.html?aid=2311236&label=zh-xt-tw-booking-desktop-EbyZWwQLwTW_IbH%2A0eHNCgS654267613595%3Apl%3Ata%3Ap1%3Ap2%3Aac%3Aap%3Aneg%3Afi%3Atikwd-65526620%3Alp1012818%3Ali%3Adec%3Adm&sid=cc7c565b55af83aca55cdc1b28c37fe3&all_sr_blocks=513564504_204048932_0_2_0&checkin=2025-06-10&checkout=2025-06-11&dest_id=-2637882&dest_type=city&dist=0&group_adults=4&group_children=0&hapos=1&highlighted_blocks=513564504_204048932_0_2_0&hpos=1&matching_block_id=513564504_204048932_0_2_0&no_rooms=1&req_adults=4&req_children=0&room1=A%2CA%2CA%2CA&sb_price_type=total&sr_order=popularity&sr_pri_blocks=513564504_204048932_0_2_0__303078&srepoch=1736329715&srpvid=d2b944f720fb03fe&type=total&ucfs=1&");
		accommodationService.saveRoomData(reqDto, null);
	}

	@Test
	public void getRoomDataTest() {
		accommodationService.getRoomData(1, null);
	}

	@Test
	public void deleteRoomDataTest() {
		BasicRes result = accommodationService.deleteRoomData(new DeleteRoomDataReq(1, List.of(1, 2)), null);
		System.out.println(result.getCode());
	}

	@Test
	public void editAccommodationTest() {
		accommodationService.editAccommodation("xiaoyi1@example.com", 1);
	}

	@Test
	public void updateRoomDataTest() {
		List<UpdateRoomData> reqList = new ArrayList<>();
		reqList.add(new UpdateRoomData(82, false));
		reqList.add(new UpdateRoomData(83, false));
		UpdateRoomDataReq reqDto = new UpdateRoomDataReq(1, reqList);
		accommodationService.updateRoomData(reqDto,"xiaoyi1@example.com");
	}

}
