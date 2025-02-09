package com.example.goosetrip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dto.Users;
import com.example.goosetrip.service.SpotService;
import com.example.goosetrip.service.UserService;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.CreateSpotReq;
import com.example.goosetrip.vo.SearchSpotAndRouteSortOutRes;
import com.example.goosetrip.vo.SearchSpotByIdAndDayReq;
import com.example.goosetrip.vo.SearchSpotByIdAndDayRes;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/spots")
public class SpotController {

	@Autowired
	private SpotService spotService;

	@Autowired
	private UserService userService;

	/**
	 * 刪除舊景點後加入更新後的景點
	 * 
	 * @param CreateSpotReq req
	 * @return
	 */
	@PostMapping("/createSpot")
	public BasicRes createSpot(@RequestBody CreateSpotReq req) {
		return spotService.createSpot(req);
	}

	/**
	 * 整理完成版取得整個行程的景點路徑
	 * 
	 * @param JourneyId
	 * @return
	 */
	@GetMapping(value = "/getSpotAndRouteSortOut")
	public SearchSpotAndRouteSortOutRes searchSpotAndRouteSortOut(@RequestParam("JourneyId") int JourneyId) {
		return spotService.searchSpotAndRouteSortOut(JourneyId);
	}

	@PostMapping(value = "/searchSpotByIdAndDay")
	public SearchSpotByIdAndDayRes searchSpotByIdAndDay(@RequestBody SearchSpotByIdAndDayReq req, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new SearchSpotByIdAndDayRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(),
					ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		// 修改userEdit TODO 檢查不為0 不用檢查這個行程id是否存在 行程是否有這個天數?
		attr.setUserEdit(req.getJourneyId() + "," + req.getDay());
		session.setAttribute("user", attr);
		// 也要存進資料庫
		userService.setUserEdit(attr.getUserMail(), req.getJourneyId() + "," + req.getDay());
		return spotService.searchSpotByIdAndDay(req.getJourneyId(), req.getDay());
	}
}
