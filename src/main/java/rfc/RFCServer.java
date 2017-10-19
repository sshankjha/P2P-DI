package rfc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import rs.RSServerThread;

public class RFCServer {

	private ServerSocket welcomeSocket;
	private static RFCServer instance = null;

	// Singleton Class
	public static synchronized RFCServer getInstance() throws IOException {
		if (instance == null) {
			instance = new RFCServer();
		}
		return instance;
	}

	private RFCServer() throws IOException {
		super();
		welcomeSocket = new ServerSocket();
	}

	public void listen() throws IOException {
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			Thread t = new Thread(new RSServerThread(connectionSocket));
			t.start();
		}
	}

	public int getListneningSocket() {
		return welcomeSocket.getLocalPort();
	}

}
