package com.example.goosetrip.vo;

import com.example.goosetrip.constants.MsgConstans;

import jakarta.validation.constraints.NotBlank;

public class GetHotelRoomReq {
	
	@NotBlank(message = MsgConstans.WEBNAME_IS_BLANK)
	private String webName;
	
	@NotBlank(message = MsgConstans.URL_IS_BLANK)
	private String url;

	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public GetHotelRoomReq(@NotBlank(message = "網站名稱不得為空") String webName, @NotBlank(message = "網站網址不得為空") String url) {
		super();
		this.webName = webName;
		this.url = url;
	}

	public GetHotelRoomReq() {
		super();
	}

}
