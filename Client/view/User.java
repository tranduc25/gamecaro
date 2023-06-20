package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Toolkit;

public class User extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private Socket client;
	private DataOutputStream os;

	public User(Socket client) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(User.class.getResource("/icon/tic-tac-toe.png")));
		this.client = client;
		try {
			os = new DataOutputStream(client.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(534, 386);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox<ImageIcon> comboBox = new JComboBox();
		comboBox.setBounds(210, 194, 100, 34);

		comboBox.setMaximumRowCount(5);
		for (int i = 0; i <= 5; i++) {
			comboBox.addItem(new ImageIcon("image/" + i + ".jpg"));
		}
		contentPane.add(comboBox);

		JLabel lblNewLabel = new JLabel("Hãy nhập tên nhân vật của bạn");
		lblNewLabel.setFont(new Font("Courier New", Font.ITALIC, 17));
		lblNewLabel.setBounds(109, 42, 302, 34);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Courier New", Font.ITALIC, 15));
		textField.setBounds(190, 93, 140, 34);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Chọn ảnh đại diện");
		lblNewLabel_1.setFont(new Font("Courier New", Font.ITALIC, 17));
		lblNewLabel_1.setBounds(168, 148, 184, 30);
		contentPane.add(lblNewLabel_1);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(10, 10, 500, 329);
		contentPane.add(panel);
		panel.setLayout(null);
		
				JButton btnNewButton = new JButton("");
				btnNewButton.setIcon(new ImageIcon(User.class.getResource("/icon/enter.png")));
				btnNewButton.setBackground(new Color(192, 192, 192));
				btnNewButton.setBounds(218, 255, 64, 64);
				panel.add(btnNewButton);
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							String username = textField.getText();
							if (username.isEmpty() || username.length() < 5 || username.length() > 15) {
								JOptionPane.showMessageDialog(null, "Vui lòng nhập lại tên của bạn");
							} else {
								int avatar = comboBox.getSelectedIndex();
								if (avatar == -1) {
									throw new Exception("Vui lòng chọn avatar");
								}
								write("set-player," + username + "," + avatar);
								dispose();
							}
						} catch (Exception e2) {
							// TODO: handle exception
						}
					}
				});
				btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		setVisible(true);
	}

	public void write(String message) throws IOException {
		os.writeUTF(message);
		os.flush();
	}
}
