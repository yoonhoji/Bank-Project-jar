package Bank;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class Menu extends JPanel{
	JButton[] button;
	Start start;

	Menu(Start start) {
		this.start = start;

		setLayout(new GridLayout(3, 1));
		button = new JButton[6];
		button[0] = new JButton("Deposit");
//		button[0].setBounds(0, 0, 100, 100);
		button[1] = new JButton("Transfer");
//		button[1].setBounds(150, 0, 100, 100);
		button[2] = new JButton("Admin Mode");
//		button[3].setBounds(150, 150, 100, 100);
//		button[3] = new JButton("forgot ID?");
////		button[4].setBounds(200, 200, 100, 100);
//		button[4] = new JButton("forgot PW?");
////		button[5].setBounds(250, 250, 100, 100);

		add(button[0]);
		add(button[1]);
		add(button[2]);
//		add(button[3]);
//		add(button[4]);
		Choice();
	}

	void Choice() {
		button[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				start.MainCards.show(start, "deposit");
			}

		});
		button[1].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				start.MainCards.show(start, "transfer");
			}

		});
		button[2].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AdminMode();
			}

		});
//		button[3].addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				new FindId();
//
//			}
//
//		});
//		button[4].addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
////				new FindPw();
//
//			}
//
//		});
	}
}