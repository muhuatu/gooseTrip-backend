package com.example.goosetrip.vo;

import java.time.LocalDate;
import java.util.List;

public class SpotData {

	private int day;

	private LocalDate date;

	private List<SpotDetail> spotList;

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<SpotDetail> getSpotList() {
		return spotList;
	}

	public void setSpotList(List<SpotDetail> spotList) {
		this.spotList = spotList;
	}

	public SpotData(int day, LocalDate date, List<SpotDetail> spotList) {
		super();
		this.day = day;
		this.date = date;
		this.spotList = spotList;
	}

	public SpotData() {
		super();
	}

}
