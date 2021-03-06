package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.vo.User;

public class UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
		try {
//			String dbURL = "jdbc:mysql://localhost:3306/BBS";
//			String dbURL = "jdbc:mysql://localhost:3306/BBS?characterEncoding=UTF-8&serverTimezone=UTC";
//			String dbID = "root";
//			String dbPassword = "0704";
			
			String dbURL = "jdbc:oracle:thin:@34.64.110.11:1521:xe";
			String dbID = "GCP_KTH";
			String dbPassword = "GCP_KTH";

//			Class.forName("com.mysql.jdbc.Driver");
//			Class.forName("com.mysql.cj.jdbc.Driver");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM BBS_USER WHERE userID = ?";
		
		try {
			
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,  userID);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				if (rs.getNString(1).equals(userPassword)) {
					return 1; // 로그인 성공
				} else {
					return 0; // 비밀번호 불일치
				}
			} 
			
			return -1; // 아이디가 없음
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -2; // 데이터베이스 오류 의미
	}
	
	public int join(User user) {
		String SQL = "INSERT INTO BBS_USER VALUES(?, ?, ?, ?, ?)";
	
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			
			// 0 이상의 숫자 반환됨
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1; // 데이터베이스 오류
	}
}
