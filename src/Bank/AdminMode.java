package Bank;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AdminMode extends JFrame {
	JTable table;
	JScrollPane panel;
	final DefaultTableModel model = new DefaultTableModel();

	public AdminMode() {
		//--------------------Table--------
		table = new JTable(model); // DB MEMBER ���̺� �޾ƿ���
		
		//----------------------Panel------
		panel = new JScrollPane(table);
		
		//------------------------FRAME-----
		setTitle("Admin Mode");
		
		add(panel);
		
		setSize(500, 500);
		setLocation(1100, 200);
		setVisible(true);
		addWindowListener(new WindowAdapter() { // ���� �͸� Ŭ����(Anonymous) - ������, ����Ͽ��� ���� ����.
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});
		select();
	}
	
	public void select() {
		String driver = "com.mysql.cj.jdbc.Driver"; // ����̹� �غ�
		String url = "jdbc:mysql://localhost:3306/app?characterEncoding=UTF-8&serverTimezone=UTC";
		Connection conn = null; // ���������� ������ ������ ���� ������ ��ü�� ���� ������ null.
		Statement stmt = null; // �����ͺ��̽��� ����� ������ ���� ��ü.
		ResultSet rs = null;

		try {
			Class.forName(driver);
			System.out.println("�����ͺ��̽� ����̹� �ε� �Ϸ�");
			conn = DriverManager.getConnection(url, "root", "java"); // getConnection(url, user, password) �������ִ� ���
			String sql = "SELECT * FROM MEMBER";
			stmt = conn.createStatement();
			// SELECT ��
			rs = stmt.executeQuery(sql); // -1 �̸� ����, 0�� 1�̸� ����.
			
			while(rs.next()) {
				String name = rs.getString("NAME");
				String age = rs.getString("AGE");
				String id = rs.getString("ID");
				String pw = rs.getString("PW");
				String balance = rs.getString("BALANCE");
				String bankNum = rs.getString("BANKNUMBER");
				String mail = rs.getString("MAIL");
				
				Object memData[] = {name, age, id, pw, balance, bankNum, mail};
				
				model.addRow(memData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("xx �����ͺ��̽� ���� ���� xx");

		} finally {
			try {
				if (rs != null) {
					rs.close(); // �� ������ �ݵ�� �ݾƾ� �Ѵ�. �� ���� �� ������ �� ����.
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
	
	public String delete() { //--------------------------DB ���̺� �޾ƿ��鼭 �ش� row delete.
		java.util.Scanner scan = new java.util.Scanner(System.in);
		
		System.out.print("���� ������ ���̵� �Է�: ");
		String delId = scan.next();
		
		System.out.print("���� ������ ��� �Է�: ");
		String delPw = scan.next();
		
		String sql = "DELETE FROM DBTEST WHERE ID = '" + delId + "' && PW = '" + delPw + "'"; // �ش� ���̵� ������ �������� �ƹ� �ϵ� �Ͼ�� �ʴ´�.
		return sql;
	}
}
