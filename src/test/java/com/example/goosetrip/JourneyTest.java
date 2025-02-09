package com.example.goosetrip;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.goosetrip.dao.JourneyDao;
import com.example.goosetrip.dto.Users;
import com.example.goosetrip.service.JourneyService;
import com.example.goosetrip.vo.BaseJourneyReq;
import com.example.goosetrip.vo.CreateJourneyRes;
import com.example.goosetrip.vo.UpdateJourneyReq;

@SpringBootTest
public class JourneyTest {

	@Autowired
	private JourneyService journeyService;

	@Autowired
	private JourneyDao journeyDao;

	@Test
	public void copyJourneyTest() {
		Users user = new Users("xiaoyi3@example.com", null, null, null, null, null, null, null, null);
		CreateJourneyRes res = journeyService.copyJourney(2, user.getUserMail());
		System.out.println(res.getJourneyId());
		System.out.println(res.getInvitation());
	}

	@Test
	public void createJourneyTest() {
		BaseJourneyReq journey = new BaseJourneyReq();
		journey.setJourneyName("台北之旅");
		journey.setInvitation("AB12CD34");
		journey.setStartDate(LocalDate.of(2024, 1, 1));
		journey.setEndDate(LocalDate.of(2024, 1, 7));
		journey.setTransportation("DRIVING");
		journey.setUserMail("bubuChacha@gmail.com");
		journeyDao.createJourney(journey);
	}

	@Test
	public void updateJourneyTest() {
		UpdateJourneyReq journey = new UpdateJourneyReq();
		journey.setJourneyId(17);
		journey.setJourneyName("台南之旅");
		journey.setStartDate(LocalDate.of(2024, 1, 1));
		journey.setEndDate(LocalDate.of(2024, 1, 7));
		journey.setTransportation("公車");
		journey.setUserMail("bobo@gmail.com");
		journeyDao.updateJourney(journey);
	}
}
