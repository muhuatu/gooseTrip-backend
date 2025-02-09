package com.example.goosetrip.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dto.Users;
import com.example.goosetrip.service.ChatroomMembersService;
import com.example.goosetrip.service.EmailService;
import com.example.goosetrip.service.UserService;
import com.example.goosetrip.utils.TokenUtil;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.CheckJourneyRes;
import com.example.goosetrip.vo.CollaboratorRes;
import com.example.goosetrip.vo.DeleteUserReq;
import com.example.goosetrip.vo.ForgetPwdReq;
import com.example.goosetrip.vo.IsFavoriteRes;
import com.example.goosetrip.vo.JourneyIdReq;
import com.example.goosetrip.vo.LoginReq;
import com.example.goosetrip.vo.LoginRes;
import com.example.goosetrip.vo.PlaceIdReq;
import com.example.goosetrip.vo.RegisterUserReq;
import com.example.goosetrip.vo.RegisterUserRes;
import com.example.goosetrip.vo.SearchFavoriteRes;
import com.example.goosetrip.vo.SearchUserRes;
import com.example.goosetrip.vo.SendReq;
import com.example.goosetrip.vo.UpdateUserEditReq;
import com.example.goosetrip.vo.UpdateUserReq;
import com.example.goosetrip.vo.UpdateUserRes;
import com.example.goosetrip.vo.UserleaveJourneyReq;
import com.example.goosetrip.vo.VerifyCodeReq;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("user/")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ChatroomMembersService chatroomMembersService;

	@GetMapping(value = "search")
	public SearchUserRes findAllUsers() {
		return userService.findAllUsers();
	}

	@PostMapping(value = "delete") 
	public BasicRes deleteUser(@Valid @RequestBody DeleteUserReq req) {
		return userService.deleteUser(req);
	}

	@PostMapping(value = "update")
	public BasicRes update(@RequestBody UpdateUserReq req, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new BasicRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		UpdateUserRes res = userService.updateUserInfo(req);
		// 若成功則存進 Session
		if (res.getCode() == 200) {
			session.setAttribute("user", res.getUser());
		}
		return new UpdateUserRes(res.getCode(), res.getMessage(), res.getUser());
	}

	@PostMapping(value = "register")
	public RegisterUserRes register(@RequestBody RegisterUserReq req, HttpSession session)
			throws IOException, SQLException {
		RegisterUserRes res = userService.register(req);
		if (res.getCode() == 200) {
			session.setAttribute("user", res.getUser());
		}
		return new RegisterUserRes(res.getCode(), res.getMessage(), res.getUser());
	}

	@PostMapping(value = "login")
	public LoginRes login(@RequestBody LoginReq req, HttpSession session) {
		// 確認是否已有先登入了
		// 如果已經登入成功，在session中有暫存帳號，也表示已經確認過帳號密碼
		Users attr = (Users) session.getAttribute("user");
		if (attr != null && attr.getUserMail().equals(req.getUserMail())) {
			return new LoginRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), attr);
		}
		LoginRes res = userService.login(req);
		if (res.getCode() == 200) {
			session.setMaxInactiveInterval(7200); //2小時
			session.setAttribute("user", res.getUser());
			Users userssss = (Users) session.getAttribute("user");
			System.out.println(userssss);
		}
		return new LoginRes(res.getCode(), res.getMessage(), res.getUser());
	}

	// 登出
	@GetMapping(value = "logout")
	public BasicRes logout(HttpSession session) {
		// 讓session失效 --> session失效後，再次登入，會得到新的session_id
		session.invalidate();
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	// 確定地點是不是我的最愛
	@PostMapping(value = "is_favorite")
	public IsFavoriteRes isFavorite(@Valid @RequestBody PlaceIdReq req, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null || !StringUtils.hasText(attr.getUserFavorite())) {
			return new IsFavoriteRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), false);
		}

		List<String> userFavorite = new ArrayList<>(Arrays.asList(attr.getUserFavorite().trim().split(",")));
		return new IsFavoriteRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(),
				userFavorite.contains(req.getPlaceId()));
	}

	// 加入或移除我的最愛
	@PostMapping(value = "update_favorite")
	public BasicRes updateFavorite(@RequestBody PlaceIdReq req, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		String newFavorite;
		if (attr == null) {
			return new BasicRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}

		String mail = attr.getUserMail();
		// 如果我的最愛為空，直接新增地點
		if (!StringUtils.hasText(attr.getUserFavorite())) {
			newFavorite = req.getPlaceId();
		} else {
			// 如果我的最愛有東西，把string變成list
			List<String> userFavorite = new ArrayList<>(Arrays.asList(attr.getUserFavorite().trim().split(",")));
			// 判斷list裡面有沒有這個placeId
			if (userFavorite.contains(req.getPlaceId())) {
				// 有，就移除該地點
				userFavorite.remove(req.getPlaceId());
				newFavorite = !CollectionUtils.isEmpty(userFavorite) ? String.join(",", userFavorite) : "";
			} else {
				// 沒有，就新增該地點
				newFavorite = attr.getUserFavorite().concat("," + req.getPlaceId());
			}
		}
		attr.setUserFavorite(newFavorite);
		session.setAttribute("user", attr);
		return userService.updateFavorite(newFavorite, mail);
	}

	// 查看我的最愛
	@GetMapping(value = "search_favorite")
	public SearchFavoriteRes searchFavorite(HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new SearchFavoriteRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(),
					ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		if (!StringUtils.hasText(attr.getUserFavorite())) {
			return new SearchFavoriteRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
		}
		return new SearchFavoriteRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(),
				Arrays.asList(attr.getUserFavorite().trim().split(",")));
	}

	// 把地點加入我的行程
	@GetMapping(value = "check_journey")
	public CheckJourneyRes checkJourney(HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new CheckJourneyRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(),
					ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		if (StringUtils.hasText(attr.getUserEdit())) {
			return new CheckJourneyRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), null, true);
		}
		return userService.checkJourney(attr.getUserMail());
	}

	// 用行程Id查詢共同編輯者
	@PostMapping(value = "find_collaborator")
	public CollaboratorRes findCollaboratorByJourneyId(@RequestBody JourneyIdReq req, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new CollaboratorRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(),
					ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return userService.findCollaboratorByJourneyId(req.getJourneyId(), attr.getUserMail());
	}

	// 退出行程
	@PostMapping(value = "leave_journey")
	public BasicRes UserleaveJourney(@RequestBody UserleaveJourneyReq req, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new BasicRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		BasicRes res = userService.userleaveJourney(req.getUserMail(), req.getJourneyId());
		return res;
	}

	// 開始編輯時修改user表的user_edit欄位
	@PostMapping(value = "startUpdateUserEdit")
	public BasicRes startUpdateUserEdit(@RequestBody UpdateUserEditReq req, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new BasicRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		attr.setUserEdit(req.getUserEdit());
		session.setAttribute("user", attr);
		return userService.updateUserEdit(attr.getUserMail(), req.getUserEdit(), true);
	}

	// 結束編輯時修改user表的user_edit欄位
	@PostMapping(value = "endUpdateUserEdit")
	public BasicRes endUpdateUserEdit(@RequestBody UpdateUserEditReq req, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new BasicRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		attr.setUserEdit(null);
		return userService.updateUserEdit(attr.getUserMail(), req.getUserEdit(), false);
	}

	// 寄驗證信
	@PostMapping(value = "send")
	public BasicRes sendVerificationEmail(@RequestBody SendReq req) {
		return emailService.sendVerificationEmail(req);
	}

	// 驗證碼驗證
	@PostMapping(value = "verify-code")
	public BasicRes verifyCode(@RequestBody VerifyCodeReq req) {
		boolean isValid = chatroomMembersService.validateInvitationCode(req.getMail(), req.getVerificationCode());

		if (isValid) {
			return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
		}
		return new BasicRes(ResMessage.VERIFY_FAILED.getCode(), ResMessage.VERIFY_FAILED.getMessage());
	}

	// 忘記密碼
	@PostMapping(value = "forget_pwd")
	public BasicRes forgetPwd(@RequestBody ForgetPwdReq req) {
		return userService.setPwd(req.getMail(), req.getPwd(), req.getVerificationCode());
	}

}
