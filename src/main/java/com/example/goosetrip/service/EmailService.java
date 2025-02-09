package com.example.goosetrip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dao.UserDao;
import com.example.goosetrip.dto.Users;
import com.example.goosetrip.utils.TokenUtil;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.SendReq;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
    private ChatroomMembersService chatroomMembersService;
	
	public BasicRes sendVerificationEmail(SendReq req) {
		Users user = userDao.getInfoById(req.getEmail());
		if(user!=null && req.isRegister()) {
			return new BasicRes(ResMessage.ACCOUNT_IS_EXIST.getCode(), ResMessage.ACCOUNT_IS_EXIST.getMessage());
		}
		if(user==null && !req.isRegister()) {
			return new BasicRes(ResMessage.ACCOUNT_NOT_EXIST.getCode(), ResMessage.ACCOUNT_NOT_EXIST.getMessage());	
		}	
		
		try {
			String invitation = chatroomMembersService.putInvitation(req.getEmail());
			
			String subject = "帳號驗證";
			String message = String.format(
		                "%s 歡迎使用去哪鵝\n以下是你的驗證碼: %s\n請輸入該驗證碼完成驗證。",
		                req.getEmail(), invitation);
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(req.getEmail());
			mailMessage.setSubject(subject);
			mailMessage.setText(message);

			mailSender.send(mailMessage);
			return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());			
		} catch (Exception e) {
			System.out.println("error:"+ e);
			return new BasicRes(ResMessage.SEND_VERIFICATION_EMAIL_FAILED.getCode(),
					ResMessage.SEND_VERIFICATION_EMAIL_FAILED.getMessage());
		}
	}

}
