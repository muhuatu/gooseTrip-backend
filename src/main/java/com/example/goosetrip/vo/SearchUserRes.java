package com.example.goosetrip.vo;


import com.example.goosetrip.dto.Users;

import java.util.List;

public class SearchUserRes extends BasicRes{

    private List<Users> userList;

    public SearchUserRes() {
    }

    public SearchUserRes(int code, String message, List<Users> userList) {
        super(code, message);
        this.userList = userList;
    }

    public SearchUserRes(List<Users> userList) {
        this.userList = userList;
    }

    public List<Users> getUserList() {
        return userList;
    }

    public void setUserList(List<Users> userList) {
        this.userList = userList;
    }
}
