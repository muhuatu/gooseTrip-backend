package com.example.goosetrip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dao.CommentDao;
import com.example.goosetrip.dto.Comment;
import com.example.goosetrip.dto.Users;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.CommentList;
import com.example.goosetrip.vo.DeleteCommentReq;
import com.example.goosetrip.vo.FindThumb;
import com.example.goosetrip.vo.ReplyPostReq;
import com.example.goosetrip.vo.ReplyPostRes;
import com.example.goosetrip.vo.UpdateCommentReq;

@Service
public class CommentService {

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private InteractService interactService;

	/**
	 * 新增評論
	 *
	 * @param reqDto
	 * @param users
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public ReplyPostRes replyPost(ReplyPostReq reqDto, Users users) {
		Comment vo = new Comment();
		Integer maxCommentId = commentDao.findMaxCommentId(reqDto.getPostId());
		if (maxCommentId == null) {
			vo.setCommentId(1);
		} else {
			vo.setCommentId(maxCommentId + 1);
		}
		if (reqDto.getReplyCommentId() != 0) {
			// 檢查回復評論id是否存在
			List<Integer> commentId = commentDao.findCommentId(reqDto.getReplyCommentId());
			if (commentId.isEmpty()) {
				return new ReplyPostRes(ResMessage.REPLY_COMMENT_NOT_EXIST.getCode(),
						ResMessage.REPLY_COMMENT_NOT_EXIST.getMessage());
			}
		}

		vo.setUserMail(users.getUserMail());
		vo.setPostId(reqDto.getPostId());
		vo.setCommentContent(reqDto.getCommentContent());
		vo.setReplyCommentId(reqDto.getReplyCommentId());
		vo.setCommentTime(reqDto.getCommentTime());

		int result = commentDao.saveComment(vo);
		if (result != 1) {
			return new ReplyPostRes(ResMessage.COMMENT_INSERT_FAILED.getCode(),
					ResMessage.COMMENT_INSERT_FAILED.getMessage());
		}
		if (maxCommentId == null) {
			return new ReplyPostRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), 1,
					vo.getSerialNumber());
		}
		return new ReplyPostRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), maxCommentId + 1,
				vo.getSerialNumber());
	}

	/**
	 * 取得貼文全部的評論
	 *
	 * @param postId
	 */
	public List<CommentList> getAllReply(int postId) {
		if (postId == 0) {
			return null;
		}
		List<CommentList> result = commentDao.getAllReply(postId);

		List<FindThumb> thumbUp = interactService.findThumbUp(postId);
		List<FindThumb> thumbDown = interactService.findThumbDown(postId);

		for (CommentList vo : result) {
			if (!thumbUp.isEmpty()) {
				for (FindThumb up : thumbUp) {
					if (up.getCommentSn() == vo.getSerialNumber()) {
						vo.setThumbUp(up.getThumbCount());
						break;
					}
				}
			}
			if (!thumbDown.isEmpty()) {
				for (FindThumb down : thumbDown) {
					if (down.getCommentSn() == vo.getSerialNumber()) {
						vo.setThumbDown(down.getThumbCount());
						break;
					}
				}
			}
		}
		return result;
	}

	/**
	 * 刪除評論
	 *
	 * @param req
	 * @param sessionMail
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public BasicRes deleteComment(DeleteCommentReq req, String sessionMail) {
		// 先判斷 sessionMail 是否等於評論者
		Boolean result = commentDao.checkMailEqualsSession(req.getPostId(), req.getCommentId(), sessionMail);
		if (result == null) {
			return new BasicRes(ResMessage.REPLY_COMMENT_NOT_EXIST.getCode(),
					ResMessage.REPLY_COMMENT_NOT_EXIST.getMessage());
		}
		if (!result) {
			return new BasicRes(ResMessage.COMMENT_DELETE_PERMISSION_DENIED.getCode(),
					ResMessage.COMMENT_DELETE_PERMISSION_DENIED.getMessage());
		}

		// 刪除互動表關聯部分(放最後的話父子都被刪掉了，會找不到資料!!)
		commentDao.deleteInteract(req.getPostId(), req.getCommentId());
		commentDao.deleteComment(req.getPostId(), req.getCommentId());

		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	// 編輯評論
	public BasicRes updateComment(UpdateCommentReq req, String sessionUserMail) {
		// 確認時間是否要轉格式，目前 req 用 String 接
		// 前端傳日期加時間的 String，後端再把字串加起來直接存 DB 的 comment_time
		req.setCommentTime(req.getCommentDate() + ' ' + req.getCommentTime());
		int result = commentDao.updateComment(req, sessionUserMail);

		if (result == 0) {
			return new BasicRes(ResMessage.COMMENT_UPDATE_FAILED.getCode(), //
					ResMessage.COMMENT_UPDATE_FAILED.getMessage());
		}
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}
}
