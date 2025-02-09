package com.example.goosetrip.vo;

import com.example.goosetrip.constants.MsgConstans;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PostReq {

    @Min(value = 0, message = MsgConstans.JOURNEY_ID_NOT_ZERO)
    private int journeyId;   // 行程Id

    @NotBlank(message = MsgConstans.POST_CONTENT_IS_BLANK)
    private String postContent; // 貼文內容

    @NotBlank(message = MsgConstans.POST_TIME_IS_BLANK)
    private String postTime; // 貼文時間

    @NotEmpty(message = MsgConstans.POST_DETAIL_IS_EMPTY)
    private List<PostDetail> postDetail; // 貼文詳細內容

    public PostReq() {
    }

    public PostReq(int journeyId, String postContent, String postTime, List<PostDetail> postDetail) {
        this.journeyId = journeyId;
        this.postContent = postContent;
        this.postTime = postTime;
        this.postDetail = postDetail;
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
}
