package com.example.goosetrip.dto;

public class Interact {

	private int commentSN;

	private String userMail;

	private boolean thumb;

	private int postId;

	private String favoritePost;

	public Interact() {
		super();
	}

	public Interact(int commentSN, String userMail, boolean thumb, int postId, String favoritePost) {
		super();
		this.commentSN = commentSN;
		this.userMail = userMail;
		this.thumb = thumb;
		this.postId = postId;
		this.favoritePost = favoritePost;
	}

	public int getCommentSN() {
		return commentSN;
	}

	public void setCommentSN(int commentSN) {
		this.commentSN = commentSN;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public boolean isThumb() {
		return thumb;
	}

	public void setThumb(boolean thumb) {
		this.thumb = thumb;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getFavoritePost() {
		return favoritePost;
	}

	public void setFavoritePost(String favoritePost) {
		this.favoritePost = favoritePost;
	}

}
