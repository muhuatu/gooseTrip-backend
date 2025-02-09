--資料庫
CREATE DATABASE IF NOT EXISTS gooseTrip; /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

--資料表
CREATE TABLE IF NOT EXISTS `accommodation` (
  `accommodation_id` int NOT NULL AUTO_INCREMENT,
  `journey_id` int NOT NULL,
  `checkout_date` date NOT NULL,
  `checkin_date` date NOT NULL,
  `hotel_name` varchar(100) NOT NULL,
  `hotel_web` varchar(20) NOT NULL,
  `room_type` varchar(50) NOT NULL,
  `bed_type` varchar(100) NOT NULL,
  `price` varchar(20) NOT NULL,
  `number` int NOT NULL,
  `url` text NOT NULL,
  `finished` tinyint NOT NULL,
  `user_mail` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`accommodation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `chat` (
  `journey_id` int NOT NULL,
  `chat_id` int NOT NULL,
  `invitation` varchar(10) NOT NULL,
  `chat_type` varchar(10) NOT NULL,
  `chat_detail` text,
  `chat_image` mediumblob,
  `chat_time` datetime NOT NULL,
  `user_mail` varchar(40) NOT NULL,
  `reply` int DEFAULT NULL,
  PRIMARY KEY (`chat_id`,`invitation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `chatroom_members` (
  `journey_id` int NOT NULL,
  `invitation` varchar(10) NOT NULL,
  `user_mail` varchar(40) NOT NULL,
  PRIMARY KEY (`invitation`,`user_mail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `users` (
  `user_mail` varchar(60) NOT NULL,
  `user_name` varchar(30) NOT NULL,
  `user_password` varchar(60) NOT NULL,
  `user_phone` varchar(12) NOT NULL,
  `user_image` varchar(255) DEFAULT NULL,
  `user_favorite` text,
  `user_edit` varchar(10) DEFAULT NULL,
  `favorite_post` text,
  `edit_time` time DEFAULT NULL,
  PRIMARY KEY (`user_mail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `journey` (
  `journey_id` int NOT NULL AUTO_INCREMENT,
  `invitation` varchar(10) DEFAULT NULL,
  `journey_name` varchar(20) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `user_mail` varchar(40) DEFAULT NULL,
  `transportation` varchar(10) NOT NULL,
  `visible` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`journey_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `spot` (
  `journey_id` int NOT NULL,
  `day` int NOT NULL,
  `spot_id` int NOT NULL,
  `date` date NOT NULL,
  `arrival_time` time DEFAULT NULL,
  `departure_time` time DEFAULT NULL,
  `spot_name` varchar(20) NOT NULL,
  `place_id` varchar(50) NOT NULL,
  `note` varchar(100) DEFAULT NULL,
  `spot_image` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`spot_id`,`journey_id`,`day`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `map_place` (
  `place_id` VARCHAR(50) NOT NULL,
  `place_name` VARCHAR(40) NOT NULL,
  `address` VARCHAR(250) NOT NULL,
  `longitude` DECIMAL(10,7) NOT NULL,
  `latitude` DECIMAL(10,7) NOT NULL,
  `place_type` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`place_id`));

CREATE TABLE IF NOT EXISTS `map_route` (
  `start_place_id` varchar(40) NOT NULL,
  `end_place_id` varchar(40) NOT NULL,
  `transportation` varchar(20) NOT NULL,
  `start_time` datetime NOT NULL,
  `route_time` time NOT NULL,
  `distance` varchar(10) NOT NULL,
  `route_line` text NOT NULL,
  `journey_id` int NOT NULL,
  `day` int NOT NULL,
  PRIMARY KEY (`start_time`,`journey_id`,`day`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `post` (
  `post_id` int NOT NULL,
  `journey_id` int NOT NULL,
  `user_mail` varchar(40) NOT NULL,
  `post_content` varchar(500) NOT NULL,
  `post_time` date NOT NULL,
  `published` tinyint NOT NULL,
  `day` int NOT NULL,
  `spot_id` int NOT NULL,
  `spot_image` mediumblob,
  `spot_note` varchar(500) DEFAULT NULL,
  `place_id` varchar(50) NOT NULL,
  PRIMARY KEY (`day`,`spot_id`,`journey_id`,`user_mail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `comment` (
  `serial_number` int NOT NULL AUTO_INCREMENT,
  `user_mail` varchar(40) NOT NULL,
  `post_id` int NOT NULL,
  `comment_id` int NOT NULL,
  `comment_content` varchar(100) NOT NULL,
  `reply_comment_id` int DEFAULT '0',
  `comment_time` datetime NOT NULL,
  PRIMARY KEY (`serial_number`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `interact` (
  `comment_sn` int NOT NULL,
  `user_mail` varchar(40) NOT NULL,
  `thumb` tinyint DEFAULT NULL,
  `post_id` int NOT NULL,
  PRIMARY KEY (`comment_sn`,`user_mail`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


