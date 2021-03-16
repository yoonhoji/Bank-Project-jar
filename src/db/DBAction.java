package db;

import java.io.FileReader;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBAction {
	private static DBAction instance = new DBAction();
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;

	public DBAction() {
		// 실습 1 Properties
		try {
			Properties properties = new Properties();
			String path = DBAction.class.getResource("database.properties").getPath();

			path = URLDecoder.decode(path, "utf-8"); // 문자셋이 깨지지 않게 해주는 작업.
			properties.load(new FileReader(path)); // 파일에 있는 데이터를 읽어오는 FileReader.
			String driver = properties.getProperty("driver");
			String url = properties.getProperty("url");
			String username = properties.getProperty("username");
			String password = properties.getProperty("password");

			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);

		} catch (Exception e) {
//			e.printStackTrace();
		}
		// 실습 2 JDBC 기본
//		String driver = "com.mysql.cj.jdbc.Driver"; // 드라이버 준비
//		String url = "jdbc:mysql://localhost:3306/app?characterEncoding=UTF-8&serverTimezone=UTC";
//
//		try {
//			Class.forName(driver);
//			System.out.println("데이터베이스 드라이버 로딩 완료");
//			conn = DriverManager.getConnection(url, "root", "java"); // getConnection(url, user, password) 연결해주는 기능
//
//		} catch (Exception e) {
////			System.out.println("xx 데이터베이스 연결 실패 xx");
//			e.printStackTrace();
//		}

	}

	public static DBAction getInstance() {
		if (instance == null) {
			instance = new DBAction();
		}
		return instance;
	}

	public Connection getConnection() {

		return conn;
	}

	public void close() {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
