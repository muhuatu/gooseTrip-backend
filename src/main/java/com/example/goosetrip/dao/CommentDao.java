package com.example.goosetrip.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.example.goosetrip.dto.Comment;
import com.example.goosetrip.vo.CommentList;
import com.example.goosetrip.vo.UpdateCommentReq;

@Mapper
public interface CommentDao {

	/**
	 * 查詢commentId最大值
	 * 
	 * @param postId
	 * @return
	 */
	public Integer findMaxCommentId(int postId);

	/**
	 * 新增評論
	 * 
	 * @param vo
	 * @return
	 */
	public int saveComment(Comment vo);

	/**
	 * 查詢評論id by回復評論Id
	 * 
	 * @param replyCommentId
	 * @return
	 */
	public List<Integer> findCommentId(int replyCommentId);

	/**
	 * 取得貼文的所有評論
	 * 
	 * @param postId
	 * @return
	 */
	public List<CommentList> getAllReply(int postId);

	/**
	 * 刪除與此評論相關的互動表中的資料
	 * 
	 * @param postId
	 * @param commentId
	 * @return
	 */
	public void deleteInteract(@Param("postId") int postId, @Param("commentId") int commentId);

	/**
	 * 刪除評論
	 * 
	 * @param postId
	 * @param commentId
	 * @return
	 */
	public void deleteComment(@Param("postId") int postId, @Param("commentId") int commentId);

	/**
	 * 確認Session信箱是否和要刪除的留言者信箱一致
	 * 
	 * @param postId
	 * @param commentId
	 * @param sessionMail
	 * @return
	 */
	public Boolean checkMailEqualsSession(@Param("postId") int postId, @Param("commentId") int commentId,
			@Param("sessionMail") String sessionMail);

	@Update("UPDATE comment SET comment_time = #{req.commentTime}, comment_content = #{req.commentContent} " //
			+ "WHERE serial_number = #{req.sn} and user_mail = #{sessionMail}")
	public int updateComment(@Param("req") UpdateCommentReq req, @Param("sessionMail") String sessionMail);

}
