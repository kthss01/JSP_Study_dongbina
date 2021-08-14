package com.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.dao.UserDao;
import com.model.vo.User;

@WebServlet("/userRegister.do")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		response.setContentType("text/html; charset=UTF-8");
		
		String userName = request.getParameter("userName");
		String userAge = request.getParameter("userAge");
		String userGender = request.getParameter("userGender");
		String userEmail = request.getParameter("userEmail");
		
		// write 안에 문자열 들어가야하는거 같다고함
		response.getWriter().write(register(userName, userAge, userGender, userEmail) + ""); // 화면에 출력해줌
	}

	private int register(String userName, String userAge, String userGender, String userEmail) {
		
		User user = new User();
		
		try {
			user.setUserName(userName);
			user.setUserAge(Integer.parseInt(userAge));
			user.setUserGender(userGender);
			user.setUserEmail(userEmail);
		} catch (Exception e) {
			return 0;
		}
		
		return new UserDao().register(user);
	}

}
