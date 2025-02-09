package com.example.goosetrip.vo;

import java.util.List;

public class GetChatRecordRes extends BasicRes {

	private List<ChatList> chatList;

	public GetChatRecordRes() {
		super();
	}

	public GetChatRecordRes(int code, String message) {
		super(code, message);
	}

	public GetChatRecordRes(int code, String message, List<ChatList> chatList) {
		super(code, message);
		this.chatList = chatList;
	}

	public List<ChatList> getChatList() {
		return chatList;
	}

	public void setChatList(List<ChatList> chatList) {
		this.chatList = chatList;
	}

}
