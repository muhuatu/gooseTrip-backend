package com.example.goosetrip;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.goosetrip.dao.MapPlaceDao;

@SpringBootTest
class GoosetripApplicationTests {
	@Autowired MapPlaceDao mapPlaceDao;

	@Test
	void contextLoads() {
	}

	@Test
	public void updatePlace() {
		// 測試方法
	    // 將原始經緯度四捨五入到小數點第7位
	    BigDecimal latitude = BigDecimal.valueOf(25.034557512810423).setScale(7, RoundingMode.HALF_UP);
	    BigDecimal longitude = BigDecimal.valueOf(121.56393774067081).setScale(7, RoundingMode.HALF_UP);

	    // 呼叫 DAO 方法
	    mapPlaceDao.updatePlace1("ChIJ____27arQjQR0maAA0Yaqi0", "台北101", "110台灣台北市信義區市府路45號4樓",
	            latitude, longitude, "bar");
	}
}
