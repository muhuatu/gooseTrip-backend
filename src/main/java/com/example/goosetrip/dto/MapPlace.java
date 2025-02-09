package com.example.goosetrip.dto;

import java.math.BigDecimal;

public class MapPlace {

	// 地點id PK
	private String placeId;
	
	// 地點名稱
	private String placeName;
	
	// 地址
	private String address;
	
	// 地點經度
	private BigDecimal longitude;
	
	// 地點緯度
	private BigDecimal latitude;
	
	// 地點型態
	private String placeType;

	public MapPlace() {
		super();
	}

	public MapPlace(String placeId, String placeName, String address, BigDecimal longitude, BigDecimal latitude,
			String placeType) {
		super();
		this.placeId = placeId;
		this.placeName = placeName;
		this.address = address;
		this.longitude = longitude;
		this.latitude = latitude;
		this.placeType = placeType;
	}

	public String getPlaceId() {
		return placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public String getAddress() {
		return address;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}
	
	
}
