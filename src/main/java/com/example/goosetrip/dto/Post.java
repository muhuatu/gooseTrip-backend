package com.example.goosetrip.dto;

import java.time.LocalDateTime;

public class Post {

    private int postId;              // 貼文 ID

    private int journeyId;           // 行程 ID

    private String userMail;         // 使用者 Email

    private String postContent;      // 貼文內容

    private LocalDateTime postTime;  // 貼文時間

    private boolean published;       // 是否發布

    private int day;              // 天數

    private int spotId;              // 景點 ID

    private String spotImage;        // 景點圖片

    private String spotNote;         // 景點備註

    private String placeId;          // 地點 ID

    public Post() {
    }

    public Post(int postId, int journeyId, String userMail, String postContent, LocalDateTime postTime, boolean published, int day, int spotId, String spotImage, String spotNote, String placeId) {
        this.postId = postId;
        this.journeyId = journeyId;
        this.userMail = userMail;
        this.postContent = postContent;
        this.postTime = postTime;
        this.published = published;
        this.day = day;
        this.spotId = spotId;
        this.spotImage = spotImage;
        this.spotNote = spotNote;
        this.placeId = placeId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(int journeyId) {
        this.journeyId = journeyId;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public LocalDateTime getPostTime() {
        return postTime;
    }

    public void setPostTime(LocalDateTime postTime) {
        this.postTime = postTime;
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

    public String getSpotNote() {
        return spotNote;
    }

    public void setSpotNote(String spotNote) {
        this.spotNote = spotNote;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
