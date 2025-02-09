package com.example.goosetrip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dto.Users;
import com.example.goosetrip.service.InteractService;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.GetPostRes;
import com.example.goosetrip.vo.ThumbActReq;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/interact")
public class InteractController {

	@Autowired
	private InteractService interactService;

	/**
	 * 點讚行為
	 * @param req
	 * @param session
	 * @return
	 */
	@PostMapping(value = "thumb_act")
	public BasicRes thumbAct(@Valid @RequestBody ThumbActReq req, HttpSession session) {
		// 檢查 Session 中是否有此使用者
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new GetPostRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		// 代入 session 的 userMail
		return interactService.thumbAct(req, attr.getUserMail());
	}
}
