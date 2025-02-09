package com.example.goosetrip.vo;

import java.time.LocalDateTime;

public class CommentList {

	private int serialNumber;

	private String userImage;

	private String userName;

	private String userMail;

	private int postId;

	private String commentContent;

	private LocalDateTime commentTime;

	private int commentId;

	private int replyCommentId;

	private int thumbUp;

	private int thumbDown;

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public LocalDateTime getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(LocalDateTime commentTime) {
		this.commentTime = commentTime;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getReplyCommentId() {
		return replyCommentId;
	}

	public void setReplyCommentId(int replyCommentId) {
		this.replyCommentId = replyCommentId;
	}

	public int getThumbUp() {
		return thumbUp;
	}

	public void setThumbUp(int thumbUp) {
		this.thumbUp = thumbUp;
	}

	public int getThumbDown() {
		return thumbDown;
	}

	public void setThumbDown(int thumbDown) {
		this.thumbDown = thumbDown;
	}

	public CommentList(int serialNumber, String userImage, String userName, int postId, String commentContent,
			LocalDateTime commentTime, int commentId, int replyCommentId, int thumbUp, int thumbDown) {
		super();
		this.serialNumber = serialNumber;
		this.userImage = userImage;
		this.userName = userName;
		this.postId = postId;
		this.commentContent = commentContent;
		this.commentTime = commentTime;
		this.commentId = commentId;
		this.replyCommentId = replyCommentId;
		this.thumbUp = thumbUp;
		this.thumbDown = thumbDown;
	}

	public CommentList() {
		super();
	}

	public CommentList(int serialNumber, String userImage, String userName, String userMail, int postId,
			String commentContent, LocalDateTime commentTime, int commentId, int replyCommentId, int thumbUp,
			int thumbDown) {
		super();
		this.serialNumber = serialNumber;
		this.userImage = userImage;
		this.userName = userName;
		this.userMail = userMail;
		this.postId = postId;
		this.commentContent = commentContent;
		this.commentTime = commentTime;
		this.commentId = commentId;
		this.replyCommentId = replyCommentId;
		this.thumbUp = thumbUp;
		this.thumbDown = thumbDown;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

}
