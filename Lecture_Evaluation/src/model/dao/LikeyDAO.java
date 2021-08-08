package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import common.DatabaseUtil;
import model.dto.UserDTO;

public class LikeyDAO {

	public int like(String userID, String evaluationID, String userIP) {
		String SQL = "INSERT INTO LIKEY VALUES (?, ?, ?)";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DatabaseUtil.getConnection();

			pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, userID);
			pstmt.setInt(2, Integer.parseInt(evaluationID));
			pstmt.setString(3, userIP);

			return pstmt.executeUpdate(); // 성공하면 1 반환

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

		return -1; // 추천 중복 오류
	}
}
