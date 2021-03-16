package Bank;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;

public class Join extends JFrame implements ActionListener {

	Join() {

		setLayout(new CardLayout(0, 0));
		setLocationRelativeTo(null);
		setSize(500, 500);
		setLocation(1100, 200);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		add("1", new Panel());
		setVisible(true);
		Panel.btn[1].addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();

		if (obj == Panel.btn[1]) {
			dispose();
		}
	}

}

class Panel extends JPanel {

	static JButton[] btn;
	static JTextField[] field;
	JLabel[] label;

	Panel() {

		setLayout(new GridLayout(8, 2));

		btn = new JButton[2];
		btn[0] = new JButton("Submit");
		btn[1] = new JButton("Close");

		label = new JLabel[8];
		label[0] = new JLabel("Name :");
		label[1] = new JLabel("Age :");
		label[2] = new JLabel("ID :");
		label[3] = new JLabel("PW :");
		label[4] = new JLabel("Phone Number :");
		label[5] = new JLabel("Mail Address :");
		label[6] = new JLabel("");
		label[7] = new JLabel("");

		field = new JTextField[6];
		field[0] = new JTextField();
		field[1] = new JTextField();
		field[2] = new JTextField();
		field[3] = new JTextField();
		field[4] = new JTextField();
		field[5] = new JTextField();

		add(label[0]);
		add(field[0]);
		add(label[1]);
		add(field[1]);
		add(label[2]);
		add(field[2]);
		add(label[3]);
		add(field[3]);
		add(label[4]);
		add(field[4]);
		add(label[5]);
		add(field[5]);
		add(label[6]);
		add(label[7]);

		add(btn[0]);
		add(btn[1]);
		set();
	}

	void set() {

		btn[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String driver = "com.mysql.cj.jdbc.Driver"; // ����̹� �غ�
				String url = "jdbc:mysql://localhost:3306/app?characterEncoding=UTF-8&serverTimezone=UTC";
				Connection conn = null; // ���������� ������ ������ ���� ������ ��ü�� ���� ������ null.
				Statement stmt = null; // �����ͺ��̽��� ����� ������ ���� ��ü.

				try {
					Class.forName(driver);
					conn = DriverManager.getConnection(url, "root", "java"); // getConnection(url, user, password) �������ִ� ���
					stmt = conn.createStatement();

					String sql = join();
					int result = stmt.executeUpdate(sql);
					if (result > -1) {
						JOptionPane.showMessageDialog(null, "You have successfully joined.");
					}
				} catch (Exception e1) {
//					JOptionPane.showMessageDialog(null, "The Account already exists.");
					e1.printStackTrace();

				} finally {
					try {
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
				
				
//				DBAction db = DBAction.getInstance();
//				Connection conn = db.getConnection();
//				Statement stmt = null;
//				
//				try {
//					stmt = conn.createStatement();
//
//					String sql = join();
//					int result = stmt.executeUpdate(sql);
//					if (result > -1) {
//						JOptionPane.showMessageDialog(null, "You have successfully joined.");
//					}
//				} catch (Exception e1) {
////					JOptionPane.showMessageDialog(null, "The Account already exists.");
//					e1.printStackTrace();
//				} finally {
//					try {
//						db.close();
//					} catch (Exception e2) {
//
//					}
//
//				}
				
				
			}

		});

	}

	public static String join() {
//		String accNum = "";
//		Random ran = new Random();
//		
//		for (int i = 0; i < 6; i++) {
//			accNum.concat(Integer.toString((ran.nextInt(9) + 1)));
//		}

		String name = field[0].getText();
		int age = Integer.parseInt(field[1].getText());
		String id = field[2].getText();
		String pw = field[3].getText();
		String phone = field[4].getText();
		String mail = field[5].getText();

		String sql = "insert into MEMBER values('" + name + "', " + age + ", '" + id + "', '" + pw + "', " + 0 +
				", '123456', '" + phone + "', '" + mail + "', 'Valid')";
		return sql;
	}

}
