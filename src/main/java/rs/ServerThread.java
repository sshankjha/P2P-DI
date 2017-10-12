package rs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.log4j.Logger;

public class ServerThread implements Runnable {
	
	final static Logger logger = Logger.getLogger(ServerThread.class);
	private Socket connectionSocket;

	public ServerThread(Socket connectionSocket) {
		super();
		this.connectionSocket = connectionSocket;
	}

	public void run() {
		logger.info("New Server Thread Created");
		BufferedReader inFromClient;
		try {
			inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			String clientSentence = inFromClient.readLine();
			System.out.println("Received: " + clientSentence);
			String capitalizedSentence = clientSentence.toUpperCase() + '\n';
			outToClient.writeBytes(capitalizedSentence);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
