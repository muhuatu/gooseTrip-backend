package com.example.goosetrip.service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dao.JourneyDao;
import com.example.goosetrip.dao.SpotDao;
import com.example.goosetrip.dto.Journey;
import com.example.goosetrip.dto.MapRoute;
import com.example.goosetrip.dto.Spot;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.CreateSpotReq;
import com.example.goosetrip.vo.SearchSpotAndRouteRes;
import com.example.goosetrip.vo.SearchSpotAndRouteSortOutRes;
import com.example.goosetrip.vo.SearchSpotByIdAndDayRes;
import com.example.goosetrip.vo.SpotData;
import com.example.goosetrip.vo.SpotDetail;
import com.example.goosetrip.vo.SpotReq;

@Service
public class SpotService {
	@Autowired
	private SpotDao spotDao;

	@Autowired
	private JourneyDao journeyDao;

	private int spotNameLengthLimit = 20;

	private void validateSpots(List<SpotReq> spotList) {
		if (spotList == null || spotList.isEmpty()) {
			throw new IllegalArgumentException("景點規劃列表不得為空");
		}

		for (SpotReq item : spotList) {
			if (!StringUtils.hasText(item.getSpotName()) || item.getSpotName().length() > spotNameLengthLimit) {
				throw new IllegalArgumentException("景點名稱無效");
			}

			if (item.getArrivalTime() == null || item.getDepartureTime() == null) {
				throw new IllegalArgumentException("到達時間和離開時間不得為空");
			}

			if (item.getArrivalTime().isAfter(item.getDepartureTime())) {
				throw new IllegalArgumentException("到達時間不能晚於離開時間");
			}
		}
	}

	/**
	 * 刪除舊景點後加入更新後的景點
	 * 
	 * @param CreateSpotReq req
	 * @return
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public BasicRes createSpot(CreateSpotReq req) {
		if (req == null || req.getDate() == null) {
			return new BasicRes(ResMessage.CREATE_SPOT_FAILED.getCode(), ResMessage.CREATE_SPOT_FAILED.getMessage());
		}

		List<SpotReq> spotList = req.getSpotList();

		// 使用者把景點刪光了
		if (spotList == null || spotList.isEmpty()) {
			spotDao.deleteSpot(req.getJourneyId(), req.getDay());
			return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
		}

		try {
			// 資料驗證
			validateSpots(spotList);

			spotDao.deleteSpot(req.getJourneyId(), req.getDay());
 
			List<Spot> spots = spotList.stream()
					.map(item -> new Spot(req.getJourneyId(), item.getSpotId(), req.getDay(), req.getDate(),
							item.getArrivalTime(), item.getDepartureTime(), item.getSpotName(), item.getPlaceId(),
							item.getNote(), item.getSpotImage()))
					.collect(Collectors.toList());

			spotDao.createSpot(spots);
			return new BasicRes(ResMessage.CREAT_SPOT_SUCCESS.getCode(), ResMessage.CREAT_SPOT_SUCCESS.getMessage());
		} catch (IllegalArgumentException e) {
			return new BasicRes(ResMessage.CREATE_SPOT_FAILED.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new BasicRes(ResMessage.FAIL_EXCEPTION.getCode(), "系統發生錯誤：" + e.getMessage());
		}
	}

	/**
	 * 整理完成版取得整個行程的景點路徑
	 * 
	 * @param journeyId
	 * @return
	 */
	public SearchSpotAndRouteSortOutRes searchSpotAndRouteSortOut(int journeyId) {
		if (journeyId <= 0) {
			return new SearchSpotAndRouteSortOutRes(ResMessage.SUCCESS.getCode(), //
					ResMessage.SUCCESS.getMessage());
		}

		// 取得所有景點資料
		List<Spot> spotList = spotDao.getAllSpotByJourney(journeyId);
		List<SpotData> spotRes = new ArrayList<>();
		item: for (Spot spotVo : spotList) {
			if (!spotRes.isEmpty()) {
				for (SpotData spot : spotRes) {
					if (spot.getDay() == spotVo.getDay() && spotVo.getDate() != null
							&& spotVo.getDate().equals(spot.getDate())) {
						SpotDetail detail = new SpotDetail(spotVo.getSpotId(), spotVo.getArrivalTime(),
								spotVo.getDepartureTime(), spotVo.getSpotName(), spotVo.getPlaceId(), spotVo.getNote(),
								spotVo.getAddress(), spotVo.getLatitude(), spotVo.getLongitude(), spotVo.getPlaceType(),
								spotVo.getSpotImage());
						spot.getSpotList().add(detail);
						continue item;
					}
				}
			}
			SpotDetail detail = new SpotDetail(spotVo.getSpotId(), spotVo.getArrivalTime(), spotVo.getDepartureTime(),
					spotVo.getSpotName(), spotVo.getPlaceId(), spotVo.getNote(), spotVo.getAddress(),
					spotVo.getLatitude(), spotVo.getLongitude(), spotVo.getPlaceType(), spotVo.getSpotImage());

			SpotData spot = new SpotData(spotVo.getDay(), spotVo.getDate(), new ArrayList<SpotDetail>(List.of(detail)));
			spotRes.add(spot);

		}
		Journey journey = journeyDao.findJourneyById(journeyId);
		if (journey == null) {
			return new SearchSpotAndRouteSortOutRes(ResMessage.JOURNEYID_NOT_EXISTED.getCode(), //
					ResMessage.JOURNEYID_NOT_EXISTED.getMessage());
		}
		int allDay = (int) ChronoUnit.DAYS.between(journey.getStartDate(), journey.getEndDate()) + 1;
		if (spotRes.size() < allDay) {
			outside: for (int i = 0; i < allDay; i++) {
				for (SpotData vo : spotRes) {
					if (vo.getDay() == (i + 1)) {
						continue outside;
					}
				}
				spotRes.add(new SpotData(i + 1, journey.getStartDate().plusDays(i), new ArrayList<>()));
			}
		}
		// 取得所有路徑資料 TODO 取得null需要返回錯誤訊息嗎?
		List<MapRoute> routeList = spotDao.getAllRouteByJourney(journeyId);

		return new SearchSpotAndRouteSortOutRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage(), spotRes, routeList);
	}

	/**
	 * 用行程id和day取得那天的List<Spot>
	 * 
	 * @param journeyId
	 * @param day
	 * @return
	 */
	public SearchSpotByIdAndDayRes searchSpotByIdAndDay(int journeyId, int day) {
		// 取得所有景點資料
		List<SpotDetail> spotList = spotDao.getAllSpotByIdAndDay(journeyId, day);
		Journey journey = journeyDao.findJourneyById(journeyId);
		if (!CollectionUtils.isEmpty(spotList)) {
			return new SearchSpotByIdAndDayRes(ResMessage.SUCCESS.getCode(), //
					ResMessage.SUCCESS.getMessage(), spotList, journey);
		}
		if (journey == null) {
			return new SearchSpotByIdAndDayRes(ResMessage.JOURNEYID_NOT_EXISTED.getCode(), //
					ResMessage.JOURNEYID_NOT_EXISTED.getMessage());
		}
		int totalDays = (int) ChronoUnit.DAYS.between(journey.getStartDate(), journey.getEndDate()) + 1;
		if (day <= 0 || day > totalDays) {
			return new SearchSpotByIdAndDayRes(ResMessage.JOURNEY_DAY_NOT_EXISTED.getCode(), //
					ResMessage.JOURNEY_DAY_NOT_EXISTED.getMessage());
		}
		return new SearchSpotByIdAndDayRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage(), null, journey);
	}

}
