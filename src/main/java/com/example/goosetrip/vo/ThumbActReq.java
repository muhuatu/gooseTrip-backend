package com.example.goosetrip.vo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Valid
public class ThumbActReq {

	// private String userMail; 從 session 拿

	@Min(value = 0, message = "評論流水號不可為負數")
	private int commentSN;

	private Boolean thumb;

	@Min(value = 0, message = "貼文Id不可為負數")
	private int postId;

	public ThumbActReq() {
		super();
	}

	public ThumbActReq(@NotNull(message = "評論流水號不可為Null") int commentSN, Boolean thumb,
			@NotNull(message = "貼文Id不可為Null") int postId) {
		super();
		this.commentSN = commentSN;
		this.thumb = thumb;
		this.postId = postId;
	}

	public int getCommentSN() {
		return commentSN;
	}

	public void setCommentSN(int commentSN) {
		this.commentSN = commentSN;
	}

	public Boolean getThumb() {
		return thumb;
	}

	public void setThumb(Boolean thumb) {
		this.thumb = thumb;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

}
