package com.example.goosetrip.vo;

public class CreateJourneyRes extends BasicRes {
	private int journeyId;
	
	private String invitation;

	public CreateJourneyRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreateJourneyRes(int code, String message) {
		super(code, message);
		// TODO Auto-generated constructor stub
	}

	public CreateJourneyRes(int code, String message,int journeyId,String invitation) {
		super(code, message);
		this.journeyId = journeyId;
		this.invitation=invitation;
	}

	public int getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(int journeyId) {
		this.journeyId = journeyId;
	}

	public String getInvitation() {
		return invitation;
	}

	public void setInvitation(String invitation) {
		this.invitation = invitation;
	}

}
