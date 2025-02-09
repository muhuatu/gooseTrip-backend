package com.example.goosetrip.vo;

import java.util.List;

public class PostSpecificRes extends BasicRes {

	private String journeyName;

	private String userName;
	
	private String userMail;

	private String userImage;

	private String postContent;

	private String postTime;

	private boolean favorite;

	private int thumbUp;

	private int thumbDown;

	private List<PostDetail> postDetail;

	private List<CommentList> commentList;

	private Boolean thumbStatus; // 使用者對於此貼文的按讚情況

	private boolean published; // 貼文狀態

	public PostSpecificRes() {
	}

	public PostSpecificRes(int code, String message) {
		super(code, message);
	}

	public PostSpecificRes(int code, String message, String journeyName, List<PostDetail> postDetail) {
		super(code, message);
		this.journeyName = journeyName;
		this.postDetail = postDetail;
	}

	public PostSpecificRes(int code, String message, String journeyName, String userName, String userMail, String userImage, String postContent,
			String postTime, boolean favorite, int thumbUp, int thumbDown, List<PostDetail> postDetail,
			List<CommentList> commentList, Boolean thumbStatus) {
		super(code, message);
		this.journeyName = journeyName;
		this.userName = userName;
		this.userMail = userMail;
		this.userImage = userImage;
		this.postContent = postContent;
		this.postTime = postTime;
		this.favorite = favorite;
		this.thumbUp = thumbUp;
		this.thumbDown = thumbDown;
		this.postDetail = postDetail;
		this.commentList = commentList;
		this.thumbStatus = thumbStatus;
	}

	
	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getJourneyName() {
		return journeyName;
	}

	public void setJourneyName(String journeyName) {
		this.journeyName = journeyName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public String getPostTime() {
		return postTime;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
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

	public List<PostDetail> getPostDetail() {
		return postDetail;
	}

	public void setPostDetail(List<PostDetail> postDetail) {
		this.postDetail = postDetail;
	}

	public List<CommentList> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<CommentList> commentList) {
		this.commentList = commentList;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public Boolean getThumbStatus() {
		return thumbStatus;
	}

	public void setThumbStatus(Boolean thumbStatus) {
		this.thumbStatus = thumbStatus;
	}

	public PostSpecificRes(String journeyName, String userName, String userImage, String postContent, String postTime,
			boolean favorite, int thumbUp, int thumbDown, List<PostDetail> postDetail, List<CommentList> commentList,
			Boolean thumbStatus, boolean published) {
		super();
		this.journeyName = journeyName;
		this.userName = userName;
		this.userImage = userImage;
		this.postContent = postContent;
		this.postTime = postTime;
		this.favorite = favorite;
		this.thumbUp = thumbUp;
		this.thumbDown = thumbDown;
		this.postDetail = postDetail;
		this.commentList = commentList;
		this.thumbStatus = thumbStatus;
		this.published = published;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

}
