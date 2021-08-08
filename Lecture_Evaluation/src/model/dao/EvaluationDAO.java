package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import common.DatabaseUtil;
import model.dto.EvaluationDTO;

public class EvaluationDAO {

	public int write(EvaluationDTO evaluatioinDTO) {
		String SQL = "INSERT INTO EVALUATION VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DatabaseUtil.getConnection();

			pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, evaluatioinDTO.getUserID().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			pstmt.setString(2, evaluatioinDTO.getLectureName().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			pstmt.setString(3, evaluatioinDTO.getProfessorName().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			
			pstmt.setInt(4, evaluatioinDTO.getLectureYear());
			
			pstmt.setString(5, evaluatioinDTO.getSemesterDivide().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			pstmt.setString(6, evaluatioinDTO.getLectureDivide().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			
			pstmt.setString(7, evaluatioinDTO.getEvaluationTitle().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			pstmt.setString(8, evaluatioinDTO.getEvaluationContent().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			
			pstmt.setString(9, evaluatioinDTO.getTotalScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			pstmt.setString(10, evaluatioinDTO.getCreditScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			pstmt.setString(11, evaluatioinDTO.getComfortableScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));
			pstmt.setString(12, evaluatioinDTO.getLectureScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n","<br>"));

			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
//				if (conn != null)
//					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return -1; // 데이터베이스 오류
	}
	
	public ArrayList<EvaluationDTO> getList (String lectureDivide, String searchType, String search, int pageNumber) {
		if (lectureDivide.equals("전체")) {
			lectureDivide = "";
		}
		
		ArrayList<EvaluationDTO> evaluationList = null;
		
		String SQL = "";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			if (searchType.equals("최신순")) {
				SQL = "SELECT * FROM EVALUATION WHERE lectureDivide LIKE ? "
						+ "AND CONCAT(lectureName, professorName, evaluationTitle, evaluationContent) LIKE ? "
						+ "ORDER BY evaluationID DESC LIMIT " + pageNumber * 5 + ", " + pageNumber * 5 + 6;
			} else if (searchType.equals("추천순")) {
				SQL = "SELECT * FROM EVALUATION WHERE lectureDivide LIKE ? "
						+ "AND CONCAT(lectureName, professorName, evaluationTitle, evaluationContent) LIKE ? "
						+ "ORDER BY likeCount DESC LIMIT " + pageNumber * 5 + ", " + pageNumber * 5 + 6;
			}
			
			conn = DatabaseUtil.getConnection();

			pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, "%" + lectureDivide + "%");
			pstmt.setString(2, "%" + search + "%");

			rs = pstmt.executeQuery();
			
			evaluationList = new ArrayList<EvaluationDTO>();

			while (rs.next()) {
				EvaluationDTO evaluation = new EvaluationDTO(
						rs.getInt(1),
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4),
						rs.getInt(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getString(10),
						rs.getString(11),
						rs.getString(12),
						rs.getString(13),
						rs.getInt(14)
						);
				
				evaluationList.add(evaluation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
//				if (conn != null)
//					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return evaluationList; 
	}
	
	public int like(String evaluationID) {
		String SQL = "UPDATE EVALUATION SET likeCount = likeCount + 1 WHERE evaluationID = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DatabaseUtil.getConnection();

			pstmt = conn.prepareStatement(SQL);

			pstmt.setInt(1, Integer.parseInt(evaluationID));

			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
//				if (conn != null)
//					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return -1; // 데이터베이스 오류
	}
	
	public int delete(String evaluationID) {
		String SQL = "DELETE FROM EVALUATION WHERE evaluationID = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DatabaseUtil.getConnection();

			pstmt = conn.prepareStatement(SQL);

			pstmt.setInt(1, Integer.parseInt(evaluationID));

			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
//				if (conn != null)
//					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return -1; // 데이터베이스 오류
	}
	
	public String getUserID(String evaluationID) {
		String SQL = "SELECT userID FROM EVALUATION WHERE evaluationID = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DatabaseUtil.getConnection();

			pstmt = conn.prepareStatement(SQL);

			pstmt.setInt(1, Integer.parseInt(evaluationID));

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
//				if (conn != null)
//					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null; // 존재하지 않는 아이디
	}
}