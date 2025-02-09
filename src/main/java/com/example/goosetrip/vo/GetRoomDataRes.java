package com.example.goosetrip.vo;

import java.util.List;

public class GetRoomDataRes extends BasicRes {

	private List data;

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public GetRoomDataRes() {
		super();
	}

	public GetRoomDataRes(int code, String message) {
		super(code, message);
	}

	public GetRoomDataRes(List data) {
		super();
		this.data = data;
	}

	public GetRoomDataRes(int code, String message, List data) {
		super(code, message);
		this.data = data;
	}
}
