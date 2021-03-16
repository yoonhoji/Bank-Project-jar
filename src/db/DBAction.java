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
		// �ǽ� 1 Properties
		try {
			Properties properties = new Properties();
			String path = DBAction.class.getResource("database.properties").getPath();

			path = URLDecoder.decode(path, "utf-8"); // ���ڼ��� ������ �ʰ� ���ִ� �۾�.
			properties.load(new FileReader(path)); // ���Ͽ� �ִ� �����͸� �о���� FileReader.
			String driver = properties.getProperty("driver");
			String url = properties.getProperty("url");
			String username = properties.getProperty("username");
			String password = properties.getProperty("password");

			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);

		} catch (Exception e) {
//			e.printStackTrace();
		}
		// �ǽ� 2 JDBC �⺻
//		String driver = "com.mysql.cj.jdbc.Driver"; // ����̹� �غ�
//		String url = "jdbc:mysql://localhost:3306/app?characterEncoding=UTF-8&serverTimezone=UTC";
//
//		try {
//			Class.forName(driver);
//			System.out.println("�����ͺ��̽� ����̹� �ε� �Ϸ�");
//			conn = DriverManager.getConnection(url, "root", "java"); // getConnection(url, user, password) �������ִ� ���
//
//		} catch (Exception e) {
////			System.out.println("xx �����ͺ��̽� ���� ���� xx");
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
