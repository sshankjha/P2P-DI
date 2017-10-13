package rs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import util.GlobalConstants;
import util.Peer;

public class RSServer {

	private List<Peer> peerList;
	private ServerSocket welcomeSocket;
	private static RSServer instance = null;

	// Singleton Class
	public static synchronized RSServer getInstance() throws IOException {
		if (instance == null) {
			instance = new RSServer();
		}
		return instance;
	}

	private RSServer() throws IOException {
		super();
		peerList = new ArrayList<Peer>();
		welcomeSocket = new ServerSocket(GlobalConstants.RS_PORT);
	}

	public void listen() throws IOException {
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			Thread t = new Thread(new RSServerThread(connectionSocket));
			t.start();
		}
	}

	public synchronized void addPeer(Peer newPeer) {
	}

	public synchronized Peer removePeer(Peer newPeer) {
		return newPeer;
	}

	public synchronized List<Peer> getPeerList() {
		return peerList;
	}

}
