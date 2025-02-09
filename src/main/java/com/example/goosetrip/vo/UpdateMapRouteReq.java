package com.example.goosetrip.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class UpdateMapRouteReq {

	// 起點位置
	private String startPlaceId;
	
	// 終點位置
	private String endPlaceId;
	
	// 交通方式
	private String transportation;
	
	// 出發時間 PK
	private LocalDateTime startTime;
	
	// 路程時間
	private LocalTime routeTime;
	
	// 總距離
	private String distance;
	
	// 線路(路徑編碼)
	private String routeLine;
	
	// 行程id PK
	private int journeyId;
	
	// 行程第幾天 PK
	private int day;
	
	public UpdateMapRouteReq() {
		super();
	}

	public UpdateMapRouteReq(String startPlaceId, String endPlaceId, String transportation, LocalDateTime startTime,
			LocalTime routeTime, String distance, String routeLine, int journeyId, int day) {
		super();
		this.startPlaceId = startPlaceId;
		this.endPlaceId = endPlaceId;
		this.transportation = transportation;
		this.startTime = startTime;
		this.routeTime = routeTime;
		this.distance = distance;
		this.routeLine = routeLine;
		this.journeyId = journeyId;
		this.day = day;
	}

	public String getStartPlaceId() {
		return startPlaceId;
	}

	public void setStartPlaceId(String startPlaceId) {
		this.startPlaceId = startPlaceId;
	}

	public String getEndPlaceId() {
		return endPlaceId;
	}

	public void setEndPlaceId(String endPlaceId) {
		this.endPlaceId = endPlaceId;
	}

	public String getTransportation() {
		return transportation;
	}

	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getRouteTime() {
		return routeTime;
	}

	public void setRouteTime(LocalTime routeTime) {
		this.routeTime = routeTime;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getRouteLine() {
		return routeLine;
	}

	public void setRouteLine(String routeLine) {
		this.routeLine = routeLine;
	}

	public int getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(int journeyId) {
		this.journeyId = journeyId;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	
	
}
