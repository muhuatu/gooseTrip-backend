### 1. searchPost -> post 用關鍵字查詢貼文 by 兔
- Post
- request body

| 欄位        | 型別   | 說明                | 必填 |
|-------------|--------|-------------------|------|
| userMail    | String | 使用者信箱（Session） |      |
| userName    | String | 使用者名稱          |      |
| journeyName | String | 行程名稱            |      |

```json
{
  "userName": "羊",
  "journeyName": "京都"
}
```

- response body
- 回傳只給 published = true 的結果。
| 欄位     | 型別         | 說明 | 必填 |
|----------|--------------|------|------|
| postList | List<Object> |      |      |

| 欄位        | 型別    | 說明       | 必填 |
|-------------|---------|----------|------|
| journeyId   | Number  | 行程Id     |      |
| journeyName | String  | 行程名稱   |      |
| spotImage   | String  | 景點圖片   |      |
| userName    | String  | 使用者名稱 |      |
| userMail    | String  | 使用者信箱 |      |
| postId      | Number  | 貼文Id     |      |
| postTime    | String  | 貼文時間   |      |
| favorite    | Boolean | 是否收藏   |      |
| thumbUp     | Number  | 讚數       |      |
| thumbDown   | Number  | 倒讚數     |      |

```json
{
  "code": "",
  "message": "",
  "postList": [
    {
        "journeyId": 2,
        "journeyName": "山羊家京都自由行",
        "spotImage": "圖片URL",
        "userName": "MR 羊咩咩",
        "userMail": "yang@ymail.com",
        "postId": 5,
        "postTime": "2024-11-10",
        "thumbUp": 150,
        "thumbDown": 10
    },
     {
        "journeyId": 4,
        "journeyName": "輝太郎京都冒險記",
        "spotImage": "圖片URL",
        "userName": "喜羊羊",
        "userMail": "happyhe@ymail.com",
        "post_id": 20,
        "postTime": "2024-12-30",
        "thumbUp": 10,
        "thumbDown": 1
    }
  ]
}
```

### 2. getPost -> post 取得特定貼文內容 by 兔
- Post
- request body

| 欄位      | 型別   | 說明       | 必填 |
|-----------|--------|----------|------|
| userMail  | String | 使用者信箱 | Y    |
| journeyId | Number | 行程Id     | Y    |
| postId    | Number | 貼文Id     | Y    |

```json
{
  "userMail": "yang@ymail.com",
  "journeyId": 2,
  "postId": 5
}
```

- response body
| 欄位        | 型別         | 說明       | 必填 |
| ----------- | ------------ | ---------- | ---- |
| userName    | String       | 使用者名稱 |      |
| userImage   | String       | 使用者頭貼 |      |
| journeyName | String       | 行程名稱   |      |
| postContent | Number       | 貼文內容   |      |
| postTime    | String       | 貼文時間   |      |
| favorite    | Boolean      | 是否收藏   |      |
| thumbUp     | Number       | 讚數       |      |
| thumbDown   | Number       | 倒讚數     |      |
| postDetail  | List<Object> | 景點清單   |      |
| commentList | List<Object> | 評論清單   |      |

- postDetail

| 欄位      | 型別   | 說明     | 必填 |
| --------- | ------ | -------- | ---- |
| day       | Number | 第幾天   |      |
| spotId    | Number | 景點Id   |      |
| spotNote  | String | 景點筆記 |      |
| spotImage | String | 景點圖片 |      |
| spotName  | String | 景點名稱 |      |
| duration  | Number | 停留時間 |      |
| placeId   | String | 地點編號 |      |

- commentList

| 欄位           | 型別          | 說明         | 必填 |
| -------------- | ------------- | ------------ | ---- |
| postId         | Number        | 貼文Id       |      |
| userName       | String        | 留言者名稱   |      |
| userImage      | String        | 留言者頭貼   |      |
| commentContent | String        | 評論內容     |      |
| commentTime    | LocalDateTime | 評論時間     |      |
| commentId      | Number        | 評論Id       |      |
| replyCommentId | Number        | 回覆評論的Id |      |
| thumbUp        | Number        | 讚數         |      |
| thumbDown      | Number        | 倒讚數       |      |

