package com.gsitm.netShared.dto;

import lombok.Data;

/*CREATE TABLE `board_tbl` (
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
public class BoardVO {
	private String userId;
	private int	boardId;
	private String title;
	private String writer;
	private String content;
	private String createdTime;				//이후 데이터베이스 쪽에서 Date형식으로 알아서 저장.
	private int recommend;
	private int starRate;
	private String genre;
	private boolean valid;
	private String updateTime;


}
