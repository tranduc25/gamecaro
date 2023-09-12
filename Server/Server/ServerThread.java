package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ServerThread implements Runnable {
	private Socket socketOfServer;
	private int clientNumber;
	private DataInputStream is;
	private DataOutputStream os;
	private boolean isClosed;
	private String name;
	private String avatar;
	private boolean isRoom;
	private int ID_ROOM;
	private boolean roomfull;
	private boolean run;
	
	

	public boolean isRoomfull() {
		return roomfull;
	}

	public void setRoomfull(boolean roomfull) {
		this.roomfull = roomfull;
	}

	public boolean isRoom() {
		return isRoom;
	}

	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}

	public int getID_ROOM() {
		return ID_ROOM;
	}

	public void setID_ROOM(int iD_ROOM) {
		ID_ROOM = iD_ROOM;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getClientNumber() {
		return clientNumber;
	}

	public ServerThread(Socket socketOfServer, int clientNumber) {
		this.socketOfServer = socketOfServer;
		this.clientNumber = clientNumber;
		isClosed = false;
		this.isRoom = false;
		this.roomfull = false;
	}

	public void write(String message) throws IOException {
		os.writeUTF(message);
		os.flush();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			// Mở luồng vào ra trên Socket tại Server.
			is = new DataInputStream(socketOfServer.getInputStream());
			os = new DataOutputStream((socketOfServer.getOutputStream()));

			System.out.println("Khời động luông mới thành công, ID là: " + clientNumber);
			Server.serverview.setnotify("Một người chơi vừa đăng nhập , ID là: " + clientNumber);
			Server.serverThreadBus.SetOnlineList();

			String message;

			while (!isClosed) {
				message = is.readUTF();
				System.out.println(message);
				if (message == null) {
					break;
				}

				String[] messageSplit = message.split(",");
				if (messageSplit[0].equals("set-player")) {
					this.setName(messageSplit[1]);
					this.avatar = messageSplit[2];
					write("set-player" + "," +messageSplit[1]+","+ this.clientNumber+","+this.avatar);
				}
				if (messageSplit[0].equals("start")) {

					for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
						if (this != serverThread && serverThread.isRoom == true
								&& serverThread.getID_ROOM() == this.getID_ROOM()) {
							this.write("start,X," + serverThread.getName()+","+serverThread.avatar);
							this.run = true;
							serverThread.write("start,O," + this.getName()+","+this.avatar);
							serverThread.run = true;
						}
					}

				}
				if (messageSplit[0].equals("attack")) {
					for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
						if (this != serverThread && serverThread.isRoom == true
								&& serverThread.getID_ROOM() == this.getID_ROOM()) {
							serverThread.write("attack," + messageSplit[1] + "," + messageSplit[2]);
						}
					}

				}
				if (messageSplit[0].equals("win")) {
					for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
						if (serverThread.getClientNumber() != getClientNumber()) {
							serverThread.write("lose,"+messageSplit[1]+","+messageSplit[2]+","+messageSplit[3]);
							this.run = false;
							serverThread.run = false;
							
						}
					}
					this.write("win");
				}
				if (messageSplit[0].equals("create-room")) {
					Server.serverview.setnotify("Đã tạo phòng mới!");
					Server.serverThreadBus.createRoom(this);
					Server.serverThreadBus.RoomList();
					write("create-success," + getID_ROOM());
					
				}

				if (messageSplit[0].equals("join-room")) {
					int i = 0;
					for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
						if (this != serverThread && serverThread.isRoom == true
								&& serverThread.getID_ROOM() == Integer.parseInt(messageSplit[1])&&serverThread.roomfull == false) {
							this.setID_ROOM(serverThread.getID_ROOM());
							this.setRoom(true);
							serverThread.setRoomfull(true);
							this.roomfull = true;
							
							Server.serverThreadBus.sendto(serverThread, "doi-thu-join-room," + this.getName()+","+this.avatar);
							this.write("me-join-room," + getID_ROOM() + "," + serverThread.getName()+","+serverThread.avatar);
							i++;

						}
							
					}
					if(i==0)write("khong-ton-tai");

				}
				if (messageSplit[0].equals("exit")) {

					for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
						if (this != serverThread && serverThread.isRoom == true
								&& serverThread.getID_ROOM() == this.getID_ROOM()) {
							serverThread.write("doi-thu-da-thoat");
							serverThread.setRoomfull(false);
						}
					}
					this.setID_ROOM(0);
					this.setRoom(false);
					this.setRoomfull(false);
					this.write("exit-room");
					Server.serverThreadBus.RoomList();
				}
				if (messageSplit[0].equals("go-room-now")) {
					int i = 0;
					for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
						if (serverThread.isRoom == true && serverThread.roomfull == false) {
							this.setID_ROOM(serverThread.getID_ROOM());
							this.setRoom(true);
							this.setRoomfull(true);
							serverThread.setRoomfull(true);
							Server.serverThreadBus.sendto(serverThread, "doi-thu-join-room-now," + this.getName()+","+this.avatar);
							this.write("me-join-room-now," + getID_ROOM() + "," + serverThread.getName()+","+serverThread.avatar);
							i = 1;
							break;

						}
					}
					if(i == 0) {
						Server.serverThreadBus.createRoom(this);
						this.setRoomfull(false);
						Server.serverThreadBus.RoomList();
						write("create-now,"+this.getID_ROOM());
					}
				}
				if (messageSplit[0].equals("khong-co-phong")) {
					this.setRoom(false);
					this.setID_ROOM(0);
					this.setRoomfull(false);
					Server.serverThreadBus.RoomList();
					write("delete");
				}
				if (messageSplit[0].equals("sms")) {
					for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
						if (this != serverThread && serverThread.isRoom == true
								&& serverThread.getID_ROOM() == this.getID_ROOM()) {
							serverThread.write("sms," + messageSplit[1]);
						}
					}
				}
				if (messageSplit[0].equals("dau-hang")) {
					for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
						if (this != serverThread && serverThread.isRoom == true
								&& serverThread.getID_ROOM() == this.getID_ROOM()) {
							serverThread.write("doi-thu-dau-hang");
						}
					}
					this.write("dau-hang");
				}
				if (messageSplit[0].equals("xin-hoa")) {
					for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
						if (this != serverThread && serverThread.isRoom == true
								&& serverThread.getID_ROOM() == this.getID_ROOM()) {
							serverThread.write("doi-thu-xin-hoa");
						}
					}
				}
				if (messageSplit[0].equals("yes-hoa")) {
					for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
						if (this != serverThread && serverThread.isRoom == true
								&& serverThread.getID_ROOM() == this.getID_ROOM()) {
							serverThread.write("dong-y-hoa");
						}
					}
				}
				if (messageSplit[0].equals("no-hoa")) {
					for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
						if (this != serverThread && serverThread.isRoom == true
								&& serverThread.getID_ROOM() == this.getID_ROOM()) {
							serverThread.write("khong-hoa");
						}
					}
				}
				if (messageSplit[0].equals("chat-server")) {
					String sms = "chat-server,"+this.name+": ";
					for(int i=1; i<messageSplit.length;i++) {
						sms+= ","+messageSplit[i];
					}
					Server.serverThreadBus.SendAll(sms);
				}
					
			}
		} catch (IOException e) {
			if(this.run == true) {
				for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
					if (this != serverThread && serverThread.isRoom == true
							&& serverThread.getID_ROOM() == this.getID_ROOM()) {
						try {
							serverThread.write("doi-thu-da-thoat-game");
							serverThread.roomfull = false;
							serverThread.run = false;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
			if(this.run == false) {
				for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
					if (this != serverThread && serverThread.isRoom == true
							&& serverThread.getID_ROOM() == this.getID_ROOM()) {
						try {
							serverThread.write("doi-thu-da-thoat");
							serverThread.roomfull = false;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
	
			setRoom(false);
			Server.serverThreadBus.RoomList();
			Server.serverThreadBus.remove(clientNumber);
			isClosed = true;

			Server.serverview.setnotify("Người chơi có ID " + this.clientNumber + " đã thoát");

			Server.serverThreadBus.SetOnlineList();
			System.out.println(this.clientNumber + " đã thoát");
		}
	}

}
