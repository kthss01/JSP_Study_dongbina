<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- UserDAO 클래스 import 하는 법 -->
<%@ page import="model.dao.UserDAO"%>
<!-- PrintWriter javaScript 문장 작성 위해서 -->
<%@ page import="java.io.PrintWriter"%>
<!-- 건너오는 데이터를 UTF-8 로 받으려고 하는거 -->
<%
	request.setCharacterEncoding("UTF-8");
%>

<!-- 자바 빈즈 사용하겠다는 거 -->
<jsp:useBean id="user" class="model.vo.User" scope="page" />
<jsp:setProperty name="user" property="userID" />
<jsp:setProperty name="user" property="userPassword" />
<jsp:setProperty name="user" property="userName" />
<jsp:setProperty name="user" property="userGender" />
<jsp:setProperty name="user" property="userEmail" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>JSP 게시판 웹 사이트</title>

<link rel="stylesheet" href="./css/bootstrap.css">

</head>
<body>
	<%
		// 로그인 여부 확인
		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		
		if (userID != null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('이미 로그인이 되어있습니다.')");
			script.println("location.href = 'main.jsp'");
			script.println("</script>");
		}
	
		// null 체크
		if (user.getUserID() == null || user.getUserPassword() == null || user.getUserName() == null
			|| user.getUserGender() == null || user.getUserEmail() == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.')");
			// 이전 페이지로 사용자 돌려보내는 거
			script.println("history.back()");
			script.println("</script>");
		} else {
			// 회원가입
			UserDAO userDAO = new UserDAO();
			int result = userDAO.join(user);
			if (result == -1) {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				// id가 primary key라서
				script.println("alert('이미 존재하는 아이디입니다.')");
				// 이전 페이지로 사용자 돌려보내는 거
				script.println("history.back()");
				script.println("</script>");
			} else {
				// 세션아이디 부여
				session.setAttribute("userID", user.getUserID());
				
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("location.href = 'main.jsp'");
				script.println("</script>");
			} 
		}
	%>
</body>
</html>