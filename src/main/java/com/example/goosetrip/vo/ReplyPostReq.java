package com.example.goosetrip.vo;

import java.time.LocalDateTime;

import com.example.goosetrip.constants.MsgConstans;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public class ReplyPostReq {

	@Min(value = 0, message = MsgConstans.POSTID_IS_NULL)
	private int postId;

	@Min(value = -1, message = MsgConstans.REPLY_COMMENT_ID_IS_NEGATIVE)
	private int replyCommentId;

	@NotBlank(message = MsgConstans.COMMENT_CONTENT_IS_BLANK)
	private String commentContent;

	@NotNull(message = MsgConstans.COMMENT_TIME_IS_NULL)
	private LocalDateTime commentTime;

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getReplyCommentId() {
		return replyCommentId;
	}

	public void setReplyCommentId(int replyCommentId) {
		this.replyCommentId = replyCommentId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public LocalDateTime getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(LocalDateTime commentTime) {
		this.commentTime = commentTime;
	}

	public ReplyPostReq(int postId, int replyCommentId, String commentContent, LocalDateTime commentTime) {
		super();
		this.postId = postId;
		this.replyCommentId = replyCommentId;
		this.commentContent = commentContent;
		this.commentTime = commentTime;
	}

	public ReplyPostReq() {
		super();
	}

}
