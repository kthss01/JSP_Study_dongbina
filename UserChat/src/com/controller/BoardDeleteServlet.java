package com.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.dao.BoardDao;
import com.model.dto.BoardDto;

public class BoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("userID");
		String boardID = request.getParameter("boardID");

		if (boardID == null || boardID.equals("")) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "접근할 수 없습니다.");
			response.sendRedirect("boardView.jsp");
			return;
		}
		
		BoardDao boardDao = new BoardDao();
		BoardDto board = boardDao.getBoard(boardID);
		
		if (!userID.equals(board.getUserID())) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "접근할 수 없습니다.");
			response.sendRedirect("boardView.jsp");
			return;
		}

		String savePath = request.getSession().getServletContext().getRealPath("/upload").replaceAll("\\\\", "/");
		String prev = boardDao.getRealFile(boardID);
		int result = boardDao.delete(boardID);
		if (result == -1) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "접근할 수 없습니다.");
			response.sendRedirect("boardView.jsp");
			return;
		} else {
			File prevFile = new File(savePath + "/" + prev);
			if (prevFile.exists()) {
				prevFile.delete();
			}
			
			request.getSession().setAttribute("messageType", "성공 메시지");
			request.getSession().setAttribute("messageContent", "삭제에 성공했습니다.");
			response.sendRedirect("boardView.jsp");
		}
	}

}
