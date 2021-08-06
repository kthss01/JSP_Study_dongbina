package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.vo.BBS;

public class BBSDAO {

	private Connection conn;
	private ResultSet rs;

	public BBSDAO() {
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

	// 현재 시간을 가져오는 함수
//	public String getDate() {
	public Timestamp getDate() {
		// MySQL에서 현재 시간 가져오는 함수
//		String SQL = "SELECT NOW()";
		String SQL = "SELECT SYSDATE FROM DUAL";

		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String str_date = rs.getString(1);

//				System.out.println(str_date);
				
				DateFormat formatter;

				formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				java.util.Date date = formatter.parse(str_date);

//				System.out.println(date.getTime());
				
				// test 코드
//				java.util.Date utilDate = new java.util.Date();
//				
//				Time time = new Time(utilDate.getTime());
//				Timestamp timeStamp = new Timestamp(utilDate.getTime());
//				
//				System.out.println(timeStamp);
//				System.out.println(time);
//				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
//				System.out.println("utilDate:" + utilDate);
//				System.out.println("sqlDate:" + sqlDate);
				
				return new Timestamp(date.getTime());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null; // 데이터베이스 오류
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

//			String date = getDate();
//			System.out.println(date);

			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
//			pstmt.setString(4, getDate());
			pstmt.setTimestamp(4, getDate());
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
//		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		String SQL = "SELECT * FROM ( SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC ) WHERE ROWNUM <= 10";
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
//		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		String SQL = "SELECT * FROM ( SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC ) WHERE ROWNUM <= 10";

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
		String SQL = "UPDATE BBS SET bbsTitle = ?, bbsContent = ? WHERE bbsID = ?";

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

	// 글 삭제
	public int delete(int bbsID) {
		String SQL = "UPDATE BBS SET bbsAvailable = 0 WHERE bbsID = ?";

		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);

			pstmt.setInt(1, bbsID);

			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1; // 데이터베이스 오류
	}
}
