package com.example.goosetrip.vo;

import com.example.goosetrip.constants.MsgConstans;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ImportRoomDataReq {

	@Min(value = 0, message = MsgConstans.JOURNEY_ID_NOT_ZERO)
	private int journeyId;

	@NotBlank(message = MsgConstans.DATE_IS_BLANK)
	private String date;

	public int getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(int journeyId) {
		this.journeyId = journeyId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ImportRoomDataReq(@Min(value = 0, message = "住宿編號不能為負數") int journeyId,
			@NotNull(message = "日期不得為空") String date) {
		super();
		this.journeyId = journeyId;
		this.date = date;
	}

	public ImportRoomDataReq() {
		super();
		// TODO Auto-generated constructor stub
	}

}
