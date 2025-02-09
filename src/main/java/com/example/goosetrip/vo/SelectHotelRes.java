package com.example.goosetrip.vo;

import java.util.List;

public class SelectHotelRes extends BasicRes{
	
	private List<HotelList> data;

	public List<HotelList> getData() {
		return data;
	}

	public void setData(List<HotelList> data) {
		this.data = data;
	}

	public SelectHotelRes() {
		super();
	}

	public SelectHotelRes(int code, String message) {
		super(code, message);
	}
	
	public SelectHotelRes(int code, String message, List<HotelList> data) {
		super(code, message);
		this.data = data;
	}
	
}
