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
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		response.setContentType("text/html; charset=UTF-8");
		
		MultipartRequest multi = null;
		
		int fileMaxSize = 10 * 1024 * 1024;
		// deprecated됨 
//		String savePath = request.getRealPath("/upload").replaceAll("\\\\", "/");
		String savePath = request.getSession().getServletContext().getRealPath("/upload").replaceAll("\\\\", "/"); 
		try {
			multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
		} catch (Exception e) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "파일 크기는 10MB를 넘을 수 없습니다.");
			response.sendRedirect("boardView.jsp");
			return;
		}
		
		String userID = multi.getParameter("userID");
		
		// 현재 건너온 유저와 session 유저가 일치하는지 확인
		HttpSession session = request.getSession();
		if (!userID.equals((String) session.getAttribute("userID"))) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "접근 할 수 없습니다.");
			response.sendRedirect("index.jsp");
			return;
		}
		
		String boardID = multi.getParameter("boardID");
		if (boardID == null || boardID.equals("")) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "접근 할 수 없습니다.");
			response.sendRedirect("index.jsp");
			return;
		}
		
		String boardTitle = multi.getParameter("boardTitle");
		String boardContent = multi.getParameter("boardContent");
		
		if (boardTitle == null || boardTitle.equals("") || boardContent == null || boardContent.equals("")) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "내용을 모두 채워주세요.");
			response.sendRedirect("boardView.jsp");
			return;
		}

		String boardFile = "";
		String boardRealFile = "";
		
		File file = multi.getFile("boardFile");
		if (file != null) {
			boardFile = multi.getOriginalFileName("boardFile");
			boardRealFile = file.getName();
		}

		BoardDao boardDao = new BoardDao();
		BoardDto parent = boardDao.getBoard(boardID);
		boardDao.replyUpdate(parent);
		boardDao.reply(userID, boardTitle, boardContent, boardFile, boardRealFile, parent);
		
		session.setAttribute("messageType", "성공 메시지");
		session.setAttribute("messageContent", "성공적으로 답변이 작성되었습니다.");
		response.sendRedirect("boardView.jsp");
		return;
	}

}
