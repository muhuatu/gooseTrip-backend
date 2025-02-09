package com.example.goosetrip.dto;

import java.time.LocalDate;

public class Accommodation {

	private int accommodationId;

	private int journeyId;

	private LocalDate checkoutDate;

	private LocalDate checkinDate;

	private String hotelName;

	private String hotelWeb;

	private String roomType;

	private String bedType;

	private String price;

	private int number;
	
	private String url;
	
	private boolean finished;

	private String userMail;

	public int getAccommodationId() {
		return accommodationId;
	}

	public void setAccommodationId(int accommodationId) {
		this.accommodationId = accommodationId;
	}

	public int getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(int journeyId) {
		this.journeyId = journeyId;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public LocalDate getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(LocalDate checkinDate) {
		this.checkinDate = checkinDate;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelWeb() {
		return hotelWeb;
	}

	public void setHotelWeb(String hotelWeb) {
		this.hotelWeb = hotelWeb;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public Accommodation(int accommodationId, int journeyId, LocalDate checkoutDate, LocalDate checkinDate,
			String hotelName, String hotelWeb, String roomType, String bedType, String price, int number, String url,
			boolean finished, String userMail) {
		super();
		this.accommodationId = accommodationId;
		this.journeyId = journeyId;
		this.checkoutDate = checkoutDate;
		this.checkinDate = checkinDate;
		this.hotelName = hotelName;
		this.hotelWeb = hotelWeb;
		this.roomType = roomType;
		this.bedType = bedType;
		this.price = price;
		this.number = number;
		this.url = url;
		this.finished = finished;
		this.userMail = userMail;
	}

	public Accommodation() {
		super();
		// TODO Auto-generated constructor stub
	}

}
