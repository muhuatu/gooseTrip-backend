package com.example.goosetrip.vo;

public class IsFavoriteRes extends BasicRes {
	private boolean isFavorite ;

	public IsFavoriteRes() {
		super();
	}

	public IsFavoriteRes(int code, String message) {
		super(code, message);
	}

	public IsFavoriteRes(int code, String message,boolean isFavorite) {
		super(code, message);
		this.isFavorite = isFavorite;
	}

	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
	
	
	

}
