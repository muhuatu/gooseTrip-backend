package com.example.goosetrip;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dao.UserDao;
import com.example.goosetrip.service.JourneyService;
import com.example.goosetrip.service.UserService;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.CheckJourneyRes;
import com.example.goosetrip.vo.CollaboratorRes;
import com.example.goosetrip.vo.LoginReq;
import com.example.goosetrip.vo.LoginRes;
import com.example.goosetrip.vo.RegisterUserReq;
import com.example.goosetrip.vo.RegisterUserRes;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsersTest {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
	private JourneyService journeyService;
    
    //userDao.deleteUser沒寫xml，問一下兔兔
//    @BeforeAll
//    public void setUp() {
//        // 測試用戶
//        Users testUser = new Users("test@test.com", "test", "0900999000", 
//                PasswordUtils.hashPassword("12345678"), "default.png");      
//        userDao.addUser(testUser);
//        // 測試用戶2
//        Users testUser2 = new Users("test2@test.com", "test2", "0900999000", 
//                PasswordUtils.hashPassword("12345678"), "default.png");      
//        userDao.addUser(testUser2);
//        //1跟2用戶加入行程1
//        journeyService.joinJourney("AB12CD34","test@test.com");
//        journeyService.joinJourney("AB12CD34","test2@test.com");   
//    }
    
//    @AfterAll
//    public void afterAll() {
//    	//刪掉測試用人員
//    	userService.leaveJourney("test@test.com");
//    	userDao.deleteUser("test@test.com", PasswordUtils.hashPassword("12345678"));
//    	userService.leaveJourney("test2@test.com");
//    	userDao.deleteUser("test2@test.com", PasswordUtils.hashPassword("12345678"));
//    }

    public void register() throws SQLException, IOException {
        RegisterUserReq reqDto = new RegisterUserReq();
        RegisterUserRes res = userService.register(reqDto);
        System.out.println(res.getCode());
    }
    
    @Test
    public void loginTest() {
    	//使用者不存在的情況
    	LoginReq req = new LoginReq("nonexistent","12345678");
    	LoginRes res = userService.login(req);
		Assert.isTrue(res.getMessage().equals(ResMessage.ACCOUNT_OR_PASSWORD_ERROR.getMessage()), "使用者不存在的情況失敗");
		
		//密碼錯誤的情況
		req.setUserMail("xiaoyi1@example.com");
		req.setPwd("passworderror");
		res = userService.login(req);
		Assert.isTrue(res.getMessage().equals(ResMessage.ACCOUNT_OR_PASSWORD_ERROR.getMessage()), "密碼錯誤的情況失敗");
		
		//成功的情況
		req.setPwd("12345678");
		res = userService.login(req);
		Assert.isTrue(res.getMessage().equals(ResMessage.SUCCESS.getMessage()), "成功的情況失敗");
    }
    
    @Test
    public void updateFavoriteTest() {
    	BasicRes res = userService.updateFavorite("ChIJt4XIVuk9aDQR1AV7r0O9bYs","xiaoyi1@example.com");
		Assert.isTrue(res.getMessage().equals(ResMessage.SUCCESS.getMessage()), "成功的情況失敗");  	
    }
    
    @Test
    public void checkJourneyTest() {
    	//使用者沒有行程的情況
    	CheckJourneyRes res =userService.checkJourney("test@test");
		Assert.isTrue(CollectionUtils.isEmpty(res.getEditList()), "使用者沒有行程的情況失敗");
		
		//成功的情況
		res =userService.checkJourney("test@test");
		Assert.isTrue(res.getMessage().equals(ResMessage.SUCCESS.getMessage()), "成功的情況失敗");
    }
    
    @Test
	public void findCollaboratorByJourneyIdTest() {
    	//使用者不在行程裡的情況
    	CollaboratorRes res =userService.findCollaboratorByJourneyId(1,"test@test");
		Assert.isTrue(CollectionUtils.isEmpty(res.getCollaboratorlist()), "使用者不在行程裡的情況失敗");
				
		//成功的情況
		res =userService.findCollaboratorByJourneyId(1,"xiaoyi1@example.com");
		Assert.isTrue(res.getMessage().equals(ResMessage.SUCCESS.getMessage()), "成功的情況失敗");
    }
}
