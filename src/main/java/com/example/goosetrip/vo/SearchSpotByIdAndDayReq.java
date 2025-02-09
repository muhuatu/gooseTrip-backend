package com.example.goosetrip.vo;

public class SearchSpotByIdAndDayReq {
	private int journeyId;
	
	private int day;

	public SearchSpotByIdAndDayReq() {
		super();
	}

	public SearchSpotByIdAndDayReq(int journeyId, int day) {
		super();
		this.journeyId = journeyId;
		this.day = day;
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
