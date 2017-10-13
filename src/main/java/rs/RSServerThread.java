package rs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.log4j.Logger;

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
	}

	public void processLeave() {

	}

	public void processLeavepQuery() {
	}

	public void processKeepAlive() {
	}

}
