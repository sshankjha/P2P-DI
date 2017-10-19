package rfc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import rs.RSServerThread;
import util.RFC;
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

	public void addOwnRFC(int rfcNumber, String title, String host) {
		RFC newrfc = new RFC(rfcNumber, title, host);
		rfcIndex.getOwnRFCList().add(newrfc);
	}

	public void addPeerRFC(List<RFC> peerRFC) {
		// filter for duplicate entries
		rfcIndex.getPeerRFCList().addAll(peerRFC);
	}

	public List<RFC> getCombinedRFCList() {
		List<RFC> listToReturn = new ArrayList<>(rfcIndex.getOwnRFCList());
		listToReturn.addAll(rfcIndex.getPeerRFCList());
		return listToReturn;
	}

	public int getListneningSocket() {
		return welcomeSocket.getLocalPort();
	}
}
