package com.example.goosetrip.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.goosetrip.dto.Journey;
import com.example.goosetrip.dto.Users;
import com.example.goosetrip.vo.Collaborator;

@Mapper
public interface UserDao {

	/**
	 * 查詢所有使用者
	 *
	 * @return
	 */
	public List<Users> findAllUsers();

	/**
	 * 查詢某個使用者
	 *
	 * @param mail
	 * @return
	 */
	public Users getInfoById(String mail);

	/**
	 * 用信箱查詢密碼
	 *
	 * @param mail
	 * @return
	 */
	public String findUserPasswordByUserMail(String mail);

	/**
	 * 新增使用者
	 *
	 * @param user
	 */
	@Insert("insert into users (user_mail, user_name, user_password, user_phone, user_image)"
			+ " values (#{userMail}, #{userName}, #{userPassword}, #{userPhone}, #{userImage})")
	public void addUser(Users user);

	/**
	 * 刪除使用者資訊
	 *
	 * @param mail
	 * @param pwd
	 */
	@Delete("delete from users where user_mail = #{mail} and user_password = #{pwd}")
	public void deleteUser(@Param("mail") String mail, @Param("pwd") String pwd);

	/**
	 * 更新使用者資訊
	 * 
	 * @param user
	 * @return
	 */
	public int updateUser(Users user);

	/**
	 * 將使用者信箱從聊天室刪除
	 * 
	 * @param mail
	 * @param journeyIdList
	 */
	public void deleteUserFromChatroom(@Param("mail") String mail, @Param("journeyIdList") List<Integer> journeyIdList);

	/**
	 * 將行程及聊天室刪除(journey/spot/chatroom_members/chat/accommodation)
	 * 
	 * @param mail
	 * @param journeyId
	 */
	public void deleteUserFromJourneyAndChatroom(@Param("mail") String mail, @Param("journeyId") Integer journeyId);

	/**
	 * 查找此行程有幾個成員（用聊天室成員表判斷）
	 *
	 * @param journeyId
	 * @return
	 */
	public int findUsersByJourneyId(Integer journeyId);

	/**
	 * 查找成員關聯的行程
	 *
	 * @param mail
	 * @return
	 */
	public List<Integer> findJourneyByUserMail(String mail);

	/**
	 * 將行程表的使用者信箱設為 null
	 * 
	 * @param journeyId
	 * @param mail
	 */
	public void updateUserMailToNull(@Param("journeyId") Integer journeyId, @Param("mail") String mail);

	public int updateFavorite(@Param("newFavorite") String newFavorite, @Param("mail") String mail);

	public List<Journey> findJourneyInfoByUserMail(@Param("mail") String mail);

	/**
	 * 查詢行程的哪幾天有人正在編輯
	 * 
	 * @param list
	 * @return List<String>
	 */
	public List<Users> findUserEditByJourneyId(List<Journey> list);

	/**
	 * 清除權限
	 * 
	 * @param mail
	 */
	public int clearEdit(@Param("mail") String mail);

	/**
	 * 用JourneyId查共同編輯者
	 * 
	 * @param journeyId
	 * @return List<Collaborator>(信箱、名稱、頭貼)
	 */
	public List<Collaborator> findCollaboratorByJourneyId(@Param("journeyId") int journeyId,
			@Param("mail") String mail);

	/**
	 * 修改user_edit欄位
	 */
	public void updateUserEdit(@Param("mail") String mail, @Param("userEdit") String userEdit,
			@Param("editTime") LocalDateTime editTime);

	/**
	 * 搜尋是否有人正在編輯同一行程同一天 * @return UserMail
	 */
	public Users searchByUserEdit(String userEdit);

	/**
	 * 新增user_edit欄位
	 */
	public void setUserEdit(@Param("mail") String mail, @Param("userEdit") String userEdit);

	/**
	 * 查詢該mail是否正在編輯中
	 * 
	 * @param mail
	 * @return
	 */
	public String searchUserEditByMail(@Param("mail") String mail);

	/**
	 * 更新密碼
	 * 
	 * @param mail
	 * @return
	 */
	public int setPwd(@Param("mail") String mail, @Param("pwd") String pwd);

}
