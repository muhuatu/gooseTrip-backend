package com.example.goosetrip.vo;

public class PostVo {

	private String journeyName;

	private String userName;

	private String userMail;

	private String userImage;

	private int postId;

	private String postContent;

	private String postTime;

	private boolean favorite;

	private int thumbUp;

	private int thumbDown;

	private boolean published; // 是否發布

	private int day; // 天數

	private int spotId; // 景點 ID

	private String spotImage; // 景點圖片

	private byte[] spotImgData;

	private String spotNote; // 景點備註

	private String spotName; // 景點名稱

	private String placeId; // 地點 ID

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

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
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

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getSpotId() {
		return spotId;
	}

	public void setSpotId(int spotId) {
		this.spotId = spotId;
	}

	public String getSpotImage() {
		return spotImage;
	}

	public void setSpotImage(String spotImage) {
		this.spotImage = spotImage;
	}

	public byte[] getSpotImgData() {
		return spotImgData;
	}

	public void setSpotImgData(byte[] spotImgData) {
		this.spotImgData = spotImgData;
	}

	public String getSpotNote() {
		return spotNote;
	}

	public void setSpotNote(String spotNote) {
		this.spotNote = spotNote;
	}

	public String getSpotName() {
		return spotName;
	}

	public void setSpotName(String spotName) {
		this.spotName = spotName;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public PostVo(String journeyName, String userName, String userMail, String userImage, int postId,
			String postContent, String postTime, boolean favorite, int thumbUp, int thumbDown, boolean published,
			int day, int spotId, String spotImage, byte[] spotImgData, String spotNote, String spotName,
			String placeId) {
		super();
		this.journeyName = journeyName;
		this.userName = userName;
		this.userMail = userMail;
		this.userImage = userImage;
		this.postId = postId;
		this.postContent = postContent;
		this.postTime = postTime;
		this.favorite = favorite;
		this.thumbUp = thumbUp;
		this.thumbDown = thumbDown;
		this.published = published;
		this.day = day;
		this.spotId = spotId;
		this.spotImage = spotImage;
		this.spotImgData = spotImgData;
		this.spotNote = spotNote;
		this.spotName = spotName;
		this.placeId = placeId;
	}

	public PostVo() {
		super();
	}

}