```json
{
  "code": "200",
  "message": "成功",
  "journeyName": "山羊家京都自由行",
  "userName": "MR 羊咩咩",
  "userImage": "",
  "postId": 5,
  "postContent": "山羊家族終於全員到齊了，期待已久的京都自由行~",
  "postTime": "2024-11-10",
  "favorite": true,
  "thumbUp": 150,
  "thumbDown": 10,
  "postDetail": [
	  {
	    "day": 1,
	    "spotId": 1,
	    "spotNote": "好好玩",
	    "spotImage": "",
        "spotName": "安平老街",
	    "duration": 3, // 三小時(後端計算departure_time-arrival_time)
	    "placeId": "ChIJ5XATMnupQjQR8BODhTplEWM"
     },
     {
	    "day": 1,
	    "spotId": 2,
	    "spotNote": "棒棒",
	    "spotImage": "",
        "spotName": "安平老街",
	    "duration": 2, // 二小時(後端計算departure_time-arrival_time)
	    "placeId": "ChIJ5XATMnupQjQR8BODhTplEZZ"
    },
    {
	    "day": 2,
	    "spotId": 1,
	    "spotNote": "好吃",
	    "spotImage": "",
        "spotName": "安平老街",
	    "duration": 1, // 一小時(後端計算departure_time-arrival_time)
	    "placeId": "ChIJ5XATMnupQjQR8BODhTplEWM"
    },
    {
	    "day": 2,
	    "spotId": 2,
	    "spotNote": "好好玩",
	    "spotImage": "",
        "spotName": "安平老街",
	    "duration": 1, // 一小時(後端計算departure_time-arrival_time)
	    "placeId": "ChIJ5XATMnupQjQR8BODhTplEWM"
    }
  ],
  "commentList": [
   {
        "serialNumber": 1,
        "userName": "企鵝先生",
        "userImage": "",
        "postId": 12,
        "commentContent": "看起來好好玩唷",
        "commentTime": "2024-12-31 09:00:05",
        "commentId": 1,
        "replyCommentId": 0,
        "thumbUp": 50,
        "thumbDown": 2
    },
    {
        "serialNumber": 2,
        "userName": "兔子先生",
        "userImage": "",
        "postId": 12,
        "commentContent": "對呀，下次一起去",
        "commentTime": "2024-12-31 09:00:06",
        "commentId": 1,
        "replyCommentId": 1,
        "thumbUp": 50,
        "thumbDown": 2
    }
  ]
}
```

### 3. getPostByUserMail -> post 查詢某使用者所有貼文 by Robin

- Post
- request body
- 頁面上是點擊使用者名稱，但應該要綁使用者信箱，前端顯示 userName [ngModel] 綁 userMail
- 查使用者本人的話，就用 Session 信箱（查詢收藏的貼文也用這支API，前端選擇 favorite = true渲染畫面）

| 欄位     | 型別   | 說明       | 必填 |
|----------|--------|----------|------|
| userMail | String | 使用者信箱 | Y    |

```json
{
  "userMail": "yang@ymail.com"
}
```

- response body
| 欄位     | 型別         | 說明       | 必填 |
|----------|--------------|----------|------|
| postList | List<Object> | 貼文的List |      |

| 欄位        | 型別    | 說明       | 必填 |
|-------------|---------|----------|------|
| userName    | String  | 使用者名稱 |      |
| userMail    | String  | 使用者信箱 |      |
| userImage   | String  | 使用者頭貼 |      |
| journeyId   | Number  | 行程Id     |      |
| journeyName | String  | 行程名稱   |      |
| Image       | String  | 景點圖片   |      |
| postId      | Number  | 貼文Id     |      |
| postTime    | String  | 貼文時間   |      |
| favorite    | Boolean | 是否收藏   |      |
| thumbUp     | Number  | 讚數       |      |
| thumbDown   | Number  | 倒讚數     |      |

