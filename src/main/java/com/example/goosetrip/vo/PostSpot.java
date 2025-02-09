package com.example.goosetrip.vo;

import java.time.LocalDate;
import java.time.LocalTime;

public class PostSpot {

    private int journeyId;

    private int spotId;

    private int day;

    private LocalDate date;

    private LocalTime arrivalTime;

    private LocalTime departureTime;

    private String spotName;

    private String placeId;

    private String spotImage; // 和Spot比多了圖片

    private String spotNote; // 和Spot比多了景點筆記

    public PostSpot() {
    }

    public PostSpot(int journeyId, int spotId, int day, LocalDate date, LocalTime arrivalTime, LocalTime departureTime, String spotName, String placeId, String spotImage, String spotNote) {
        this.journeyId = journeyId;
        this.spotId = spotId;
        this.day = day;
        this.date = date;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.spotName = spotName;
        this.placeId = placeId;
        this.spotImage = spotImage;
        this.spotNote = spotNote;
    }

    public String getSpotNote() {
        return spotNote;
    }

    public void setSpotNote(String spotNote) {
        this.spotNote = spotNote;
    }

    public int getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(int journeyId) {
        this.journeyId = journeyId;
    }

    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
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

    public String getSpotImage() {
        return spotImage;
    }

    public void setSpotImage(String spotImage) {
        this.spotImage = spotImage;
    }
}
