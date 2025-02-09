package com.example.goosetrip.dto;

public class ChatroomMembers {

	private int journeyId;

	private String invitation;

	private String userMail;

	public ChatroomMembers() {
		super();
	}

	public ChatroomMembers(int journeyId, String invitation, String userMail) {
		super();
		this.journeyId = journeyId;
		this.invitation = invitation;
		this.userMail = userMail;
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

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

}
