package com.example.goosetrip.vo;

import java.util.List;

import com.example.goosetrip.constants.MsgConstans;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UpdateRoomDataReq {

	@Min(value = 0, message = MsgConstans.JOURNEY_ID_NOT_ZERO)
	private int journeyId;

	@NotEmpty(message = MsgConstans.ROOMDATE_IS_EMPTY)
	private List<UpdateRoomData> roomDate;

	public int getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(int journeyId) {
		this.journeyId = journeyId;
	}

	public List<UpdateRoomData> getRoomDate() {
		return roomDate;
	}

	public void setRoomDate(List<UpdateRoomData> roomDate) {
		this.roomDate = roomDate;
	}

	public UpdateRoomDataReq(@NotNull(message = "行程編號不得為空") int journeyId,
			@NotEmpty(message = "房間資訊不得為空") List<UpdateRoomData> roomDate) {
		super();
		this.journeyId = journeyId;
		this.roomDate = roomDate;
	}

	public UpdateRoomDataReq() {
		super();
		// TODO Auto-generated constructor stub
	}

}
