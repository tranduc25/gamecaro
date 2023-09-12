package controller;

import java.awt.Color;
import java.awt.EventQueue;
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.ButtonCell;
import model.Player;
import view.GameRoom;
import view.InRoom;
import view.Offline;
import view.User;
import view.Home;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private GameRoom gameroom;
	private Home viewonline;
	private InRoom inroom;
	private User user;

	private Socket client;
	private DataOutputStream os;
	private DataInputStream is;
	private int id;
	private Thread thread;
	private Player player;
	private String name;
	private String avatar;

 
	public Client() {
		upSocket();
	}

	public void upSocket() {
		try {
			thread = new Thread() {
				@Override
				public void run() {

					try {
						client = new Socket("127.0.0.1", 6666);
						System.out.println("Kết nối thành công!");
						// Tạo luồng đầu ra tại client (Gửi dữ liệu tới server)
						os = new DataOutputStream(client.getOutputStream());
						// Luồng đầu vào tại Client (Nhận dữ liệu từ server).
						is = new DataInputStream(client.getInputStream());
						user = new User(client);
						String message;

						while (true) {
							message = is.readUTF();
							if (message == null) {
								break;
							}
							String[] messageSplit = message.split(",");
							if (messageSplit[0].equals("set-player")) {
								int ID = Integer.parseInt(messageSplit[2]);
								player = new Player(messageSplit[1], "No", ID,messageSplit[3]);
								viewonline = new Home(client, player);
							}
							if (messageSplit[0].equals("khong-ton-tai")) {
								JOptionPane.showMessageDialog(null, "không tồn tại phòng", "Thông báo",
										JOptionPane.INFORMATION_MESSAGE);
							}
							if (messageSplit[0].equals("create-success")) {
								viewonline.setVisible(false);
								inroom = new InRoom(client,player);
								inroom.SetIDRoom(messageSplit[1]);
							}
							if (messageSplit[0].equals("create-now")) {
								inroom = new InRoom(client,player);
								inroom.setVisible(false);
								inroom.SetIDRoom(messageSplit[1]);
							}
							if (messageSplit[0].equals("doi-thu-join-room")) {
								inroom.setplayer1(messageSplit[1],messageSplit[2]);
								System.out.println("Doi thu vao room");
							}
			
							if (messageSplit[0].equals("me-join-room")) {
								System.out.println("toi vao room");

								viewonline.setVisible(false);
								inroom = new InRoom(client,player);
								inroom.SetIDRoom(messageSplit[1]);
								inroom.setplayer2(messageSplit[2],messageSplit[3]);
							}
							if (messageSplit[0].equals("doi-thu-join-room-now")) {
								inroom.setVisible(true);
								viewonline.closeld();
								viewonline.setVisible(false);
								inroom.setplayer1(messageSplit[1],messageSplit[2]);
								System.out.println("Doi thu vao room");
							}
			
							if (messageSplit[0].equals("me-join-room-now")) {
								viewonline.closeld();

								viewonline.setVisible(false);
								inroom = new InRoom(client,player);
								inroom.SetIDRoom(messageSplit[1]);
								inroom.setplayer2(messageSplit[2],messageSplit[3]);
							}
							if (messageSplit[0].equals("doi-thu-da-thoat")) {
								inroom.exitroom();
							}
							 
							if (messageSplit[0].equals("start")) {
								player.setValue(messageSplit[1]);
								inroom.setVisible(false);
								gameroom = new GameRoom(client, player);
								gameroom.setplayer(messageSplit[2], messageSplit[3]);
							}
							if (messageSplit[0].equals("attack")) {
								gameroom.setAttack(Integer.parseInt(messageSplit[1]),Integer.parseInt(messageSplit[2]));

							}
							if (messageSplit[0].equals("lose")) {
								
								gameroom.SetWin(Integer.parseInt(messageSplit[1]), Integer.parseInt(messageSplit[2]),Integer.parseInt(messageSplit[3]));
								gameroom.lose();
								gameroom.dispose();
								inroom.setVisible(true);

							}if (messageSplit[0].equals("win")) {
								JOptionPane.showMessageDialog(null, "Bạn đã chiến thắng", "Thông báo",
										JOptionPane.INFORMATION_MESSAGE);
								gameroom.dispose();
								inroom.setVisible(true);

							}
							if(messageSplit[0].equals("exit-room")){
								inroom.dispose();
								viewonline.setVisible(true);
							}
							if(messageSplit[0].equals("delete")){
								inroom.dispose();
							}
							if(messageSplit[0].equals("sms")){
								gameroom.setsms(messageSplit[1]);
							}
							if(messageSplit[0].equals("chat-server")){
								String sms = "";
								for(int i=1; i<messageSplit.length;i++) {
									sms+= messageSplit[i]+" ";
								}
								viewonline.SetChat(sms);
							}
							if(messageSplit[0].equals("doi-thu-da-thoat-game")){
								
								gameroom.end();
								gameroom.dispose();
								inroom.exitroom();
								inroom.setVisible(true);
							}
							if(messageSplit[0].equals("doi-thu-dau-hang")){
								JOptionPane.showMessageDialog(null, "Đối thủ đầu hàng, bạn đã chiến thắng", "Thông báo",
										JOptionPane.INFORMATION_MESSAGE);
								gameroom.dispose();
								inroom.setVisible(true);
							}
							if(messageSplit[0].equals("dau-hang")){
								JOptionPane.showMessageDialog(null, "Bạn đã thua", "Thông báo",
										JOptionPane.INFORMATION_MESSAGE);
								gameroom.dispose();
								inroom.setVisible(true);
							}
							if(messageSplit[0].equals("doi-thu-xin-hoa")){
								int res = JOptionPane.showConfirmDialog(null, "Bạn có đồng ý hòa không?","Đối thủ gửi yêu cầu hòa hoãn?",
										JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
								if (res == JOptionPane.YES_OPTION) {
									write("yes-hoa");
									JOptionPane.showMessageDialog(null, "Ván này hòa", "Thông báo",
											JOptionPane.INFORMATION_MESSAGE);
									gameroom.dispose();
									inroom.setVisible(true);
								} else if (res == JOptionPane.NO_OPTION) {
									write("no-hoa");
								}
							}
							if(messageSplit[0].equals("dong-y-hoa")){
								JOptionPane.showMessageDialog(null, "Ván này hòa", "Thông báo",
										JOptionPane.INFORMATION_MESSAGE);
								gameroom.dispose();
								inroom.setVisible(true);
							}
							if(messageSplit[0].equals("khong-hoa")){
								JOptionPane.showMessageDialog(null, "Đối thủ không đồng ý hòa, hãy tiếp tục thi đấu", "Thông báo",
										JOptionPane.INFORMATION_MESSAGE);
							}
							
							
						}

					} catch (UnknownHostException e) {
						return;
					} catch (IOException e) {
						return;
					}
				}
			};

			thread.start();
		} catch (Exception e) {
		}

	}

	public void write(String message) throws IOException {
		os.writeUTF(message);
		os.flush();
	}

}
