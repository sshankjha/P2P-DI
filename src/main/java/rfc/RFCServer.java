package rfc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import rs.RSServerThread;
import util.RFCIndex;

public class RFCServer {

	private ServerSocket welcomeSocket;
	private static RFCServer instance = null;
	private static RFCIndex rfcIndex = new RFCIndex();

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
