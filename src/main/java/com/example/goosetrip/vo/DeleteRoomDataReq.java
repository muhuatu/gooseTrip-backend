package com.example.goosetrip.vo;

import java.util.List;

import com.example.goosetrip.constants.MsgConstans;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class DeleteRoomDataReq {

	@Min(value = 0, message = MsgConstans.JOURNEY_ID_NOT_ZERO)
	private int journeyId;

	@NotEmpty(message = MsgConstans.ACCOMMODATION_ID_IS_EMPTY)
	private List<Integer> accommodationId;

	public int getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(int journeyId) {
		this.journeyId = journeyId;
	}

	public List<Integer> getAccommodationId() {
		return accommodationId;
	}

	public void setAccommodationId(List<Integer> accommodationId) {
		this.accommodationId = accommodationId;
	}

	public DeleteRoomDataReq(int journeyId, List<Integer> accommodationId) {
		super();
		this.journeyId = journeyId;
		this.accommodationId = accommodationId;
	}

	public DeleteRoomDataReq() {
		super();
	}

}
