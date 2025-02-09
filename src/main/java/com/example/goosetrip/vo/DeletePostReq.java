package com.example.goosetrip.vo;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Valid
public class DeletePostReq {

	// private String userMail;

	@NotEmpty(message = "貼文Id不可為空")
	private List<String> postIdList;

	public DeletePostReq() {
		super();
	}

	public DeletePostReq(@NotNull(message = "貼文Id不可為空") List<String> postIdList) {
		super();
		this.postIdList = postIdList;
	}

	public List<String> getPostIdList() {
		return postIdList;
	}

	public void setPostIdList(List<String> postIdList) {
		this.postIdList = postIdList;
	}

}
