package rs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.xml.ws.handler.MessageContext;

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
		logger.info("New Server Thread Started");
		try {
			processRequest();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processRequest() throws IOException {
		RequestMessage message = MessageUtility.extractRequest(fromPeer);
		logger.info(message);
		if (Constants.METHOD_REGISTER.equals(message.getMethod())) {
			processRegister(message);
		}

	}

	public void processRegister(RequestMessage message) throws IOException {
		int requestCookie = Integer.parseInt(message.getHeader(Constants.HEADER_COOKIE));
		// TODO Validate against peers - for now assume cookie is correct
		// TODO Check for invalid request/negative cookie number
		String sentence = "";
		sentence = Constants.PROTOCOL_VERSION + " " + Constants.STATUS_OK + Constants.CR_LF;
		if (requestCookie > 0) {
			// TODO Might need to update port and all
			sentence += Constants.HEADER_COOKIE + " " + requestCookie + Constants.CR_LF;
		} else {
			// TODO Add to peer list
			int cookieNum = RSServer.getInstance().getNextCookieNumber();
			Peer peer = new Peer();
			peer.setCookie(cookieNum);
			// TODO Set port number of RFC SERVER from request
			peer.setPortNumber(0);
			peer.setHostname(connectionSocket.getInetAddress().toString());
			RSServer.getInstance().addPeer(peer);
			sentence += Constants.HEADER_COOKIE + " " + cookieNum + Constants.CR_LF;
		}
		sentence += Constants.CR_LF;
		sentence += Constants.CR_LF;
		toPeer.writeBytes(sentence);

	}

	public void processLeave() {

	}

	public void processPQuery() {
	}

	public void processKeepAlive() {
		// TODO RESET TTL
	}

	// Close connection during garbage collection
	public void finalize() {
		try {
			connectionSocket.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}

}
