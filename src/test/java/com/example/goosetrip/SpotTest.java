package com.example.goosetrip;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.goosetrip.service.SpotService;

@SpringBootTest
//@Transactional
public class SpotTest {

	@Autowired
	private SpotService spotService;

	@Test
	public void searchSpotAndRouteSortOutTest() {
		spotService.searchSpotAndRouteSortOut(1);
	}
}
