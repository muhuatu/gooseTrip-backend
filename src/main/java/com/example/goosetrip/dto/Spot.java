package com.example.goosetrip.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Spot {

	private int journeyId;

	private int spotId;

	private int day;

	private LocalDate date;

	private LocalTime arrivalTime;

	private LocalTime departureTime;

	private String spotName;

	private String placeId;

	private String note;

	// 新增欄位
	private String address;
	private double latitude;
	private double longitude;
	private String placeType;
	private String spotImage;

	public Spot() {
		super();
	}

	public Spot(int journeyId, int spotId, int day, LocalDate date, LocalTime arrivalTime, LocalTime departureTime,
			String spotName, String placeId, String note, String spotImage) {
		super();
		this.journeyId = journeyId;
		this.spotId = spotId;
		this.day = day;
		this.date = date;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.spotName = spotName;
		this.placeId = placeId;
		this.note = note;
		this.spotImage = spotImage;
	}

	public Spot(int journeyId, int spotId, int day, LocalDate date, LocalTime arrivalTime, LocalTime departureTime,
			String spotName, String placeId, String note, String address, double latitude, double longitude,
			String placeType, String spotImage) {
		super();
		this.journeyId = journeyId;
		this.spotId = spotId;
		this.day = day;
		this.date = date;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.spotName = spotName;
		this.placeId = placeId;
		this.note = note;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.placeType = placeType;
		this.spotImage = spotImage;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public String getSpotImage() {
		return spotImage;
	}

	public void setSpotImage(String spotImage) {
		this.spotImage = spotImage;
	}
}