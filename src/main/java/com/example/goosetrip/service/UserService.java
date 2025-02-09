package com.example.goosetrip.service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dao.UserDao;
import com.example.goosetrip.dto.Journey;
import com.example.goosetrip.dto.Users;
import com.example.goosetrip.utils.PasswordUtils;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.CheckJourneyRes;
import com.example.goosetrip.vo.Collaborator;
import com.example.goosetrip.vo.CollaboratorRes;
import com.example.goosetrip.vo.DailyEditStatus;
import com.example.goosetrip.vo.DeleteUserReq;
import com.example.goosetrip.vo.LoginReq;
import com.example.goosetrip.vo.LoginRes;
import com.example.goosetrip.vo.RegisterUserReq;
import com.example.goosetrip.vo.RegisterUserRes;
import com.example.goosetrip.vo.SearchUserRes;
import com.example.goosetrip.vo.UpdateUserReq;
import com.example.goosetrip.vo.UpdateUserRes;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ChatroomMembersService chatroomMembersService;

	public SearchUserRes findAllUsers() {
		List<Users> userList = userDao.findAllUsers();
		return new SearchUserRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), userList);
	}

	@Transactional(rollbackFor = Exception.class)
	public RegisterUserRes register(RegisterUserReq req) throws SQLException, IOException {

		// 帳戶若存在則不可新增
		Users user = userDao.getInfoById(req.getUserMail());
		if (user != null) {
			return new RegisterUserRes(ResMessage.ACCOUNT_IS_EXIST.getCode(), ResMessage.ACCOUNT_IS_EXIST.getMessage());
		}

		// 頭貼不可為空
		if (req.getUserImage() == null || req.getUserImage().isBlank()) {
			return new RegisterUserRes(ResMessage.IMAGE_NOT_FOUND.getCode(), ResMessage.IMAGE_NOT_FOUND.getMessage());
		}

		// 密碼加密存到資料庫
		String hashPassword = PasswordUtils.hashPassword(req.getUserPassword());
		req.setUserPassword(hashPassword);

		// 存圖片路徑
		if (req.getUserImage() == null || req.getUserImage().isBlank()) {
			return new RegisterUserRes(ResMessage.IMAGE_NOT_FOUND.getCode(), ResMessage.IMAGE_NOT_FOUND.getMessage());
		}
		// 放入 User 對象中
		user = new Users(req.getUserMail(), req.getUserName(), req.getUserPhone(), req.getUserPassword(),
				req.getUserImage());
		userDao.addUser(user);
		return new RegisterUserRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), user);
	}

	@Transactional(rollbackFor = Exception.class)
	public UpdateUserRes updateUserInfo(UpdateUserReq req) {

		boolean hasChanges = false;

		// 從資料庫獲取使用者資料 TODO 檢查回傳為null
		Users userDB = userDao.getInfoById(req.getUserMail());

		// 1. 變更密碼(如果使用者有輸入密碼)
		if (req.getUserOldPassword() != null && !req.getUserOldPassword().isEmpty() && req.getUserPassword() != null
				&& !req.getUserPassword().isEmpty()) {
			String DBPwd = userDB.getUserPassword();
			// 核對密碼
			if (!PasswordUtils.verifyPassword(req.getUserOldPassword(), DBPwd)) {
				return new UpdateUserRes(ResMessage.INVALID_PASSWORD.getCode(),
						ResMessage.INVALID_PASSWORD.getMessage());
			}
			// 將新密碼加密存
			String hashPwd = PasswordUtils.hashPassword(req.getUserPassword());
			userDB.setUserPassword(hashPwd);
			hasChanges = true;
		}
		// 2. 變更其他資訊
		if (req.getUserName() != null && !req.getUserName().equals(userDB.getUserName())) {
			userDB.setUserName(req.getUserName());
			hasChanges = true;
		}
		if (req.getUserPhone() != null && !req.getUserPhone().equals(userDB.getUserPhone())) {
			userDB.setUserPhone(req.getUserPhone());
			hasChanges = true;
		}
		if (req.getUserImage() != null) {
			userDB.setUserImage(req.getUserImage());
			hasChanges = true;
		}
		// 3. 如有變更存到資料庫並更新Session(把暫存的資料放到User user裡)
		if (hasChanges) {
			// 更新資料庫資料
			int changedRow = userDao.updateUser(userDB); // 有變更的話，結果會大於0
			// 更新 session 資料
			if (changedRow > 0) {
				return new UpdateUserRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), userDB);
			} else {
				return new UpdateUserRes(ResMessage.UPDATE_FAILED.getCode(), ResMessage.UPDATE_FAILED.getMessage());
			}
		}
		// 沒有變更也回傳成功（沒變更的話也不會動到資料庫因為SQL是用CASE WHEN寫的）
		return new UpdateUserRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), userDB);
	}

	// TODO 需要修改 不用刪除行程了
	@Transactional(rollbackFor = Exception.class)
	public BasicRes deleteUser(DeleteUserReq req) {
		// 帳戶若存在才能刪除
		Users user = userDao.getInfoById(req.getUserMail());
		if (user == null) {
			return new BasicRes(ResMessage.ACCOUNT_NOT_EXIST.getCode(), ResMessage.ACCOUNT_NOT_EXIST.getMessage());
		}
		// 核對 使用者輸入密碼 VS 資料庫密碼
		String inputPwd = req.getUserPassword();
		String DBPwd = userDao.findUserPasswordByUserMail(req.getUserMail());
		if (!PasswordUtils.verifyPassword(inputPwd, DBPwd)) {
			return new BasicRes(ResMessage.INVALID_PASSWORD.getCode(), ResMessage.INVALID_PASSWORD.getMessage());
		}
		// 刪除關聯行程
		// 確認使用者是否有關連到行程
		List<Integer> journeyIdList = userDao.findJourneyByUserMail(req.getUserMail());

		// 狀況 1：使用者無關聯行程（直接刪除會員資料）
		if (CollectionUtils.isEmpty(journeyIdList)) {
			userDao.deleteUser(req.getUserMail(), DBPwd); // 刪除會員資料
		} else {
			for (Integer journeyId : journeyIdList) {
				int count = userDao.findUsersByJourneyId(journeyId);
				// System.out.println(count);
				if (count > 1) {
					// 狀況 2：行程中尚有其他成員接 leaveJourney
					leaveJourney(req.getUserMail());
					userDao.deleteUser(req.getUserMail(), DBPwd); // 刪除會員資料
				} else {
					// 狀況3：行程中只有本人（刪除行程）
					userDao.deleteUserFromJourneyAndChatroom(req.getUserMail(), journeyId);
					userDao.deleteUser(req.getUserMail(), DBPwd); // 刪除會員資料
				}
			}
		}
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	// 將會員退出行程與聊天室（行程的mail設null，並退出聊天室）
	@Transactional(rollbackFor = Exception.class)
	public void leaveJourney(String mail) {
		// 找出有該會員的行程ID表
		List<Integer> journeyIdList = userDao.findJourneyByUserMail(mail);
		// 將聊天室中的該會員信箱刪除
		userDao.deleteUserFromChatroom(mail, journeyIdList);
		// 如果行程表中是要退出成員的信箱，就將行程表的信箱設為 null（但一般不會在編輯狀態下初始化會員資料？）
		for (Integer id : journeyIdList) {
			userDao.updateUserMailToNull(id, mail);
		}
	}

	// 登入
	public LoginRes login(LoginReq req) {
		// 檢查有無該帳號
		Users user = userDao.getInfoById(req.getUserMail());
		if (user == null) {
			return new LoginRes(ResMessage.ACCOUNT_OR_PASSWORD_ERROR.getCode(),
					ResMessage.ACCOUNT_OR_PASSWORD_ERROR.getMessage());
		}
		// 再比對密碼
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		boolean check = encoder.matches(req.getPwd(), user.getUserPassword());
		// matches(原始的密碼, 存在DB裡的加密密碼)
		if (!check) {
			return new LoginRes(ResMessage.ACCOUNT_OR_PASSWORD_ERROR.getCode(),
					ResMessage.ACCOUNT_OR_PASSWORD_ERROR.getMessage());
		}
		if (StringUtils.hasText(user.getUserEdit())) {
			userDao.clearEdit(req.getUserMail());
			user.setEditTime(null);
			user.setUserEdit(null);
		}
		// 傳送帳號資料
		return new LoginRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), user);
	}

	// 更新我的最愛
	@Transactional(rollbackFor = Exception.class)
	public BasicRes updateFavorite(String newFavorite, String mail) {
		int daoRes = userDao.updateFavorite(newFavorite, mail);
		if (daoRes == 0) {
			return new BasicRes(ResMessage.UPDATE_MYFAVORITE_FAILED.getCode(),
					ResMessage.UPDATE_MYFAVORITE_FAILED.getMessage());
		}
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	// 把地點加入我的行程前的確認
	public CheckJourneyRes checkJourney(String mail) {
		// 確定有哪些行程(取得行程id、行程名稱、開始日期、結束日期)
		List<Journey> journeyInfoList = userDao.findJourneyInfoByUserMail(mail);
		// 如果journeyInfoList為空，直接返回空結果
		if (CollectionUtils.isEmpty(journeyInfoList)) {
			return new CheckJourneyRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), null, false);
		}
		// 拿到那些行程有人在編輯的userEdit
		List<Users> userEditList = userDao.findUserEditByJourneyId(journeyInfoList);
		List<DailyEditStatus> editList = new ArrayList<>();
		for (Journey item : journeyInfoList) {
			// editingDays是哪幾天有人正在編輯
			List<Integer> editingDays = new ArrayList<>();
			for (Users vo : userEditList) {
				if (vo.getUserEdit().contains(item.getJourneyId() + ",")) {
					Duration duration = Duration.between(vo.getEditTime(), LocalDateTime.now());
					if (duration.toHours() > 2) {
						int result = userDao.clearEdit(vo.getUserMail());
						// TODO 清除權限失敗
					} else {
						String[] arr = vo.getUserEdit().split(",");
						editingDays.add(Integer.parseInt(arr[1]));
					}

				}
			}
			// 計算兩個日期之間的天數
			// ChronoUnit.DAYS.between(startDate,
			// endDate)可以計算出兩個LocalDate差幾天(不包括開始日期)，回傳型態為long
			int totalDays = (int) ChronoUnit.DAYS.between(item.getStartDate(), item.getEndDate()) + 1;
			editList.add(new DailyEditStatus(item.getJourneyId(), item.getJourneyName(), totalDays, editingDays));
		}
		return new CheckJourneyRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), editList, false);
	}

	// 用id找到共同編輯者
	public CollaboratorRes findCollaboratorByJourneyId(int journeyId, String mail) {
		List<Collaborator> collaboratorlist = userDao.findCollaboratorByJourneyId(journeyId, mail);
		return new CollaboratorRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), collaboratorlist);
	}

	// 退出行程
	@Transactional(rollbackFor = Exception.class)
	public BasicRes userleaveJourney(String mail, int id) {
		String userEdit = userDao.searchUserEditByMail(mail);
		if (StringUtils.hasText(userEdit)) {
			return new BasicRes(ResMessage.USER_IS_EDITING.getCode(), ResMessage.USER_IS_EDITING.getMessage());
		}
		List<Integer> list = new ArrayList<>();
		list.add(id);
		userDao.deleteUserFromChatroom(mail, list);
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	@Transactional(rollbackFor = Exception.class)
	public BasicRes updateUserEdit(String mail, String userEdit, boolean Editing) {
		// 開始編輯
		if (Editing) {
			// 確認開始user編輯某一天時沒有在同時編輯其他天
			String res = userDao.getInfoById(mail).getUserEdit();
			if (res != null && !res.isBlank()) {
				return new BasicRes(ResMessage.MULTIPLE_EDITS_ARE_NOT_ALLOWED.getCode(), //
						ResMessage.MULTIPLE_EDITS_ARE_NOT_ALLOWED.getMessage());
			}
			// 確認有沒有其他使用者正在編輯 TODO 改成用變數判斷 不用查兩次DB
			Users voUser = userDao.searchByUserEdit(userEdit);
			if (voUser != null) {
				Duration duration = Duration.between(voUser.getEditTime(), LocalDateTime.now());
				if (duration.toHours() > 2) {
					int result = userDao.clearEdit(voUser.getUserMail());
					// TODO 清除權限失敗
				}else if (voUser.getUserMail() != mail) {
					return new BasicRes(ResMessage.DUPLICATE_EDITING_WITH_OTHERS.getCode(), //
							ResMessage.DUPLICATE_EDITING_WITH_OTHERS.getMessage());
				}
				
			}

		}
		// 結束編輯
		if (!Editing) {
			// 確認要結束編輯前User_Edit是在編輯該天 TODO 改成用變數判斷 不用查兩次DB
			String result = userDao.getInfoById(mail).getUserEdit();
			if (result == null || !result.equalsIgnoreCase(userEdit)) {
				return new BasicRes(ResMessage.NOT_EDITING.getCode(), //
						ResMessage.NOT_EDITING.getMessage());
			}
//			if (userDao.getInfoById(mail).getUserEdit() == null
//					|| !userDao.getInfoById(mail).getUserEdit().equalsIgnoreCase(userEdit)) {
//				return new BasicRes(ResMessage.NOT_EDITING.getCode(), //
//						ResMessage.NOT_EDITING.getMessage());
//			}
		}

		userDao.updateUserEdit(mail, userEdit, LocalDateTime.now());

		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	/**
	 * 設定UserEdit欄位
	 * 
	 * @param mail
	 * @param userEdit
	 */
	@Transactional(rollbackFor = Exception.class)
	public void setUserEdit(String mail, String userEdit) {
		// TODO userEdit中的邢台id 是0
		userDao.setUserEdit(mail, userEdit);
	}

	/**
	 * 忘記密碼時設置新密碼
	 * 
	 * @param mail
	 * @param pwd
	 * @return
	 */
	public BasicRes setPwd(String mail, String pwd, String verificationCode) {
		if (userDao.getInfoById(mail) == null) {
			return new RegisterUserRes(ResMessage.ACCOUNT_NOT_EXIST.getCode(),
					ResMessage.ACCOUNT_NOT_EXIST.getMessage());
		}

		boolean isValid = chatroomMembersService.validateInvitationCode(mail, verificationCode);
		if (!isValid) {
			return new BasicRes(ResMessage.VERIFY_FAILED.getCode(), ResMessage.VERIFY_FAILED.getMessage());
		}

		int re = userDao.setPwd(mail, PasswordUtils.hashPassword(pwd));
		if (re == 0) {
			return new BasicRes(ResMessage.UPDATE_FAILED.getCode(), ResMessage.UPDATE_FAILED.getMessage());
		}
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

}
