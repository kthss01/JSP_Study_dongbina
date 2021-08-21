package com.model.dto;

import lombok.Data;

@Data
public class BoardDto {
	private String userID;
	private int boardID;
	private String boardTitle;
	private String boardContent;
	private String boardDate;
	private int boardHit;
	private String boardFile;
	private String boardRealFile;
	private int boardGroup;
	private int boardSequence;
	private int boardLevel;
}
