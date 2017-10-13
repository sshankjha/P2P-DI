package rs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.log4j.Logger;

import util.GlobalConstants;

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
		String clientSentence = fromPeer.readLine();
		System.out.println("Received: " + clientSentence);
		String sentence = "P2P-DI/1.0 200\r\n";
		sentence += GlobalConstants.HEADER_COOKIE + " " + 10 + "\r\n";
		toPeer.writeBytes(sentence);
	}

	public void processLeave() {

	}

	public void processLeavepQuery() {
	}

	public void processKeepAlive() {
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
