package com.example.goosetrip.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dao.CommentDao;
import com.example.goosetrip.dao.InteractDao;
import com.example.goosetrip.dao.JourneyDao;
import com.example.goosetrip.dao.PostDao;
import com.example.goosetrip.dao.SpotDao;
import com.example.goosetrip.dao.UserDao;
import com.example.goosetrip.dto.Journey;
import com.example.goosetrip.dto.Spot;
import com.example.goosetrip.dto.Users;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.CommentList;
import com.example.goosetrip.vo.DeletePostReq;
import com.example.goosetrip.vo.FavoritePostReq;
import com.example.goosetrip.vo.GetPostRes;
import com.example.goosetrip.vo.JourneyIdReq;
import com.example.goosetrip.vo.PostDetail;
import com.example.goosetrip.vo.PostList;
import com.example.goosetrip.vo.PostListRes;
import com.example.goosetrip.vo.PostReq;
import com.example.goosetrip.vo.PostSpecificReq;
import com.example.goosetrip.vo.PostSpecificRes;
import com.example.goosetrip.vo.PostVo;
import com.example.goosetrip.vo.UpdatePostReq;

@Service
public class PostService {

	@Autowired
	private PostDao postDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private SpotDao spotDao;

	@Autowired
	private JourneyDao journeyDao;

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private InteractDao interactDao;

