package com.example.goosetrip.vo;

import java.math.BigDecimal;

import jakarta.validation.Valid;

@Valid
public class UpdateMapPlaceReq {

	// 地點id 主鍵(PK)
	private String placeId;
	
	// 地點名稱
	private String placeName;
	
	// 地址
	private String address;
	
	// 經度
	private BigDecimal longitude;
	
	// 緯度
	private BigDecimal latitude;
	
	// 地點型態
	private String placeType;

	public UpdateMapPlaceReq() {
		super();
	}

	public UpdateMapPlaceReq(String placeId, String placeName, String address, BigDecimal longitude, BigDecimal latitude,
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

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}
	
	
}
