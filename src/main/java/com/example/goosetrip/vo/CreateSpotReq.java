package com.example.goosetrip.vo;

import java.time.LocalDate;
import java.util.List;

public class CreateSpotReq {
  private int journeyId;

  private int day;

  private LocalDate date;

  private List<SpotReq> spotList;

  public CreateSpotReq() {
    super();
  }

  public CreateSpotReq(int journeyId, int day, LocalDate date, List<SpotReq> spotList) {
    super();
    this.journeyId = journeyId;
    this.day = day;
    this.date = date;
    this.spotList = spotList;
  }

  public int getJourneyId() {
    return journeyId;
  }

  public int getDay() {
    return day;
  }

  public LocalDate getDate() {
    return date;
  }

  public List<SpotReq> getSpotList() {
    return spotList;
  }
}
