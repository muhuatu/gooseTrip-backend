package com.example.goosetrip.vo;

public class UserleaveJourneyReq {
	private int journeyId;

	private String userMail;

	public UserleaveJourneyReq() {
		super();
	}

	public UserleaveJourneyReq(int journeyId, String userMail) {
		super();
		this.journeyId = journeyId;
		this.userMail = userMail;
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

}
