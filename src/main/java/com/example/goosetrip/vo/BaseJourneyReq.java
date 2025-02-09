package com.example.goosetrip.vo;

import java.time.LocalDate;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BaseJourneyReq {

	// 行程名稱
	@Size(max = 16, message = "行程名稱不可超過16字")
	@NotBlank(message = "行程名稱不可為空")
	private String journeyName;

	// 開始日期
	@NotNull(message = "開始日期不可為空")
	private LocalDate startDate;

	// 結束日期
	@NotNull(message = "結束日期不可為空")
	private LocalDate endDate;

	// 交通方式
	@NotBlank(message = "交通方式不可為空")
	private String transportation;

	// 編輯者信箱
	private String userMail;

	// 聊天室邀請碼
	private String invitation;

	// 自增主鍵
	private Integer journeyId;

	@AssertTrue(message = "開始日期必須早於或等於結束日期")
	public boolean isDateRangeValid() {
		return endDate.isAfter(startDate) || endDate.isEqual(startDate);
	}

	// Getters and Setters
	public String getJourneyName() {
		return journeyName;
	}

	public void setJourneyName(String journeyName) {
		this.journeyName = journeyName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getTransportation() {
		return transportation;
	}

	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getInvitation() {
		return invitation;
	}

	public void setInvitation(String invitation) {
		this.invitation = invitation;
	}

	public Integer getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(Integer journeyId) {
		this.journeyId = journeyId;
	}
}
