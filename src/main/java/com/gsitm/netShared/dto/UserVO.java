package com.gsitm.netShared.dto;

import lombok.Data;

/*
CREATE TABLE `board_tbl` (
		  `board_id` int(20) NOT NULL AUTO_INCREMENT,
		  `user_id` varchar(50) COLLATE utf8_bin NOT NULL,
		  `writer` varchar(20) COLLATE utf8_bin NOT NULL,
		  `title` varchar(50) COLLATE utf8_bin NOT NULL,
		  `content` longtext COLLATE utf8_bin NOT NULL,
		  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
		  `recommend` int(11) NOT NULL DEFAULT '0',
		  `genre` varchar(30) COLLATE utf8_bin NOT NULL,
		  `valid` tinyint(4) NOT NULL DEFAULT '1',
		  `star_rate` int(4) NOT NULL DEFAULT '0',
		  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
		  PRIMARY KEY (`board_id`),
		  KEY `foreign_user_id_idx` (`user_id`),
		  CONSTRAINT `foreign_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_tbl` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
		) ENGINE=InnoDB AUTO_INCREMENT=32795 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

*/
@Data
public class UserVO {
	private String id;
	private String password;
	private String userName;
	private String identity;
	private String phone;
	private String account;
	private String auth;
	private String createdTime;
	private String updateTime;
	private boolean wouldUYN;

}
