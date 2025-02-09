package com.example.goosetrip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.goosetrip.constants.ChatType;
import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dao.ChatDao;
import com.example.goosetrip.dto.Users;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.GetChatRecordRes;
import com.example.goosetrip.vo.SaveMessageReq;

@Service
public class ChatService {

	@Autowired
	private ChatDao chatDao;
	
	public GetChatRecordRes getChatRecord(String invitation) {
		// 如果傳入的參數是null或空白
		if(invitation.isBlank()) {
			return new GetChatRecordRes(ResMessage.PARAMETER_ERROR.getCode(), //
					ResMessage.PARAMETER_ERROR.getMessage());
		}
		// 檢查該聊天室是否存在
		if (chatDao.checkChatroom(invitation) < 1) {
			return new GetChatRecordRes(ResMessage.CHATROOM_NOT_EXISTED.getCode(), //
					ResMessage.CHATROOM_NOT_EXISTED.getMessage());
		}
		// 操作資料庫拿出對話紀錄
		return new GetChatRecordRes(ResMessage.SUCCESS.getCode(), //
					ResMessage.SUCCESS.getMessage(), //
					chatDao.getChatRecord(invitation));
		
	};
	
	/**
	 * 儲存訊息到DB
	 * @param req
	 * @param user
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public BasicRes saveMessage(SaveMessageReq req, Users user) {
		// 1. 檢查聊天類型是否符合
		if(!(ChatType.checkType(req.getChatType()))) {
			return new BasicRes(ResMessage.CHAT_TYPE_ERROR.getCode(), //
					ResMessage.CHAT_TYPE_ERROR.getMessage());
		}
		// 2. 檢查該聊天室是否存在
		if (chatDao.checkChatroom(req.getInvitation()) < 1) {
			return new GetChatRecordRes(ResMessage.CHATROOM_NOT_EXISTED.getCode(), //
					ResMessage.CHATROOM_NOT_EXISTED.getMessage());
		}
		// 3. 操作資料庫
		// 3.1 獲取行程 id
		int journeyId = chatDao.getJourneyId(req.getInvitation());
		// 3.2 把行程 id 塞回 req
		req.setJourneyId(journeyId);
		// 把聊天訊息儲存到DB
		chatDao.saveMessage(req, user.getUserMail());
		return new BasicRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage());
	};
}
