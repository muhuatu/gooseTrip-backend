package com.example.goosetrip.vo;

import jakarta.validation.constraints.NotBlank;

public class JoinJourneyReq {
	
	@NotBlank(message = "邀請碼不可為空")
	private String invitation;

	public JoinJourneyReq(String invitation) {
		super();
		this.invitation = invitation;
	}

	public JoinJourneyReq() {
		super();
	}

	public String getInvitation() {
		return invitation;
	}
	
	
}
