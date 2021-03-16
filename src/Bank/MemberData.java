package Bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class MemberData {
	private static String name;
	private static int age;
	private static String id;
	private static String pw;
	private static int balance;
	private static String bankNum;
	private static String phoneNumber;
	private static String mail;
	
	public boolean loginCheck(String loginId, String loginPw) {
		String driver = "com.mysql.cj.jdbc.Driver"; // ����̹� �غ�
		String url = "jdbc:mysql://localhost:3306/app?characterEncoding=UTF-8&serverTimezone=UTC";
		Connection conn = null; // ���������� ������ ������ ���� ������ ��ü�� ���� ������ null.
		Statement stmt = null; // �����ͺ��̽��� ����� ������ ���� ��ü.
		ResultSet rs = null;
		{

			try {
				Class.forName(driver);
				System.out.println("�����ͺ��̽� ����̹� �ε� �Ϸ�");
				conn = DriverManager.getConnection(url, "root", "java"); // getConnection(url, user, password) �������ִ� ���
				stmt = conn.createStatement();
				// SELECT ��
				rs = stmt.executeQuery("SELECT * FROM MEMBER WHERE ID = '" + loginId + "' && PW = '" + loginPw + "'");

		if (rs.next()) { // �α��� �ÿ��� ���Ϲ� ��� ������ ���
			name = rs.getString("NAME");
			age = rs.getInt("AGE");
			id = rs.getString("ID");
			pw = rs.getString("PW");
			balance = rs.getInt("BALANCE");
			bankNum = rs.getString("BANKNUMBER");
			mail = rs.getString("MAIL");
			System.out.println(name + ", " + age + ", " + id + ", " + pw + ", " + balance + ", " + bankNum + ", " + mail);
			
			return true;
		}
				ResultSetMetaData rsmd = rs.getMetaData();
				int colCnt = rsmd.getColumnCount();

				while (rs.next()) { // �α��� �ÿ��� ���Ϲ� ��� ������ ���
					for (int i = 1; i <= colCnt; i++) {
						String result = rs.getString(i);
						System.out.println(result);
					}

				}
			} catch (Exception e) {
//		System.out.println("xx �����ͺ��̽� ���� ���� xx");
				e.printStackTrace();

			} finally {
				try {
					if (rs != null) {
						rs.close(); // ������ ������ �ݴ� ������ �ݾƾ� �Ѵ� ������ !!!
					}
					if (stmt != null) {
						stmt.close(); // ������ ������ �ݴ� ������ �ݾƾ� �Ѵ� ������ !!!
					}
					if (conn != null) {
						conn.close(); // �� ������ �ݵ�� �ݾƾ� �Ѵ�. �� ���� �� ������ �� ����.
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		return false;
	}
}
