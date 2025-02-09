package com.example.goosetrip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dao.InteractDao;
import com.example.goosetrip.dao.PostDao;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.FindThumb;
import com.example.goosetrip.vo.PostList;
import com.example.goosetrip.vo.ThumbActReq;

@Service
public class InteractService {

	@Autowired
	private InteractDao interactDao;

	@Autowired
	private PostDao postDao;
	
	/**
	 * 點讚行為
	 * @param req
	 * @param sessionUserMail
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public BasicRes thumbAct(ThumbActReq req, String sessionUserMail) {
		//TODO 可以改用postId+sessionUserMail查詢，有資料代表是自己的貼文
		// 不可對自己的貼文有點讚行為
		List<PostList> userPost = postDao.getPostByUserMail(sessionUserMail);
		for(PostList item : userPost) {
			if(item.getPostId() == req.getPostId()) {
				return new BasicRes(ResMessage.CAN_NOT_INTERACT_WITH_SELF_POST.getCode(), //
						ResMessage.CAN_NOT_INTERACT_WITH_SELF_POST.getMessage());
			}
		}
		// 1. 檢查參數
		// postId, commentSN @NotNull
		// 2. 把 thumb 的狀態儲存到 DB (thumb 前端檢查狀態有沒有改變才打API，後端直接儲存)
		// 如果已經有紀錄了--> update; 如果沒有紀錄 --> insert
		// 用 mySQL DUPLICATE KEY 判斷主鍵有衝突用 update，反之用 insert
		interactDao.thumbAct(req, sessionUserMail);
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	/**
	 * 取得貼文評論的正讚
	 * 
	 * @param postId
	 * @return
	 */
	public List<FindThumb> findThumbUp(int postId) {
		if (postId == 0) {
			return null;
		}
		return interactDao.findThumbUp(postId);
	}

	/**
	 * 取得貼文評論的倒讚
	 * 
	 * @param postId
	 * @return
	 */
	public List<FindThumb> findThumbDown(int postId) {
		if (postId == 0) {
			return null;
		}
		return interactDao.findThumbDown(postId);
	}
}
