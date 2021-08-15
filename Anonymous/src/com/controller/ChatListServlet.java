package com.controller;

import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.dao.ChatDao;
import com.model.vo.Chat;

@WebServlet("/chatList.do")
public class ChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String listType = request.getParameter("listType");
		if (listType == null || listType.equals("")) {
			response.getWriter().write("");
		} else if (listType.equals("today")) {
			response.getWriter().write(getToday());
		} else if (listType.equals("ten")) {
			response.getWriter().write(getTen());
		} else {
			try {
				Integer.parseInt(listType);
				response.getWriter().write(getID(listType));
			} catch (Exception e) {
				response.getWriter().write("");
			}
		}
		
	}

	private String getToday() {
		StringBuffer result = new StringBuffer("");
		
		result.append("{\"result\":[");
		
		ChatDao chatDao = new ChatDao();
		ArrayList<Chat> chatList = chatDao.getChatList(
				new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
		for (int i = 0; i < chatList.size(); i++) {
			Chat chat = chatList.get(i);
			result.append("[{\"value\": \"" + chat.getChatName() + "\"},");
			result.append("{\"value\": \"" + chat.getChatContent() + "\"},");
			result.append("{\"value\": \"" + chat.getChatTime() + "\"}]");
			
			if (i != chatList.size() - 1)
				result.append(",");
		}
		
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
		
		return result.toString();
	}

	private String getTen() {
		StringBuffer result = new StringBuffer("");
		
		result.append("{\"result\":[");
		
		ChatDao chatDao = new ChatDao();
		ArrayList<Chat> chatList = chatDao.getChatListByRecent(10);
		
		for (int i = 0; i < chatList.size(); i++) {
			Chat chat = chatList.get(i);
			result.append("[{\"value\": \"" + chat.getChatName() + "\"},");
			result.append("{\"value\": \"" + chat.getChatContent() + "\"},");
			result.append("{\"value\": \"" + chat.getChatTime() + "\"}]");
			
			if (i != chatList.size() - 1)
				result.append(",");
		}
		
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
		
		return result.toString();
	}
	
	private String getID(String chatID) {
		StringBuffer result = new StringBuffer("");
		
		result.append("{\"result\":[");
		
		ChatDao chatDao = new ChatDao();
		ArrayList<Chat> chatList = chatDao.getChatListByChatID(chatID);
		
		for (int i = 0; i < chatList.size(); i++) {
			Chat chat = chatList.get(i);
			result.append("[{\"value\": \"" + chat.getChatName() + "\"},");
			result.append("{\"value\": \"" + chat.getChatContent() + "\"},");
			result.append("{\"value\": \"" + chat.getChatTime() + "\"}]");
			
			if (i != chatList.size() - 1)
				result.append(",");
		}
		
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
		
		return result.toString();
	}
}
