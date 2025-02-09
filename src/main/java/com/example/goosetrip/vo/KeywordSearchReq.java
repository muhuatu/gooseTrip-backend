package com.example.goosetrip.vo;

public class KeywordSearchReq {

    private String userName;

    private String journeyName;

    public KeywordSearchReq() {
    }

    public KeywordSearchReq(String userName, String journeyName) {
        this.userName = userName;
        this.journeyName = journeyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }
}
