package com.example.goosetrip.service;

import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dao.MapPlaceDao;
import com.example.goosetrip.dto.MapPlace;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.UpdateMapPlaceReq;

@Service
public class MapPlaceService {

	@Autowired MapPlaceDao mapPlaceDao;
		//儲存地點
//		public BasicRes saveMapPlace(UpdateMapPlaceReq req) {
//			return null;
//		}
		//更新地點
	@Transactional(rollbackFor = Exception.class)
	public BasicRes saveMapPlace(UpdateMapPlaceReq req) {
	    // 檢查ID不得為空
	    if (!StringUtils.hasText(req.getPlaceId())) {
	        return new BasicRes(ResMessage.PLACE_ID_ERROR.getCode(), //
	                ResMessage.PLACE_ID_ERROR.getMessage());
	    }
	    // 檢查名稱不得為空
	    if(!StringUtils.hasText(req.getPlaceName())) {
	    	return new BasicRes(ResMessage.PLACE_NAME_ERROR.getCode(), //
	                ResMessage.PLACE_NAME_ERROR.getMessage());
	    }
	    //TODO 補檢查地址、經緯度、地點型態
	    // 處理經緯度：四捨五入到小數第七位
	    req.setLatitude(req.getLatitude().setScale(7, RoundingMode.HALF_UP));
	    req.setLongitude(req.getLongitude().setScale(7, RoundingMode.HALF_UP));

	    // 執行更新
	    mapPlaceDao.updatePlace(req);

	    return new BasicRes(ResMessage.PLACE_SAVE_SUCCESS.getCode(), //
	            ResMessage.PLACE_SAVE_SUCCESS.getMessage());
	}
	@Transactional(rollbackFor = Exception.class)
	public BasicRes saveAllMapPlace(List<UpdateMapPlaceReq> reqList) {
		// 檢查列表是否為空或空集合
	    if (reqList == null || reqList.isEmpty()) {
	        return new BasicRes(ResMessage.LIST_ERROR.getCode(), //
	                ResMessage.LIST_ERROR.getMessage());
	    }
	    for(UpdateMapPlaceReq req :reqList ) {
	    	// 檢查ID不得為空
		    if (!StringUtils.hasText(req.getPlaceId())) {
		        return new BasicRes(ResMessage.PLACE_ID_ERROR.getCode(), //
		                ResMessage.PLACE_ID_ERROR.getMessage());
		    }
		    // 檢查名稱不得為空
		    if(!StringUtils.hasText(req.getPlaceName())) {
		    	return new BasicRes(ResMessage.PLACE_NAME_ERROR.getCode(), //
		                ResMessage.PLACE_NAME_ERROR.getMessage());
		    }
		    // 地址檢查
            if (!StringUtils.hasText(req.getAddress())) {
                return new BasicRes(ResMessage.ADDRESS_ERROR.getCode(),
                        ResMessage.ADDRESS_ERROR.getMessage());
            }

            // placeType 檢查
            if (!StringUtils.hasText(req.getPlaceType())) {
                return new BasicRes(ResMessage.PLACE_TYPE_ERROR.getCode(),
                        ResMessage.PLACE_TYPE_ERROR.getMessage());
            }

            // 經緯度檢查
            if (req.getLatitude() == null || req.getLongitude() == null) {
                return new BasicRes(ResMessage.LATANDLONG_ERROR.getCode(),
                        ResMessage.LATANDLONG_ERROR.getMessage());
            }
		 // 處理經緯度：四捨五入到小數第七位
		    req.setLatitude(req.getLatitude().setScale(7, RoundingMode.HALF_UP));
		    req.setLongitude(req.getLongitude().setScale(7, RoundingMode.HALF_UP));
	    }
	    try {
	    	mapPlaceDao.saveAllPlace(reqList);
		} catch (Exception e) {
			return new BasicRes(ResMessage.DATA_ERROR.getCode(), //
	                ResMessage.DATA_ERROR.getMessage());
		}
	    return new BasicRes(ResMessage.PLACE_SAVE_SUCCESS.getCode(), //
	            ResMessage.PLACE_SAVE_SUCCESS.getMessage());
	}
	@Transactional(rollbackFor = Exception.class)
	public BasicRes updateAllPlace(List<UpdateMapPlaceReq> reqList) {
		if(reqList == null || reqList.isEmpty()) {
			return new BasicRes(ResMessage.LIST_ERROR.getCode(), //
	                ResMessage.LIST_ERROR.getMessage());
		}for(UpdateMapPlaceReq req :reqList ) {
	    	// 檢查ID不得為空
		    if (!StringUtils.hasText(req.getPlaceId())) {
		        return new BasicRes(ResMessage.PLACE_ID_ERROR.getCode(), //
		                ResMessage.PLACE_ID_ERROR.getMessage());
		    }
		    // 檢查名稱不得為空
		    if(!StringUtils.hasText(req.getPlaceName())) {
		    	return new BasicRes(ResMessage.PLACE_NAME_ERROR.getCode(), //
		                ResMessage.PLACE_NAME_ERROR.getMessage());
		    }
		    // 地址檢查
            if (!StringUtils.hasText(req.getAddress())) {
                return new BasicRes(ResMessage.ADDRESS_ERROR.getCode(),
                        ResMessage.ADDRESS_ERROR.getMessage());
            }

            // placeType 檢查
            if (!StringUtils.hasText(req.getPlaceType())) {
                return new BasicRes(ResMessage.PLACE_TYPE_ERROR.getCode(),
                        ResMessage.PLACE_TYPE_ERROR.getMessage());
            }

            // 經緯度檢查
            if (req.getLatitude() == null || req.getLongitude() == null) {
                return new BasicRes(ResMessage.LATANDLONG_ERROR.getCode(),
                        ResMessage.LATANDLONG_ERROR.getMessage());
            }
		 // 處理經緯度：四捨五入到小數第七位
		    req.setLatitude(req.getLatitude().setScale(7, RoundingMode.HALF_UP));
		    req.setLongitude(req.getLongitude().setScale(7, RoundingMode.HALF_UP));
		    
		    MapPlace existingPlace = mapPlaceDao.findAllById(req.getPlaceId());
		    
		    if(existingPlace == null) {
		    	mapPlaceDao.savePlace(req);
		    }else {
		    	mapPlaceDao.updatePlace(req);
		    }
	    }
	    
	    return new BasicRes(ResMessage.PLACE_SAVE_SUCCESS.getCode(), //
	            ResMessage.PLACE_SAVE_SUCCESS.getMessage());
	}
	
	
}
