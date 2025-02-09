package com.example.goosetrip.dto;

import java.time.LocalDateTime;

public class Chat {

	private int chatId;

	private String invitation;

	private String chatType;

	private String chatDetail;

	private byte[] chatImage;

	private LocalDateTime chatTime;

	private String userMail;

	private Integer reply;

	public Chat() {
		super();
	}

	public Chat(int chatId, String invitation, String chatType, String chatDetail, byte[] chatImage,
			LocalDateTime chatTime, String userMail, Integer reply) {
		super();
		this.chatId = chatId;
		this.invitation = invitation;
		this.chatType = chatType;
		this.chatDetail = chatDetail;
		this.chatImage = chatImage;
		this.chatTime = chatTime;
		this.userMail = userMail;
		this.reply = reply;
	}

	public int getChatId() {
		return chatId;
	}

	public void setChatId(int chatId) {
		this.chatId = chatId;
	}

	public String getInvitation() {
		return invitation;
	}

	public void setInvitation(String invitation) {
		this.invitation = invitation;
	}

	public String getChatType() {
		return chatType;
	}

	public void setChatType(String chatType) {
		this.chatType = chatType;
	}

	public String getChatDetail() {
		return chatDetail;
	}

	public void setChatDetail(String chatDetail) {
		this.chatDetail = chatDetail;
	}

	public byte[] getChatImage() {
		return chatImage;
	}

	public void setChatImage(byte[] chatImage) {
		this.chatImage = chatImage;
	}

	public LocalDateTime getChatTime() {
		return chatTime;
	}

	public void setChatTime(LocalDateTime chatTime) {
		this.chatTime = chatTime;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public Integer getReply() {
		return reply;
	}

	public void setReply(Integer reply) {
		this.reply = reply;
	}

}
