package com.example.goosetrip.vo;

public class FindThumb {

	private int commentSn;
	
	private int thumbCount;

	public int getCommentSn() {
		return commentSn;
	}

	public void setCommentSn(int commentSn) {
		this.commentSn = commentSn;
	}

	public int getThumbCount() {
		return thumbCount;
	}

	public void setThumbCount(int thumbCount) {
		this.thumbCount = thumbCount;
	}

	public FindThumb() {
		super();
	}

	public FindThumb(int commentSn, int thumbCount) {
		super();
		this.commentSn = commentSn;
		this.thumbCount = thumbCount;
	}
	
}
