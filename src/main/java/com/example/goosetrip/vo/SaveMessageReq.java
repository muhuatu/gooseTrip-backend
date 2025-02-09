package com.example.goosetrip.vo;

import java.time.LocalDateTime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public class SaveMessageReq {

//	@NotNull(message = "行程id不可為空")
	// 因為會透過邀請碼撈出行程 id，所以此處不用檢查
	private int journeyId;

	@NotBlank(message = "邀請碼不可為空")
	private String invitation;

	// 從session "user" 拿
//	private String userMail;
//	
//	private String userName;

	@NotBlank(message = "聊天類型不可為空")
	private String chatType;

	@NotBlank(message = "聊天內容不可為空")
	private String chatDetail;

	private byte[] chatImage;

	private LocalDateTime chatTime;

	private Integer reply;

	public SaveMessageReq() {
		super();
	}

	public SaveMessageReq(@NotBlank(message = "行程id不可為空") int journeyId,
			@NotBlank(message = "邀請碼不可為空") String invitation, //
			@NotBlank(message = "聊天類型不可為空") String chatType, //
			@NotBlank(message = "聊天內容不可為空") String chatDetail, //
			byte[] chatImage, LocalDateTime chatTime, Integer reply) {
		super();
		this.journeyId = journeyId;
		this.invitation = invitation;
		this.chatType = chatType;
		this.chatDetail = chatDetail;
		this.chatImage = chatImage;
		this.chatTime = chatTime;
		this.reply = reply;
	}

	public int getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(int journeyId) {
		this.journeyId = journeyId;
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

	public Integer getReply() {
		return reply;
	}

	public void setReply(Integer reply) {
		this.reply = reply;
	}

}
