package com.example.goosetrip.vo;

public class PostDetail {

    private int day;        // 第幾天

    private int spotId;     // 景點Id

    private String spotNote; // 景點筆記

    private String spotImage; // 景點圖片的Base64編碼的字串
    
    private byte[] spotImgData; // 景點圖片轉換為byte[]格式進DB

    private String spotName; // 景點名稱

    private int duration; // 停留時間

    private String placeId;  // 地點編號

    public PostDetail() {
    }

    public PostDetail(int day, int spotId, String spotNote, String spotImage, int duration, String placeId) {
        this.day = day;
        this.spotId = spotId;
        this.spotNote = spotNote;
        this.spotImage = spotImage;
        this.duration = duration;
        this.placeId = placeId;
    }

    // 放前端過來的 request，這一包中的spotImage是Base64編碼的字串，要轉格式
    public PostDetail(int day, int spotId, String spotNote, String spotImage, String spotName, int duration, String placeId) {
        this.day = day;
        this.spotId = spotId;
        this.spotNote = spotNote;
        this.spotImage = spotImage;
        this.spotName = spotName;
        this.duration = duration;
        this.placeId = placeId;
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

    public String getSpotNote() {
        return spotNote;
    }

    public void setSpotNote(String spotNote) {
        this.spotNote = spotNote;
    }

    public String getSpotImage() {
        return spotImage;
    }

    public void setSpotImage(String spotImage) {
        this.spotImage = spotImage;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

	
    // 放轉成byte[]的景點圖片，這一包進資料庫
	public PostDetail(int day, int spotId, String spotNote, byte[] spotImgData, String spotName, int duration,
			String placeId) {
		super();
		this.day = day;
		this.spotId = spotId;
		this.spotNote = spotNote;
		this.spotImgData = spotImgData;
		this.spotName = spotName;
		this.duration = duration;
		this.placeId = placeId;
	}

	public byte[] getSpotImgData() {
		return spotImgData;
	}

	public void setSpotImgData(byte[] spotImgData) {
		this.spotImgData = spotImgData;
	}
    
	
    
    
}
