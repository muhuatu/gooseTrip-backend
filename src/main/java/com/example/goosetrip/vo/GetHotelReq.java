package com.example.goosetrip.vo;

import com.example.goosetrip.constants.MsgConstans;

import jakarta.validation.constraints.NotBlank;

public class GetHotelReq {

	private String webName;

	@NotBlank(message = MsgConstans.AREA_IS_BLANK)
	private String area;

	@NotBlank(message = MsgConstans.CHECKINDATE_IS_BLANK)
	private String checkinDate;

	@NotBlank(message = MsgConstans.CHECKOUTDATE_IS_BLANK)
	private String checkoutDate;

	@NotBlank(message = MsgConstans.ADULTS_IS_BLANK)
	private String adults;

	@NotBlank(message = MsgConstans.ROOMS_IS_BLANK)
	private String rooms;

	@NotBlank(message = MsgConstans.CHILDREN_IS_BLANK)
	private String children;

	private Integer number;

	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(String checkinDate) {
		this.checkinDate = checkinDate;
	}

	public String getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public String getAdults() {
		return adults;
	}

	public void setAdults(String adults) {
		this.adults = adults;
	}

	public String getRooms() {
		return rooms;
	}

	public void setRooms(String rooms) {
		this.rooms = rooms;
	}

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public GetHotelReq(String webName, @NotBlank(message = "區域不得為空") String area,
			@NotBlank(message = "入住時間不得為空") String checkinDate, @NotBlank(message = "退宿時間不得為空") String checkoutDate,
			@NotBlank(message = "成人人數不得為空") String adults, @NotBlank(message = "房間數量不得為空") String rooms,
			@NotBlank(message = "兒童數量不得為空") String children, Integer number) {
		super();
		this.webName = webName;
		this.area = area;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
		this.adults = adults;
		this.rooms = rooms;
		this.children = children;
		this.number = number;
	}

	public GetHotelReq(String webName, @NotBlank(message = "區域不得為空") String area,
			@NotBlank(message = "入住時間不得為空") String checkinDate, @NotBlank(message = "退宿時間不得為空") String checkoutDate,
			@NotBlank(message = "成人人數不得為空") String adults, @NotBlank(message = "房間數量不得為空") String rooms,
			@NotBlank(message = "兒童數量不得為空") String children) {
		super();
		this.webName = webName;
		this.area = area;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
		this.adults = adults;
		this.rooms = rooms;
		this.children = children;
		
	}
	
	public GetHotelReq() {
		super();
	}
	
}
