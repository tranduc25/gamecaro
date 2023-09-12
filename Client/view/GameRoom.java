package view;

import java.awt.Color;
import java.util.TimerTask;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioPermission;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.ButtonCell;
import model.Player;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Toolkit;

public class GameRoom extends JFrame {
	private static final int M = 10;
	private ButtonCell[][] BT = new ButtonCell[M][M];
	private JPanel contentPane;
	private Socket client;
	private DataOutputStream os;
	private Player player;
	private boolean click;
	private JTextField sms;
	private JButton doithu;
	private JButton me;
	private JLabel luotdoithu;
	private JLabel denluotban;
	private JTextArea allsms;
	private JLabel loading2;
	private JLabel loading1;
	private JLabel timebd;
	private JTextField textField;
	private Timer timer;
	private int sec;
	private Image imgO;
	private Image imgX;
	private JLabel lblNewLabel_1;

	public GameRoom(Socket client, Player player) throws IOException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameRoom.class.getResource("/icon/vs.png")));
		imgO = ImageIO.read(getClass().getResource("/icon/o2.jpg"));
		imgX = ImageIO.read(getClass().getResource("/icon/x2.jpg"));
		timer = new Timer();

		this.client = client;
		this.player = player;
		System.out.println(player.getValue());
		loading2 = new JLabel("");
		loading2.setHorizontalAlignment(SwingConstants.CENTER);
		loading2.setBounds(815, 175, 70, 70);

		loading1 = new JLabel("");
		loading1.setBackground(new Color(255, 255, 255));
		loading1.setHorizontalAlignment(SwingConstants.CENTER);
		loading1.setBounds(98, 175, 70, 70);

		if (player.getValue().equals("X")) {
			click = true;
//			loading1.setIcon(new ImageIcon(GameRoom.class.getResource("/view/Ellipsis-1s-70px.gif")));
		} else if (player.getValue().equals("O")) {
			click = false;
//			loading2.setIcon(new ImageIcon(GameRoom.class.getResource("/view/Ellipsis-1s-70px.gif")));

		}
		os = new DataOutputStream(client.getOutputStream());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 512);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(83, 168, 168));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(279, 10, 430, 430);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < M; j++) {
				int x = i, y = j;
				ButtonCell bt = new ButtonCell();
				bt.setIcon(new ImageIcon("/icon/oco.jpg"));
				bt.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						if (bt.cell.getValue().equals("No")) {
							bt.setIcon(new ImageIcon("/icon/oco.jpg"));
						}

					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						if (bt.cell.getValue().equals("No") && click == false) {
							bt.setIcon(new ImageIcon("image/border2.jpg"));
						}
						if (bt.cell.getValue().equals("No") && click == true) {
							if (player.getValue().equals("X")) {
								bt.setIcon(new ImageIcon(GameRoom.class.getResource("/icon/x2_pre.jpg")));
							}
							if (player.getValue().equals("O")) {
								bt.setIcon(new ImageIcon(GameRoom.class.getResource("/icon/o2_pre.jpg")));
							}
						}
					}

					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub

					}
				});
				bt.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (bt.cell.isVisited() == false && click == true) {
							playSound();
							bt.cell.setVisited(true);
							bt.cell.setValue(player.getValue());
							if (bt.cell.getValue().equals("X")) {
								bt.setIcon(new ImageIcon(imgX));
							} else if (bt.cell.getValue().equals("O")) {
								bt.setIcon(new ImageIcon(imgO));
							}
							setClick(false);
//							loading1.setIcon(null);
//							loading2.setIcon(new ImageIcon(GameRoom.class.getResource("/view/Ellipsis-1s-70px.gif")));

							setluotdanh();
							try {
								write("attack," + x + "," + y);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							checkWin5(x, y);
						}
						// TODO Auto-generated method stub

					}
				});
				BT[i][j] = bt;
				panel.add(bt);

			}
		}
		contentPane.add(panel);
		panel.setLayout(new GridLayout(M, M, 1, 1));

		me = new JButton("New button");
		me.setBackground(new Color(0, 128, 128));
		me.setIcon(new ImageIcon("image/" + player.getAvatar() + ".jpg"));
		me.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		me.setBounds(83, 10, 80, 90);
		contentPane.add(me);

		doithu = new JButton("New button");
		doithu.setBackground(new Color(0, 128, 128));
		doithu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		doithu.setBounds(801, 10, 80, 90);
		contentPane.add(doithu);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(746, 260, 213, 180);
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);

		allsms = new JTextArea("");
		allsms.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 13));
		allsms.setEditable(false);
		scrollPane.setViewportView(allsms);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 128, 128));
		panel_2.setBounds(10, 391, 245, 49);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		sms = new JTextField();
		sms.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
