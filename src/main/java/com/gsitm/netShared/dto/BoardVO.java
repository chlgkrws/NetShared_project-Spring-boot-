package com.gsitm.netshared.dto;

import lombok.Data;

@Data
public class BoardVO {
	private String userId;
	private int	boardId;
	private String title;
	private String writer;
	private String content;
	private String createdTime;
	private int recommend;
	private int starRate;
	private String genre;
	private boolean valid;
	private String updateTime;


}
