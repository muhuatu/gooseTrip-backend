package com.example.goosetrip.vo;

import java.util.List;

import com.example.goosetrip.dto.Spot;

public class CheckJourneyRes extends BasicRes {
	private List<DailyEditStatus> editList; // 如果沒有在編輯中的話

	private boolean isEditing; // 有在編輯中，就回傳正在編輯中，再從前端找編輯中的資料

	public CheckJourneyRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CheckJourneyRes(int code, String message) {
		super(code, message);
		// TODO Auto-generated constructor stub
	}

	public CheckJourneyRes(int code, String message, List<DailyEditStatus> editList, boolean isEditing) {
		super(code, message);
		this.editList = editList;
		this.isEditing = isEditing;
	}

	public List<DailyEditStatus> getEditList() {
		return editList;
	}

	public void setEditList(List<DailyEditStatus> editList) {
		this.editList = editList;
	}

	public boolean isEditing() {
		return isEditing;
	}

	public void setEditing(boolean isEditing) {
		this.isEditing = isEditing;
	}

}
