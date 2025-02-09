package com.example.goosetrip.dao;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.goosetrip.dto.MapRoute;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.UpdateMapRouteReq;

@Mapper
public interface MapRouteDao {

	public int saveRoute(UpdateMapRouteReq req);
	
	public BasicRes updateRoute(UpdateMapRouteReq req);
	
	public MapRoute findRouteByPK(@Param("journeyId") int journeyId, @Param("day")int day, @Param("startTime")LocalDateTime startTime);
	
	public int deleteAllRoute(@Param("journeyId") int journeyId, @Param("day") int day);
	
	public int deleteRoute(@Param("journeyId") int journeyId, @Param("day") int day,@Param("startTime")LocalDateTime startTime);
	
	public int saveOrUpdateAllRoute(@Param("inputReqList")List<UpdateMapRouteReq > reqList);
	
	public int deleteRoutesByJourneyAndDay(@Param("journeyId") int journeyId, @Param("day") int day);
}
