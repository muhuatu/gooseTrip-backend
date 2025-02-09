package com.example.goosetrip.vo;

import java.util.List;

public class DailyEditStatus {
	private int journeyId;
	private String journeyName;
	private int totalDays; // 總共有幾天
	private List<Integer> editingDays; // 哪幾天有人正在編輯

	public DailyEditStatus() {
		super();
	}

	public DailyEditStatus(int journeyId,String journeyName, int totalDays, List<Integer> editingDays) {
		super();
		this.journeyId = journeyId;
		this.journeyName=journeyName;
		this.totalDays = totalDays;
		this.editingDays = editingDays;
	}

	public int getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(int journeyId) {
		this.journeyId = journeyId;
	}

	public int getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}

	public List<Integer> getEditingDays() {
		return editingDays;
	}

	public void setEditingDays(List<Integer> editingDays) {
		this.editingDays = editingDays;
	}

	public String getJourneyName() {
		return journeyName;
	}

	public void setJourneyName(String journeyName) {
		this.journeyName = journeyName;
	}

}
