package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DBEx13 {
//	DBAction class Test

	public DBEx13() {
		DBAction db = DBAction.getInstance();
		Connection conn = db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM TEST1");
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCnt = rsmd.getColumnCount();

			while (rs.next()) {
				for (int i = 1; i <= colCnt; i++) {
					System.out.print(rs.getString(i) + "\t");
				}
				System.out.println();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e2) {
				// TODO: handle exception
			}
		}
	}

	public static void main(String[] args) {
		new DBEx13();

	}

}
