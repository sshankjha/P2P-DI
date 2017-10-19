package rfc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.log4j.Logger;

import util.MessageUtility;
import util.RequestMessage;

public class RFCServerThread implements Runnable {

	final static Logger logger = Logger.getLogger(RFCServerThread.class);
	private Socket connectionSocket;
	private BufferedReader fromPeer;
	DataOutputStream toPeer;

	public RFCServerThread(Socket newConnectionSocket) throws IOException {
		super();
		this.connectionSocket = newConnectionSocket;
		fromPeer = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		toPeer = new DataOutputStream(connectionSocket.getOutputStream());
	}

	public void run() {
		logger.info("New RFCServer Thread Started");
		try {
			processRequest();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processRequest() throws IOException {
		RequestMessage message = MessageUtility.extractRequest(fromPeer);
		
	}

	public void processRFCQuery() {
	}

	public void processGetRfc() {
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

}
