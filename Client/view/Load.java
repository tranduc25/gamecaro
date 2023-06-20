package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Player;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;
import java.awt.Toolkit;

public class Load extends JFrame {

	private JPanel contentPane;
	public JButton btnNewButton;
	private Timer timer;
	private Socket client;
	private DataOutputStream os;

	public Load(Socket client) {
		this.client = client;
		setTitle("Game caro");
		try {
			this.os = new DataOutputStream(client.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setIconImage(Toolkit.getDefaultToolkit().getImage(Load.class.getResource("/icon/tic-tac-toe.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 455, 298);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(-5, 10, 454, 261);
		panel.setBackground(new Color(255, 255, 255));
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Đang tìm phòng, xin chờ !!!");
		lblNewLabel.setBounds(111, 10, 218, 48);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);

		btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(Load.class.getResource("/icon/logout.png")));
		btnNewButton.setBounds(373, 198, 58, 38);
		btnNewButton.setBackground(new Color(128, 128, 128));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.add(btnNewButton);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 152, 411, 19);
		panel.add(progressBar);

		JLabel lblNewLabel_1 = new JLabel("00:20");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(179, 181, 83, 28);
		panel.add(lblNewLabel_1);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(128, 128, 128));
		panel_1.setBounds(0, 0, 454, 94);
		panel.add(panel_1);
		timer = new Timer(1000, new ActionListener() {
			int count = 20;
			int pc = 0;

			@Override
			public void actionPerformed(ActionEvent e) {

				count--;
				if (count == 0) {
					try {
						write("khong co phong");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dispose();
				}

				pc = pc + 5;
				if (count >= 0) {
					if (count >= 10)
						lblNewLabel_1.setText("00:" + count);
					else
						lblNewLabel_1.setText("00:0" + count);
					progressBar.setValue(pc);

				} else {
					((Timer) (e.getSource())).stop();

				}

			}
		});
		timer.start();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void close() {
		timer.stop();
	}

	public void write(String message) throws IOException {
		os.writeUTF(message);
		os.flush();
	}

}
