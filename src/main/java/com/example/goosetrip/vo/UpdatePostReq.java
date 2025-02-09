package com.example.goosetrip.vo;

import java.util.List;

import com.example.goosetrip.constants.MsgConstans;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UpdatePostReq {

	@Min(value = 0, message = "貼文ID不能為負數")
	private int postId;

	@Min(value = 0, message = "行程ID不能為負數")
	private int journeyId;

	@NotBlank(message = MsgConstans.POST_CONTENT_IS_BLANK)
	private String postContent;

	@NotBlank(message = MsgConstans.POST_TIME_IS_BLANK)
	private String postTime;

	@NotEmpty(message = MsgConstans.POST_DETAIL_IS_EMPTY)
	private List<PostDetail> postDetail;

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(int journeyId) {
		this.journeyId = journeyId;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public String getPostTime() {
		return postTime;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public List<PostDetail> getPostDetail() {
		return postDetail;
	}

	public void setPostDetail(List<PostDetail> postDetail) {
		this.postDetail = postDetail;
	}

	public UpdatePostReq(@NotNull(message = "貼文編號不得為空") int postId, @NotNull(message = "行程編號不得為空") int journeyId,
			@NotBlank(message = "貼文敘述不得為空") String postContent, @NotBlank(message = "貼文時間不得為空") String postTime,
			@NotEmpty(message = "景點內容不得為空") List<PostDetail> postDetail) {
		super();
		this.postId = postId;
		this.journeyId = journeyId;
		this.postContent = postContent;
		this.postTime = postTime;
		this.postDetail = postDetail;
	}

	public UpdatePostReq() {
		super();
	}

}
