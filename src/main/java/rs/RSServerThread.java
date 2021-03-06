package rs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.log4j.Logger;

import util.Constants;
import util.MessageUtility;
import util.Peer;
import util.RequestMessage;

public class RSServerThread implements Runnable {

	final static Logger logger = Logger.getLogger(RSServerThread.class);
	private Socket connectionSocket;
	private BufferedReader fromPeer;
	DataOutputStream toPeer;

	public RSServerThread(Socket newConnectionSocket) throws IOException {
		super();
		this.connectionSocket = newConnectionSocket;
		fromPeer = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		toPeer = new DataOutputStream(connectionSocket.getOutputStream());
	}

	public void run() {
		logger.debug("New Server Thread Started");
		try {
			processRequest();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processRequest() throws IOException {
		RequestMessage message = MessageUtility.extractRequest(fromPeer);
		// TODO Validate against peers - for now assume cookie is correct
		// TODO Check for invalid request/negative cookie number
		if (Constants.METHOD_REGISTER.equals(message.getMethod())) {
			processRegister(message);
		} else if (Constants.METHOD_PQUERY.equals(message.getMethod())) {
			processPQuery(message);
		} else if (Constants.METHOD_KEEPALIVE.equals(message.getMethod())) {
			processKeepAlive(message);
		} else if (Constants.METHOD_LEAVE.equals(message.getMethod())) {
			processLeave(message);
		} else {
			// TODO: Send invalid request message
			return;
		}

	}

	private void processRegister(RequestMessage message) throws IOException {
		int requestCookie = Integer.parseInt(message.getHeader(Constants.HEADER_COOKIE));
		int rfcServerPort = Integer.parseInt(message.getHeader(Constants.HEADER_RFCPORT));
		String sentence = "";
		sentence = Constants.PROTOCOL_VERSION + " " + Constants.STATUS_OK + Constants.CR_LF;
		int cookieNum;
		// Assign a new cookie number if request cookie = 0
		if (requestCookie > 0) {
			cookieNum = requestCookie;
		} else {
			cookieNum = RSServer.getInstance().getNextCookieNumber();
		}

		Peer peer = new Peer();
		peer.setCookie(cookieNum);
		// TODO Set port number of RFC SERVER from request
		peer.setPortNumber(rfcServerPort);
		peer.setHostname(connectionSocket.getInetAddress().toString().replaceAll("/", ""));
		RSServer.getInstance().addUpdatePeer(peer);
		sentence += Constants.HEADER_COOKIE + " " + cookieNum + Constants.CR_LF;
		sentence += Constants.CR_LF;
		sentence += Constants.CR_LF;
		toPeer.writeBytes(sentence);
		connectionSocket.close();

	}

	private void processPQuery(RequestMessage message) throws IOException {
		int requestCookie = Integer.parseInt(message.getHeader(Constants.HEADER_COOKIE));
		String sentence = "";
		if (isActivePeerPresent(requestCookie)) {
			sentence = Constants.PROTOCOL_VERSION + " " + Constants.STATUS_OK + Constants.CR_LF;
			sentence += Constants.HEADER_COOKIE + " " + requestCookie + Constants.CR_LF;
			sentence += Constants.CR_LF;
			// Sending PeerList
			sentence += sendPeerList(requestCookie);
		} else {
			sentence = Constants.PROTOCOL_VERSION + " " + Constants.STATUS_ERROR + Constants.CR_LF;
			sentence += Constants.HEADER_COOKIE + " " + requestCookie + Constants.CR_LF;
		}
		sentence += Constants.CR_LF;
		sentence += Constants.CR_LF;
		toPeer.writeBytes(sentence);
		connectionSocket.close();
	}

	private boolean isActivePeerPresent(int requestCookie) throws IOException {
		synchronized (RSServer.getInstance().getPeerList()) {
			for (Peer peer : RSServer.getInstance().getPeerList()) {
				if (!(peer.getCookie() == requestCookie) && peer.isActive()) {
					return true;
				}
			}
		}
		return false;
	}

	private void processLeave(RequestMessage message) throws IOException {
		int requestCookie = Integer.parseInt(message.getHeader(Constants.HEADER_COOKIE));
		String sentence = "";
		sentence = Constants.PROTOCOL_VERSION + " " + Constants.STATUS_OK + Constants.CR_LF;
		// Assign a new cookie number if request cookie = 0
		Peer peer = new Peer();
		peer.setCookie(requestCookie);
		peer.setHostname(connectionSocket.getInetAddress().toString().replaceAll("/", ""));
		RSServer.getInstance().removePeer(peer);
		sentence += Constants.HEADER_COOKIE + " " + requestCookie + Constants.CR_LF;
		sentence += Constants.CR_LF;
		sentence += Constants.CR_LF;
		toPeer.writeBytes(sentence);
		connectionSocket.close();
	}

	public void processKeepAlive(RequestMessage message) throws IOException {
		int requestCookie = Integer.parseInt(message.getHeader(Constants.HEADER_COOKIE));
		String sentence = "";
		sentence = Constants.PROTOCOL_VERSION + " " + Constants.STATUS_OK + Constants.CR_LF;
		// Assign a new cookie number if request cookie = 0
		Peer peer = new Peer();
		peer.setCookie(requestCookie);
		// TODO Set port number of RFC SERVER from request
		peer.setPortNumber(0);
		peer.setHostname(connectionSocket.getInetAddress().toString().replaceAll("/", ""));
		RSServer.getInstance().updatePeer(peer);
		sentence += Constants.HEADER_COOKIE + " " + requestCookie + Constants.CR_LF;
		sentence += Constants.CR_LF;
		sentence += Constants.CR_LF;
		toPeer.writeBytes(sentence);
		connectionSocket.close();
	}

	/**
	 * Close connection during garbage collection
	 **/
	public void finalize() {
		try {
			connectionSocket.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}

	private String sendPeerList(int requestCookie) throws IOException {
		String statement = "";
		synchronized (RSServer.getInstance().getPeerList()) {
			for (Peer peer : RSServer.getInstance().getPeerList()) {
				if (!(peer.getCookie() == requestCookie) && peer.isActive()) {
					statement += peer.getHostname() + Constants.SEPARATOR + peer.getPortNumber() + Constants.SEPARATOR
							+ peer.getCookie() + Constants.SEPARATOR;
				}
			}
		}
		return statement;
	}
}
