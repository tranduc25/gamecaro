package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OffOnePlayerView extends JFrame {

	private JPanel contentPane;

	public OffOnePlayerView() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, 620, 470);
		setLocationRelativeTo(null);
		this.setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(133, 186, 186));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Dễ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new OnePlayerView(1);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		btnNewButton.setBounds(190, 184, 230, 50);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Trung Bình");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new OnePlayerView(2);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 35));
		btnNewButton_1.setBounds(190, 250, 230, 50);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Khó");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new OnePlayerView(3);
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 35));
		btnNewButton_2.setBounds(190, 320, 230, 50);
		contentPane.add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("Chọn mức độ");
		lblNewLabel.setForeground(new Color(54, 34, 151));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 45));
		lblNewLabel.setBounds(144, 70, 316, 61);
		contentPane.add(lblNewLabel);
	}
}
