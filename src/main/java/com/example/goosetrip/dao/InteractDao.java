package com.example.goosetrip.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.goosetrip.vo.FindThumb;
import com.example.goosetrip.vo.ThumbActReq;

@Mapper
public interface InteractDao {

	/**
	 * 取得使用者對某貼文的評論流水號的點讚行為
	 * @param req
	 * @param userMail
	 */
	public void thumbAct(@Param(value = "req") ThumbActReq req, @Param("userMail") String userMail);

	/**
	 * 取得貼文評論的正讚
	 * 
	 * @param postId
	 * @return
	 */
	public List<FindThumb> findThumbUp(int postId);

	/**
	 * 取得貼文評論的倒讚
	 * @param postId
	 * @return
	 */
	public List<FindThumb> findThumbDown(int postId);

	/**
	 * 用信箱和貼文ID查詢此人是否有按讚
	 * @param postId
	 * @param userMail
	 * @return
	 */
	public Boolean findThumbStatus(@Param("postId") int postId, @Param("userMail")String userMail);
}
