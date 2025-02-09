package com.example.goosetrip.vo;

import java.util.List;

public class RoomData {

	private Integer accommodationId;

	private String roomType;

	private List<String> bedType;

	private String roomCurrency;

	private int roomPrice;

	private int number;

	private boolean finished;

	public Integer getAccommodationId() {
		return accommodationId;
	}

	public void setAccommodationId(Integer accommodationId) {
		this.accommodationId = accommodationId;
	}

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

	public String getRoomCurrency() {
		return roomCurrency;
	}

	public void setRoomCurrency(String roomCurrency) {
		this.roomCurrency = roomCurrency;
	}

	public int getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(int roomPrice) {
		this.roomPrice = roomPrice;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public RoomData(String roomType, List<String> bedType, String roomCurrency, int roomPrice, int number,
			boolean finished) {
		super();
		this.roomType = roomType;
		this.bedType = bedType;
		this.roomCurrency = roomCurrency;
		this.roomPrice = roomPrice;
		this.number = number;
		this.finished = finished;
	}

	public RoomData(Integer accommodationId, String roomType, List<String> bedType, String roomCurrency, int roomPrice,
			int number, boolean finished) {
		super();
		this.accommodationId = accommodationId;
		this.roomType = roomType;
		this.bedType = bedType;
		this.roomCurrency = roomCurrency;
		this.roomPrice = roomPrice;
		this.number = number;
		this.finished = finished;
	}

	public RoomData() {
		super();
		// TODO Auto-generated constructor stub
	}

}
