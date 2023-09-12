package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.AlphaBeta;
import model.ButtonCell;

public class OnePlayerView extends JFrame {

	private JPanel contentPane;
	private static final int N = 16;
	private ButtonCell[][] buttonCellModel = new ButtonCell[N][N];
	private int[][] markPlayer = new int[N][N];

    private Timer timer = new Timer();
    private int ST = 0;
    private int sec = 0;

    
    AlphaBeta alphaBeta = new AlphaBeta();
    public static final int winScore = 100000000;
	public static final int Machine_WIN = 1;
	public static final int Player_WIN = -1;
	public static final int ST_DRAW = 0;;
	public static final int ST_NORMAL = 2;
	public int count = 0;
	private JButton btnStart;
	private JLabel lblTime;

	
	Stack<Integer> stk = new Stack<>();

	public OnePlayerView(int depth) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(OnePlayerView.class.getResource("/icon/tic-tac-toe.png")));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(940, 685);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(83, 168, 168));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel tictactoe = new JPanel();
		tictactoe.setBackground(new Color(83, 168, 168));

		tictactoe.setLayout(new GridLayout(N, N));
		tictactoe.setBounds(248, 15, 675, 616);
		contentPane.add(tictactoe);
		
		JLabel lblgameCaro = new JLabel("Game Caro");
		lblgameCaro.setForeground(Color.WHITE);
		lblgameCaro.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 20));
		lblgameCaro.setBounds(31, 15, 152, 44);
		contentPane.add(lblgameCaro);
		
		JLabel lblChoivsMay = new JLabel("Chơi với máy");
		lblChoivsMay.setForeground(new Color(255, 255, 255));
		lblChoivsMay.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 19));
		lblChoivsMay.setBounds(62, 132, 137, 28);
		contentPane.add(lblChoivsMay);
		
		JLabel lblOffline = new JLabel("Offline");
		lblOffline.setForeground(Color.WHITE);
		lblOffline.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 18));
		lblOffline.setBounds(128, 69, 85, 28);
		contentPane.add(lblOffline);
		
		JLabel lblThongtinTrandau = new JLabel("Thông tin trận đấu");
		lblThongtinTrandau.setForeground(new Color(255, 255, 181));
		lblThongtinTrandau.setFont(new Font("Courier New", Font.BOLD, 21));
		lblThongtinTrandau.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongtinTrandau.setBounds(0, 184, 245, 43);
		contentPane.add(lblThongtinTrandau);

		
		if(depth==1) {
			JLabel lblNewLabel_7 = new JLabel("Mức độ: Dễ");
			lblNewLabel_7.setForeground(new Color(255, 255, 255));
			lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_7.setFont(new Font("Courier New", Font.BOLD, 16));
			lblNewLabel_7.setBounds(40, 207, 155, 37);
			contentPane.add(lblNewLabel_7);

		} else if(depth == 2) {
			JLabel lblNewLabel_7 = new JLabel("Mức độ: Trung bình");
			lblNewLabel_7.setForeground(new Color(255, 255, 255));
			lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_7.setFont(new Font("Courier New", Font.BOLD, 16));
			lblNewLabel_7.setBounds(30, 207, 185, 37);
			contentPane.add(lblNewLabel_7);
		} else {
			JLabel lblNewLabel_7 = new JLabel("Mức độ: Khó");
			lblNewLabel_7.setForeground(new Color(255, 255, 255));
			lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_7.setFont(new Font("Courier New", Font.BOLD, 16));
			lblNewLabel_7.setBounds(42, 207, 155, 37);
			contentPane.add(lblNewLabel_7);
		}
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				int x = i; int y = j;
				ButtonCell btCell = new ButtonCell();
				buttonCellModel[i][j] = btCell;
				buttonCellModel[x][y].setIcon(new ImageIcon("/icon/oco.jpg"));
				tictactoe.add(buttonCellModel[i][j]);
				buttonCellModel[x][y].addMouseListener(new MouseAdapter() {

					@Override
					public void mouseExited(MouseEvent e) {

					}

					@Override
					public void mouseEntered(MouseEvent e) {

					}

					@Override
					public void mousePressed(MouseEvent e) {
						if (markPlayer[x][y]==0&&ST==1) {
							
							//Hàm đánh cờ 
							changePos("X", buttonCellModel[x][y],x,y);	
							
							if(checkWin(x,y,count,-1)== Player_WIN) { //Hàm kiểm kết thúc trận. nếu dúng thì kết thúc luôn
								choose(Player_WIN, depth);
								return;	
							}
							
							//CalculateNextMove: Hàm tìm nước đánh tốt nhất cho Máy 
							int[] nextMove = AlphaBeta.calculateNextMove(markPlayer, depth);
							
							if(nextMove[0]!=-1 && nextMove[1]!=-1) {
								changePos("O", buttonCellModel[nextMove[0]][nextMove[1]],nextMove[0],nextMove[1]);
							} 
							if(checkWin(nextMove[0],nextMove[1],count,1)== Machine_WIN) { //Hàm kiểm kết thúc trận. nếu dúng thì kết thúc luôn
								choose(Machine_WIN, depth);
								return;	
							}	
							
						}
					}
				});
			}
		
		
		JLabel lblTrandaugiua = new JLabel("Trận đấu giữa:");
		lblTrandaugiua.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 20));
		lblTrandaugiua.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrandaugiua.setForeground(new Color(255, 255, 255));
		lblTrandaugiua.setBounds(31, 371, 199, 28);
		contentPane.add(lblTrandaugiua);
		
		JLabel lblNguoichoi1 = new JLabel("Bạn");
		lblNguoichoi1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNguoichoi1.setForeground(new Color(255, 255, 255));
		lblNguoichoi1.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 20));
		lblNguoichoi1.setBounds(0, 409, 102, 40);
		contentPane.add(lblNguoichoi1);
		
		JLabel lblIconsVs = new JLabel("");
		lblIconsVs.setHorizontalAlignment(SwingConstants.CENTER);
		lblIconsVs.setBounds(86, 409, 67, 54);
		lblIconsVs.setIcon(new ImageIcon(OnePlayerView.class.getResource("/view/vs_85x85.png")));
		contentPane.add(lblIconsVs);
		
		JLabel lblMay = new JLabel("Máy");
		lblMay.setHorizontalAlignment(SwingConstants.CENTER);
		lblMay.setForeground(new Color(255, 255, 255));
		lblMay.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 20));
		lblMay.setBounds(159, 409, 54, 40);
		contentPane.add(lblMay);
		
		JLabel lblTgThidau = new JLabel("Thời gian thi đấu: ");
		lblTgThidau.setHorizontalAlignment(SwingConstants.CENTER);
		lblTgThidau.setForeground(Color.WHITE);
		lblTgThidau.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 16));
		lblTgThidau.setBounds(18, 298, 208, 22);
		contentPane.add(lblTgThidau);
		
		lblTime = new JLabel("00:00");
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setForeground(Color.WHITE);
		lblTime.setFont(new Font("Courier New", Font.ITALIC, 16));
		lblTime.setBounds(86, 327, 61, 22);
		contentPane.add(lblTime);

		JButton btnReset = new JButton("");
		btnReset.setFont(new Font("Space Mono", Font.BOLD, 18));
		btnReset.setBackground(new Color(83, 168, 168));
		btnReset.setBounds(140, 562, 54, 54);
		btnReset.setIcon(new ImageIcon(OnePlayerView.class.getResource("/view/reset_50x50.png")));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSoundButton();
				Reset();
			}
		});
		contentPane.add(btnReset);
		
		JButton btnExit = new JButton("");
		btnExit.setFont(new Font("Space Mono", Font.BOLD, 18));
		btnExit.setBackground(new Color(83, 168, 168));
		btnExit.setBounds(48, 562, 54, 54);
		btnExit.setIcon(new ImageIcon(OnePlayerView.class.getResource("/view/exit_50x50.png")));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSoundButton();
				System.exit(0);
			}
		});
		contentPane.add(btnExit);
	
		btnStart = new JButton("0");
		btnStart.setForeground(new Color(83, 168, 168));
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 5));
		btnStart.setBackground(new Color(83, 168, 168));
		btnStart.setBounds(57, 485, 126, 40);
		btnStart.setIcon(new ImageIcon(OnePlayerView.class.getResource("/view/buttonBatdau_114x38.png")));
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSoundButton();
				String cl = e.getActionCommand();

				if (cl.equals("0")) {
					ST = 1;
					timer.scheduleAtFixedRate(new TimerTask() {
						@Override
						public void run() {
							sec++;
							lblTime.setText(((sec / 60) / 10) + "" + (sec / 60) % 10 + ":" + ((sec % 60) / 10) + (sec % 60 % 10));
						}
					}, 1000, 1000); // trễ ban đầu 1000ms ,vòng lặp lại sau 1000ms
					btnStart.setIcon(new ImageIcon(OnePlayerView.class.getResource("/view/buttonTamdung_114x38.png")));
					btnStart.setText("1");

				} else if (cl.equals("2")) {
					ST = 1;
					btnStart.setIcon(new ImageIcon(OnePlayerView.class.getResource("/view/buttonTamdung_114x38.png")));
					timer.scheduleAtFixedRate(new TimerTask() {
						@Override
						public void run() {
							sec++;
							lblTime.setText(((sec / 60) / 10) + "" + (sec / 60) % 10 + ":" + ((sec % 60) / 10) + (sec % 60 % 10));
						}
					}, 1000, 1000);
					btnStart.setText("1");
					
					for (int i = 0; i < N; i++) {
						for (int j = 0; j < N; j++) {
//							if (markPlayer[i][j]==0) buttonCellModel[i][j].setIcon(new ImageIcon("image/border.jpg"));
							if (markPlayer[i][j]==1) buttonCellModel[i][j].setIcon(new ImageIcon(OnePlayerView.class.getResource("/icon/o.jpg")));
							if (markPlayer[i][j]==-1) buttonCellModel[i][j].setIcon(new ImageIcon(OnePlayerView.class.getResource("/icon/x.jpg")));
						}
					}
					
				} else if (cl.equals("1")) {
					ST = 2;
					timer.cancel();
					btnStart.setIcon(new ImageIcon(OnePlayerView.class.getResource("/view/buttonTieptuc_114x38.png")));
					btnStart.setText("2");
					
				}

			}
		});
		
		contentPane.add(btnStart);
	}
	
	//Hàm đánh cờ (vẽ O hoặc X) vào ô cờ, rồi đánh dấu xem đã được thăm chưa
	public void changePos(String turn, ButtonCell bt,int x, int y) {
		int k=x*10000+y;
		if(turn=="O") {
			bt.setIcon(new ImageIcon(OnePlayerView.class.getResource("/icon/o.jpg")));
			markPlayer[x][y] = 1;
			count++;
			stk.push(k);
			playSound();
		}else if(turn=="X") {
			bt.setIcon(new ImageIcon(OnePlayerView.class.getResource("/icon/x.jpg")));
			markPlayer[x][y] = -1;
			count++;
			stk.push(k);
			playSound();
		}
	}
	
	public int checkWin(int i, int j, int countXo, int xo) {
		// Hang ngang
		int count = 0;
		for (int col = 0; col < N; col++) {
			if (markPlayer[i][col]==xo) {
				count++;
				if (count == 5) {
					for (int k = col; k > col - 5; k--) {
						if (xo==1) buttonCellModel[i][k].setIcon(new ImageIcon(OnePlayerView.class.getResource("/icon/Ow.jpg")));//icon
						if (xo==-1)buttonCellModel[i][k].setIcon(new ImageIcon(OnePlayerView.class.getResource("/icon/Xw.jpg")));
					}
					if(xo==1) return Machine_WIN;
					if(xo==-1)	return Player_WIN;
				}
			} else {
				count = 0;
			}
		}

		// Chiều dọc
		count = 0;
		for (int row = 0; row < N; row++) {
			if (markPlayer[row][j]==xo) {
				count++;
				if (count == 5) {
					for (int k = row; k > row - 5; k--) {
						if (xo==1) buttonCellModel[k][j].setIcon(new ImageIcon(OnePlayerView.class.getResource("/icon/Ow.jpg")));//icon
						if (xo==-1)buttonCellModel[k][j].setIcon(new ImageIcon(OnePlayerView.class.getResource("/icon/Xw.jpg")));
					}
					if(xo==1) return Machine_WIN;
					if(xo==-1)	return Player_WIN;
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

		for (; TopI < N && TopJ < N; TopI++, TopJ++) {
			if (markPlayer[TopI][TopJ]==xo) {
				count++;
				if (count == 5) {
					int x = TopI, y = TopJ;
					for (; x > TopI - 5 && TopJ - 5 < y; x--, y--) {
						if (xo==1) buttonCellModel[x][y].setIcon(new ImageIcon(OnePlayerView.class.getResource("/icon/Ow.jpg")));//icon
						if (xo==-1)buttonCellModel[x][y].setIcon(new ImageIcon(OnePlayerView.class.getResource("/icon/Xw.jpg")));
					}
					if(xo==1) return Machine_WIN;
					if(xo==-1)	return Player_WIN;
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

		if (TopJ >= N) {
			int du = TopJ - (N - 1);
			TopI = TopI + du;
			TopJ = N - 1;
		}

		for (; TopI < N && TopJ >= 0; TopI++, TopJ--) {
			if (markPlayer[TopI][TopJ]==xo) {
				count++;
				if (count == 5) {
					int x = TopI, y = TopJ;
					for (; x > TopI - 5 && TopJ + 5 > y; x--, y++) {
						if (xo==1) buttonCellModel[x][y].setIcon(new ImageIcon(OnePlayerView.class.getResource("/icon/Ow.jpg")));//icon
						if (xo==-1)buttonCellModel[x][y].setIcon(new ImageIcon(OnePlayerView.class.getResource("/icon/Xw.jpg")));
					}
					if(xo==1) return Machine_WIN;
					if(xo==-1)	return Player_WIN;
				}
			} else {
				count = 0;
			}
		}
		if (countXo==N*N) return ST_DRAW;
		return ST_NORMAL;
	}
	//Hàm lựa chọn sau khi kết thúc trò chơi
	public void choose(int winner, int depth) {
		if(winner== ST_DRAW) JOptionPane.showMessageDialog(null, "Draw!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		
		if(winner== Machine_WIN) JOptionPane.showMessageDialog(null, "You lose!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

		if(winner== Player_WIN) JOptionPane.showMessageDialog(null, "You win!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		
		int res = JOptionPane.showConfirmDialog(this, "Bạn muốn chơi lại không?","Đã hết game ",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		
		if(res == JOptionPane.YES_OPTION) {
			ResetEnd();
		}
		if(res == JOptionPane.NO_OPTION) {
			String[] option = {"Trở về Menu chính","Thoát game"};
			int choose = JOptionPane.showOptionDialog(this,"Bạn muốn: ","Menu",0, JOptionPane.QUESTION_MESSAGE, null, option, option);
			if(choose  == 0 ) {
				dispose();
				new Offline();
			}
			if(choose == 1 ) {
				System.exit(0);
			}
		}
	}
	

	public void Reset() {
		int res = JOptionPane.showConfirmDialog(this, "Bạn có muốn chơi lại không?", "Thông báo",
			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		if (res == JOptionPane.YES_OPTION) ResetEnd();
	}
	
	public void ResetEnd() {
		btnStart.setText("0");
		btnStart.setIcon(new ImageIcon(OnePlayerView.class.getResource("/view/buttonBatdau_114x38.png")));
		ST = 0;
		count = 0;
		stk.clear();
			
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				buttonCellModel[i][j].setIcon(new ImageIcon("/icon/oco.jpg"));
				buttonCellModel[i][j].cell.setValue("no");
				buttonCellModel[i][j].cell.setVisited(false);
				markPlayer[i][j] = 0;
			}
	}
	public void playSoundButton() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("image/click3.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
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
