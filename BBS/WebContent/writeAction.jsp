<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- UserDAO 클래스 import 하는 법 -->
<%@ page import="model.dao.BBSDAO"%>
<!-- PrintWriter javaScript 문장 작성 위해서 -->
<%@ page import="java.io.PrintWriter"%>
<!-- 건너오는 데이터를 UTF-8 로 받으려고 하는거 -->
<%
	request.setCharacterEncoding("UTF-8");
%>

<!-- 자바 빈즈 사용하겠다는 거 -->
<jsp:useBean id="bbs" class="model.vo.BBS" scope="page" />
<jsp:setProperty name="bbs" property="bbsTitle" />
<jsp:setProperty name="bbs" property="bbsContent" />

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
		
		if (userID == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인을 하세요.')");
			script.println("location.href = 'login.jsp'");
			script.println("</script>");
		} else {
			// null 체크
			if (bbs.getBbsTitle() == null || bbs.getBbsContent() == null) {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('입력이 안 된 사항이 있습니다.')");
				// 이전 페이지로 사용자 돌려보내는 거
				script.println("history.back()");
				script.println("</script>");
			} else {
				// 글쓰기
				BBSDAO bbsDAO = new BBSDAO();
				int result = bbsDAO.write(bbs.getBbsTitle(), userID, bbs.getBbsContent());
				if (result == -1) {
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('글쓰기에 실패했습니다.')");
					// 이전 페이지로 사용자 돌려보내는 거
					script.println("history.back()");
					script.println("</script>");
				} else {
					PrintWriter script = response.getWriter();
					script.println("<script>");
					// 다시 게시판으로 이동
					script.println("location.href = 'bbs.jsp'");
					script.println("</script>");
				} 
			}
		}
		
	%>
</body>
</html>