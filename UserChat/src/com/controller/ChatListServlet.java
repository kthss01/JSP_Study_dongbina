package com.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.dao.ChatDao;
import com.model.dto.ChatDto;

@WebServlet("/chatList.do")
public class ChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		response.setContentType("text/html; charset=UTF-8");

		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String listType = request.getParameter("listType");

		if (fromID == null || fromID.equals("") || toID == null || toID.equals("") || listType == null
				|| listType.equals("")) {
			response.getWriter().write("");
		} else if (listType.equals("ten")) {
			response.getWriter().write(getTen(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID, "UTF-8")));
		} else {
			try {
				response.getWriter().write(getID(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID, "UTF-8"), listType));
			} catch (Exception e) {
				response.getWriter().write("");
			}
		}
	}

	private String getID(String fromID, String toID, String chatID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		
		ChatDao chatDao = new ChatDao();
		ArrayList<ChatDto> chatList = chatDao.getChatListByID(fromID, toID, chatID);
		
		if (chatList.size() == 0) 
			return "";
		
		for (int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			if (i != chatList.size() - 1)
				result.append(",");
		}
		
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
		
		chatDao.readChat(fromID, toID);
		
		return result.toString();
	}

	private String getTen(String fromID, String toID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		
		ChatDao chatDao = new ChatDao();
		ArrayList<ChatDto> chatList = chatDao.getChatListRecent(fromID, toID, 100);
		
		if (chatList.size() == 0) 
			return "";
		
		for (int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			if (i != chatList.size() - 1)
				result.append(",");
		}
		
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
		
		chatDao.readChat(fromID, toID);
		
		return result.toString();
	}

}