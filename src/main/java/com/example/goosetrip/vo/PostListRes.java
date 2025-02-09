package com.example.goosetrip.vo;

import java.util.List;

public class PostListRes extends BasicRes {

	private List<PostList> postList;

	public PostListRes() {
		super();
	}

	public PostListRes(int code, String message) {
		super(code, message);
	}

	public PostListRes(int code, String message, List<PostList> postList) {
		super(code, message);
		this.postList = postList;
	}

	public List<PostList> getPostList() {
		return postList;
	}

	public void setPostList(List<PostList> postList) {
		this.postList = postList;
	}

}
