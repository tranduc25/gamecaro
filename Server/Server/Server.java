package Server;

import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.DropMode;

public class Server {
	public static  volatile ServerThreadBus serverThreadBus;
	private static Socket socketOfServer;
	public static ServerView serverview;
	

	public Server() {
		// TODO Auto-generated constructor stub

	}

	public static void main(String[] args) {
		serverview = new ServerView();
		Server sv = new Server();
		ServerSocket listener = null;
		serverThreadBus = new ServerThreadBus();
		System.out.println("Server is waiting to accept user...");
		serverview.setnotify("Server is waiting to accept user..");
		int clientNumber = 0;

		ThreadPoolExecutor executor = new ThreadPoolExecutor (10, // corePoolSize
				100, // maximumPoolSize
				10, // thread timeout
				TimeUnit.SECONDS, new ArrayBlockingQueue<>(8) // queueCapacity
		);

		try {
			listener = new ServerSocket(6666);
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}

		while (true) {

			try {
				socketOfServer = listener.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ServerThread serverThread = new ServerThread(socketOfServer, clientNumber++);
			serverThreadBus.add(serverThread);
			executor.execute(serverThread);

		}

	}

}
