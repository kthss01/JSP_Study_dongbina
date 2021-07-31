<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- UserDAO 클래스 import 하는 법 -->
<%@ page import="model.dao.UserDAO" %>
<!-- PrintWriter javascript 문장 작성 위해서 -->
<%@ page import="java.io.PrintWriter" %>

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
		// 세션을 빼앗기도록 하는거
		session.invalidate();
	%>
	<script>
		location.href = 'main.jsp';
	</script>
</body>
</html>