```json
{
  "code": "200",
  "message": "成功",
  "postList": [
    {
        "userName": "MR 羊咩咩",
        "userMail": "yang@ymail.com",
        "userImage": "",
        "journeyId": 2,
        "journeyName": "台南百日遊",
        "spot_image": "assets/destination/tainan.jpg",
        "postId": 5,
        "postTime": "2024-11-10",
        "favorite": true,
        "thumbUp": 150,
        "thumbDown": 10
    },
     {
        "userName": "喜羊羊",
        "userMail": "happyhe@ymail.com",
        "userImage": "",
        "journeyId": 6,
        "journeyName": "花蓮深度旅行",
        "spot_image": "assets/spot/4.jpg",
        "postId": 10,
        "postTime": "2024-12-24",
        "favorite": false,
        "thumbUp": 50,
        "thumbDown": 2
    }
  ]
}
```

### 4. createPost -> post 新增貼文 by 兔
- Post
- request body

| 欄位        | 型別         | 說明       | 必填 |
|-------------|--------------|------------|------|
| journeyId   | Number       | 行程Id     | Y    |
| userMail    | String       | 使用者信箱 | Y    |
| postContent | Number       | 貼文內容   | Y    |
| postTime    | String       | 貼文時間   | Y    |
| postDetail  | List<Object> |            |      |

| 欄位      | 型別   | 說明     | 必填 |
|-----------|--------|--------|------|
| day       | Number | 第幾天   | Y    |
| spotId    | Number | 景點Id   | Y    |
| spotNote  | String | 景點筆記 |      |
| spotImage | String | 景點圖片 |      |
| duration  | Number | 停留時間 | Y    |
| placeId   | String | 地點編號 | Y    |

```json
{
  "journeyId": 9,
  "userMail": "ho@rsemail.com",
  "postContent": "在徐徐的微風的午後，與...一起到...",
  "postTime": "2024-12-30",
  "postDetail": [
	  {
        "day": 1,
        "spotId": 1,
        "spotNote": "徐徐的微風與搖曳的樹木...",
        "spotImage": "",
        "duration": 3, // 前端計算完回傳給後端？
        "placeId": "ChIJ5XATMnupQjQR8BODhTplEWM"
    },
     {
	    "day": 1,
	    "spotId": 2,
	    "spotNote": "溫暖的天氣和充滿人文氣息的街景中...",
	    "spotImage": "",
        "duration": 3,
        "placeId": "ChIJ5XATMnupQjQR8BODhTplEWZ"
    }
  ]
}
```

- response body

```json
{
  "code": "200",
  "message": "成功"
}
```

### 5. updatePost -> post 更新貼文 by Robin
- Post
- request body

| 欄位        | 型別         | 說明       | 必填 |
|-------------|--------------|------------|------|
| postId      | Number       | 貼文Id     | Y    |
| journeyId   | Number       | 行程Id     | Y    |
| userMail    | String       | 使用者信箱 | Y    |
| published   | boolean      | 已發佈     | Y    |
| postContent | Number       | 貼文內容   |      |
| postTime    | String       | 貼文時間   | Y    |
| postDetail  | List<Object> |            |      |

| 欄位      | 型別   | 說明     | 必填 |
|-----------|--------|--------|------|
| day       | Number | 第幾天   | Y    |
| spotId    | Number | 景點Id   | Y    |
| spotNote  | String | 景點筆記 |      |
| spotImage | String | 景點圖片 |      |
| duration  | Number | 停留時間 | Y    |
| placeId   | String | 地點編號 | Y    |

