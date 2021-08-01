package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.vo.BBS;

public class BBSDAO {

	private Connection conn;
	private ResultSet rs;

	public BBSDAO() {
		try {
//			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			String dbURL = "jdbc:mysql://localhost:3306/BBS?characterEncoding=UTF-8&serverTimezone=UTC";
			String dbID = "root";
			String dbPassword = "0704";

//			Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 현재 시간을 가져오는 함수
	public String getDate() {
		// MySQL에서 현재 시간 가져오는 함수
		String SQL = "SELECT NOW()";

		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ""; // 데이터베이스 오류
	}

	// 다음 번호가 뭔지 가져오는 함수
	public int getNext() {
		String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";

		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) + 1;
			}

			return 1; // 첫번째 게시물인 경우
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1; // 데이터베이스 오류
	}

	public int write(String bbsTitle, String userID, String bbsContent) {
		String SQL = "INSERT INTO BBS VALUES (?, ?, ?, ?, ?, ?)";

		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);
			
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6, 1);
			
			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1; // 데이터베이스 오류
	}
	
	// 페이지에 따른 게시글 가져오기 10개씩 가져올꺼
	public ArrayList<BBS> getList(int pageNumber) {
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		ArrayList<BBS> list = new ArrayList<>();
		
		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);
			
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BBS bbs = new BBS();
				
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				
				list.add(bbs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	// 다음 페이지가 있는지 확인 - 페이징 처리
	public boolean nextPage(int pageNumber) {
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		
		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);
			
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public BBS getBbs(int bbsID) {
		String SQL = "SELECT * FROM BBS WHERE bbsID = ?";
		
		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);
			
			pstmt.setInt(1, bbsID);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				BBS bbs = new BBS();
				
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				
				return bbs;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	// 글 수정
	public int update(int bbsID, String bbsTitle, String bbsContent) {
		String SQL = "UPDATE BBS SET bbsTitle = ?, bbsContent WHERE bbsID = ?";

		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);
			
			pstmt.setString(1, bbsTitle);
			pstmt.setString(2, bbsContent);
			pstmt.setInt(3, bbsID);
			
			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1; // 데이터베이스 오류
	}
}
