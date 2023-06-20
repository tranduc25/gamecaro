package view;

import controller.Client;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Player;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class WaitingBO3 extends JFrame {

	private JPanel contentPane;
	private Socket client;
	private DataOutputStream os;
	private Player player;
	private JButton btnNewButton;
	private JLabel lblNewLabel;
	private JButton btnNewButton_1;
	private boolean first;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_4;
	private JButton btnNewButton_2;

	public WaitingBO3(Socket client, Player player) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(InRoom.class.getResource("/icon/tic-tac-toe.png")));
		this.client = client;
		this.player = player;
		try {
			this.os = new DataOutputStream(client.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 426, 212);
		panel.setBackground(new Color(255, 255, 255));
		contentPane.add(panel);
		panel.setLayout(null);

		lblNewLabel = new JLabel("ID ROOM: ");
		lblNewLabel.setFont(new Font("Viner Hand ITC", Font.ITALIC, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(150, 10, 132, 41);
		panel.add(lblNewLabel);
		
		lblNewLabel_2 = new JLabel("<name>\r\n");
		lblNewLabel_2.setForeground(new Color(0, 0, 0));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel_2.setBounds(0, 126, 142, 21);
		lblNewLabel_2.setText(player.getName());
		panel.add(lblNewLabel_2);
		
		lblNewLabel_4 = new JLabel("<name>\r\n");
		lblNewLabel_4.setForeground(new Color(0, 0, 0));
		lblNewLabel_4.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel_4.setBounds(288, 126, 138, 21);
		lblNewLabel_4.setText(null);
		panel.add(lblNewLabel_4);
		
		  btnNewButton_2 = new JButton("");
		  btnNewButton_2.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent e) {
		  	}
		  });
		  btnNewButton_2.setBackground(new Color(255, 255, 255));
		  btnNewButton_2.setIcon(new ImageIcon(InRoom.class.getResource("/icon/user.png")));
		btnNewButton_2.setBounds(312, 26, 80, 90);
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_2_1 = new JButton("New button");
		btnNewButton_2_1.setBounds(36, 26, 80, 90);
		btnNewButton_2_1.setIcon(new ImageIcon("image/"+player.getAvatar()+".jpg"));
		panel.add(btnNewButton_2_1);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(InRoom.class.getResource("/icon/vs.png")));
		lblNewLabel_1.setBounds(180, 61, 64, 64);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setIcon(new ImageIcon(InRoom.class.getResource("/icon/cross.png")));
		lblNewLabel_3.setBounds(52, 170, 32, 32);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setIcon(new ImageIcon(InRoom.class.getResource("/icon/o.png")));
		lblNewLabel_5.setBounds(343, 170, 32, 32);
		panel.add(lblNewLabel_5);
		

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 217, 426, 41);
		panel_1.setBackground(new Color(192, 192, 192));
		contentPane.add(panel_1);

		btnNewButton = new JButton("Start");
		btnNewButton.setBackground(new Color(192, 192, 192));
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBounds(176, 7, 74, 26);
		btnNewButton.setEnabled(false);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					write("startbo3");
//					 playSound();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_1.setLayout(null);
		btnNewButton.setFont(new Font("Viner Hand ITC", Font.ITALIC, 13));
		panel_1.add(btnNewButton);
		
		btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(InRoom.class.getResource("/icon/logout.png")));
		btnNewButton_1.setBackground(new Color(192, 192, 192));
		btnNewButton_1.setBounds(384, 4, 32, 32);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					write("exitbo3");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setFont(new Font("Viner Hand ITC", Font.ITALIC, 11));
		panel_1.add(btnNewButton_1);
		setVisible(true);
	}

	public void write(String message) throws IOException {
		os.writeUTF(message);
		os.flush();
	}

	public void setplayer1(String user,String avt) {
//		 playSound();
		btnNewButton_2.setIcon(new ImageIcon("image/"+avt+".jpg"));
		lblNewLabel_4.setText(user);
		btnNewButton.setEnabled(true);
		

	}
	public void SetIDRoom(String id) {
		lblNewLabel.setText("ID ROOM: "+id);
	}
	public void setplayer2(String user,String avt) {
//		 playSound();
		lblNewLabel_4.setText(user);
		btnNewButton_2.setIcon(new ImageIcon("image/"+avt+".jpg"));
		btnNewButton.setVisible(false);
	}
	public void exitroom() {
//		 playSound();
		btnNewButton_2.setIcon(new ImageIcon(InRoom.class.getResource("/icon/user.png")));
		lblNewLabel_4.setText(null);
		btnNewButton.setVisible(true);
		btnNewButton.setEnabled(false);
	}
//	 public void playSound() {
//	        try {
//	            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("image/click3.wav").getAbsoluteFile());
//	            Clip clip = AudioSystem.getClip();
//	            clip.open(audioInputStream);
//	            clip.start();
//	        } catch (Exception ex) {
//	            System.out.println("Error with playing sound.");
//	            ex.printStackTrace();
//	        }
//	    }
}
