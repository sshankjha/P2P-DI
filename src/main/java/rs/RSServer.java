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
			if (peerList.contains(newPeer)) {
				Peer peer = peerList.get(peerList.indexOf(newPeer));
				peer.setTTL(7200);
				peer.setPortNumber(newPeer.getPortNumber());
				peer.setLastRegistrationTime(new Date());
				peer.setStatus(true);
			} else {
				peerList.add(newPeer);
			}
		}
	}

	public void updatePeer(Peer newPeer) {
		synchronized (peerList) {
			if (peerList.contains(newPeer)) {
				Peer peer = peerList.get(peerList.indexOf(newPeer));
				peer.setTTL(7200);
				peer.setStatus(true);
			}
		}
	}

	public void removePeer(Peer peerToRemove) {
		synchronized (peerList) {
			if (peerList.contains(peerToRemove)) {
				peerList.get(peerList.indexOf(peerToRemove)).setStatus(false);
			}
		}
	}

	public List<Peer> getPeerList() {
		return peerList;
	}
}
