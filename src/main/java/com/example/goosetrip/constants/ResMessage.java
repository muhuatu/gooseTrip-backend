package com.example.goosetrip.constants;

public enum ResMessage {

	SUCCESS(200, "成功"), //
	FAIL_EXCEPTION(400, "處理異常"), //

	// 管理員帳戶
	ACCOUNT_ERROR(400, "帳戶資訊錯誤"), //
	ACCOUNT_NOT_EXIST(404, "此帳戶不存在"), //
	ACCOUNT_IS_EXIST(400, "此帳戶已存在"), //
	INVALID_PASSWORD(400, "密碼錯誤"), //
	ADD_INFO_FAILED(400, "增加帳戶資訊失敗"), //
	ACCOUNT_OR_PASSWORD_ERROR(400, "帳號或密碼錯誤"), //
	PLEASE_LOGIN_FIRST(400, "請先登入帳戶"), //
	IMAGE_PROCESSING_FAILED(400, "頭貼轉換格式失敗"), //
	IMAGE_NOT_FOUND(404, "請選擇頭貼"), //
	UPDATE_FAILED(400, "更新帳戶資訊失敗"), // IMAGE_NOT_FOUND
	SEND_VERIFICATION_EMAIL_FAILED(400, "驗證信寄送失敗"),//
	VERIFY_FAILED(400, "驗證碼錯誤"),//
	USER_IS_EDITING(400,"該使用者正在編輯中"),//
	UPDATE_MYFAVORITE_FAILED(400, "更新我的最愛失敗"),//

	// 聊天室
	PARAMETER_ERROR(400, "參數錯誤"), //
	JOURNRY_NOT_EXISTED(404, "此用戶尚未有行程"), //
	CHATROOM_NOT_EXISTED(404, "此聊天室不存在"), //
	CHAT_TYPE_ERROR(400, "聊天類型不符"), //

	// 行程管理
	CREATE_JOURNEY_FAILED(400, "建立行程失敗"), //
	UPDATE_JOURNEY_FAILED(400, "更新行程失敗"), //
	DELETE_JOURNEY_FAILED(400, "刪除行程失敗"), //
	CREATE_SPOTS_FAILED(400, "新增景點失敗"),
	INVITATION_NOT_EXISTS(400, "邀請碼不存在"), //
	ALREADY_JOINED(400, "已加入過該行程"), //
	JOURNEYID_ERROR(400, "行程ID錯誤"), //
	JOURNEYID_NOT_EXISTED(400, "該行程不存在"), //
	JOURNEY_DAY_NOT_EXISTED(404, "這個行程沒有這一天"), //
	MULTIPLE_EDITS_ARE_NOT_ALLOWED(400,"無法同時編輯多天"),//
	DUPLICATE_EDITING_WITH_OTHERS(400,"已有人正在編輯該天"),//
	NOT_EDITING(400,"沒有正在編輯該天"),//
	ETD_ETA_NOT_FOUND(404,"開始與結束時間不可為空白"),//
	MEMBER_NOT_IN_JOURNEY(400,"尚未加入此行程"),//
	
	// 景點規劃
	CREATE_SPOT_FAILED(400, "新增景點失敗"), //
	CREAT_SPOT_SUCCESS(200,"行程景點儲存成功"),//

	// 住宿資訊
	NOT_FIND_HOTEL(400, "沒有找到飯店資訊"), //
	FIND_HOTEL_FAILED(400, "查詢飯店資訊失敗"), //
	INSERT_ROOM_FAILED(400, "新增飯店房間失敗"), //
	CHECKIN_DAY_IS_ERROR(400,"入住日期錯誤"),//
	CHECKOUT_DAY_IS_ERROR(400,"退住日期錯誤"),//
	GET_EDIT_PURVIEW_FAILED(400,"取得住宿編輯權限失敗"),//

	// 地點及路徑資訊
	PLACE_ID_ERROR(400, "地點ID錯誤"), //
	PLACE_NAME_ERROR(400, "地點名稱錯誤"), //
	STARTORENDPLACE_ID_ERROR(400, "開始地點或結束地點ID錯誤"),//
	TRANSPORTATION_ERROR(400, "交通方式不正確"),//
	ROUTE_NOT_FOUND(404, "找不到此路徑"),//
	STARTTIME_ERROR(400,"開始時間錯誤"),//
	ROUTETIME_ERROR(400,"路徑時間錯誤"),//
	DISTANCE_ERROR(400,"距離格式錯誤"),//
	ROUTELINE_ERROR(400,"路線格式錯誤"),//
	LIST_ERROR(400,"陣列資料錯誤"),//
	DATA_ERROR(400,"資料錯誤"),//
	ADDRESS_ERROR(400,"地址錯誤"),//
	PLACE_TYPE_ERROR(400,"地點型態錯誤"),//
	LATANDLONG_ERROR(400,"經緯度錯誤"),//
	ROUTE_SAVE_SUCCESS(200, "路徑儲存成功"),//
	PLACE_SAVE_SUCCESS(200,"地點儲存成功"),

	// 評論
	REPLY_COMMENT_NOT_EXIST(404, "找不到回覆評論編號"), //
	COMMENT_INSERT_FAILED(400, "新增評論失敗"), //
	COMMENT_UPDATE_FAILED(400, "更新評論失敗"), //
	COMMENT_DELETE_PERMISSION_DENIED(400, "只能刪除自己的評論"), //
	COMMENT_DELETE_FAILED(400, "刪除評論失敗"), //

	// 貼文
	FAVORITE_POST_NOT_FOUND(404, "無收藏的貼文"), //
	FAVORITE_POST_NOT_CHOICE(404, "未選擇移除的貼文"), //
	POST_ID_ERROR(400, "貼文ID不可為空"), //
	POST_CREATE_ERROR(400, "新增貼文失敗"), //
	POST_IS_DUPLICATE(400, "您已發布過此行程的貼文"), //
	NO_SPOTS_IN_POST(400, "此行程沒有景點，無法發布貼文"), //
	SPOT_ERROR_IN_POST(400, "此行程沒有對應景點，無法發布貼文"), //
	USER_AND_JOURNEY_NOT_MATCH(400, "您沒有關聯此行程"), //
	POST_DATE_ERROR(400, "發文日期不可小於結束日期"), //
	POST_NOT_FOUND(404, "此貼文不存在"), //
	JOURNEY_AND_POST_NOT_MATCH(404, "此行程無此貼文"), //
	POST_DATA_ERROR(404, "貼文景點內容錯誤"), //
	CAN_NOT_ADD_OWN_POST(400, "不可收藏自己的貼文"), //
	CAN_NOT_INTERACT_WITH_SELF_POST(400, "按讚不適用於自己的貼文"), //
	POST_CONTENT_ERROR(400, "貼文內容不可為空白"), //
	;

	private int code;

	private String message;

	private ResMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
