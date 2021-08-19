package com.controller;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.dao.ChatDao;

@WebServlet("/chatUnread.do")
public class ChatUnreadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String userID = request.getParameter("userID");
		if (userID == null || userID.equals("")) {
			response.getWriter().write("0");
		} else {
			userID = URLDecoder.decode(userID, "UTF-8");
			
			// 현재 건너온 유저와 session 유저가 일치하는지 확인
			HttpSession session = request.getSession();
			if (!userID.equals((String) session.getAttribute("userID"))) {
				response.getWriter().write("");
				return;
			}
			
			response.getWriter().write(new ChatDao().getAllUnreadChat(userID) + "");
		}
	}

}
