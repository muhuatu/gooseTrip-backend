package com.example.goosetrip.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.goosetrip.dto.Journey;
import com.example.goosetrip.vo.BaseJourneyReq;
import com.example.goosetrip.vo.UpdateJourneyReq;

@Mapper
public interface JourneyDao {

	public List<Journey> searchJourneyByInvitationList(@Param(value = "invitationList") List<String> invitationList);

	public int createJourney(BaseJourneyReq req);

	public void updateJourney(UpdateJourneyReq req);

	public void deleteJourney(int journeyId);

	public List<String> searchInvitationByUserMail(String userMail);

	public Integer searchJourneyId(String invitation);

	public void joinJourneyId(int journeyId, String invitation, String UserMail);

	/**
	 * 用行程id搜尋行程資料
	 * 
	 * @param journeyId
	 * @return
	 */
	public Journey findJourneyById(int journeyId);
}
