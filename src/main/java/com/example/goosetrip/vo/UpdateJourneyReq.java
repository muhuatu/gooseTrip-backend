package com.example.goosetrip.vo;

import jakarta.validation.constraints.Min;

public class UpdateJourneyReq extends BaseJourneyReq {

	// 行程 id
	@Min(value = 0, message = "行程 ID 不能為負數")
	private int journeyId;

}
