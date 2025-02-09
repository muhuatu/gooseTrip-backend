package com.example.goosetrip.vo;

import com.example.goosetrip.constants.MsgConstans;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Valid
public class JourneyIdReq {

	@Min(value = 0, message = MsgConstans.JOURNEY_ID_NOT_ZERO)
	private int journeyId;

	public JourneyIdReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JourneyIdReq(@NotBlank int journeyId) {
		super();
		this.journeyId = journeyId;
	}

	public int getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(int journeyId) {
		this.journeyId = journeyId;
	}

}
