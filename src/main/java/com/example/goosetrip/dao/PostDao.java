package com.example.goosetrip.dao;

import java.util.List;

import com.example.goosetrip.dto.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.example.goosetrip.vo.PostDetail;
import com.example.goosetrip.vo.PostList;
import com.example.goosetrip.vo.PostVo;

@Mapper
public interface PostDao {

	/**
	 * 查詢使用者貼文
	 *
	 * @param userMail
	 * @return
	 */
	public List<PostList> getPostByUserMail(String userMail);

	/**
	 * 刪除使用者貼文
	 *
	 * @param userMail
	 * @param postIdList
	 */
	public void deletePost(@Param("userMail") String userMail, @Param("postIdList") List<String> postIdList);

	/**
	 * 用關鍵字查詢貼文
	 * @param keyword
	 * @return
	 */
	public List<PostList> searchPostByKeyword(@Param("keyword") String keyword);

	/**
	 * 根據信箱查詢該使用者收藏的貼文ID列表
	 *
	 * @param userMail
	 * @return
	 */
	public String getFavoritePosts(String userMail);

	/**
	 * 刪除收藏貼文
	 *
	 * @param userMail
	 * @param favoritePosts
	 */
	public void updateFavoritePostByUserMail(@Param("userMail") String userMail,
			@Param("favoritePosts") String favoritePosts);

	/**
	 * 檢查該信箱與行程ID是否已存在於POST資料表
	 *
	 * @param userMail
	 * @param journeyId
	 * @param spotId
	 * @param day
	 * @return
	 */
	public int findPostByUserMailAndJourneyId(@Param("userMail") String userMail, @Param("journeyId") int journeyId,
			@Param("spotId") int spotId, @Param("day") int day);

	/**
	 * 取出 post 表中的最大 post_id
	 *
	 * @return
	 */
	public int getMaxPostId();

	/**
	 * 新增貼文
	 *
	 * @param journeyId
	 * @param userMail
	 * @param postContent
	 * @param postTime
	 * @param details
	 */
	public int insertPost(@Param("postId") int postId, @Param("journeyId") int journeyId,
			@Param("userMail") String userMail, @Param("postContent") String postContent,
			@Param("postTime") String postTime, @Param("details") List<PostDetail> details);

	/**
	 * 用貼文ID查詢該貼文
	 *
	 * @param postId
	 */
	public List<PostVo> findAllByPostId(@Param("postId") int postId);

	/**
	 * 比對行程ID與貼文ID是否對應
	 * 
	 * @param postId
	 * @param journeyId
	 * @return
	 */
	public int checkJourneyIdAndPostId(@Param("postId") int postId, @Param("journeyId") int journeyId);

	/**
	 * 更新單一貼文
	 * 
	 * @param postId
	 * @param journeyId
	 * @param userMail
	 * @param postContent
	 * @param postTime
	 * @param details
	 * @return
	 */
	public int updatePost(@Param("postId") int postId, @Param("journeyId") int journeyId,
			@Param("userMail") String userMail, @Param("postContent") String postContent,
			@Param("postTime") String postTime, @Param("details") PostDetail details);
	
	/**
	 * 編輯貼文將貼文設為未發佈
	 * @param postId
	 * @param userMail
	 */
	@Update("update post set published = false where post_id = #{postId} and user_mail = #{userMail}")
	public void setPostUnpublished(@Param("postId") int postId, @Param("userMail") String userMail);

	/**
	 * 檢查該信箱與行程ID是否已存在於POST資料表
	 *
	 * @param userMail
	 * @param journeyId
	 * @return
	 */
	public int findPostIsPublished(@Param("userMail") String userMail, @Param("journeyId") int journeyId);


}
