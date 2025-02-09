package com.example.goosetrip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dto.Users;
import com.example.goosetrip.service.ChatService;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.GetChatRecordRes;
import com.example.goosetrip.vo.SaveMessageReq;

import jakarta.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("chat/")
public class ChatController {

	@Autowired
	private ChatService chatService;

	@PostMapping(value = "chat_record")
	public GetChatRecordRes getChatRecord(@RequestBody String req) {
		return chatService.getChatRecord(req);
	}

	@PostMapping(value = "save_message")
	public BasicRes saveMessage(@RequestBody SaveMessageReq req, HttpSession session) {
		Users user = (Users) session.getAttribute("user");
		if (user == null) {
			return new BasicRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), //
					ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return chatService.saveMessage(req, user);
	};
}
