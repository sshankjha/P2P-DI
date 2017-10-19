package rs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import util.Constants;
import util.Peer;

public class RSServer {
	final static Logger logger = Logger.getLogger(RSServer.class);
	private List<Peer> peerList;
	private ServerSocket welcomeSocket;
	private static RSServer instance = null;
	private static int currentCookie = 0;

	// Singleton Class
	public static synchronized RSServer getInstance() throws IOException {
		if (instance == null) {
			instance = new RSServer();
		}
		return instance;
	}

	private RSServer() throws IOException {
		super();
		peerList = Collections.synchronizedList(new ArrayList<Peer>());
		welcomeSocket = new ServerSocket(Constants.RS_PORT);
	}

	public void listen() throws IOException {
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			Thread t = new Thread(new RSServerThread(connectionSocket));
			t.start();
		}
	}

	public synchronized int getNextCookieNumber() {
		currentCookie++;
		return currentCookie;
	}

	public void addUpdatePeer(Peer newPeer) {
		synchronized (peerList) {
			boolean flag = true;
			for (Peer peer : peerList) {
				if (peer.equals(newPeer)) {
					peer.setTTL(7200);
					peer.setPortNumber(newPeer.getPortNumber());
					peer.setLastRegistrationTime(new Date());
					flag = false;
					break;
				}
			}
			if (flag) {
				peerList.add(newPeer);
			}
		}
		peerList.add(newPeer);
	}

	public void updatePeer(Peer newPeer) {
		synchronized (peerList) {
			for (Peer peer : peerList) {
				if (peer.equals(newPeer)) {
					peer.setTTL(7200);
					break;
				}
			}
		}
	}

	public void removePeer(Peer newPeer) {
		peerList.remove(newPeer);
	}

	public List<Peer> getPeerList() {
		return peerList;
	}

}
