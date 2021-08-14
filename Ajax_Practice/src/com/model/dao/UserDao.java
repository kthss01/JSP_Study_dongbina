package com.model.dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import com.common.JDBCTemplate;
import com.model.vo.User;

public class UserDao {
	
	private Properties prop = new Properties();
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs;
	
	public UserDao() {
		
		String fileName = UserDao.class.getResource("/sql/user/user-query.properties").getPath();
//		System.out.println("fileName :" + fileName);
		try {
			prop.load(new FileReader(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<User> search(String userName) {
		conn = JDBCTemplate.getConnection();
		
		String sql = prop.getProperty("search");
		
		ArrayList<User> userList = new ArrayList<User>();
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%" + userName + "%");
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				User user = new User();
				
				user.setUserName(rs.getString(1));
				user.setUserAge(rs.getInt(2));
				user.setUserGender(rs.getString(3));
				user.setUserEmail(rs.getString(4));
				
				userList.add(user);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(conn);
		}
		
		return userList;
	}
	
	public int register(User user) {
		conn = JDBCTemplate.getConnection();
		
		String sql = prop.getProperty("register");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.getUserName());
			pstmt.setInt(2, user.getUserAge());
			pstmt.setString(3, user.getUserGender());
			pstmt.setString(4, user.getUserEmail());
			
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			JDBCTemplate.commit(conn);
			JDBCTemplate.close(conn);
		}
		
		return -1; // 데이터베이스 오류
	}
}


