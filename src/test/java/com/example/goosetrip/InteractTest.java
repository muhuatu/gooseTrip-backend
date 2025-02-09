package com.example.goosetrip;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.goosetrip.dto.Users;
import com.example.goosetrip.service.InteractService;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.FindThumb;
import com.example.goosetrip.vo.ThumbActReq;

@SpringBootTest
@Transactional
public class InteractTest {

	@Autowired
	private InteractService interactService;

	@Test
	public void thumbActTest() {
		Users user = new Users("xiaoyi3@example.com", null, null, null, null, null, null, null, null);
		ThumbActReq req = new ThumbActReq(3, null, 2);
		System.out.println(req.getCommentSN());
		System.out.println(req.getPostId());
		System.out.println(req.getThumb());
		BasicRes res = interactService.thumbAct(req, user.getUserMail());
	};

	public void findThumbUpTest() {
		List<FindThumb> result = interactService.findThumbUp(1);
		System.out.println(result.get(0).getCommentSn());
		System.out.println(result.get(0).getThumbCount());

	}

	@Test
	public void findThumbDownTest() {
		List<FindThumb> result = interactService.findThumbDown(1);
		System.out.println(result.isEmpty());
//		System.out.println(result.get(0).getCommentSn());
//		System.out.println(result.get(0).getThumbCount());

	}
}
