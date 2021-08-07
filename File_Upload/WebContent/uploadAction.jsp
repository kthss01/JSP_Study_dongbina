<%@ page import="model.dao.FileDAO" %>
<%@ page import="java.io.File" %>
<!-- 사용자가 업로드한 파일 중 이름 똑같으면 자동으로 파일 이름 바꿔주고 오류 발생하지 않게 해주는 클래스 -->
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<!-- 파일 업로드 수행하는 클래스 -->
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 파일 업로드</title>
</head>
<body>
	<%
		//String directory = application.getRealPath("/upload/");
		// 하드코딩으로 업로드 경로 루트 디렉터리 밖으로 이동
		String directory = "D:/git/JSP_Study_dongbina/upload";
		int maxSize = 1024 * 1024 * 100; // 최대 100MB만 저장 가능하게
		String encoding = "UTF-8";
		
		// 이때 파일이 업로드 됨
		MultipartRequest multipartRequest
			= new MultipartRequest(request, directory, maxSize, encoding,
					new DefaultFileRenamePolicy());
		
		String fileName = multipartRequest.getOriginalFileName("file");
		String fileRealName = multipartRequest.getFilesystemName("file");
		
		// secure coding 1 업로드 가능한 확장자 명확히 제시하기
		// 해당 확장자 아니면 파일 지워버림
		if (!fileName.endsWith(".doc") && !fileName.endsWith(".hwp")
				&& !fileName.endsWith(".pdf") && !fileName.endsWith(".xls")) {
			File file = new File(directory + fileRealName);
			file.delete();
			out.write("업로드 할 수 없는 확장자입니다.");
		} else {
			new FileDAO().upload(fileName, fileRealName);
			
			out.write("파일명 : " + fileName + "<br>");
			out.write("실제 파일명 : " + fileName + "<br>");
		}
	%>
</body>
</html>