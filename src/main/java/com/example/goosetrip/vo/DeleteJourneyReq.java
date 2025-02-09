package com.example.goosetrip.vo;

import com.example.goosetrip.constants.MsgConstans;

import jakarta.validation.constraints.Min;

public class DeleteJourneyReq {
	@Min(value = 0, message = MsgConstans.JOURNEY_ID_NOT_ZERO)
	private int journeyId;

	public DeleteJourneyReq() {
	}

	public DeleteJourneyReq(int journeyId) {
		this.journeyId = journeyId;
	}

	public int getJourneyId() {
		return journeyId;
	}

}
