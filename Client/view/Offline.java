package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Client;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Offline extends JFrame {

	private JPanel contentPane;

	public Offline() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, 620, 470);
		setLocationRelativeTo(null);
		this.setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(133, 186, 186));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		setContentPane(contentPane);
		
		JLabel lblGameCaro_1 = new JLabel("Game Caro");
		lblGameCaro_1.setFont(new Font("Dialog", Font.PLAIN, 50));
		lblGameCaro_1.setBounds(164, 23, 262, 90);
		contentPane.add(lblGameCaro_1);
		
		JLabel lblChonCheDoChoi = new JLabel("Chọn chế độ chơi");
		lblChonCheDoChoi.setHorizontalAlignment(SwingConstants.CENTER);
		lblChonCheDoChoi.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 30));
		lblChonCheDoChoi.setForeground(new Color(255, 255, 181));
		lblChonCheDoChoi.setBounds(151, 133, 311, 58);
		contentPane.add(lblChonCheDoChoi);
		
		JButton btnOffline = new JButton("Offline");

		btnOffline.setBackground(new Color(50, 205, 170));
		btnOffline.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
		btnOffline.setBounds(164, 248, 262, 43);
		btnOffline.setForeground(new Color(54, 34, 151));
		contentPane.add(btnOffline);
		
		btnOffline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new OffOnePlayerView();
			}
		});
		
		JButton btnOnline = new JButton("Online");

		btnOnline.setBackground(new Color(50, 205, 170));
		btnOnline.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
		btnOnline.setBounds(164, 333, 262, 37);
		btnOnline.setForeground(new Color(54, 34, 151));
		btnOnline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Client();
			}
		});
		contentPane.add(btnOnline);
	}
}
