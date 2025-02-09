package com.example.goosetrip.dto;

public class Image {

	private int imageId; // 圖片ID

	private byte[] image; // 景點圖片

	public Image() {
		super();
	}

	public Image(int imageId, byte[] image) {
		super();
		this.imageId = imageId;
		this.image = image;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
