package com.example.goosetrip.dto;

import java.time.LocalDateTime;

public class Comment {

	private int serialNumber;

	private String userMail;

	private int postId;

	private int commentId;

	private String commentContent;

	private int replyCommentId;

	private LocalDateTime commentTime;

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public int getReplyCommentId() {
		return replyCommentId;
	}

	public void setReplyCommentId(int replyCommentId) {
		this.replyCommentId = replyCommentId;
	}

	public LocalDateTime getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(LocalDateTime commentTime) {
		this.commentTime = commentTime;
	}

	public Comment(int serialNumber, String userMail, int postId, int commentId, String commentContent,
			int replyCommentId, LocalDateTime commentTime) {
		super();
		this.serialNumber = serialNumber;
		this.userMail = userMail;
		this.postId = postId;
		this.commentId = commentId;
		this.commentContent = commentContent;
		this.replyCommentId = replyCommentId;
		this.commentTime = commentTime;
	}

	public Comment() {
		super();
	}

}
