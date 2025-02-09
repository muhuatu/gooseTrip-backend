package com.example.goosetrip.vo;

import jakarta.validation.constraints.Min;

public class PostSpecificReq {

	@Min(value = 0, message = "行程ID不能為負數")
	private int journeyId;

	@Min(value = 0, message = "貼文ID不能為負數")
	private int postId;

	public PostSpecificReq() {
	}

	public PostSpecificReq(int journeyId, int postId) {
		this.journeyId = journeyId;
		this.postId = postId;
	}

	public Integer getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(Integer journeyId) {
		this.journeyId = journeyId;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}
}
