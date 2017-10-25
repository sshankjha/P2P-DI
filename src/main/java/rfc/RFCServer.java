package rfc;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.Constants;
import util.P2PUtil;
import util.RFC;
import util.RFCIndex;

public class RFCServer {
	final static Logger logger = Logger.getLogger(RFCServer.class);
	private static ServerSocket welcomeSocket;
	private static RFCServer instance = null;
	private static RFCIndex rfcIndex = new RFCIndex();

	public static RFCIndex getRfcIndex() {
		return rfcIndex;
	}

	public static void setRfcIndex(RFCIndex rfcIndex) {
		RFCServer.rfcIndex = rfcIndex;
	}

	// Singleton Class
	public static synchronized RFCServer getInstance() throws IOException {
		if (instance == null) {
			// Creating directory to read and save rfc files
			new File(Constants.RFC_PATH).mkdirs();
			instance = new RFCServer();
			Thread thread = new Thread("RFC_LISTNER") {
				public void run() {
					try {
						RFCServer.listen(welcomeSocket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						logger.error(e);
					}
				}
			};
			thread.start();
		}
		return instance;
	}

	private RFCServer() throws IOException {
		super();
		welcomeSocket = new ServerSocket(0);
	}

	public static void listen(ServerSocket welcomeSocket) throws IOException {
		logger.debug("Inside RFCServer.listen()");
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			Thread t = new Thread(new RFCServerThread(connectionSocket));
			t.start();
		}
	}

	public void addOwnRFC(int startingRfcNumber, int count) throws UnknownHostException {
		for (int i = 0; i < count; i++) {
			RFC newrfc = new RFC(startingRfcNumber + i, P2PUtil.getLocalIpAddress(), getListneningSocket());
			rfcIndex.getOwnRFCList().add(newrfc);
		}
	}

	public void addSingleOwnRFC(int rfcNumber) throws UnknownHostException {
		RFC newrfc = new RFC(rfcNumber, P2PUtil.getLocalIpAddress(), getListneningSocket());
		rfcIndex.getOwnRFCList().add(newrfc);
	}

	public void addPeerRFC(List<RFC> fetchedPeerRFC) {
		List<RFC> existingList = rfcIndex.getPeerRFCList();
		for (RFC peerRFC : fetchedPeerRFC) {
			if (!existingList.contains(peerRFC)) {
				existingList.add(peerRFC);
			}
		}
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
