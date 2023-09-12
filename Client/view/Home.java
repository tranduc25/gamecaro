package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.Player;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BoxLayout;
import javax.swing.SpringLayout;
//import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import javax.swing.JScrollBar;
import java.awt.Toolkit;

public class Home extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Socket client;
	private DataOutputStream os;
	private Player player;
	private Load ld;
	private JTextField textField;
	private JTextArea textArea;

	public Home(Socket client, Player player) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/icon/tic-tac-toe.png")));
		this.client = client;
		this.player = player;

		try {
			this.os = new DataOutputStream(client.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(777, 557);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(71, 141, 141));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setResizable(false);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(83, 168, 168));
		panel_1.setBounds(242, 11, 510, 193);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(88, 66, 95, 83);
		lblNewLabel_2.setBackground(new Color(64, 128, 128));
		lblNewLabel_2.setIcon(new ImageIcon("image/" + player.getAvatar() + ".jpg"));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_2);

		JLabel lblNewLabel = new JLabel("Tên người chơi: ");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(217, 52, 166, 17);
		lblNewLabel.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 17));
		panel_1.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel(" ID     : ");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(263, 95, 120, 19);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 17));
		panel_1.add(lblNewLabel_1);

		JLabel lblNewLabel_3 = new JLabel("<Name>");
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setBounds(380, 53, 120, 17);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 17));
		panel_1.add(lblNewLabel_3);
		lblNewLabel_3.setText(player.getName());

		JLabel lblNewLabel_1_1 = new JLabel("<id>");
		lblNewLabel_1_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1_1.setBounds(424, 95, 36, 19);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 17));
		panel_1.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setText(player.getID() + "");

		JLabel lblNewLabel_4 = new JLabel("Server :");
		lblNewLabel_4.setForeground(new Color(255, 255, 255));
		lblNewLabel_4.setBounds(275, 140, 95, 23);
		lblNewLabel_4.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 17));
		panel_1.add(lblNewLabel_4);

		JLabel lblNewLabel_4_1 = new JLabel("Asia");
		lblNewLabel_4_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_4_1.setBounds(412, 140, 59, 23);
		lblNewLabel_4_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4_1.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 17));
		panel_1.add(lblNewLabel_4_1);

		JLabel lblNewLabel_5 = new JLabel("Thông tin người chơi");
		lblNewLabel_5.setForeground(new Color(255, 255, 255));
		lblNewLabel_5.setBounds(147, 11, 215, 23);
		lblNewLabel_5.setBackground(new Color(128, 128, 128));
		lblNewLabel_5.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 17));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_5);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(83, 168, 168));
		panel_2.setBounds(242, 336, 510, 173);
		contentPane.add(panel_2);

		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setBackground(new Color(125, 191, 191));
		btnNewButton_2.setBounds(176, 105, 168, 58);
		btnNewButton_2.setIcon(new ImageIcon(Home.class.getResource("/view/button_ChoingayOnline_170x60.png")));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound();
				try {
					ld = new Load(client);

					ld.setVisible(true);
					ld.setLocationRelativeTo(contentPane);
					
					ld.btnNewButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								write("khong-co-phong");
								ld.dispose();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					write("go-room-now");

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_2.setLayout(null);
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_2.add(btnNewButton_2);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setBackground(new Color(83, 168, 168));
		btnNewButton_1.setBounds(320, 29, 168, 49);
		btnNewButton_1.setIcon(new ImageIcon(Home.class.getResource("/view/button_Taophong_167x60.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound();
				try {
					write("create-room");

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_2.add(btnNewButton_1);

		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(new Color(83, 168, 168));
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBounds(37, 29, 168, 49);
		btnNewButton.setIcon(new ImageIcon(Home.class.getResource("/view/button_vaophong_167x60.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound();
				String idroom = JOptionPane.showInputDialog("Nhập id phòng");
				if(idroom != null)try {
					System.out.println("join-room," + idroom);
					write("join-room," + idroom);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_2.add(btnNewButton);
		
		
		
		

		
		
		textArea = new JTextArea();
		textArea.setText("              --------< Thông báo toàn Server>---------");
		textArea.setEditable(false);
		textArea.setBounds(242, 216, 510, 65);
		contentPane.add(textArea);

		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.setBackground(new Color(0, 128, 128));
		btnNewButton_4.setIcon(new ImageIcon(Home.class.getResource("/icon/send.png")));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound();
				String sms = textField.getText();
				if(!sms.equals("")) {
					textField.setText("");
					try {
						write("chat-server,"+sms);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton_4.setBounds(692, 291, 60, 34);
		contentPane.add(btnNewButton_4);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setBackground(new Color(71, 141, 141));
		btnNewButton_3.setBounds(36, 427, 159, 54);
		contentPane.add(btnNewButton_3);
		btnNewButton_3.setIcon(new ImageIcon(Home.class.getResource("/view/button_thoatonline_137x53.png")));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound();
				System.exit(0);
			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 30));
		
//		JLabel lblIconsXO_1 = new JLabel("");
//		lblIconsXO_1.setHorizontalAlignment(SwingConstants.CENTER);
//		lblIconsXO_1.setBounds(20, 11, 199, 177);
//		lblIconsXO_1.setIcon(new ImageIcon("src/view/IconsXO_200x200.png"));
//		contentPane.add(lblIconsXO_1);
		
		JLabel lblGame = new JLabel("Game");
		lblGame.setHorizontalAlignment(SwingConstants.CENTER);
		lblGame.setForeground(new Color(255, 244, 245));
		lblGame.setFont(new Font("Segoe Print", Font.BOLD, 28));
		lblGame.setBounds(10, 229, 101, 45);
		contentPane.add(lblGame);
		
		JLabel lblOnline = new JLabel("Online");
		lblOnline.setHorizontalAlignment(SwingConstants.CENTER);
		lblOnline.setForeground(new Color(255, 244, 245));
		lblOnline.setFont(new Font("Segoe Script", Font.BOLD, 43));
		lblOnline.setBackground(new Color(191, 238, 255));
		lblOnline.setBounds(36, 271, 183, 54);
		contentPane.add(lblOnline);
		
		textField = new JTextField("");
		textField.setBounds(242, 291, 440, 35);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				playSound();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(242, 216, 510, 65);
		contentPane.add(scrollPane);
		
		setVisible(true);
	}

	public void write(String message) throws IOException {
		os.writeUTF(message);
		os.flush();
	}

	public void closeld() {
		if (ld != null) {
			ld.close();
			ld.dispose();
		}
	}
	public void SetChat(String sms) {
		textArea.setText(textArea.getText()+"\n" + sms);
	}
	public void playSound() {
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File("image/click3.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}
}