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
import com.example.goosetrip.service.JourneyService;
import com.example.goosetrip.vo.BaseJourneyReq;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.CreateJourneyRes;
import com.example.goosetrip.vo.JoinJourneyReq;
import com.example.goosetrip.vo.SearchUserJourneyRes;
import com.example.goosetrip.vo.UpdateJourneyReq;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("journey/")
public class JourneyController {

	@Autowired
	private JourneyService journeyService;

	/**
	 * 新增行程
	 * 
	 * @param CreateJourneyReq req
	 * @param session
	 * @return
	 */
	@PostMapping("/createJourney")
	public CreateJourneyRes createJourney(@Valid @RequestBody BaseJourneyReq req, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new CreateJourneyRes(ResMessage.PLEASE_LOGIN_FIRST.getCode()//
					, ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return journeyService.createJourney(req, attr.getUserMail());
	}

	/**
	 * 更新行程
	 * 
	 * @param UpdateJourneyReq req
	 * @return
	 */
	@PostMapping("/updateJourney")
	public BasicRes updateJourney(@Valid @RequestBody UpdateJourneyReq req) {

		return journeyService.updateJourney(req);
	}

	@GetMapping(value = "/getJourney")
	public SearchUserJourneyRes getJourney(HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new SearchUserJourneyRes(ResMessage.PLEASE_LOGIN_FIRST.getCode()//
					, ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}

		return journeyService.searchUserJourney(attr.getUserMail());
	}

	@PostMapping(value = "/joinJourney")
	public BasicRes joinJourney(@Valid @RequestBody JoinJourneyReq req, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new BasicRes(ResMessage.PLEASE_LOGIN_FIRST.getCode()//
					, ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return journeyService.joinJourney(req.getInvitation(), attr.getUserMail());
	}

	/**
	 * 複製行程
	 * 
	 * @param journeyId
	 * @param session
	 * @return
	 */
	@PostMapping(value = "copy_journey")
	public CreateJourneyRes copyJourney(@RequestParam(value = "journeyId") int journeyId, HttpSession session) {
		// 檢查是否登入
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new CreateJourneyRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), //
					ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return journeyService.copyJourney(journeyId, attr.getUserMail());
	}

}
