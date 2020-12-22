package com.it1457.vo;


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
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public int getRecommend() {
		return recommend;
	}
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	public int getStarRate() {
		return starRate;
	}
	public void setStarRate(int starRate) {
		this.starRate = starRate;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
}
