package com.example.goosetrip.vo;

public class ChatList {

	private int chatId;

	private String userName;

	private String userMail;

	private String userImage;

	private String chatType;
	
	private String chatDetail;
	
	private String chatImage;

	private String chatTime; // 時間格式
	
	private Integer reply;

	public ChatList() {
		super();
	}

	public ChatList(int chatId, String userName, String userMail, String userImage, String chatType, String chatDetail,
			String chatImage, String chatTime, Integer reply) {
		super();
		this.chatId = chatId;
		this.userName = userName;
		this.userMail = userMail;
		this.userImage = userImage;
		this.chatType = chatType;
		this.chatDetail = chatDetail;
		this.chatImage = chatImage;
		this.chatTime = chatTime;
		this.reply = reply;
	}

	public int getChatId() {
		return chatId;
	}

	public void setChatId(int chatId) {
		this.chatId = chatId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
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

	public String getChatImage() {
		return chatImage;
	}

	public void setChatImage(String chatImage) {
		this.chatImage = chatImage;
	}

	public String getChatTime() {
		return chatTime;
	}

	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}

	public Integer getReply() {
		return reply;
	}

	public void setReply(Integer reply) {
		this.reply = reply;
	}

}
