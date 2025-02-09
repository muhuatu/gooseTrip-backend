package com.example.goosetrip.vo;

import java.util.List;

public class SearchFavoriteRes extends BasicRes {
	private List<String> userFavorite;

	public SearchFavoriteRes() {
		super();
	}

	public SearchFavoriteRes(int code, String message) {
		super(code, message);
	}

	public SearchFavoriteRes(int code, String message, List<String> userFavorite) {
		super(code, message);
		this.userFavorite = userFavorite;
	}

	public List<String> getUserFavorite() {
		return userFavorite;
	}

	public void setUserFavorite(List<String> userFavorite) {
		this.userFavorite = userFavorite;
	}

}
