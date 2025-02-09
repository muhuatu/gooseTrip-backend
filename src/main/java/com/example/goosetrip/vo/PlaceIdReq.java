package com.example.goosetrip.vo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Valid
public class PlaceIdReq {
	@NotBlank(message = "地點編號不得為空")
	private String placeId;

	public PlaceIdReq() {
		super();
	}

	public PlaceIdReq(@NotBlank String placeId) {
		super();
		this.placeId = placeId;
	}

	public String getPlaceId() {
		return placeId;
	}

}
