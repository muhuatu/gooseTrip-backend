package com.example.goosetrip.vo;

public class ReplyPostRes extends BasicRes {

	private int commentId;

	private int serialNumber;

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public ReplyPostRes(int commentId, int serialNumber) {
		super();
		this.commentId = commentId;
		this.serialNumber = serialNumber;
	}

	public ReplyPostRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReplyPostRes(int code, String message) {
		super(code, message);
	}

	public ReplyPostRes(int code, String message, int commentId, int serialNumber) {
		super(code, message);
		this.commentId = commentId;
		this.serialNumber = serialNumber;
	}

}
