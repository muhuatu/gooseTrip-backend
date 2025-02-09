package com.example.goosetrip.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.goosetrip.vo.ChatList;
import com.example.goosetrip.vo.SaveMessageReq;

@Mapper
public interface ChatDao {

	public int checkChatroom(String invitation);
	
	public List<ChatList> getChatRecord(String invitation);
	
	public int getJourneyId(String invitation);
	
	public void saveMessage(SaveMessageReq req, String userMail);
}
