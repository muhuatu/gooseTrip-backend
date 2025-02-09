package com.example.goosetrip.vo;

import java.util.List;

public class FindChatroomsRes extends BasicRes {

	private List<String> chatroomList;

	public FindChatroomsRes() {
		super();
	}

	public FindChatroomsRes(int code, String message) {
		super(code, message);

	}

	// need those parameters for findChatroomsRes
	public FindChatroomsRes(int code, String message, List<String> chatroomList) {
		super(code, message);
		this.chatroomList = chatroomList;
	}

	public List<String> getChatroomList() {
		return chatroomList;
	}

	public void setChatroomList(List<String> chatroomList) {
		this.chatroomList = chatroomList;
	}

}
