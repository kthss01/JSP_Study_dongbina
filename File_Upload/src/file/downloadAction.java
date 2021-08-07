package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.FileDAO;


@WebServlet("/downloadAction")
public class downloadAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fileName = request.getParameter("file");
		
		//String directory = this.getServletContext().getRealPath("/upload/");
		String directory = "D:/git/JSP_Study_dongbina/upload";
		File file = new File(directory + "/" + fileName);
		
//		System.out.println(file.toString());
		
		// mimeType 은 어떠한 데이터를 주고받을지에 대한 정보
		String mimeType = getServletContext().getMimeType(file.toString());
		if (mimeType == null) {
			// octet-stream 파일 관련 데이터를 주고 받을 때 이거 사용함
			response.setContentType("application/octet-stream");
		}
		
//		System.out.println(mimeType);
//		System.out.println(response.getContentType());
//		response.setContentType(mimeType + ";name=\"" + fileName + "\"");
		
//		System.out.println(response.getContentType());
		
		// 이거 해주면 pdf 면 그냥 바로 열림 -> 그건 아닌듯 이것만 쓰면 바로 열림
		response.setHeader("Content-Description", "JSP Generated Data");
		
		String downloadName = null;
		// MSIE 인터넷 익스플로러 의미
		if (request.getHeader("user-agent").indexOf("MSIE") == -1) {
			// 인터넷 익스플로러가 아니면 UTF-8로 받아서 8859_1 형식으로 바꿔준단 얘기
			// 8859_1 형식으로 바꿔주면 파일이 안깨진다고 함
			downloadName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
//			downloadName = new String(fileName.getBytes("UTF-8"), "8859_1");
		} else {
			downloadName = new String(fileName.getBytes("EUC_KR"), "8859_1");
		}
		
//		System.out.println(downloadName);
		
		// 이걸 없애줘야 다운로드 안되고 바로 보여지는거 같음
		// attachment; 가 다운로드 한단 얘기
//		response.addHeader(
//				"Content-Disposition", 
//				"inline; filename=\"" + downloadName + "\";");
		response.addHeader(
				"Content-Disposition", 
				"attachment; filename=\"" + downloadName + "\";");
		
		
		FileInputStream fileInputStream = new FileInputStream(file);
		
		ServletOutputStream servletOutputStream = response.getOutputStream();
		
		// 바이트 단위로 쪼개서 보냄
		byte b[] = new byte[1024];
		int data = 0;
		
		while ((data = (fileInputStream.read(b, 0, b.length))) != -1) {
			servletOutputStream.write(b, 0, data);
		}
		
		// 다운로드 횟수 증가
		new FileDAO().hit(fileName);
		
		servletOutputStream.flush();
		servletOutputStream.close();
		fileInputStream.close();
	}

}
