package com.example.goosetrip.dao;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.goosetrip.dto.MapRoute;
import com.example.goosetrip.dto.Spot;
import com.example.goosetrip.vo.SpotDetail;

@Mapper
public interface SpotDao {

	/**
	 * 取特定行程中所有景點
	 * 
	 * @param int journeyId
	 * @return List<Spot>
	 */
	public List<Spot> getAllSpotByJourney(int journeyId);

	/**
	 * 新增景點
	 * 
	 * @param List<Spot> spots
	 * @return
	 */
	public void createSpot(List<Spot> spots);

	/**
	 * 刪除特定行程中所有景點
	 * 
	 * @param int journeyId
	 * @return
	 */
	public void deleteSpot(@Param("journeyId") int journeyId, @Param("day") int day);

	/**
	 * 更新特定行程中所有景點的日期
	 * 
	 * @param int       journeyId
	 * @param LocalDate newStartDate
	 * @return
	 */
	public void updateSpotDates(@Param("journeyId") int journeyId, @Param("newStartDate") LocalDate newStartDate);

	/**
	 * 搜尋特定行程中所有路徑
	 * 
	 * @param int journeyId
	 * @return List<MapRoute>
	 */
	public List<MapRoute> getAllRouteByJourney(int journeyId);

	/**
	 * 確認某行程中的景點是否存在
	 *
	 * @param journeyId
	 * @param spotId
	 * @param day
	 * @return Spot
	 */
	public Spot checkSpotExist(@Param("journeyId") int journeyId, @Param("spotId") int spotId, @Param("day") int day);

	/**
	 * 搜尋特定行程中特定天數的景點
	 *
	 * @param journeyId
	 * @param spotId
	 * @param day
	 * @return List<SpotDetail>
	 */
	public List<SpotDetail> getAllSpotByIdAndDay(int journeyId, int day);

}
