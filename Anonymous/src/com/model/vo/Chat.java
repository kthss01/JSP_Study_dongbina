package com.model.vo;

import lombok.Data;

@Data
public class Chat {
	int chatID;
	private String chatName;
	private String chatContent;
	private String chatTime;
}
