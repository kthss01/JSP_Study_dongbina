package com.model.dto;

import lombok.Data;

@Data
public class ChatDto {

	private int chatID;
	private String fromID;
	private String toID;
	private String chatContent;
	private String chatTime;
	
}
