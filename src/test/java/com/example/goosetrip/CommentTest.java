package com.example.goosetrip;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.goosetrip.dto.Users;
import com.example.goosetrip.service.CommentService;
import com.example.goosetrip.vo.CommentList;
import com.example.goosetrip.vo.ReplyPostReq;
import com.example.goosetrip.vo.UpdateCommentReq;

@SpringBootTest
@Transactional
public class CommentTest {

	@Autowired
	private CommentService commentService;

	@Test
	public void replyPostTest() {
		Users users = new Users("123@qq.com", null, null, null, null);
		commentService.replyPost(new ReplyPostReq(1, 10, "reply0", LocalDateTime.now()), users);
	}

	@Test
	public void getAllReplyTest() {
		List<CommentList> result = commentService.getAllReply(1);
		for (CommentList resp : result) {

			System.out.println(resp.getSerialNumber());
			System.out.println(resp.getCommentContent());
			System.out.println(resp.getCommentId());
			System.out.println(resp.getPostId());
			System.out.println(resp.getReplyCommentId());
			System.out.println(resp.getThumbDown());
			System.out.println(resp.getThumbUp());
			System.out.println(resp.getUserImage());
			System.out.println(resp.getUserName());
			System.out.println(resp.getCommentTime());
		}
	}
	
	@Test
	public void updateComment() {
		Users user = new Users("r456@mail.com", "r456", null, null, null, null, null, null, null);
		commentService.updateComment(new UpdateCommentReq(15, "2025-01-16 10:19:00", "123"), user.getUserMail());
	}
	
}
