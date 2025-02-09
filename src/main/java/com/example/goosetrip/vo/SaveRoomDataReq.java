package com.example.goosetrip.vo;

import java.time.LocalDate;
import java.util.List;

import com.example.goosetrip.constants.MsgConstans;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SaveRoomDataReq {

	@Min(value = 0, message = MsgConstans.JOURNEY_ID_NOT_ZERO)
	private int journeyId;

	@NotBlank(message = MsgConstans.WEBNAME_IS_BLANK)
	private String webName;

	@NotNull(message = MsgConstans.CHECKINDATE_IS_NULL)
	private LocalDate checkinDate;

	@NotNull(message = MsgConstans.CHECKOUTDATE_IS_NULL)
	private LocalDate checkoutDate;

	@NotBlank(message = MsgConstans.HOTELNAME_IS_BLANK)
	private String hotelName;

	@NotEmpty(message = MsgConstans.ROOMDATE_IS_EMPTY)
	private List<RoomData> roomData;
	
	@NotBlank(message = MsgConstans.URL_IS_BLANK)
	private String url;

	public int getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(int journeyId) {
		this.journeyId = journeyId;
	}

	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	public LocalDate getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(LocalDate checkinDate) {
		this.checkinDate = checkinDate;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public List<RoomData> getRoomData() {
		return roomData;
	}

	public void setRoomData(List<RoomData> roomData) {
		this.roomData = roomData;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SaveRoomDataReq(@NotNull(message = "行程編號不得為空") int journeyId, @NotBlank(message = "網站名稱不得為空") String webName,
			@NotNull(message = "入住時間不得為空") LocalDate checkinDate, @NotNull(message = "退宿時間不得為空") LocalDate checkoutDate,
			@NotBlank(message = "飯店名稱不得為空") String hotelName, @NotEmpty(message = "房間資訊不得為空") List<RoomData> roomData,
			@NotBlank(message = "網站網址不得為空") String url) {
		super();
		this.journeyId = journeyId;
		this.webName = webName;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
		this.hotelName = hotelName;
		this.roomData = roomData;
		this.url = url;
	}

	public SaveRoomDataReq() {
		super();
	}
	
}
