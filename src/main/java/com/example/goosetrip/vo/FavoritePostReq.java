package com.example.goosetrip.vo;

import jakarta.validation.constraints.Min;

public class FavoritePostReq {

	@Min(value = 0, message = "貼文ID不能為負數")
	private int postId;

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}
}
