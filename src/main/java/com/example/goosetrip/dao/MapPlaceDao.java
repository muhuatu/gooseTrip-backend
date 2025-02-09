package com.example.goosetrip.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.goosetrip.dto.MapPlace;
import com.example.goosetrip.vo.UpdateMapPlaceReq;

@Mapper
public interface MapPlaceDao {

	public void savePlace(UpdateMapPlaceReq req);
	
	public void updatePlace(UpdateMapPlaceReq req);
	
	public MapPlace findAllById(String placeId);
	
	public void updatePlace1(String placeId,String placeName,String address,//
			BigDecimal longitude,BigDecimal latitude,String placeType);
	
	public void saveAllPlace(@Param("inputReqList") List<UpdateMapPlaceReq> reqList);
	
	public void updateAllPlace(@Param("inputReqList") List<UpdateMapPlaceReq> reqList);
	
	public void deleteAllPlace(@Param("inputReqList") List<UpdateMapPlaceReq> reqList);
}
