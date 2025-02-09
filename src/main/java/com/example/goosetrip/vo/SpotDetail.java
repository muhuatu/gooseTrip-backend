package com.example.goosetrip.vo;

import java.time.LocalTime;

public class SpotDetail {

	private int spotId;

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

	public SpotDetail() {
		super();
	}

	public SpotDetail(int spotId, LocalTime arrivalTime, LocalTime departureTime, String spotName, String placeId,
			String note) {
		super();
		this.spotId = spotId;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.spotName = spotName;
		this.placeId = placeId;
		this.note = note;
	}

	public SpotDetail(int spotId, LocalTime arrivalTime, LocalTime departureTime, String spotName, String placeId,
			String note, String address, double latitude, double longitude, String placeType, String spotImage) {
		this.spotId = spotId;
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

	public int getSpotId() {
		return spotId;
	}

	public void setSpotId(int spotId) {
		this.spotId = spotId;
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
