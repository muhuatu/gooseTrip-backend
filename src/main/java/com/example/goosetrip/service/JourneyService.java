package com.example.goosetrip.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dao.ChatroomMembersDao;
import com.example.goosetrip.dao.JourneyDao;
import com.example.goosetrip.dao.SpotDao;
import com.example.goosetrip.dto.Journey;
import com.example.goosetrip.dto.Spot;
import com.example.goosetrip.vo.BaseJourneyReq;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.CreateJourneyRes;
import com.example.goosetrip.vo.DeleteJourneyReq;
import com.example.goosetrip.vo.SearchUserJourneyRes;
import com.example.goosetrip.vo.UpdateJourneyReq;

@Service
public class JourneyService {
	@Autowired
	private JourneyDao journeyDao;

	@Autowired
	private ChatroomMembersDao chatroomMembersDao;

	@Autowired
	private SpotDao spotDao;

	@Autowired
	private ChatroomMembersService chatroomMembersService;

	/**
	 * 新增行程
	 * 
	 * @param BaseJourneyReq req
	 * @param String         mail
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public CreateJourneyRes createJourney(BaseJourneyReq req, String mail) {
		// 設定邀請碼
		String invitation = chatroomMembersService.generateInvitation();
		req.setInvitation(invitation);

		int createJourneyResult = journeyDao.createJourney(req);
		if (createJourneyResult <= 0) {
			return new CreateJourneyRes(ResMessage.CREATE_JOURNEY_FAILED.getCode(),
					ResMessage.CREATE_JOURNEY_FAILED.getMessage());
		}

		chatroomMembersDao.createJourney(req.getJourneyId(), invitation, mail);
		return new CreateJourneyRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), req.getJourneyId(),
				invitation);
	}

	/**
	 * 更新行程
	 * 
	 * @param UpdateJourneyReq req
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public BasicRes updateJourney(UpdateJourneyReq req) {
		Journey journey = journeyDao.findJourneyById(req.getJourneyId());

		if (journey == null) {
			return new BasicRes(ResMessage.UPDATE_JOURNEY_FAILED.getCode(),
					ResMessage.UPDATE_JOURNEY_FAILED.getMessage());
		}

		try {
			// 計算日期差
			LocalDate originalStartDate = journey.getStartDate();
			LocalDate originalEndDate = journey.getEndDate();
			LocalDate newStartDate = req.getStartDate();
			LocalDate newEndDate = req.getEndDate();
			int originalDayDiff = (int) ChronoUnit.DAYS.between(originalStartDate, originalEndDate);
			int updatedDayDiff = (int) ChronoUnit.DAYS.between(newStartDate, newEndDate);

			// 防止使用者縮短日期(只能加長，不能縮短
			if (updatedDayDiff < originalDayDiff) {
				return new BasicRes(400, "更新行程不能縮短日期");
			}

			// 更新行程與景點日期
			journeyDao.updateJourney(req);
			spotDao.updateSpotDates(journey.getJourneyId(), req.getStartDate());

			return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
		} catch (Exception e) {
			return new BasicRes(ResMessage.UPDATE_JOURNEY_FAILED.getCode(),
					ResMessage.UPDATE_JOURNEY_FAILED.getMessage());
		}
	}

	/**
	 * 取得該會員所有的行程
	 * 
	 * @param userMail
	 * @return
	 */
	public SearchUserJourneyRes searchUserJourney(String userMail) {
		// TODO 可以改join
		// 取得該會員有加入的所有聊天室邀請碼
		List<String> invitationList = journeyDao.searchInvitationByUserMail(userMail);
		// 以聊天室邀請碼取得所有行程
		if (CollectionUtils.isEmpty(invitationList)) {
			return new SearchUserJourneyRes(ResMessage.SUCCESS.getCode(), //
					ResMessage.SUCCESS.getMessage(), null);
		}
		List<Journey> JourneyList = journeyDao.searchJourneyByInvitationList(invitationList);

		return new SearchUserJourneyRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage(), JourneyList);
	}

	/**
	 * 以邀請碼加入行程
	 * 
	 * @param invitation
	 * @param UserMail
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public BasicRes joinJourney(String invitation, String UserMail) {
		Integer journeyId = journeyDao.searchJourneyId(invitation);
		// 邀請碼不存在
		if (journeyId == null) {
			return new BasicRes(ResMessage.INVITATION_NOT_EXISTS.getCode(), //
					ResMessage.INVITATION_NOT_EXISTS.getMessage());
		}
		try {
			// TODO 檢查加入是否成功
			journeyDao.joinJourneyId(journeyId, invitation, UserMail);
		} catch (Exception e) { // TODO 要改成確切的Exception
			// 重複加入行程
			return new BasicRes(ResMessage.ALREADY_JOINED.getCode(), //
					ResMessage.ALREADY_JOINED.getMessage());
		}

		return new BasicRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage());
	}

	/**
	 * 複製行程: 在登入的使用者信箱(sessionUserMail)新增與傳入 journeyId 相同內容的行程
	 * 
	 * @param journeyId
	 * @param sessionUserMail
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public CreateJourneyRes copyJourney(int journeyId, String sessionUserMail) {
		// 檢查參數
		if (journeyId <= 0 || !StringUtils.hasText(sessionUserMail)) {
			return new CreateJourneyRes(ResMessage.PARAMETER_ERROR.getCode(), //
					ResMessage.PARAMETER_ERROR.getMessage());
		}
		// 1. 用 journeyId 找出對應的Journey TODO 補檢查回傳是null
		Journey daoRes = journeyDao.findJourneyById(journeyId);
		BaseJourneyReq req = new BaseJourneyReq();
		// 2. A. 把 Journey 的值塞回去 CreateJourneyReq
		LocalDate today = LocalDate.now(); // 預設行程的開始日期是今天
		// 用原本行程的結束日期減開始日期得到天數
		long day = ChronoUnit.DAYS.between(daoRes.getStartDate(), daoRes.getEndDate());
		req.setJourneyName(daoRes.getJourneyName());
		req.setStartDate(today); // 預設行程的開始日期是今天
		req.setEndDate(today.plusDays(day)); // 預設行程的結束日期是今天加上天數
		req.setTransportation(daoRes.getTransportation());
		// 2. B. 把 invitation 的值塞回去 CreateJourneyReq
		req.setInvitation(chatroomMembersService.generateInvitation());
		// 2. C. 結合兩者去創建行程
		journeyDao.createJourney(req); // TODO 檢查創建失敗
		// 2. D. 為登入的使用者創建該行程 TODO 檢查創建失敗
		chatroomMembersDao.createJourney(req.getJourneyId(), req.getInvitation(), sessionUserMail);
		// 3. 用 1.的 journeyId 去找出行程景點資料 TODO 補如果回傳是null
		List<Spot> spot = spotDao.getAllSpotByJourney(journeyId);
		// 4. 把新的 journeyId (2. D.) 加到 3.的資料
		for (Spot item : spot) {
			item.setJourneyId(req.getJourneyId());
		}
		// 5. 新增景點資料 TODO 檢查創建失敗
		spotDao.createSpot(spot);
		return new CreateJourneyRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), //
				req.getJourneyId(), req.getInvitation());
	}
}
