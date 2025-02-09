package com.example.goosetrip.vo;

import java.util.List;

public class GetPostRes extends BasicRes{

    private List<PostList> postList;

    public GetPostRes() {
    }

    public GetPostRes(List<PostList> postList) {
        this.postList = postList;
    }

    public GetPostRes(int code, String message) {
        super(code, message);
    }

    public GetPostRes(int code, String message, List<PostList> postList) {
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
