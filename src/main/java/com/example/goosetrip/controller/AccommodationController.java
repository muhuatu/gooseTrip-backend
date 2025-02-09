package com.example.goosetrip.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.example.goosetrip.service.AccommodationService;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.DeleteRoomDataReq;
import com.example.goosetrip.vo.GetHotelReq;
import com.example.goosetrip.vo.GetHotelRoomReq;
import com.example.goosetrip.vo.GetHotelRoomRes;
import com.example.goosetrip.vo.GetRoomDataRes;
import com.example.goosetrip.vo.HotelList;
import com.example.goosetrip.vo.HotelRoom;
import com.example.goosetrip.vo.SaveRoomDataReq;
import com.example.goosetrip.vo.SelectHotelRes;
import com.example.goosetrip.vo.UpdateRoomDataReq;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/accommodation")
public class AccommodationController {

	@Autowired
	private AccommodationService accommodationService;

	/**
	 * 爬蟲取得飯店資訊
	 * 
	 * @param reqDto
	 * @param session
	 * @return
	 */
	@PostMapping("/selectHotel")
	public SelectHotelRes selectHotel(@Valid @RequestBody GetHotelReq reqDto, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new SelectHotelRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(),
					ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}

		List<HotelList> resList = new ArrayList<>();
		if ("booking".equals(reqDto.getWebName())) {
			return accommodationService.fingBookingList(reqDto);
//			resList = accommodationService.selectHotel(reqDto);
		} else if ("agoda".equals(reqDto.getWebName())) {
			SelectHotelRes resp = accommodationService.fingAgodaList(reqDto);
			int time = 0;

			while (resp == null) {
				if (time > 5) {
					return new SelectHotelRes(ResMessage.FIND_HOTEL_FAILED.getCode(),
							ResMessage.FIND_HOTEL_FAILED.getMessage());
				}
				resp = accommodationService.fingAgodaList(reqDto);
				time++;
			}
			return resp;
		} else {
//			resList = accommodationService.selectHotel(reqDto);
			resList = accommodationService.fingBookingList(reqDto).getData();
			List<HotelList> agoda = accommodationService.fingAgodaList(reqDto).getData();
			resList.addAll(agoda);
		}
		return new SelectHotelRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), resList);
	}

	/**
	 * 爬蟲取得房間資訊
	 * 
	 * @param reqDto
	 * @param session
	 * @return
	 */
	@PostMapping("/getHotelRoom")
	public GetHotelRoomRes getHotelRoom(@Valid @RequestBody GetHotelRoomReq reqDto, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new GetHotelRoomRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(),
					ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		List<HotelRoom> resList = new ArrayList<>();
		if ("booking".equals(reqDto.getWebName())) {
			resList = accommodationService.getBookingHotelRoom(reqDto);
//			resList = accommodationService.getHotelRoom(reqDto);
		}
		if ("agoda".equals(reqDto.getWebName())) {
			resList = accommodationService.getAgodaHotelRoom(reqDto);
//			resList = accommodationService.getHotelRoom(reqDto);
		}
		return new GetHotelRoomRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), resList);
	}

	/**
	 * 儲存住宿資訊
	 * 
	 * @param reqDto
	 * @param session
	 * @return
	 */
	@PostMapping("/saveRoomData")
	public BasicRes saveRoomData(@Valid @RequestBody SaveRoomDataReq reqDto, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new BasicRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}

		return accommodationService.saveRoomData(reqDto, attr);
	}

	/**
	 * 取得整個行程的住宿資訊
	 * 
	 * @param journeyId
	 * @param session
	 * @return
	 */
	@GetMapping("/getRoomData")
	public GetRoomDataRes getRoomData(@RequestParam(value = "journeyId") int journeyId, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new GetRoomDataRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(),
					ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return accommodationService.getRoomData(journeyId, attr);
	}

	/**
	 * 批量刪除住宿資訊
	 * 
	 * @param reqDto
	 * @param session
	 * @return
	 */
	@PostMapping("/deleteRoomData")
	public BasicRes deleteRoomData(@Valid @RequestBody DeleteRoomDataReq reqDto, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new BasicRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return accommodationService.deleteRoomData(reqDto, attr);
	}

	/**
	 * 取得編輯住宿資訊權限
	 * 
	 * @param journeyId
	 * @param session
	 * @return
	 */
	@GetMapping("/editAccommodation")
	public BasicRes editAccommodation(@RequestParam(value = "journeyId") int journeyId, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new BasicRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return accommodationService.editAccommodation(attr.getUserMail(), journeyId);
	}

	/**
	 * 更新住宿狀態、結束權限
	 * 
	 * @param reqDto
	 * @param session
	 * @return
	 */
	@PostMapping("/updateRoomData")
	public BasicRes updateRoomData(@Valid @RequestBody UpdateRoomDataReq reqDto, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new BasicRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return accommodationService.updateRoomData(reqDto, attr.getUserMail());
	}

}