//				playSound1();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		sms.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 10));
		sms.setBounds(59, 0, 186, 49);
		panel_2.add(sms);
		sms.setColumns(10);

		JButton sendsms = new JButton("");
		sendsms.setBackground(new Color(0, 128, 128));
		sendsms.setBounds(0, 0, 49, 49);
		sendsms.setIcon(new ImageIcon(GameRoom.class.getResource("/icon/gui.png")));
		sendsms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound1();
				String mess = sms.getText();
				System.out.println(mess);
				if (!mess.trim().equals("")) {
					try {
						write("sms," + mess);
						allsms.setText(allsms.getText() + "\nTôi: " + mess);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				sms.setText("");
			}
		});
		panel_2.add(sendsms);

		denluotban = new JLabel("New label");
		denluotban.setForeground(new Color(255, 255, 255));
		denluotban.setHorizontalAlignment(SwingConstants.CENTER);
		denluotban.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 17));
		denluotban.setBounds(38, 135, 204, 27);
		contentPane.add(denluotban);

		luotdoithu = new JLabel("New label");
		luotdoithu.setForeground(new Color(255, 255, 255));
		luotdoithu.setHorizontalAlignment(SwingConstants.CENTER);
		luotdoithu.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 17));
		luotdoithu.setBounds(765, 135, 194, 27);
		contentPane.add(luotdoithu);

		contentPane.add(loading2);

		contentPane.add(loading1);

		timebd = new JLabel("Thời gian thi đấu");
		timebd.setForeground(new Color(255, 255, 255));
		timebd.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 16));
		timebd.setHorizontalAlignment(SwingConstants.CENTER);
		timebd.setBounds(328, 446, 198, 19);
		contentPane.add(timebd);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setText("00:00");
		textField.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 17));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(536, 446, 96, 19);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Đầu hàng");
		btnNewButton.setBackground(new Color(83, 168, 168));
		btnNewButton.setIcon(new ImageIcon(Home.class.getResource("/view/button_Dauhang_109x42.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					playSound1();
					write("dau-hang");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(79, 271, 112, 43);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Xin hòa");
		btnNewButton_1.setBackground(new Color(83, 168, 168));
		btnNewButton_1.setIcon(new ImageIcon(Home.class.getResource("/view/button_Xinhoa_109x42.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					playSound1();
					write("xin-hoa");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_1.setBounds(79, 330, 112, 42);
		contentPane.add(btnNewButton_1);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel.setBounds(28, 110, 193, 19);
		lblNewLabel.setText(player.getName());
		contentPane.add(lblNewLabel);

		lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel_1.setBounds(801, 110, 95, 19);
		contentPane.add(lblNewLabel_1);
		setluotdanh();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				sec++;
				// TODO Auto-generated method stub
				textField.setText(((sec / 60) / 10) + "" + (sec / 60) % 10 + ":" + ((sec % 60) / 10) + (sec % 60 % 10));
			}
		}, 1000, 1000);
		setVisible(true);
	}

	public void write(String message) throws IOException {
		os.writeUTF(message);
		os.flush();
	}

	public void setAttack(int x, int y) {
		if (BT[x][y].cell.isVisited() == false) {
			playSound();
			BT[x][y].cell.setVisited(true);
			if (player.getValue().equals("O")) {
				BT[x][y].cell.setValue("X");
				BT[x][y].setIcon(new ImageIcon(imgX));
			} else if (player.getValue().equals("X")) {
				BT[x][y].cell.setValue("O");
				BT[x][y].setIcon(new ImageIcon(imgO));
			}
		}
		setClick(true);
		loading1.setIcon(new ImageIcon(GameRoom.class.getResource("/view/Ellipsis-1s-70px.gif")));
		loading2.setIcon(null);

		setluotdanh();
	}

	public boolean isClick() {
		return click;
	}

	public void setClick(boolean click) {
		this.click = click;
	}

	public void checkWin5(int i, int j) {
		// Hang ngang
		int count = 0;
		for (int col = 0; col < M; col++) {
			ButtonCell cell = BT[i][col];
			if (cell.cell.getValue().equals(player.getValue())) {
				count++;
				if (count == 5) {
					for (int k = col; k > col - 5; k--) {
						if (player.getValue().equals("X"))
							BT[i][k].setIcon(new ImageIcon(GameRoom.class.getResource("/icon/xwin.jpg")));
						if (player.getValue().equals("O"))
							BT[i][k].setIcon(new ImageIcon(GameRoom.class.getResource("/icon/owin.jpg")));
					}

					win(1, i, col);

				}
			} else {
				count = 0;
			}
		}

		// Chiều dọc
		count = 0;
		for (int row = 0; row < M; row++) {
			ButtonCell cell = BT[row][j];
			if (cell.cell.getValue().equals(player.getValue())) {
				count++;
				if (count == 5) {
					for (int k = row; k > row - 5; k--) {
						if (player.getValue().equals("X"))
							BT[k][j].setIcon(new ImageIcon(GameRoom.class.getResource("/icon/xwin.jpg")));
						if (player.getValue().equals("O"))
							BT[k][j].setIcon(new ImageIcon(GameRoom.class.getResource("/icon/owin.jpg")));
					}
					win(2, row, j);
				}
			} else {
				count = 0;
			}
		}

		// Chéo trái
		int min = Math.min(i, j);
		int TopI = i - min;
		int TopJ = j - min;
		count = 0;

		for (; TopI < M && TopJ < M; TopI++, TopJ++) {
			ButtonCell cell = BT[TopI][TopJ];
			if (cell.cell.getValue().equals(player.getValue())) {
				count++;
				if (count == 5) {
					int x = TopI, y = TopJ;
					for (; x > TopI - 5 && TopJ - 5 < y; x--, y--) {
						if (player.getValue().equals("X"))
							BT[x][y].setIcon(new ImageIcon(GameRoom.class.getResource("/icon/xwin.jpg")));
						if (player.getValue().equals("O"))
							BT[x][y].setIcon(new ImageIcon(GameRoom.class.getResource("/icon/owin.jpg")));
					}

					win(3, TopI, TopJ);
				}
			} else {
				count = 0;
			}
		}
		// cheophai
		min = i;
		TopI = i - min;
		TopJ = j + min;
		count = 0;

		if (TopJ >= M) {
			int du = TopJ - (M - 1);
			TopI = TopI + du;
			TopJ = M - 1;
		}

		for (; TopI < M && TopJ >= 0; TopI++, TopJ--) {
			ButtonCell cell = BT[TopI][TopJ];
			if (cell.cell.getValue().equals(player.getValue())) {
				count++;
				if (count == 5) {
					int x = TopI, y = TopJ;
					for (; x > TopI - 5 && TopJ + 5 > y; x--, y++) {
						if (player.getValue().equals("X"))
							BT[x][y].setIcon(new ImageIcon(GameRoom.class.getResource("/icon/xwin.jpg")));
						if (player.getValue().equals("O"))
							BT[x][y].setIcon(new ImageIcon(GameRoom.class.getResource("/icon/owin.jpg")));
					}
					win(4, TopI, TopJ);
				}
			} else {
				count = 0;
			}
		}
	}

	public void win(int h, int x, int y) {
		try {
			playSoundwin();
			timer.cancel();
			write("win," + h + "," + x + "," + y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void SetWin(int k, int i, int j) {
		if (k == 1) {
			for (int h = j; h > j - 5; h--) {
				if (player.getValue().equals("O"))
					BT[i][h].setIcon(new ImageIcon(GameRoom.class.getResource("/icon/xwin.jpg")));
				if (player.getValue().equals("X"))
					BT[i][h].setIcon(new ImageIcon(GameRoom.class.getResource("/icon/owin.jpg")));
			}
		}
		if (k == 2) {
			for (int h = i; h > i - 5; h--) {
				if (player.getValue().equals("O"))
					BT[h][j].setIcon(new ImageIcon(GameRoom.class.getResource("/icon/xwin.jpg")));
				if (player.getValue().equals("X"))
					BT[h][j].setIcon(new ImageIcon(GameRoom.class.getResource("/icon/owin.jpg")));
			}
		}
		if (k == 3) {
			int x = i, y = j;
			for (; x > i - 5 && j - 5 < y; x--, y--) {
				if (player.getValue().equals("O"))
					BT[x][y].setIcon(new ImageIcon(GameRoom.class.getResource("/icon/xwin.jpg")));
				if (player.getValue().equals("X"))
					BT[x][y].setIcon(new ImageIcon(GameRoom.class.getResource("/icon/owin.jpg")));
			}
		}
		if (k == 4) {
			int x = i, y = j;
			for (; x > i - 5 && j + 5 > y; x--, y++) {
				if (player.getValue().equals("O"))
					BT[x][y].setIcon(new ImageIcon(GameRoom.class.getResource("/icon/xwin.jpg")));
				if (player.getValue().equals("X"))
					BT[x][y].setIcon(new ImageIcon(GameRoom.class.getResource("/icon/owin.jpg")));
			}
		}
	}

	public void lose() {
		timer.cancel();
		playSoundwin();
		JOptionPane.showMessageDialog(null, "Bạn đã thua ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	}

	public void setplayer(String name, String avt) {
		doithu.setIcon(new ImageIcon("image/" + avt + ".jpg"));
		lblNewLabel_1.setText(name);
	}

	public void setluotdanh() {
		if (click == true) {
			this.denluotban.setText("Đến lượt bạn");
			this.luotdoithu.setText("Đối thủ đang chờ");
		}
		if (click == false) {
			this.denluotban.setText("Đến lượt đối thủ");
			this.luotdoithu.setText("Đối thủ đang đánh");

		}
	}

	public void setsms(String string) {
		// TODO Auto-generated method stub
		allsms.setText(allsms.getText() + "\nĐối thủ: " + string);
		playSound();
	}

	public void end() {
		playSoundwin();
		JOptionPane.showMessageDialog(null, "Đối thủ đã thoát trận, bạn đã thắng", "Thông báo",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void playSound() {
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File("image/click.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}
	public void playSound1() {
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

	public void playSoundwin() {
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File("image/win.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}

}