```json
{
  "postId": 18,
  "journeyId": 9,
  "userMail": "ho@rsemail.com",
  "published": false,
  "postContent": "更正原文內容: 在徐徐的微風的午後，與...一起到...",
  "postTime": "2024-12-30", // 前端 Date.now()
  "postDetail": [
	  {
	    "day": 1,
	    "spotId": 1,
	    "spotNote": "徐徐的微風與搖曳的樹木...",
	    "spotImage": "", // 暫定圖片路徑
        "duration": 3, // 三小時(後端計算departure_time-arrival_time)
        "placeId": "ChIJ5XATMnupQjQR8BODhTplEWZ"
    },
     {
	    "day": 1,
	    "spotId": 2,
	    "spotNote": "溫暖的天氣和充滿人文氣息的街景中...",
	    "spotImage": "",
        "duration": 3, // 三小時(後端計算departure_time-arrival_time)
        "placeId": "ChIJ5XATMnupQjQR8BODhTplEWZ"
    }
  ]
}
```

- response body

```json
{
  "code": "200",
  "message": "成功"
}
```

### 6. deletePost -> post 刪除貼文 by Robin
- Post
- request body

| 欄位       | 型別         | 說明         | 必填 |
|------------|--------------|------------|------|
| userMail   | String       | 使用者信箱   | Y    |
| postIdList | List<String> | 貼文ID的List | Y    |

```json
{
  "userMail": "goo@gmail.com",
  "postIdList":  ["5","10"]
}
```

- response body

```json
{
  "code": "200",
  "message": "成功",
}
```

### 7. copyJourney -> journey 複製行程 by Robin
- Post
- request body
- 在使用者的行程中建立與該行程ID相同的行程（從 Session 拿 userMail）
- 組合 createJourney() 和 createSpot() 兩支 API

| 欄位      | 型別   | 說明                       | 必填 |
|-----------|--------|--------------------------|------|
| userMail  | String | 使用者信箱                 | Y    |
| journeyId | Number | 行程Id（使用者想複製的行程） | Y    |

```json
{
  "userMail": "penguin@southmail.com",
  "journeyId": 12
}
```

- response body

```json
{
  "code": "200",
  "message": "成功"
}
```

### 8. replyPost ->  comment 回覆貼文或評論 by 翊
- Post
- request body
- comment_id 和 reply_comment_id 是調用後端 getPost() 後獲得的最大ID再加一

| 欄位           | 型別   | 說明                | 必填 |
|----------------|--------|-------------------|------|
| userMail       | String | 使用者信箱(Session) | Y    |
| postId         | Number | 貼文Id              | Y    |
| commentId      | Number | 評論Id              | Y    |
| replyCommentId | Number | 回覆評論的Id        | Y    |
| commentContent | String | 評論內容            | Y    |
| commentTime    | String | 評論時間            | Y    |

- 貼文12的第一筆評論
```json
{
  "userMail": "penguin@southmail.com",
  "postId": 12,
  "commentContent": "看起來好好玩唷",
  "commentTime": "2024-12-31 09:00:05",
}
```

- 貼文12的第一筆評論的第一筆回覆

```json
{
  "userMail": "rabbit@rmail.com",
  "postId": 12,
  "commentContent": "對呀，下次一起去",
  "commentTime": "2024-12-31 09:00:06",
}
```

- response body

- 貼文12的第一筆評論


```json
{
    "code": "200",
    "message": "成功",
}
```

- 貼文12的第一筆評論的第一筆回覆

```json
{
    "code": "200",
    "message": "成功",
}
```

### 9. getAllReply -> comment 取得某貼文的所有評論 by 翊

- Post
- request body

| 欄位   | 型別   | 說明   | 必填 |
|--------|--------|------|------|
| postId | Number | 貼文Id | Y    |

```json
{
  "postId": 12,
}
```

- response body

| 欄位        | 型別         | 說明     | 必填 |
|-------------|--------------|--------|------|
| commentList | List<Object> | 評論清單 |      |

