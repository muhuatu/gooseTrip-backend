package com.example.goosetrip.vo;

import java.util.List;

import com.example.goosetrip.dto.Journey;

public class SearchSpotByIdAndDayRes extends BasicRes {
	private List<SpotDetail> spotList;

	private Journey journey;

	public SearchSpotByIdAndDayRes() {
		super();
	}

	public SearchSpotByIdAndDayRes(int code, String message) {
		super(code, message);
	}

	public SearchSpotByIdAndDayRes(int code, String message, List<SpotDetail> spotList, Journey journey) {
		super(code, message);
		this.spotList = spotList;
		this.journey = journey;
	}

	public List<SpotDetail> getSpotList() {
		return spotList;
	}

	public void setSpotList(List<SpotDetail> spotList) {
		this.spotList = spotList;
	}

	public Journey getJourney() {
		return journey;
	}

	public void setJourney(Journey journey) {
		this.journey = journey;
	}

}
