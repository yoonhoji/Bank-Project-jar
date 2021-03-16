package Bank;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.DBAction;

public class Start extends Frame implements ActionListener, MouseMotionListener {
	JPanel first;
	JLabel[] la;
	JButton[] btn;
	static int x, y;
	static CardLayout MainCards;

	Menu menu = new Menu(this);
	Deposit dep = new Deposit(this);
	Transfer trf = new Transfer(this);
	LogIn lg = new LogIn(this);
	LoginThing lt = new LoginThing(this);

	public Start() {
		lg.dispose();

		la = new JLabel[10];
		for (int i = 0; i < la.length; i++) {
			la[i] = new JLabel("");
		}

		btn = new JButton[10];
		for (int i = 0; i < btn.length; i++) {
			btn[i] = new JButton("");
		}

		la[0].setText("THE BANK");
		la[0].setFont(new Font("", 1, 50));
		la[0].setBounds(120, 50, 400, 200);

		btn[0].setText("Log In");
		btn[0].setBounds(100, 350, 100, 60);
		btn[0].addActionListener(this);

		btn[1].setText("Join");
		btn[1].setBounds(300, 350, 100, 60);
		btn[1].addActionListener(this);

		// -----------------------First------
		first = new JPanel();
		first.setLayout(null);
		first.add(la[0]);
		first.add(btn[0]);
		first.add(btn[1]);

		// -------------------------FRAME---------
		MainCards = new CardLayout();
		menu = new Menu(this);

		setTitle("THE BANK");
		setLayout(MainCards);

		add(first, "first");
		add(menu, "menu");

		add(dep, "deposit");
		add(trf, "transfer");

		setSize(500, 500);
		setLocation(600, 200);
		setVisible(true);
		addWindowListener(new WindowAdapter() { // ���� �͸� Ŭ����(Anonymous) - ������, ����Ͽ��� ���� ����.
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource(); // -----------------�������� �̿��� ������Ʈ�� ��ü�� �����Ѵ�.
		JButton b = (JButton) obj;

		if (obj == btn[0]) {
			new LogIn(this);
		}
		if (obj == btn[1]) {
			new Join();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

		x = getLocationOnScreen().x;
		y = getLocationOnScreen().y;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
//		String driver = "com.mysql.cj.jdbc.Driver"; // ����̹� �غ�
//		String url = "jdbc:mysql://localhost:3306/app?characterEncoding=UTF-8&serverTimezone=UTC";
//		Connection conn = null; // ���������� ������ ������ ���� ������ ��ü�� ���� ������ null.
//		Statement stmt = null; // �����ͺ��̽��� ����� ������ ���� ��ü.
//
//		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, "root", "java"); // getConnection(url, user, password) �������ִ� ���
//			stmt = conn.createStatement();
//			stmt.executeUpdate(
//					"create table MEMBER(NAME varchar(10), AGE int, ID varchar(10) PRIMARY KEY, PW varchar(10), BALANCE int, BANKNUMBER varchar(10), PHONENUMBER varchar(10), MAIL varchar(10))");
//		} catch (Exception e1) {
//			System.out.println();
//		} finally {
//			try {
//				if (stmt != null) {
//					stmt.close(); // ������ ������ �ݴ� ������ �ݾƾ� �Ѵ� ������ !!!
//				}
//				if (conn != null) {
//					conn.close(); // �� ������ �ݵ�� �ݾƾ� �Ѵ�. �� ���� �� ������ �� ����.
//				}
//			} catch (Exception e2) {
//				// TODO: handle exception
//			}
//		}
//		new Start();
		
		try {
			DBAction db = DBAction.getInstance();
			Connection conn = db.getConnection();
			Statement stmt = null;
			stmt = conn.createStatement();
			stmt.executeUpdate(
					"create table MEMBER(NAME varchar(10), "
					+ "AGE int, "
					+ "ID varchar(10) PRIMARY KEY, "
					+ "PW varchar(10), "
					+ "BALANCE int, "
					+ "BANKNUMBER varchar(10), "
					+ "PHONENUMBER varchar(10), "
					+ "MAIL varchar(10))");
			db.close();

		} catch (SQLException e) {
			
		} catch (NullPointerException e1) {
			
		}
		finally {

		}
		new Start();

	}

}
