package com.example.goosetrip.vo;

public class PostList {

	private String userName;

	private String userMail;

	private String userImage;

	private int journeyId;

	private String journeyName;

	private String spotImage;
	
	private byte[] spotImgData;
	
	private boolean published; // 貼文狀態要撈出來給前端

	private int postId;

	private String postTime;

	private Boolean favorite;

	private int thumbUp;

	private int thumbDown;

	public PostList() {
		super();
	}

	// 這包放轉成Base64編碼的景點圖片字串給前端
	public PostList(String userName, String userMail, String userImage, int journeyId, String journeyName,
			String spotImage, int postId, String postTime, Boolean favorite, int thumbUp, int thumbDown) {
		super();
		this.userName = userName;
		this.userMail = userMail;
		this.userImage = userImage;
		this.journeyId = journeyId;
		this.journeyName = journeyName;
		this.spotImage = spotImage;
		this.postId = postId;
		this.postTime = postTime;
		this.favorite = favorite;
		this.thumbUp = thumbUp;
		this.thumbDown = thumbDown;
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

	public int getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(int journeyId) {
		this.journeyId = journeyId;
	}

	public String getJourneyName() {
		return journeyName;
	}

	public void setJourneyName(String journeyName) {
		this.journeyName = journeyName;
	}

	public String getSpotImage() {
		return spotImage;
	}

	public void setSpotImage(String spotImage) {
		this.spotImage = spotImage;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getPostTime() {
		return postTime;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public Boolean getFavorite() {
		return favorite;
	}

	public void setFavorite(Boolean favorite) {
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

	// 這包接從資料庫出來的blob 景點圖片
	public PostList(String userName, String userMail, String userImage, int journeyId, String journeyName,
			byte[] spotImgData, int postId, String postTime, Boolean favorite, int thumbUp, int thumbDown) {
		super();
		this.userName = userName;
		this.userMail = userMail;
		this.userImage = userImage;
		this.journeyId = journeyId;
		this.journeyName = journeyName;
		this.spotImgData = spotImgData;
		this.postId = postId;
		this.postTime = postTime;
		this.favorite = favorite;
		this.thumbUp = thumbUp;
		this.thumbDown = thumbDown;
	}

	public byte[] getSpotImgData() {
		return spotImgData;
	}

	public void setSpotImgData(byte[] spotImgData) {
		this.spotImgData = spotImgData;
	}

	// getPostByMail 新增把貼文發佈狀態撈出來
	public PostList(String userName, String userMail, String userImage, int journeyId, String journeyName,
			byte[] spotImgData, boolean published, int postId, String postTime, Boolean favorite, int thumbUp,
			int thumbDown) {
		super();
		this.userName = userName;
		this.userMail = userMail;
		this.userImage = userImage;
		this.journeyId = journeyId;
		this.journeyName = journeyName;
		this.spotImgData = spotImgData;
		this.published = published;
		this.postId = postId;
		this.postTime = postTime;
		this.favorite = favorite;
		this.thumbUp = thumbUp;
		this.thumbDown = thumbDown;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

}
