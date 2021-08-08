<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.dao.UserDAO" %>
<%@ page import="model.dao.EvaluationDAO" %>
<%@ page import="model.dao.LikeyDAO" %>
<%@ page import="java.io.PrintWriter" %>

<%!
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARD-FOR");
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0) {
			ip = request.getRemoteAddr();
		}
		
		return ip;
	}
%>

<%
	UserDAO userDAO = new UserDAO();
	String userID = null;
	if (session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID");
	} 
	
	if (userID == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인을 해주세요.');");
		script.println("location.href = 'userLogin.jsp'");
		script.println("</script>");
		script.close();
		return; // jsp 페이지 작동 종료
	}
	
	request.setCharacterEncoding("UTF-8");
	String evaluationID = null;
	
	if (request.getParameter("evaluationID") != null) {
		evaluationID = request.getParameter("evaluationID");
	}
	
	EvaluationDAO evaluationDAO = new EvaluationDAO();
	LikeyDAO likeyDAO = new LikeyDAO();
	int result = likeyDAO.like(userID, evaluationID, getClientIP(request));
	
	if (result == 1) {
		result = evaluationDAO.like(evaluationID);
		
		if (result == 1) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('추천이 완료되었습니다.');");
			script.println("location.href = 'index.jsp'");
			script.println("</script>");
			script.close();
			return; // jsp 페이지 작동 종료
		} else {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('데이터베이스 오류가 발생했습니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
			return; // jsp 페이지 작동 종료
		}
	} else {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('이미 추천을 누른 글입니다.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return; // jsp 페이지 작동 종료
	}
%>