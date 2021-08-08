<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 모든 클라이언트의 모든 세션 정보 파기
	session.invalidate();
%>
<script>
	location.href = 'index.jsp';
</script>

