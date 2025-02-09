package com.example.goosetrip.vo;

import java.util.List;

public class GetHotelRoomRes extends BasicRes{
	
	private List<HotelRoom> data;

	public List<HotelRoom> getData() {
		return data;
	}

	public void setData(List<HotelRoom> data) {
		this.data = data;
	}

	public GetHotelRoomRes(List<HotelRoom> data) {
		super();
		this.data = data;
	}

	public GetHotelRoomRes() {
		super();
	}

	public GetHotelRoomRes(int code, String message) {
		super(code, message);
	}
	
	public GetHotelRoomRes(int code, String message, List<HotelRoom> data) {
		super(code, message);
		this.data = data;
	}
	
}
