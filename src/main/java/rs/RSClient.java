package rs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import util.GlobalConstants;

public class RSClient {
	final static Logger logger = Logger.getLogger(RSClient.class);
	public static int cookie;

	public RSClient() throws UnknownHostException, IOException {
		// Read cookie from a file else set to a default value of 0
		cookie = 1;

	}

	public void connect() throws IOException {

		// modifiedSentence = inFromServer.readLine();
		//
		// System.out.println("FROM SERVER: " + modifiedSentence);

	}

	public void register() throws IOException {
		Socket socket = new Socket(GlobalConstants.INTERFACE_NAME, GlobalConstants.RS_PORT);
		DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
		BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String sentence;
		sentence = "Get Register P2P-DI/1.0\r\n";
		sentence += "Length: " + "1024\r\n";
		sentence += "Host: " + "localhost\r\n";
		sentence += "Cookie: " + cookie + "\r\n";
		toServer.writeBytes(sentence);
		// TODO Extract cookie from server
		socket.close();
	}

	public void leave() {
	}

	public void pQuery() {
	}

	public void keepAlive() {
	}

}
