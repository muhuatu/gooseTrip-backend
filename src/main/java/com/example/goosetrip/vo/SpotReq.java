package com.example.goosetrip.vo;

import java.time.LocalTime;

public class SpotReq {
  private int spotId;
  private String spotName;
  private String placeId;
  private String note;
  private LocalTime arrivalTime;
  private LocalTime departureTime;
  private String spotImage;

  public SpotReq() {
    super();
  }

  public SpotReq(String spotName, String placeId, String note, LocalTime arrivalTime, LocalTime departureTime,
      String spotImage) {
    super();
    this.spotName = spotName;
    this.placeId = placeId;
    this.note = note;
    this.arrivalTime = arrivalTime;
    this.departureTime = departureTime;
    this.spotImage = spotImage;
  }

  public int getSpotId() {
    return spotId;
  }

  public String getSpotName() {
    return spotName;
  }

  public String getPlaceId() {
    return placeId;
  }

  public String getNote() {
    return note;
  }

  public LocalTime getArrivalTime() {
    return arrivalTime;
  }

  public LocalTime getDepartureTime() {
    return departureTime;
  }

  public String getSpotImage() {
    return spotImage;
  }

}
