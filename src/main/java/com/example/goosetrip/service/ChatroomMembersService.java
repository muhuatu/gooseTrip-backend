package com.example.goosetrip.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dao.ChatroomMembersDao;
import com.example.goosetrip.dto.Users;
import com.example.goosetrip.vo.FindChatroomsRes;

@Service
public class ChatroomMembersService {

	@Autowired
	private ChatroomMembersDao chatroomMembersDao;

	// 此變數會使用SHA-256演算法
	private final MessageDigest digest;

	/**
	 * 此方法generateInvitation()將會把UUID縮成8碼的聊天室邀請碼
	 */
	public ChatroomMembersService() {
		try {
			this.digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("SHA-256 algorithm not available", e);
		}
	}

	/**
	 * 生成邀請碼
	 * 
	 * @return
	 */
	public String generateInvitation() {
		UUID uuid = UUID.randomUUID();
		byte[] hash = digest.digest(uuid.toString().getBytes(StandardCharsets.UTF_8));

		StringBuilder invitation = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			invitation.append(String.format("%02x", hash[i]));
		}

		return invitation.toString();
	}

	// 用來儲存驗證碼
	private Map<String, String> invitationCodes = new HashMap<>();

	/**
	 * 生成、儲存驗證碼
	 * 
	 * @param mail
	 * @return
	 */
	public String putInvitation(String mail) {
		String invitation = generateInvitation();
		invitationCodes.put(mail, invitation);
		System.out.println("驗證碼:" + invitation);
		return invitation;
	}

	/**
	 * 驗證驗證碼
	 * 
	 * @param mail
	 * @param code
	 * @return
	 */
	public boolean validateInvitationCode(String mail, String code) {
		return code.equals(invitationCodes.get(mail)); // 檢查驗證碼
	}

	/**
	 * 查詢該user的所有聊天室
	 * 
	 * @param user
	 * @return
	 */
	public FindChatroomsRes findChatrooms(Users user) {
		if (user.getUserMail() == null) {
			return new FindChatroomsRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(),
					ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}

		List<String> chatroomList = chatroomMembersDao.findChatroomsByUserMail(user.getUserMail());
		// 排除該 user 沒有行程
		if (chatroomList == null) {
			return new FindChatroomsRes(ResMessage.JOURNRY_NOT_EXISTED.getCode(),
					ResMessage.JOURNRY_NOT_EXISTED.getMessage());
		}
		return new FindChatroomsRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), chatroomList);
	}

}
