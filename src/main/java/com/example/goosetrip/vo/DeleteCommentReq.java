package com.example.goosetrip.vo;

import com.example.goosetrip.constants.MsgConstans;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Valid
public class DeleteCommentReq {

    @Min(value = 1, message = MsgConstans.POSTID_IS_NULL)
    private int postId;

    @Min(value = 1, message = MsgConstans.COMMENT_ID_IS_NULL)
    private int commentId;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
}
