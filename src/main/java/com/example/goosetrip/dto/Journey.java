package com.example.goosetrip.dto;

import java.time.LocalDate;

public class Journey {

	private int journeyId;

	private String invitation;

	private String journeyName;

	private LocalDate startDate;

	private LocalDate endDate;

	private String userMail;

	private String transportation;

	public Journey() {
		super();
	}

	public Journey(int journeyId, String invitation, String journeyName, LocalDate startDate, LocalDate endDate,
			String userMail, String transportation) {
		super();
		this.journeyId = journeyId;
		this.invitation = invitation;
		this.journeyName = journeyName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.userMail = userMail;
		this.transportation = transportation;
	}

	public int getJourneyId() {
		return journeyId;
	}

	public String getInvitation() {
		return invitation;
	}

	public String getJourneyName() {
		return journeyName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public String getUserMail() {
		return userMail;
	}

	public String getTransportation() {
		return transportation;
	}

}
