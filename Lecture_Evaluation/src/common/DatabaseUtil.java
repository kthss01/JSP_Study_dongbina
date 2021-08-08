package common;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {

	private static Connection conn = null;

	public static Connection getConnection() {
		if (conn == null) {

			try {
				String dbURL = "jdbc:mysql://localhost:3306/LectureEvaluation?characterEncoding=UTF-8&serverTimezone=UTC";
				String dbID = "root";
				String dbPassword = "0704";

				Class.forName("com.mysql.cj.jdbc.Driver");

				conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("JDBC 연결 성공");
		}

		return conn;
	}

}
