package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Client;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Offline extends JFrame {

	private JPanel contentPane;
    private Image img_offline, img_online, img_XO;

	public Offline() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Offline.class.getResource("/icon/tic-tac-toe.png")));
		init();
		this.setVisible(true);
	}
		
	public void init() {	
		try {
			img_offline = ImageIO.read(getClass().getResource("OFFLINE.png"));
			img_online = ImageIO.read(getClass().getResource("ONLINE.png"));
			img_XO = ImageIO.read(getClass().getResource("iconsXO3_300x300.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, 639, 462);
		setLocationRelativeTo(null);
		this.setResizable(false);

        
		contentPane = new JPanel();
		contentPane.setBackground(new Color(83, 168, 168));
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
		lblChonCheDoChoi.setBounds(140, 100, 311, 58);
		contentPane.add(lblChonCheDoChoi);
		
		JButton btnOffline = new JButton("");
		
		btnOffline.setForeground(new Color(83, 168, 168));
		btnOffline.setBackground(new Color(83, 168, 168));
		btnOffline.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
		btnOffline.setBounds(376, 214, 185, 54);
		btnOffline.setForeground(new Color(54, 34, 151));
		btnOffline.setIcon(new ImageIcon(img_offline));
		contentPane.add(btnOffline);
		
		btnOffline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSoundButton();
				dispose();
				new OffOnePlayerView();
			}
		});
		
		JButton btnOnline = new JButton("");
		
		btnOnline.setForeground(new Color(83, 168, 168));
		btnOnline.setBackground(new Color(83, 168, 168));
		btnOnline.setBackground(new Color(50, 205, 170));
		btnOnline.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 25));
		btnOnline.setBounds(376, 308, 185, 54);
		btnOnline.setForeground(new Color(54, 34, 151));
		btnOnline.setIcon(new ImageIcon(img_online));
		btnOnline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSoundButton();
				dispose();
				new Client();
			}
		});
		contentPane.add(btnOnline);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_1.setBackground(new Color(71, 141, 141));
		panel_1.setBounds(0, 0, 305, 425);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblIconsXO_2 = new JLabel("");
		lblIconsXO_2.setBounds(40, 163, 233, 220);
		panel_1.add(lblIconsXO_2);
		lblIconsXO_2.setForeground(new Color(189, 128, 213));
		lblIconsXO_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblIconsXO_2.setIcon(new ImageIcon(img_XO));
		
//		JLabel lblNewLabel_1 = new JLabel("Game Offline");
//		lblNewLabel_1.setBounds(373, 180, 179, 46);
//		contentPane.add(lblNewLabel_1);
//		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
//		lblNewLabel_1.setBackground(new Color(240, 240, 240));
//		lblNewLabel_1.setForeground(new Color(191, 238, 255));
//		lblNewLabel_1.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 24));
//		
//		JLabel lblNewLabel_1_1 = new JLabel("Game Online");
//		lblNewLabel_1_1.setBounds(411, 369, 178, 46);
//		contentPane.add(lblNewLabel_1_1);
//		lblNewLabel_1_1.setBackground(new Color(240, 240, 240));
//		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
//		lblNewLabel_1_1.setForeground(new Color(6, 34, 70));
//		lblNewLabel_1_1.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 25));
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
