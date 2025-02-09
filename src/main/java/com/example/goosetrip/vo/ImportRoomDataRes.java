package com.example.goosetrip.vo;

public class ImportRoomDataRes extends BasicRes {

	private String hotelName;

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public ImportRoomDataRes(String hotelName) {
		super();
		this.hotelName = hotelName;
	}

	public ImportRoomDataRes() {
		super();
	}

	public ImportRoomDataRes(int code, String message) {
		super(code, message);
	}

	public ImportRoomDataRes(int code, String message, String hotelName) {
		super(code, message);
		this.hotelName = hotelName;
	}
}
