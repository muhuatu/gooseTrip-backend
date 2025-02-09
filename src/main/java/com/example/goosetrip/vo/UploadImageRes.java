package com.example.goosetrip.vo;

public class UploadImageRes extends BasicRes {

	private int imageId;

	public UploadImageRes() {
		super();
	}

	public UploadImageRes(int code, String message) {
		super(code, message);
	}

	public UploadImageRes(int code, String message, int imageId) {
		super(code, message);
		this.imageId = imageId;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

}
