package Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerThreadBus {
	private List<ServerThread> listServerThreads;
	public static int ID_room = 1;

	public List<ServerThread> getListServerThreads() {
		return listServerThreads;
	}

	public int getLength() {
		return listServerThreads.size();
	}

	public ServerThreadBus() {
		listServerThreads = new ArrayList<>();
	}

	public void add(ServerThread serverThread) {
		listServerThreads.add(serverThread);
	}

	public void SendAll(String message) { 
		for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
			try {
				serverThread.write(message);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void sendto(ServerThread serverThread, String sms) {  

		try {
			serverThread.write(sms);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void attack(int id, String message) {  
		for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
			if (serverThread.getClientNumber() != id)
				try {
					serverThread.write(message);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
		}
	}

	public void remove(int id) {
		for (int i = 0; i < Server.serverThreadBus.getLength(); i++) {
			if (Server.serverThreadBus.getListServerThreads().get(i).getClientNumber() == id) {
				System.out.println("da xoa" + id);
				Server.serverThreadBus.listServerThreads.remove(i);
			}
		}
	}

	public void SetOnlineList() {
		String res = "";
		List<ServerThread> threadbus = Server.serverThreadBus.getListServerThreads();
		for (ServerThread serverThread : threadbus) {
			res += "Người chơi có ID " + serverThread.getClientNumber() + "đang online.\n";
		}
		Server.serverview.SetOn(res);

	}
	public void createRoom(ServerThread t) {
		t.setID_ROOM(ID_room++);
		t.setRoom(true);
		
	}
	public void RoomList() {
		String res = "";

		List<ServerThread> threadbus = Server.serverThreadBus.getListServerThreads();
		for (ServerThread serverThread : threadbus) {
			if(serverThread.isRoom()) {
				res = res+"Phòng có id "+ serverThread.getID_ROOM()+" đang mở\n";
			}
			Server.serverview.Room(res);
		}

	}
}
