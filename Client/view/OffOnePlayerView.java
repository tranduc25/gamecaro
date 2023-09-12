package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class OffOnePlayerView extends JFrame {

	private JPanel contentPane;
	private Image img_easy, img_medium, img_hard, img_back, img_ninja;

	public OffOnePlayerView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Offline.class.getResource("/icon/tic-tac-toe.png")));
		try {
			img_easy = ImageIO.read(getClass().getResource("EASY.png"));
			img_medium = ImageIO.read(getClass().getResource("MEDIUM.png"));
			img_hard = ImageIO.read(getClass().getResource("HARD.png")); 
			img_back = ImageIO.read(getClass().getResource("back_42x42.png")); 
			img_ninja = ImageIO.read(getClass().getResource("IconsXO_200x200.png")); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, 672, 551);
		setLocationRelativeTo(null);
		this.setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(83, 168, 168));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblGame = new JLabel("Game");
		lblGame.setHorizontalAlignment(SwingConstants.CENTER);
		lblGame.setForeground(new Color(191, 238, 255));
		lblGame.setFont(new Font("Segoe Print", Font.BOLD, 21));
		lblGame.setBounds(41, 116, 103, 34);
		contentPane.add(lblGame);
		
		JLabel lblOffline = new JLabel("Offline");
		lblOffline.setHorizontalAlignment(SwingConstants.CENTER);
		lblOffline.setForeground(new Color(191, 238, 255));
		lblOffline.setFont(new Font("Segoe Print", Font.BOLD, 35));
		lblOffline.setBackground(new Color(191, 238, 255));
		lblOffline.setBounds(134, 150, 151, 54);
		contentPane.add(lblOffline);
		
		JLabel lblMotnguoichoi = new JLabel("Một người chơi");
		lblMotnguoichoi.setHorizontalAlignment(SwingConstants.CENTER);
		lblMotnguoichoi.setForeground(new Color(242, 231, 252));
		lblMotnguoichoi.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 26));
		lblMotnguoichoi.setBackground(new Color(242, 252, 255));
		lblMotnguoichoi.setBounds(209, 428, 269, 54);
		contentPane.add(lblMotnguoichoi);
		
		JLabel lblIconsXO_1 = new JLabel("");
		lblIconsXO_1.setIcon(new ImageIcon(img_ninja));
		lblIconsXO_1.setBounds(27, 214, 227, 177);
		contentPane.add(lblIconsXO_1);
		
		JButton btnQuaylai = new JButton("");
		btnQuaylai.setHorizontalAlignment(SwingConstants.LEFT);
		btnQuaylai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSoundButton();
				dispose();
				new Offline();
			}
		});
		btnQuaylai.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 18));
		btnQuaylai.setBackground(new Color(71, 141, 141));
		btnQuaylai.setBounds(61, 438, 68, 44);
		btnQuaylai.setIcon(new ImageIcon(img_back));
		contentPane.add(btnQuaylai);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSoundButton();
				dispose();
				new OnePlayerView(1);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		btnNewButton.setBounds(346, 150, 180, 50);
		btnNewButton.setIcon(new ImageIcon(img_easy));
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSoundButton();
				dispose();
				new OnePlayerView(2);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 35));
		btnNewButton_1.setBounds(264, 252, 180, 50);
		btnNewButton_1.setIcon(new ImageIcon(img_medium));
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSoundButton();
				dispose();
				new OnePlayerView(3);
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 35));
		btnNewButton_2.setBounds(414, 351, 180, 50);
		btnNewButton_2.setIcon(new ImageIcon(img_hard));
		contentPane.add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("Chọn mức độ");
		lblNewLabel.setForeground(new Color(54, 34, 151));
		lblNewLabel.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 44));
		lblNewLabel.setBounds(161, 26, 316, 61);
		contentPane.add(lblNewLabel);
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
}
