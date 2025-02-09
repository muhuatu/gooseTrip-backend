package com.example.goosetrip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.goosetrip.dto.Users;
import com.example.goosetrip.service.ChatroomMembersService;
import com.example.goosetrip.vo.FindChatroomsRes;

import jakarta.servlet.http.HttpSession;


@CrossOrigin
@RestController
@RequestMapping("chatroom_members/")
public class ChatroomMembersController {

	@Autowired
	private ChatroomMembersService chatroomMembersService;
	
	@PostMapping(value = "find_chatrooms")
	public FindChatroomsRes findChatrooms(@RequestBody HttpSession session) {
		Users user = (Users) session.getAttribute("user");
		return chatroomMembersService.findChatrooms(user);
	};
}
