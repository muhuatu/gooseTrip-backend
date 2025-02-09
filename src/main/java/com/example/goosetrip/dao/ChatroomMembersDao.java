package com.example.goosetrip.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChatroomMembersDao {

	public List<String> findChatroomsByUserMail(String userMail);

	public void createJourney(@Param("journeyId") int journeyId, //
			@Param("invitation") String invitation, //
			@Param("userMail") String userMail);

	/**
	 * 用userMail查行程id
	 * 
	 * @param userMail
	 * @return
	 */
	public List<Integer> findJourneyIdByUserMail(@Param("userMail") String userMail);

}
