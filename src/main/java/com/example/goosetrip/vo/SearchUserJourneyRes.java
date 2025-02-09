package com.example.goosetrip.vo;

import java.util.List;

import com.example.goosetrip.dto.Journey;

public class SearchUserJourneyRes extends BasicRes{
	
	private List<Journey>JourneyList;


	public SearchUserJourneyRes() {
		super();
	}

	public SearchUserJourneyRes(int code, String message,List<Journey> journeyList) {
		super(code, message);
		JourneyList = journeyList;
	}
	public SearchUserJourneyRes(int code, String message) {
		super(code, message);
	}

	public List<Journey> getJourneyList() {
		return JourneyList;
	}
	

	
	
	
	
}