| 欄位           | 型別          | 說明         | 必填 |
| -------------- | ------------- | ------------ | ---- |
| postId         | Number        | 貼文Id       |      |
| userName       | String        | 留言者名稱   |      |
| userImage      | String        | 留言者頭貼   |      |
| commentContent | String        | 評論內容     |      |
| commentTime    | LocalDateTime | 評論時間     |      |
| commentSN      | Number        | 評論流水號   |      |
| commentId      | Number        | 評論Id       |      |
| replyCommentId | Number        | 回覆評論的Id |      |
| thumbUp        | Number        | 讚數         |      |
| thumbDown      | Number        | 倒讚數       |      |

```json
{
  "code": "200",
  "message": "成功",
  "commentList": [
   {
        "userName": "企鵝先生",
       	"userImage": "assets/avatar/usagi1.jpg",
        "postId": 12,
        "commentContent": "看起來好好玩唷",
        "commentTime": "2024-12-31 09:00:05",
        "commentSN": 5,
        "commentId": 1,
        "replyCommentId": 0,
        "thumbUp": 50,
        "thumbDown": 2
    },
    {
        "userName": "兔子先生",
        "userImage": "assets/avatar/usagi2.jpg",
        "postId": 12,
        "commentContent": "對呀，下次一起去",
        "commentTime": "2024-12-31 09:00:06",
        "commentSN": 5,
        "commentId": 1,
        "replyCommentId": 1,
        "thumbUp": 50,
        "thumbDown": 2
    }
  ]
}
```
### 10. thumbAct -> interact 點讚/倒讚行為 by Robin
- Post
- request body
- 對貼文、評論、回覆都可以
- 要用 userMail 確定是否有此 Session
- 前端判斷 thumb 的狀態有改變才打這支 API

| 欄位       | 型別    | 說明                                | 必填 |
|------------|---------|-----------------------------------|------|
| userMail   | String  | 使用者信箱                          | Y    |
| post_id    | Number  | 貼文Id                              | Y    |
| comment_sn | Number  | 流水號                              |      |
| thumb      | boolean | 按讚/倒讚 (true: 按讚； false: 倒讚) |      |

```json
{
  "postId": 5,
  "comment_sn": 2,
  "thumb": true
}
```

- response body

```json
{
  "code": "200",
  "message": "成功"
}
```

### 11. updateFavoritePost -> users 加入或移除收藏貼文 by 兔
- Post
- request body
- 要用 userMail 確定是否有此 Session
- 前端判斷 favorite 的狀態有改變才打這支 API


| 欄位   | 型別   | 說明   | 必填 |
|--------|--------|------|------|
| postId | Number | 貼文Id |      |

```json
{
  "postId": 5,
}
```


- response body

```json
{
  "code": "200",
  "message": "成功"
}
```

### 12. deleteFavoritePost -> users 多筆移除收藏貼文 by 兔
- Post
- request body
- 要用 userMail 確定是否有此 Session

| 欄位       | 型別   | 說明             | 必填 |
|------------|--------|----------------|------|
| userMail   | String | 使用者信箱       |      |
| postIdList | Number | 收藏貼文Id的List | Y    |

```json
{
  "userMail": "penguin@southmail.com",
  "postIdList": ["5","10"]
}
```

- response body

```json
{
  "code": "200",
  "message": "成功" 
}
```

### 13. isFavoritePost -> users 是否有收藏貼文 by 兔

- Post
- request body
- 要用 userMail 確定是否有此 Session
- 用於某貼文內容頁一進去就渲染收藏的 ICON 是 true 還是 false


| 欄位   | 型別   | 說明   | 必填 |
|--------|--------|------|------|
| postId | Number | 貼文Id |      |

```json
{
  "postId": 5,
}
```


- response body

```json
{
  "code": "200",
  "message": "成功" 
}
```

### 14. deleteComment -> comment 刪除評論 by 兔

- Post
- request body
- 要用 userMail 確定是否有此 Session


| 欄位      | 型別   | 說明   | 必填 |
| --------- | ------ | ------ | ---- |
| postId    | Number | 貼文Id | Y    |
| commentId | Number | 評論Id | Y    |

```json
{
    "postId": 1,
    "commentId": 2,
}
```


- response body

```json
{
  "code": "200",
  "message": "成功" 
}
```

