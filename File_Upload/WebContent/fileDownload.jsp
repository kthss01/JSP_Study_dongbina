<%@ page import="java.io.File" %>
<%@ page import="model.dto.FileDTO" %>
<%@ page import="model.dao.FileDAO" %>
<%@ page import="java.util.ArrayList" %>
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
		/*
		String directory = application.getRealPath("/upload");
		//String directory = "D:/git/JSP_Study_dongbina/upload";
		String[] files = new File(directory).list();
		
		for (String file : files) {
			out.write(
					"<a href=\"" + request.getContextPath() 
					+ "/downloadAction?file=" 
					+ java.net.URLEncoder.encode(file, "UTF-8") 
					+ "\">" + file + "</a><br>");
		}
		*/
	
	
		ArrayList<FileDTO> fileList = new FileDAO().getList();
	
		for (FileDTO file : fileList) {
			out.write("<a href=\""  + request.getContextPath() 
				+ "/downloadAction?file=" 
				+ java.net.URLEncoder.encode(file.getFileRealName(), "UTF-8")
				+ "\">" + file.getFileName() 
				+ " (다운로드 횟수 : " + file.getDownloadCount() + ")</a><br>");
		}
		
		out.write("<a href=\"http://localhost:8000/File_Upload/upload/img_unsplash.jpg\" download>download jpg 속성 예제</a><br>");
		out.write("<a href=\"http://localhost:8000/File_Upload/upload/QRCodePrintNew.pdf\" download>download pdf 속성 예제</a><br>");
		out.write("<a href=\"http://localhost:8000/File_Upload/upload/QRCodePrintNew.pdf\" >pdf 예제</a><br>");
	%>
</body>
</html>