	/**
	 * 查詢使用者貼文
	 *
	 * @param userMail
	 * @param sessionUserMail
	 * @return
	 */
	public PostListRes getPostByMail(String userMail, String sessionUserMail) {

		// 1. 檢查參數
		if (!StringUtils.hasText(userMail)) {
			return new PostListRes(ResMessage.PARAMETER_ERROR.getCode(), //
					ResMessage.PARAMETER_ERROR.getMessage());
		}
//		if (!StringUtils.hasText(sessionUserMail)) {
//			return new PostListRes(ResMessage.PARAMETER_ERROR.getCode(), //
//					ResMessage.PARAMETER_ERROR.getMessage());
//		}
		// 2. 操作資料庫調出資料
		List<PostList> postList = postDao.getPostByUserMail(userMail);
		// 如果 postList = null，前端會看見沒有貼文的預設畫面
		// A. Session 中的使用者信箱與請求的使用者信箱相同
		// --> 查詢該登入使用者的貼文
		if (userMail == sessionUserMail) {
			return new PostListRes(ResMessage.SUCCESS.getCode(), //
					ResMessage.SUCCESS.getMessage(), postList);
		} else {
			// B. 查詢其他使用者的貼文
			// a. 找出該使用者的收藏貼文Id
			String favoritePost = postDao.getFavoritePosts(sessionUserMail);
			// b.1 創建放已收藏的行程ID的Set
			Set<Integer> favoriteIdsSet = new HashSet<>();
			// b.2 去掉,後在放入list
			if (favoritePost != null && !favoritePost.isEmpty()) {
				String[] ids = favoritePost.split(",");
				for (String postId : ids) {
					favoriteIdsSet.add(Integer.parseInt(postId)); // 把收藏的行程ID放回Set
				}
			}

			for (PostList item : postList) {
				// C. 比對 favoritePost 把 favorite 屬性 set 到 postList 中
				item.setFavorite(favoriteIdsSet.contains(item.getPostId()));
				// D. 檢查blob 圖片不是 null 再進行格式轉換 --> more strict method?
				if (item.getSpotImgData() != null) {
					// 把撈出來的 blob 圖片轉成 Base64 編碼的字串
					item.setSpotImage(Base64.getEncoder().encodeToString(item.getSpotImgData()));
				}
			}
		}
		return new PostListRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage(), postList);
	}

	/**
	 * 刪除使用者貼文
	 *
	 * @param req
	 * @param sessionUserMail
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public BasicRes deletePost(DeletePostReq req, String sessionUserMail) {

		// 操作資料庫刪除貼文
		postDao.deletePost(sessionUserMail, req.getPostIdList());
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	/**
	 * 用關鍵字查詢貼文
	 *
	 * @param keyword
	 * @param sessionUserMail
	 * @return
	 */
	public GetPostRes searchPostByKeyword(String keyword, String sessionUserMail) {

		Users userDB = userDao.getInfoById(sessionUserMail);
//		if (userDB == null) {
//			return new GetPostRes(ResMessage.ACCOUNT_NOT_EXIST.getCode(), ResMessage.ACCOUNT_NOT_EXIST.getMessage());
//		}

		// 1. 關鍵字為空，設定為 null

		// 2. 確認是否在收藏貼文中
		String favoritePosts = postDao.getFavoritePosts(sessionUserMail);
		// 創建 已收藏的貼文ID 的List
		List<Integer> favoriteIdsList = new ArrayList<>();
		// 去掉,後在放入list裡面
		if (StringUtils.hasText(favoritePosts)) {
			String[] ids = favoritePosts.split(",");
			for (String postId : ids) {
				favoriteIdsList.add(Integer.parseInt(postId)); // 把收藏貼文ID放回List
			}
		}

		// 3. 查詢結果
		List<PostList> postList = postDao.searchPostByKeyword(keyword);
		// 判斷貼文是否為空
		if (postList == null || postList.isEmpty()) {
			return new GetPostRes(ResMessage.POST_NOT_FOUND.getCode(), ResMessage.POST_NOT_FOUND.getMessage());
		}
		// 確認此人信箱有無收藏貼文
//		if (StringUtils.hasText(userDB.getFavoritePost())) {
			// 判斷是否為收藏貼文
			for (PostList post : postList) {
				if (favoriteIdsList.contains(post.getPostId())) {
					post.setFavorite(true); // 使用者的收藏貼文ID與此次查詢到的貼文ID
				} else {
					post.setFavorite(false);
				}
				// 檢查blob 圖片不是 null 再進行格式轉換
				if (post.getSpotImgData() != null) {
					// 把撈出來的 blob 圖片轉成 Base64 編碼的字串
					post.setSpotImage(Base64.getEncoder().encodeToString(post.getSpotImgData()));
				}
			}
//		}
		return new GetPostRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), postList);
	}

	/**
	 * 移除收藏貼文(可多筆，會員資料頁的前端可做多選框)
	 *
	 * @param req
	 * @param sessionUserMail
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public BasicRes deleteFavoritePost(DeletePostReq req, String sessionUserMail) {
		// 目的：從 Users 資料表 favorite_post 的 String 刪除收藏貼文
		// 想法：將資料庫的 String 轉為 List，根據 req 刪除貼文ID後，轉 String 存回資料庫
		// 資料庫："1,5,10"、前端：[1,5,10]

		// 從資料庫拿資料
		String favoritePosts = postDao.getFavoritePosts(sessionUserMail);

		// 1. 無收藏貼文 TODO 改StringUtils.hasText
		if (favoritePosts == null || favoritePosts.isEmpty()) {
			return new BasicRes(ResMessage.FAVORITE_POST_NOT_FOUND.getCode(),
					ResMessage.FAVORITE_POST_NOT_FOUND.getMessage());
		}

		// 2. 有收藏貼文
		// 2.1 將資料庫的 String 轉為 List
		List<String> favoritePostsDBList = new ArrayList<>(
				Arrays.asList(favoritePosts.replace("[", "").replace("]", "").split(",")));
		// 2.2 遍歷要刪除的貼文ID清單
		for (String postId : req.getPostIdList()) {
			favoritePostsDBList.remove(postId.trim());
		}

		// 3. 更新資料庫（格式改回字串 "1,10"）
		// 3.1 比較 資料庫List 與 新增後List 的大小，大於資料庫的話才會戳它新增
		if (favoritePostsDBList.size() < Arrays.asList(favoritePosts.split(",")).size()) {
			// 把 List 變回字串
			String result = String.join(",", favoritePostsDBList);
			// 把字串加回資料庫
			postDao.updateFavoritePostByUserMail(sessionUserMail, result);
			return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
		} else {
			// 如果收藏清單的大小沒有變更 -> 未選擇移除的貼文
			return new BasicRes(ResMessage.FAVORITE_POST_NOT_CHOICE.getCode(),
					ResMessage.FAVORITE_POST_NOT_CHOICE.getMessage());
		}
	}

	/**
	 * 更新收藏貼文（加入或移除一筆）
	 *
	 * @param req
	 * @param sessionUserMail
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public BasicRes updateFavoritePost(FavoritePostReq req, String sessionUserMail) {

		// 1. 將ID轉型
		String postId = Integer.toString(req.getPostId());
		// 不可收藏自己的貼文 TODO 可以反向查詢
		List<PostList> userFavoritePostList = postDao.getPostByUserMail(sessionUserMail);
		for (PostList post : userFavoritePostList) {
			if (post.getPostId() == req.getPostId()) {
				return new BasicRes(ResMessage.CAN_NOT_ADD_OWN_POST.getCode(),
						ResMessage.CAN_NOT_ADD_OWN_POST.getMessage());
			}
		}

		// 2. 從資料庫拿資料
		String favoritePosts = postDao.getFavoritePosts(sessionUserMail);

		// 3. 將資料庫的 String 轉為 List
		List<String> favoritePostsList = new ArrayList<>();
		if (favoritePosts != null && !favoritePosts.isEmpty()) {
			// 資料庫有內容時，轉為 List // 是否需要去除左右中括號 189 => 要去除，在248改格式了
			favoritePostsList = new ArrayList<>(Arrays.asList(favoritePosts.split(",")));
		}

		// 4. 判斷是加入或移除
		if (!favoritePostsList.contains(postId)) {
			favoritePostsList.add(postId);
		} else {
			favoritePostsList.remove(postId);
		}

		// 5. 更新資料庫（格式改回 "1,10"）
		String result = String.join(",", favoritePostsList);
		postDao.updateFavoritePostByUserMail(sessionUserMail, result);
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	/**
	 * 確認該貼文是否有收藏
	 *
	 * @param postId
	 * @param sessionUserMail
	 * @return
	 */
	public boolean isFavoritePost(Integer postId, String sessionUserMail) {
		String favoritePosts = postDao.getFavoritePosts(sessionUserMail);
		// TODO 改用StringUtils.hasText
		if (favoritePosts == null || favoritePosts.isEmpty()) {
			return false;
		}
		return Arrays.asList(favoritePosts.split(",")).contains(postId.toString());
	}

	/**
	 * 新增貼文
	 *
	 * @param req
	 * @param sessionUserMail
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public BasicRes createPost(PostReq req, String sessionUserMail) {

		// 取出 postDetail
		List<PostDetail> details = req.getPostDetail();

		// 確認使用者是否有這個行程
		if (!userDao.findJourneyByUserMail(sessionUserMail).contains(req.getJourneyId())) {
			return new BasicRes(ResMessage.USER_AND_JOURNEY_NOT_MATCH.getCode(),
					ResMessage.USER_AND_JOURNEY_NOT_MATCH.getMessage());
		}

		// 發文日期不可小於結束日期
		Journey journey = journeyDao.findJourneyById(req.getJourneyId());
		if (transferLocalDate(req.getPostTime()).isBefore(journey.getEndDate())) {
			return new BasicRes(ResMessage.POST_DATE_ERROR.getCode(), ResMessage.POST_DATE_ERROR.getMessage());
		}

		for (PostDetail d : details) {

			// 景點內容檢核
			if (!StringUtils.hasText(d.getSpotName())) { // 貼文景點內容錯誤
				return new BasicRes(ResMessage.POST_DATA_ERROR.getCode(), ResMessage.POST_DATA_ERROR.getMessage());
			}

			if (d.getDuration() < 0) {
				return new BasicRes(ResMessage.POST_DATA_ERROR.getCode(), ResMessage.POST_DATA_ERROR.getMessage());
			}

			if (!StringUtils.hasText(d.getPlaceId())) {
				return new BasicRes(ResMessage.POST_DATA_ERROR.getCode(), ResMessage.POST_DATA_ERROR.getMessage());
			}

			if (StringUtils.hasText(d.getSpotNote()) && d.getSpotNote().length() > 501) {
				return new BasicRes(ResMessage.POST_DATA_ERROR.getCode(), ResMessage.POST_DATA_ERROR.getMessage());
			}

			// 確認該行程+景點+第幾天三個PK有對上資料庫
			Spot spot = spotDao.checkSpotExist(req.getJourneyId(), d.getSpotId(), d.getDay());
			if (spot == null) {
				return new BasicRes(ResMessage.SPOT_ERROR_IN_POST.getCode(),
						ResMessage.SPOT_ERROR_IN_POST.getMessage());
			}
			// 用行程ID和使用者信箱檢查貼文是否重複
			int postExisted = postDao.findPostByUserMailAndJourneyId(sessionUserMail, req.getJourneyId(), d.getSpotId(),
					d.getDay());
			if (postExisted > 0) {
				return new BasicRes(ResMessage.POST_IS_DUPLICATE.getCode(), ResMessage.POST_IS_DUPLICATE.getMessage());
			}

			// 有Base64圖片編碼再進行格式轉換
			if (d.getSpotImage() != null) {
				// 把Base64編碼的spotImage字串轉成byte[]，用byte[]格式的spotImageData接
				d.setSpotImgData(Base64.getDecoder().decode(d.getSpotImage()));
			}
		}

		// 設定貼文ID
		int postId = postDao.getMaxPostId() + 1;

		// 貼文內容不可為空
		if (!StringUtils.hasText(req.getPostContent()) || req.getPostContent().length() > 501) {
			return new PostSpecificRes(ResMessage.POST_CONTENT_ERROR.getCode(),
					ResMessage.POST_CONTENT_ERROR.getMessage());
		}

		// 將此貼文加入資料庫
		int res = postDao.insertPost(postId, req.getJourneyId(), sessionUserMail, req.getPostContent(),
				req.getPostTime(), details);
		// 新增貼文失敗時回傳錯誤訊息
		if (res == 0) {
			return new BasicRes(ResMessage.POST_CREATE_ERROR.getCode(), ResMessage.POST_CREATE_ERROR.getMessage());
		}

		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	/**
	 * 取得特定貼文內容
	 *
	 * @param req
	 * @param sessionUserMail
	 * @return
	 */
	public PostSpecificRes getPost(PostSpecificReq req, String sessionUserMail) {

		// 比對行程ID與貼文ID是否對應
		if (postDao.checkJourneyIdAndPostId(req.getPostId(), req.getJourneyId()) == 0) {
			return new PostSpecificRes(ResMessage.JOURNEY_AND_POST_NOT_MATCH.getCode(),
					ResMessage.JOURNEY_AND_POST_NOT_MATCH.getMessage());
		}

		// 從資料庫拿資料
		List<PostVo> allPost = postDao.findAllByPostId(req.getPostId());
		List<Spot> allSpot = spotDao.getAllSpotByJourney(req.getJourneyId());
		List<CommentList> allComment = commentDao.getAllReply(req.getPostId());
		// 查詢此人是否有對此貼文按讚
		Boolean thumbStatus = interactDao.findThumbStatus(req.getPostId(), sessionUserMail);

		// 檢查貼文與對應的行程是否存在(評論可為空)
		if (allPost == null || allPost.isEmpty()) {
			return new PostSpecificRes(ResMessage.POST_NOT_FOUND.getCode(), ResMessage.POST_NOT_FOUND.getMessage());
		}
		if (allSpot == null || allSpot.isEmpty()) {
			return new PostSpecificRes(ResMessage.SPOT_ERROR_IN_POST.getCode(),
					ResMessage.SPOT_ERROR_IN_POST.getMessage());
		}

		// 裝景點的容器(要放停留時間)
		List<PostDetail> postSpotDto = new ArrayList<>();

		// 計算停留時間後將結果放回DTO
		for (PostVo post : allPost) {
			Spot relatedSpot = null;
			for (Spot spot : allSpot) {
				if (spot.getSpotId() == post.getSpotId() && spot.getDay() == post.getDay()) {
					relatedSpot = spot;
					break; // 如果spot跟post的景點對上了就跳出迴圈
				}
			}

			// 塞一筆筆景點資料
			PostDetail detail = new PostDetail();

			detail.setDay(post.getDay());
			detail.setSpotId(post.getSpotId());
			if (post.getSpotImgData() != null) {
				detail.setSpotImage(Base64.getEncoder().encodeToString(post.getSpotImgData()));
			}
			detail.setSpotName(post.getSpotName());
			detail.setPlaceId(post.getPlaceId());
			detail.setSpotNote(post.getSpotNote());

			// 放入停留時間
			if (relatedSpot != null) {
				if (relatedSpot.getArrivalTime() == null || relatedSpot.getDepartureTime() == null) {
					return new PostSpecificRes(ResMessage.ETD_ETA_NOT_FOUND.getCode(),
							ResMessage.ETD_ETA_NOT_FOUND.getMessage());
				}
				int duration = calculateDuration(relatedSpot.getArrivalTime(), relatedSpot.getDepartureTime());
				detail.setDuration(duration);
			} else {
				detail.setDuration(0); // 無相關景點資料就設0
			}

			// 將景點資料塞進DTO
			postSpotDto.add(detail);
		}

		// 判斷使用者是否有收藏這則貼文
		boolean favorite = isFavoritePost(req.getPostId(), sessionUserMail);

		// 貼文內容取得第一筆就可(因為都一樣)
		PostVo firstPost = allPost.get(0);

		return new PostSpecificRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(),
				firstPost.getJourneyName(), firstPost.getUserName(), firstPost.getUserMail(), firstPost.getUserImage(),
				firstPost.getPostContent(), firstPost.getPostTime(), favorite, firstPost.getThumbUp(),
				firstPost.getThumbDown(), postSpotDto, allComment, thumbStatus);
	}

	/**
	 * 將日期從 String 轉成 LocalDate
	 *
	 * @param date
	 * @return
	 */
	// TODO 只有這個class呼叫 改private
	public static LocalDate transferLocalDate(String date) {
		// 定義日期時間的格式
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(date, formatter);
	}

	/**
	 * 計算停留時間
	 *
	 * @param arrivalTime
	 * @param departureTime
	 * @return
	 */
	// TODO 只有這個class呼叫 改private
	public int calculateDuration(LocalTime arrivalTime, LocalTime departureTime) {
		Duration duration = Duration.between(arrivalTime, departureTime);
		return (int) duration.toHours(); // 單位為小時
	}

	/**
	 * 更新貼文
	 *
	 * @param req
	 * @param sessionUserMail
	 * @return
	 */
	@Transactional
	public BasicRes updatePost(UpdatePostReq req, String sessionUserMail) {
		// 取出 postDetail
		List<PostDetail> details = req.getPostDetail();

		// 確認使用者是否有這個行程
		if (!userDao.findJourneyByUserMail(sessionUserMail).contains(req.getJourneyId())) {
			return new BasicRes(ResMessage.USER_AND_JOURNEY_NOT_MATCH.getCode(),
					ResMessage.USER_AND_JOURNEY_NOT_MATCH.getMessage());
		}

		// 發文日期不可小於結束日期
		Journey journey = journeyDao.findJourneyById(req.getJourneyId());
		if (transferLocalDate(req.getPostTime()).isBefore(journey.getEndDate())) {
			return new BasicRes(ResMessage.POST_DATE_ERROR.getCode(), ResMessage.POST_DATE_ERROR.getMessage());
		}

		// 貼文內容不可為空
		if (req.getPostContent() == null || req.getPostContent().trim().isEmpty()) {
			return new PostSpecificRes(ResMessage.POST_CONTENT_ERROR.getCode(),
					ResMessage.POST_CONTENT_ERROR.getMessage());
		}

		for (PostDetail reqList : details) {
			if (reqList.getSpotImage() != null) {
				reqList.setSpotImgData(Base64.getDecoder().decode(reqList.getSpotImage()));
			}
			postDao.updatePost(req.getPostId(), req.getJourneyId(), sessionUserMail, req.getPostContent(),
					req.getPostTime(), reqList);
		}
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	/**
	 * 只返回有收藏的貼文
	 *
	 * @param keyword
	 * @param sessionUserMail
	 * @return
	 */
	public GetPostRes getFavoritePost(String keyword, String sessionUserMail) {

		// 裝結果的容器
		List<PostList> results = new ArrayList<>();

		Users userDB = userDao.getInfoById(sessionUserMail);
		if (userDB == null) {
			return new GetPostRes(ResMessage.ACCOUNT_NOT_EXIST.getCode(), ResMessage.ACCOUNT_NOT_EXIST.getMessage());
		}

		// 查詢結果
		List<PostList> postList = postDao.searchPostByKeyword(keyword);
		// 判斷貼文是否為空
		if (postList == null || postList.isEmpty()) {
			return new GetPostRes(ResMessage.POST_NOT_FOUND.getCode(), ResMessage.POST_NOT_FOUND.getMessage());
		}
		// 判斷是否為收藏貼文
		for (PostList post : postList) {
			if (isFavoritePost(post.getPostId(), sessionUserMail)) {
				PostList detail = new PostList();
				// 有景點圖片再進行格式轉換
				if (post.getSpotImgData() != null) {
					// 把轉換完的 Base64 字串塞回 spotImage
					post.setSpotImage(Base64.getEncoder().encodeToString(post.getSpotImgData()));
				}
				// 設置所需的屬性值
				detail.setUserName(post.getUserName());
				detail.setUserMail(post.getUserMail());
				detail.setUserImage(post.getUserImage());
				detail.setJourneyId(post.getJourneyId());
				detail.setJourneyName(post.getJourneyName());
				detail.setSpotImage(post.getSpotImage());
				detail.setPostId(post.getPostId());
				detail.setPostTime(post.getPostTime());
				detail.setFavorite(post.getFavorite());
				detail.setThumbUp(post.getThumbUp());
				detail.setThumbDown(post.getThumbDown());
				detail.setPublished(post.isPublished());

				results.add(detail);

			}
		}
		return new GetPostRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), results);
	}

	/**
	 * 用登入的使用者信箱和行程ID查找行程
	 *
	 * @param req
	 * @param sessionUserMail
	 * @return
	 */
	public PostSpecificRes getJourneyDetail(JourneyIdReq req, String sessionUserMail) {

		// 從資料庫拿資料
		Journey myJourney = journeyDao.findJourneyById(req.getJourneyId());
		List<Spot> allSpot = spotDao.getAllSpotByJourney(req.getJourneyId());

		// 檢查對應的行程是否存在
		if (myJourney == null) {
			return new PostSpecificRes(ResMessage.JOURNEYID_NOT_EXISTED.getCode(),
					ResMessage.JOURNEYID_NOT_EXISTED.getMessage());
		}
		if (allSpot == null || allSpot.isEmpty()) {
			return new PostSpecificRes(ResMessage.SPOT_ERROR_IN_POST.getCode(),
					ResMessage.SPOT_ERROR_IN_POST.getMessage());
		}

		// 裝景點的容器(要放停留時間)
		List<PostDetail> postSpotDto = new ArrayList<>();

		// 計算停留時間後將結果放回DTO
		for (Spot spot : allSpot) {

			// 塞一筆筆景點資料
			PostDetail detail = new PostDetail();
			detail.setDay(spot.getDay());
			detail.setSpotId(spot.getSpotId());
			detail.setSpotImage(spot.getSpotImage());
			detail.setSpotNote(spot.getNote());
			detail.setSpotName(spot.getSpotName());
			detail.setPlaceId(spot.getPlaceId());

			// 放入停留時間
			int duration = calculateDuration(spot.getArrivalTime(), spot.getDepartureTime());
			detail.setDuration(duration);

			// 將景點資料塞進DTO
			postSpotDto.add(detail);
		}

		// byte[] spotImg = Base64.getDecoder().decode(post.getSpotImage());

		return new PostSpecificRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(),
				myJourney.getJourneyName(), postSpotDto);
	}

	/**
	 * 點進編輯貼文頁時，將貼文設為未發佈
	 * 
	 * @param postId
	 * @param sessionUserMail
	 * @return
	 */
	public BasicRes setPostUnpublished(int postId, String sessionUserMail) {
		if (postId <= 0) {
			return new BasicRes(ResMessage.POST_ID_ERROR.getCode(), ResMessage.POST_ID_ERROR.getMessage());
		}
		// 將狀態設為false
		postDao.setPostUnpublished(postId, sessionUserMail);
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	/**
	 * 查詢使用者是否已發布貼文
	 * 
	 * @param journeyId
	 * @param sessionUserMail
	 * @return
	 */
	public boolean isPublishedPost(Integer journeyId, String sessionUserMail) {
		int result = postDao.findPostIsPublished(sessionUserMail, journeyId);
		return result > 0; // 大於0表示已發布
	}

}
