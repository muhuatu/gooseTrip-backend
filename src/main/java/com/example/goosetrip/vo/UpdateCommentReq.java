package com.example.goosetrip.vo;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Valid
public class UpdateCommentReq {

	@Min(value = 0, message = "流水號不可小於0")
	@JsonAlias("serialNumber")
	private int sn;

	@NotBlank(message = "評論日期不可為空")
	private String commentDate;
	
	@NotBlank(message = "評論時間不可為空")
	private String commentTime;

	@NotBlank(message = "評論內容不可為空")
	private String commentContent;

	public UpdateCommentReq() {
		super();
	}

	public UpdateCommentReq(int sn, String commentTime, String commentContent) {
		super();
		this.sn = sn;
		this.commentTime = commentTime;
		this.commentContent = commentContent;
	}

	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public UpdateCommentReq(@Min(value = 0, message = "流水號不可小於0") int sn,
			@NotBlank(message = "評論日期不可為空") String commentDate, @NotBlank(message = "評論時間不可為空") String commentTime,
			@NotBlank(message = "評論內容不可為空") String commentContent) {
		super();
		this.sn = sn;
		this.commentDate = commentDate;
		this.commentTime = commentTime;
		this.commentContent = commentContent;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

}
