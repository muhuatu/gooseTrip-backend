package com.example.goosetrip.vo;

import java.util.List;

public class HotelRoom {

	private String roomType;

	private List<String> bedType;

	private String currency;

	private int price;

	private boolean hightFloor;

	private String infantBed;

	private List<String> notificationList;

	private int maxMemberAdults;
	
	private int maxMemberChildren;
	
	private String img;

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public List<String> getBedType() {
		return bedType;
	}

	public void setBedType(List<String> bedType) {
		this.bedType = bedType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isHightFloor() {
		return hightFloor;
	}

	public void setHightFloor(boolean hightFloor) {
		this.hightFloor = hightFloor;
	}

	public String getInfantBed() {
		return infantBed;
	}

	public void setInfantBed(String infantBed) {
		this.infantBed = infantBed;
	}

	public List<String> getNotificationList() {
		return notificationList;
	}

	public void setNotificationList(List<String> notificationList) {
		this.notificationList = notificationList;
	}

	public int getMaxMemberAdults() {
		return maxMemberAdults;
	}

	public void setMaxMemberAdults(int maxMemberAdults) {
		this.maxMemberAdults = maxMemberAdults;
	}

	public int getMaxMemberChildren() {
		return maxMemberChildren;
	}

	public void setMaxMemberChildren(int maxMemberChildren) {
		this.maxMemberChildren = maxMemberChildren;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public HotelRoom(String roomType, List<String> bedType, String currency, int price, boolean hightFloor,
			String infantBed, List<String> notificationList, int maxMemberAdults, int maxMemberChildren, String img) {
		super();
		this.roomType = roomType;
		this.bedType = bedType;
		this.currency = currency;
		this.price = price;
		this.hightFloor = hightFloor;
		this.infantBed = infantBed;
		this.notificationList = notificationList;
		this.maxMemberAdults = maxMemberAdults;
		this.maxMemberChildren = maxMemberChildren;
		this.img = img;
	}

	public HotelRoom() {
		super();
	}

}
