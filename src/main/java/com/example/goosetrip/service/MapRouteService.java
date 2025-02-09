package com.example.goosetrip.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dao.MapRouteDao;
import com.example.goosetrip.dto.MapRoute;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.UpdateMapRouteReq;

@Service
public class MapRouteService {

	@Autowired
	MapRouteDao mapRouteDao;

	/**
	 * 儲存路徑
	 * @param req
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public BasicRes saveMapRoute(UpdateMapRouteReq req) {
		// 檢查起點跟終點ID
		if (!StringUtils.hasText(req.getStartPlaceId()) || !StringUtils.hasText(req.getEndPlaceId())) {
			return new BasicRes(ResMessage.STARTORENDPLACE_ID_ERROR.getCode(), //
					ResMessage.STARTORENDPLACE_ID_ERROR.getMessage());
		}
		// 檢查交通方式
		if (!StringUtils.hasText(req.getTransportation())) {
			return new BasicRes(ResMessage.TRANSPORTATION_ERROR.getCode(), //
					ResMessage.TRANSPORTATION_ERROR.getMessage());
		}
		// 檢查開始時間(先不使用目前會跟前端衝突)
		if (!isValidStartTime(req.getStartTime())) {
			return new BasicRes(ResMessage.STARTTIME_ERROR.getCode(), //
					ResMessage.STARTTIME_ERROR.getMessage());
		}
		// 檢查路程時間()
		if (!isValidRouteTime(req.getRouteTime())) {
			return new BasicRes(ResMessage.ROUTETIME_ERROR.getCode(), //
					ResMessage.ROUTETIME_ERROR.getMessage());
		}
		// 檢查距離()
		if (!StringUtils.hasText(req.getDistance())) {
			return new BasicRes(ResMessage.DISTANCE_ERROR.getCode(), //
					ResMessage.DISTANCE_ERROR.getMessage());
		}
		// 檢查路線()
		if (!StringUtils.hasText(req.getRouteLine())) {
			return new BasicRes(ResMessage.ROUTELINE_ERROR.getCode(), //
					ResMessage.ROUTELINE_ERROR.getMessage());
		}

		mapRouteDao.saveRoute(req);
		return new BasicRes(ResMessage.ROUTE_SAVE_SUCCESS.getCode(), //
				ResMessage.ROUTE_SAVE_SUCCESS.getMessage());
	}

	/**
	 * 更新路徑
	 * @param req
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public BasicRes updateMapRoute(UpdateMapRouteReq req) {
		if (!StringUtils.hasText(req.getStartPlaceId()) || !StringUtils.hasText(req.getEndPlaceId())) {
			return new BasicRes(ResMessage.STARTORENDPLACE_ID_ERROR.getCode(), //
					ResMessage.STARTORENDPLACE_ID_ERROR.getMessage());
		}
		if (!StringUtils.hasText(req.getTransportation())) {
			return new BasicRes(ResMessage.TRANSPORTATION_ERROR.getCode(), //
					ResMessage.TRANSPORTATION_ERROR.getMessage());
		}
		// 根據 三個PK(startTime,journeyId,day)來查詢原有路徑
		MapRoute existingRoute = mapRouteDao.findRouteByPK(req.getJourneyId(), req.getDay(),req.getStartTime());
		if (existingRoute == null) {
			return new BasicRes(ResMessage.ROUTE_NOT_FOUND.getCode(), //
					ResMessage.ROUTE_NOT_FOUND.getMessage());
		}
		// 先刪除原先路徑資料
		mapRouteDao.deleteRoute(req.getJourneyId(),req.getDay(),req.getStartTime());
		// 更新路徑資訊
		existingRoute.setStartPlaceId(req.getStartPlaceId());
		existingRoute.setEndPlaceId(req.getEndPlaceId());
		existingRoute.setTransportation(req.getTransportation());
		existingRoute.setStartTime(req.getStartTime());
		existingRoute.setRouteTime(req.getRouteTime());
		existingRoute.setRouteLine(req.getRouteLine());
		existingRoute.setJourneyId(req.getJourneyId());
		existingRoute.setDay(req.getDay());
		// 重新存最新的路徑
		mapRouteDao.saveRoute(req);
		return new BasicRes(ResMessage.ROUTE_SAVE_SUCCESS.getCode(), //
				ResMessage.ROUTE_SAVE_SUCCESS.getMessage());
	}
	
	@Transactional(rollbackFor = Exception.class)
	public BasicRes saveOrUpdateAllRoute(List<UpdateMapRouteReq> reqList) {
	    // 檢查列表是否為空或空集合
	    if (reqList == null || reqList.isEmpty()) {
	        return new BasicRes(ResMessage.LIST_ERROR.getCode(), //
	                ResMessage.LIST_ERROR.getMessage());
	    }
	    // 遍歷檢查每一條請求
	    for (UpdateMapRouteReq req : reqList) {
	        // 檢查起點跟終點ID
	        if (!StringUtils.hasText(req.getStartPlaceId()) || !StringUtils.hasText(req.getEndPlaceId())) {
	            return new BasicRes(ResMessage.STARTORENDPLACE_ID_ERROR.getCode(), //
	                    ResMessage.STARTORENDPLACE_ID_ERROR.getMessage());
	        }
	        // 檢查交通方式
	        if (!StringUtils.hasText(req.getTransportation())) {
	            return new BasicRes(ResMessage.TRANSPORTATION_ERROR.getCode(), //
	                    ResMessage.TRANSPORTATION_ERROR.getMessage());
	        }
	        // 檢查路程時間()
	        if (!isValidRouteTime(req.getRouteTime())) {
	            return new BasicRes(ResMessage.ROUTETIME_ERROR.getCode(), //
	                    ResMessage.ROUTETIME_ERROR.getMessage());
	        }
	        // 檢查距離()
	        if (!StringUtils.hasText(req.getDistance())) {
	            return new BasicRes(ResMessage.DISTANCE_ERROR.getCode(), //
	                    ResMessage.DISTANCE_ERROR.getMessage());
	        }
	        // 檢查路線()
	        if (!StringUtils.hasText(req.getRouteLine())) {
	            return new BasicRes(ResMessage.ROUTELINE_ERROR.getCode(), //
	                    ResMessage.ROUTELINE_ERROR.getMessage());
	        }
//	        int journeyId = reqList.get(0).getJourneyId(); // 假設所有請求的 journeyId 相同
//	        int day = reqList.get(0).getDay(); // 假設所有請求的 day 相同
//	        LocalDateTime startTime = reqList.get(0).getStartTime();
//	        String startPlaceId = reqList.get(0).getStartPlaceId();
//	        String endPlaceId = reqList.get(0).getEndPlaceId();
	        mapRouteDao.deleteAllRoute(req.getJourneyId(), req.getDay());
	        
	    }
	    // 執行批量存儲
//	    try {
	    	// 刪除符合條件的舊資料
	       
	        mapRouteDao.saveOrUpdateAllRoute(reqList); // 改用批量儲存方法
//	    } catch (Exception e) {
//	        return new BasicRes(ResMessage.DATA_ERROR.getCode(), //
//	                ResMessage.DATA_ERROR.getMessage());
//	    }

	    return new BasicRes(ResMessage.ROUTE_SAVE_SUCCESS.getCode(), //
	            ResMessage.ROUTE_SAVE_SUCCESS.getMessage());
	}
	
	

	// 檢查開始時間必須大於現在時間
	private boolean isValidStartTime(LocalDateTime startTime) {
		if (startTime.isBefore(LocalDateTime.now())) {
			return false;
		}
		return true;
	}

	// 檢查 routeTime 是否為有效的時間
	private boolean isValidRouteTime(LocalTime routeTime) {
		// 檢查路徑時間不應為零或負
		if ((routeTime.getHour() == 0 && routeTime.getMinute() == 0 && routeTime.getSecond() == 0)
				|| routeTime == null) {
			return false;
		}
		return true;
	}
	public BasicRes saveOrUpdateRoute(UpdateMapRouteReq req) {
        // 查詢是否存在相同的資料
        MapRoute existingRoute = mapRouteDao.findRouteByPK(req.getJourneyId(), req.getDay(),req.getStartTime());
        
        if (existingRoute == null) {
            // 若資料不存在，執行新增邏輯
            return saveMapRoute(req);
        } else {
            // 若資料存在，執行更新邏輯
            return updateMapRoute(req);
        }
    }
	/**
	 * 將 00:00 格式轉換為 00:00:00 格式
	 * 
	 * @param routeTime 前端傳入的路程時間
	 * @return 轉換後的時間
	 */
	private String convertRouteTimeToFullFormat(String routeTime) {
	    if (routeTime != null && routeTime.matches("^\\d{2}:\\d{2}$")) {
	        return routeTime + ":00";
	    }
	    return routeTime; // 保持原始值，防止非預期格式出錯
	}
}
