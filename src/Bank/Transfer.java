package Bank;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Transfer extends JPanel {

	JButton[] button;
	static JTextField[] field;
	JLabel[] label;
	File file;
	FileWriter write;
	JPasswordField password;
	int bal;
	Start start;

	Transfer(Start start) {
		this.start = start;

//		setLocation(800, 350);
//		setSize(new Dimension(300, 300));

		button = new JButton[2];
		field = new JTextField[4];
		label = new JLabel[4];

		label[0] = new JLabel("Amount to Transfer :");
		field[0] = new JTextField("Unit(won)");

		label[3] = new JLabel("Transfer to (Other) :");
		field[3] = new JTextField("(Bank Account Number)");

		label[1] = new JLabel("Transfer from (You) :");
		field[1] = new JTextField("(Bank Account Number)");

		label[2] = new JLabel("Password :");
		password = new JPasswordField("");

		button[0] = new JButton("OK");
		button[1] = new JButton("CANCEL");

		add(label[0]);
		add(field[0]);
		add(label[3]);
		add(field[3]);
		add(label[1]);
		add(field[1]);
		add(label[2]);
		add(password);
		add(button[0]);
		add(button[1]);
		setLayout(new GridLayout(5, 2));

		add();

		button[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				start.MainCards.show(start, "menu");
			}
		});

		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				from();
				to();

			}

			void from() {
				String driver = "com.mysql.cj.jdbc.Driver"; // 드라이버 준비
				String url = "jdbc:mysql://localhost:3306/app?characterEncoding=UTF-8&serverTimezone=UTC";
				Connection conn = null; // 물리적으로 서버에 연결이 되지 않으면 객체가 없기 때문에 null.
				Statement stmt = null; // 데이터베이스에 명령을 내리기 위한 객체.
				ResultSet rs = null;

				try {
					Class.forName(driver);
					System.out.println("데이터베이스 드라이버 로딩 완료");
					conn = DriverManager.getConnection(url, "root", "java"); // getConnection(url, user, password) 연결해주는
																				// 기능
					stmt = conn.createStatement();

					int sum = 0;
					rs = stmt.executeQuery(
							"select BALANCE, PW from MEMBER WHERE BANKNUMBER = '" + field[1].getText() + "'");

					if (rs.next()) {
						if (new String(password.getPassword()).equals(rs.getString("PW"))) { // ---------여기부터 들어오지 못함.

							int bal = rs.getInt("BALANCE");
							sum = bal - Integer.parseInt(field[0].getText());
							System.out.println(sum);
							String sql2 = "update MEMBER set BALANCE = '" + sum + "' WHERE BANKNUMBER = '"
									+ field[1].getText() + "'";
							int result = stmt.executeUpdate(sql2);
							if (result > -1) {
								System.out.println("success from");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Wrong password.", "Notice", JOptionPane.ERROR_MESSAGE);
						}
					}
				} catch (Exception e2) {
//					JOptionPane.showMessageDialog(null, "Failed to execute command");
					e2.printStackTrace();

				} finally {
					try {
						if (rs != null) {
							rs.close(); // 열었던 순서의 반대 순서로 닫아야 한다 무조건 !!!
						}
						if (stmt != null) {
							stmt.close(); // 열었던 순서의 반대 순서로 닫아야 한다 무조건 !!!
						}
						if (conn != null) {
							conn.close(); // 다 썼으면 반드시 닫아야 한다. 안 닫을 시 에러날 수 있음.
						}
					} catch (Exception e3) {
						// TODO: handle exception
					}
				}
			}
		});

	}

	void to() {
		String driver = "com.mysql.cj.jdbc.Driver"; // 드라이버 준비
		String url = "jdbc:mysql://localhost:3306/app?characterEncoding=UTF-8&serverTimezone=UTC";
		Connection conn = null; // 물리적으로 서버에 연결이 되지 않으면 객체가 없기 때문에 null.
		Statement stmt = null; // 데이터베이스에 명령을 내리기 위한 객체.
		ResultSet rs = null;

		try {
			Class.forName(driver);
			System.out.println("데이터베이스 드라이버 로딩 완료");
			conn = DriverManager.getConnection(url, "root", "java"); // getConnection(url, user, password) 연결해주는
																		// 기능
			stmt = conn.createStatement();

			int sum = 0;
			rs = stmt.executeQuery("select BALANCE, NAME from MEMBER WHERE BANKNUMBER = '" + field[3].getText() + "'");

			if (rs.next()) {
				int bal = rs.getInt("BALANCE");
				sum = bal + Integer.parseInt(field[0].getText());
				System.out.println(sum);
				int result = stmt.executeUpdate(
						"update MEMBER set BALANCE = '" + sum + "' WHERE BANKNUMBER = '" + field[3].getText() + "'");
				if (result > -1) {
					System.out.println("success to");
					JOptionPane.showMessageDialog(null,
							"You have sent " + field[0].getText() + "won to " + rs.getString("NAME"));
					start.MainCards.show(start, "menu");
				}
			}
		} catch (Exception e2) {
		JOptionPane.showMessageDialog(null, "Failed to execute command");

		} finally {
			try {
				if (rs != null) {
					rs.close(); // 열었던 순서의 반대 순서로 닫아야 한다 무조건 !!!
				}
				if (stmt != null) {
					stmt.close(); // 열었던 순서의 반대 순서로 닫아야 한다 무조건 !!!
				}
				if (conn != null) {
					conn.close(); // 다 썼으면 반드시 닫아야 한다. 안 닫을 시 에러날 수 있음.
				}
			} catch (Exception e3) {
				// TODO: handle exception
			}
		}
	}

	void add() {
		field[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				field[0].setText("");
				field[0].removeMouseListener(this);
			}
		});
		field[0].addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				super.keyTyped(e);
				char c = e.getKeyChar();

				if (!Character.isDigit(c)) {
					e.consume();
					JOptionPane.showMessageDialog(null, "You can type numbers only.", "Notice",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		field[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				field[3].setText("");
				field[3].removeMouseListener(this);
			}
		});
		field[3].addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				super.keyTyped(e);
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
					JOptionPane.showMessageDialog(null, "You can type numbers only.", "Notice",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		field[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				field[1].setText("");
				field[1].removeMouseListener(this);
			}
		});
		field[1].addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				super.keyTyped(e);
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
					JOptionPane.showMessageDialog(null, "You can type numbers only.", "Notice",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		password.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				password.setText("");
				password.removeMouseListener(this);
			}
		});
		password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				super.keyTyped(e);
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
					JOptionPane.showMessageDialog(null, "You can type numbers only.", "Notice",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
	}

}
