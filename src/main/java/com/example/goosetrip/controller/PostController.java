package com.example.goosetrip.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.goosetrip.constants.ResMessage;
import com.example.goosetrip.dao.ImageDao;
import com.example.goosetrip.dto.Image;
import com.example.goosetrip.dto.Users;
import com.example.goosetrip.service.PostService;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.DeletePostReq;
import com.example.goosetrip.vo.FavoritePostReq;
import com.example.goosetrip.vo.GetPostRes;
import com.example.goosetrip.vo.JourneyIdReq;
import com.example.goosetrip.vo.PostListRes;
import com.example.goosetrip.vo.PostReq;
import com.example.goosetrip.vo.PostSpecificReq;
import com.example.goosetrip.vo.PostSpecificRes;
import com.example.goosetrip.vo.UpdatePostReq;
import com.example.goosetrip.vo.UploadImageRes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("post/")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private ImageDao imageDao;

	/**
	 * 上傳圖片
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping("upload_image")
	public UploadImageRes uploadImage(@RequestParam("file") MultipartFile file) {
		Image image = new Image();
		try {
			// 把檔案轉成byte[]並存到資料庫
			byte[] bytes = file.getBytes();
			image.setImage(bytes);
			// 調用 ImageDao 保存圖片，成功會回傳
			int res = imageDao.saveImage(image);
			// 獲取插入的 image_id
			int imageId = image.getImageId();
			return new UploadImageRes(ResMessage.SUCCESS.getCode(), //
					ResMessage.SUCCESS.getMessage(), imageId);
		} catch (IOException e) {
			e.printStackTrace();
			return new UploadImageRes(ResMessage.IMAGE_PROCESSING_FAILED.getCode(), //
					ResMessage.IMAGE_PROCESSING_FAILED.getMessage());
		}
	}

	/**
	 * 依圖片Id回傳圖片
	 * 
	 * @param imageId
	 * @return
	 */
	@GetMapping("/image/{imageId}")
	public ResponseEntity<Image> getImage(@PathVariable("imageId") int imageId) {
		try {
			// 查詢資料庫中的圖片
			Image image = imageDao.getImageById(imageId);

			if (image == null) {
				System.out.println("Image not found for ID: " + imageId); // 添加日誌
				return ResponseEntity.notFound().build();
			}

			// 返回包含 Base64 編碼圖片的 Image 類別物件
			return ResponseEntity.ok(image);
//            byte[] imageData = image.getImage();
//            
//            // 設定內容類型為圖片格式，先使用 PNG
////            HttpHeaders headers = new HttpHeaders();
////            headers.setContentType(MediaType.IMAGE_PNG);
//            String encodedImage = Base64.getEncoder().encodeToString(imageData);
//            Image img = new Image();
//            img.setImage(imageData);
//            System.out.println(encodedImage);
//            
//            HttpHeaders headers = new HttpHeaders();
//			return ResponseEntity.ok().headers(headers).body(img);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * 用使用者信箱取得貼文列表
	 *
	 * @param userMail
	 * @param session
	 * @return
	 */
	@PostMapping(value = "get_post")
	public PostListRes getPostByUserMail(@RequestParam(value = "userMail") String userMail, @Nullable HttpSession session) {
		
		if(session != null) {
			Users attr = (Users) session.getAttribute("user");
			if(attr != null) {
				return postService.getPostByMail(userMail, attr.getUserMail());
			} else {
				return postService.getPostByMail(userMail, null);
			}
		}
		// 檢查 Session 中是否有此使用者
//		if (attr == null) {
//			return new PostListRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
//		}
		else {
			return postService.getPostByMail(userMail, null);
		}
	}

	/**
	 * 刪除貼文
	 *
	 * @param req
	 * @param session
	 * @return
	 */
	@PostMapping(value = "delete")
	public BasicRes deletePost(@Valid @RequestBody DeletePostReq req, HttpSession session) {
		// 檢查 Session 中是否有此使用者
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new GetPostRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		// 代入 Session 中的使用者信箱
		return postService.deletePost(req, attr.getUserMail());
	}

	/**
	 * 用關鍵字查詢貼文
	 * 
	 * @param keyword
	 * @param session
	 * @return
	 */
	@GetMapping(value = "searchPostByKeyword")
	public GetPostRes searchPostByKeyword(@RequestParam(value = "keyword", required = false) String keyword,
			@Nullable HttpSession session) {
		if(session != null) {
			Users attr = (Users) session.getAttribute("user");
			if(attr != null) {
				return postService.searchPostByKeyword(keyword, attr.getUserMail());
			} else {
				return postService.searchPostByKeyword(keyword, null);
			}
		}
		
//		if (attr == null) {
//			return new GetPostRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
//		}
		else {
			return postService.searchPostByKeyword(keyword, null);
		}
//		return postService.searchPostByKeyword(keyword, null);
	}

	/**
	 * 移除收藏貼文(可多筆，會員資料頁的前端可做多選框)
	 *
	 * @param req
	 * @param session
	 * @return
	 */
	@PostMapping(value = "deleteFavoritePost")
	public BasicRes deleteFavoritePost(@Valid @RequestBody DeletePostReq req, HttpSession session) {
		// 檢查 Session 中是否有此使用者
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new GetPostRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		// 帶入 Session 中的使用者信箱
		return postService.deleteFavoritePost(req, attr.getUserMail());
	}

	/**
	 * 更新收藏貼文（加入或移除一筆）
	 *
	 * @param req
	 * @param session
	 * @return
	 */
	@PostMapping(value = "updateFavoritePost")
	public BasicRes updateFavoritePost(@Valid @RequestBody FavoritePostReq req, HttpSession session) {
		// 檢查 Session 中是否有此使用者
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new GetPostRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		// 檢查貼文ID不可為""
		if (req.getPostId() == null) {
			return new BasicRes(ResMessage.POST_ID_ERROR.getCode(), ResMessage.POST_ID_ERROR.getMessage());
		}
		// 帶入 Session 中的使用者信箱
		return postService.updateFavoritePost(req, attr.getUserMail());
	}

	/**
	 * 確認該貼文是否有收藏
	 *
	 * @param req
	 * @param session
	 * @return
	 */
	@PostMapping("isFavoritePost")
	public BasicRes isFavoritePost(@Valid @RequestBody FavoritePostReq req, HttpSession session) {
		// 檢查 Session 中是否有此使用者
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new GetPostRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		if (req.getPostId() == null || req.getPostId() < 0) {
			return new BasicRes(ResMessage.POST_ID_ERROR.getCode(), ResMessage.POST_ID_ERROR.getMessage());
		}
		// 檢查是否收藏
		boolean isFavorite = postService.isFavoritePost(req.getPostId(), attr.getUserMail());
		// 回傳 200, true 或 false
		return new BasicRes(ResMessage.SUCCESS.getCode(), String.valueOf(isFavorite));
	}

	/**
	 * 新增貼文
	 *
	 * @param req
	 * @param session
	 * @return
	 */
	@PostMapping("createPost")
	public BasicRes createPost(@Valid @RequestBody PostReq req, HttpSession session) {
		// 檢查 Session 中是否有此使用者
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new GetPostRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		// 行程ID必須大於0
		if (req.getJourneyId() < 0) {
			return new BasicRes(ResMessage.JOURNEYID_ERROR.getCode(), ResMessage.JOURNEYID_ERROR.getMessage());
		}
		return postService.createPost(req, attr.getUserMail());
	}

	/**
	 * 取得特定貼文內容
	 *
	 * @param req
	 * @param session
	 * @return
	 */
	@PostMapping("getPost")
	public PostSpecificRes getPost(@Valid @RequestBody PostSpecificReq req, @Nullable HttpSession session) {
		// 檢查 Session 中是否有此使用者
//		Users attr = (Users) session.getAttribute("user");
//		if (attr == null) {
//			return new PostSpecificRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(),
//					ResMessage.PLEASE_LOGIN_FIRST.getMessage());
//		}
		if(session != null) {
			Users attr = (Users) session.getAttribute("user");
			if(attr != null) {
				return postService.getPost(req, attr.getUserMail());
			} else {
				return postService.getPost(req, null);
			}
		}
		return postService.getPost(req, null);
	}

	/**
	 * 更新貼文
	 * 
	 * @param req
	 * @param session
	 * @return
	 */
	@PostMapping("updatePost")
	public BasicRes updatePost(@Valid @RequestBody UpdatePostReq req, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new PostSpecificRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(),
					ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return postService.updatePost(req, attr.getUserMail());
	}

	/**
	 * 只返回有收藏的貼文
	 * 
	 * @param keyword
	 * @param session
	 * @return
	 */
	@GetMapping(value = "getFavoritePost")
	public GetPostRes getFavoritePost(@RequestParam(value = "keyword") String keyword, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new GetPostRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return postService.getFavoritePost(keyword, attr.getUserMail());
	}

	/**
	 * 用登入的使用者信箱和行程ID查找行程
	 * 
	 * @param req
	 * @param session
	 * @return
	 */
	@PostMapping("getJourneyDetail")
	public PostSpecificRes getJourneyDetail(@Valid @RequestBody JourneyIdReq req, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new PostSpecificRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(),
					ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return postService.getJourneyDetail(req, attr.getUserMail());
	}

	/**
	 * 至編輯貼文頁前，將貼文設為未發佈
	 * 
	 * @param postId
	 * @param session
	 * @return
	 */
	@GetMapping("post_unpublished")
	public BasicRes setPostUnpublished(@RequestParam(value = "postId") int postId, HttpSession session) {
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new BasicRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(), ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		postService.setPostUnpublished(postId, attr.getUserMail());
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());

	}

	/**
	 * 查詢使用者是否已發布貼文
	 * @param postId
	 * @param session
	 * @return
	 */
	@GetMapping("isPublishedPost")
	public BasicRes isPublishedPost(@RequestParam(value = "journeyId") int journeyId, HttpSession session) {
		// 檢查 Session 中是否有此使用者
		Users attr = (Users) session.getAttribute("user");
		if (attr == null) {
			return new GetPostRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(),
					ResMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		if (journeyId < 0) {
			return new BasicRes(ResMessage.POST_ID_ERROR.getCode(),
					ResMessage.POST_ID_ERROR.getMessage());
		}
		// 檢查使用者於此行程ID是否已發布貼文
		boolean isPublished = postService.isPublishedPost(journeyId, attr.getUserMail());
		return new BasicRes(ResMessage.SUCCESS.getCode(), String.valueOf(isPublished));
	}

}
