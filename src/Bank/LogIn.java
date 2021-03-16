package Bank;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LogIn extends JFrame {

	static JButton[] button;
	Start start;

	LogIn(Start start) {
		String driver = "com.mysql.cj.jdbc.Driver"; // ����̹� �غ�
		String url = "jdbc:mysql://localhost:3306/app?characterEncoding=UTF-8&serverTimezone=UTC";
		Connection conn = null; // ���������� ������ ������ ���� ������ ��ü�� ���� ������ null.
		Statement stmt = null; // �����ͺ��̽��� ����� ������ ���� ��ü.
		ResultSet rs = null;
		
		this.start = start;
		button = new JButton[3];
		button[0] = new JButton("OK");
		button[1] = new JButton("CANCEL");

		setTitle("Log In");
		setLocation(800, 350);
		setLayout(new BorderLayout());
		setSize(new Dimension(250, 250));
		getContentPane().add(new LoginThing(start), BorderLayout.CENTER);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		button[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}

class LoginThing extends JPanel {

	static JTextField[] field;
	JLabel[] label;
	FileWriter write;
	static JPasswordField password;
	LogIn lg;
	Start start;

	LoginThing(Start start) {
		this.start = start;

		setSize(200, 200);
		setLayout(new GridLayout(3, 3));

		label = new JLabel[2];
		label[0] = new JLabel("ID :");
		label[1] = new JLabel("PW :");

		field = new JTextField[1];
		field[0] = new JTextField();
		password = new JPasswordField();

		add(label[0]);
		add(field[0]);
		add(label[1]);
		add(password);
		add(lg.button[0]);
		add(lg.button[1]);
		set();
//		login(field[0].getText(), new String(password.getPassword()));
	}

	void set() {
		field[0].addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				field[0].setText("");
				field[0].removeMouseListener(this);
			}
		});
		password.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				password.setText("");
				password.removeMouseListener(this);
			}
		});
		password.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();

				if (!Character.isDigit(c)) {
					e.consume();
					JOptionPane.showConfirmDialog(null, "���ڸ� �Է°����մϴ�", "�˸�", JOptionPane.CLOSED_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null);
					return;
				}
			}

		});
		lg.button[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				search();
				start.MainCards.show(start, "menu");
//				lg.dispose();
			}

		});
		lg.button[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				lg.dispose();
			}

		});

	}
//	public boolean login(String id, String pw) {
//		boolean result = false;
//		String driver = "com.mysql.cj.jdbc.Driver"; // ����̹� �غ�
//		String url = "jdbc:mysql://localhost:3306/app?characterEncoding=UTF-8&serverTimezone=UTC";
//		Connection conn = null; // ���������� ������ ������ ���� ������ ��ü�� ���� ������ null.
//		Statement stmt = null; // �����ͺ��̽��� ����� ������ ���� ��ü.
//		ResultSet rs = null;
//		
//		String sql = "SELECT NAME, ID, PW FROM MEMBER WHERE ID = '" + id + "'";
//		
//		try {
//			rs = stmt.executeQuery(sql);
//			if(rs.next()) {
//				String r_pw = rs.getString("PW");
//				if(pw.equals(r_pw)) {
//					String name = rs.getString("NAME");
//					member = new Member();
//					member.setName(name);
//					//dialog with name
//					result = true;
//				}else {
//					JOptionPane.showMessageDialog(null, "Invalid password.", "Notice", JOptionPane.ERROR_MESSAGE);
//				}
//			}else {
//				JOptionPane.showMessageDialog(null, "Invalid ID.", "Notice", JOptionPane.ERROR_MESSAGE);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			
//		}
//	}

	void search() { // ------------------------------------DB ����--------------

		String driver = "com.mysql.cj.jdbc.Driver"; // ����̹� �غ�
		String url = "jdbc:mysql://localhost:3306/app?characterEncoding=UTF-8&serverTimezone=UTC";
		Connection conn = null; // ���������� ������ ������ ���� ������ ��ü�� ���� ������ null.
		Statement stmt = null; // �����ͺ��̽��� ����� ������ ���� ��ü.
		ResultSet rs = null;

		try {
			Class.forName(driver);
			System.out.println("�����ͺ��̽� ����̹� �ε� �Ϸ�");
			conn = DriverManager.getConnection(url, "root", "java"); // getConnection(url, user, password) �������ִ� ���
			stmt = conn.createStatement();
			// SELECT ��
			String sql = select();
			rs = stmt.executeQuery(sql);
			boolean slct = true;
			boolean pwErr = true;
			String pw_DB = null;

			if (rs.next()) {
				pw_DB = rs.getString("PW");
			}

			if (pw_DB != null && new String(password.getPassword()).equals(pw_DB)) {
				slct = false;
				JOptionPane.showMessageDialog(null, "You have successfully logged in.");
				start.MainCards.show(start, "menu");
				lg.dispose(); // �Ƹ��� �θ� �������� ���̷� �� �����Ф�				
				
			} else if (pw_DB != null) {
				JOptionPane.showMessageDialog(null, "Invalid password.", "Notice", JOptionPane.ERROR_MESSAGE);
				pwErr = false;
			}
			if (slct) {
				if (pwErr) {
					JOptionPane.showMessageDialog(null, "Invalid ID.", "Notice", JOptionPane.ERROR_MESSAGE);
				}
			}

		} catch (Exception e) {
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

	public static String select() {
		String sql = "SELECT PW FROM MEMBER WHERE ID = '" + field[0].getText() + "'";
		return sql;
	}
}
