package rs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import util.GlobalConstants;

public class Server {

	private ServerSocket welcomeSocket;

	public Server() throws IOException {
		super();
		welcomeSocket = new ServerSocket(GlobalConstants.RC_PORT);
	}

	public void listen() throws IOException {
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			Thread t = new Thread(new ServerThread(connectionSocket));
			t.start();
		}
	}

}
