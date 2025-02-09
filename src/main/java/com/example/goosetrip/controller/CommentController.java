package com.example.goosetrip.controller;

import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.DeleteCommentReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dto.Users;
import com.example.goosetrip.service.CommentService;
import com.example.goosetrip.vo.ReplyPostReq;
import com.example.goosetrip.vo.ReplyPostRes;
import com.example.goosetrip.vo.UpdateCommentReq;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	/**
	 * 新增評論
	 * @param reqDto
	 * @param session
	 * @return
	 */
	@PostMapping("/replyPost")
	public ReplyPostRes replyPost(@Valid @RequestBody ReplyPostReq reqDto, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new ReplyPostRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(),
					ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return commentService.replyPost(reqDto,attr);
	}


	/**
	 * 刪除評論
	 * @param req
	 * @param session
	 * @return
	 */
	@PostMapping("/deleteComment")
	public BasicRes deleteComment(@Valid @RequestBody DeleteCommentReq req, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new ReplyPostRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(),
					ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return commentService.deleteComment(req, attr.getUserMail());
	}
	
	/**
	 * 更新評論
	 * @param req
	 * @param session
	 * @return
	 */
	@PostMapping("/update_comment")
	public BasicRes updateComment(@Valid @RequestBody UpdateCommentReq req, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new ReplyPostRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(),
					ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return commentService.updateComment(req, attr.getUserMail());	
	}

}
