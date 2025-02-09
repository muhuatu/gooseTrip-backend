package com.example.goosetrip.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.goosetrip.dto.Accommodation;

@Mapper
public interface AccommodationDao {

	/**
	 * 儲存住宿資訊
	 * 
	 * @param saveList
	 * @return
	 */
	public int saveRoomData(List<Accommodation> saveList);

	/**
	 * 取得住宿資訊
	 * 
	 * @param journeyId
	 * @return
	 */
	public List<Accommodation> getRoomData(int journeyId);

	/**
	 * 批量刪除住宿資訊
	 * 
	 * @param journeyId
	 * @param accommodationId
	 */
	public void deleteRoomData(@Param("journeyId") int journeyId,
			@Param("accommodationId") List<Integer> accommodationId);

	/**
	 * 取得編輯權限
	 * 
	 * @param userMail
	 * @return
	 */
	public int editAccommodation(@Param("userMail") String userMail, @Param("journeyId") int journeyId);

	/**
	 * 更新住宿資訊
	 * 
	 * @param saveList
	 * @return
	 */
	public void updateRoomData(Accommodation updateList);
	
	/**
	 * 停止編輯權限
	 * @param journeyId
	 */
	public void stopEditAccommodation(int journeyId);

}
