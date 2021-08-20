package com.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.dao.ChatDao;
import com.model.dao.UserDao;
import com.model.dto.ChatDto;

@WebServlet("/chatBox.do")
public class ChatBoxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String userID = request.getParameter("userID");
		if (userID == null || userID.equals("")) {
			response.getWriter().write("");
		} else {
			try {
				userID = URLDecoder.decode(userID, "UTF-8");
				
				// 현재 건너온 유저와 session 유저가 일치하는지 확인
				HttpSession session = request.getSession();
				if (!URLDecoder.decode(userID, "UTF-8").equals((String) session.getAttribute("userID"))) {
					response.getWriter().write("");
					return;
				}
				
				response.getWriter().write(getBox(userID));
			} catch (Exception e) {
				response.getWriter().write("");
			}
		}
	}

	private String getBox(String userID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		
		ChatDao chatDao = new ChatDao();
		ArrayList<ChatDto> chatList = chatDao.getBox(userID);
		
		if (chatList.size() == 0) 
			return "";
		
		for (int i = chatList.size() - 1; i >= 0; i--) {
			String unread = "";
			String userProfile = "";
			if (userID.equals(chatList.get(i).getToID())) {
				unread = chatDao.getUnreadChat(chatList.get(i).getFromID(), userID) + "";
				if (unread.equals("0"))
					unread = "";
			}
			
			if (userID.equals(chatList.get(i).getToID())) {
				userProfile = new UserDao().getProfile(chatList.get(i).getFromID());
			} else {
				userProfile = new UserDao().getProfile(chatList.get(i).getToID());
			}
			
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"},");
			result.append("{\"value\": \"" + unread + "\"},");
			result.append("{\"value\": \"" + userProfile + "\"}]");
			if (i != 0)
				result.append(",");
		}
		
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
		
		return result.toString();
	}

}
