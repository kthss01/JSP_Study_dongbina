package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

			pstmt.setString(1, evaluatioinDTO.getUserID());
			pstmt.setString(2, evaluatioinDTO.getLectureName());
			pstmt.setString(3, evaluatioinDTO.getProfessorName());
			
			pstmt.setInt(4, evaluatioinDTO.getLectureYear());
			
			pstmt.setString(5, evaluatioinDTO.getSemesterDivide());
			pstmt.setString(6, evaluatioinDTO.getLectureDivide());
			
			pstmt.setString(7, evaluatioinDTO.getEvaluationTitle());
			pstmt.setString(8, evaluatioinDTO.getEvaluationContent());
			
			pstmt.setString(9, evaluatioinDTO.getTotalScore());
			pstmt.setString(10, evaluatioinDTO.getCreditScore());
			pstmt.setString(11, evaluatioinDTO.getComfortableScore());
			pstmt.setString(12, evaluatioinDTO.getLectureScore());

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
	
}
