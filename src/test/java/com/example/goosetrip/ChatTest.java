package com.example.goosetrip;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.goosetrip.service.ChatService;
import com.example.goosetrip.vo.ChatList;
import com.example.goosetrip.vo.GetChatRecordRes;

@SpringBootTest
public class ChatTest {

	@Autowired
	private ChatService chatService;

	@Test
	public void getChatRecordTest() {
		GetChatRecordRes res = chatService.getChatRecord("E5F6G7H8");
		for (ChatList item : res.getChatList()) {
			System.out.println(item.getChatType());
			System.out.println(item.getChatDetail());
			System.out.println("-------------------");
		}
	}
}
