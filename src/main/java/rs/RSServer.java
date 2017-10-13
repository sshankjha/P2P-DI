package rs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import util.GlobalConstants;

public class RSServer {

	private ServerSocket welcomeSocket;

	public RSServer() throws IOException {
		super();
		welcomeSocket = new ServerSocket(GlobalConstants.RS_PORT);
	}

	public void listen() throws IOException {
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			Thread t = new Thread(new RSServerThread(connectionSocket));
			t.start();
		}
	}

}
