package rs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import util.GlobalConstants;
import util.P2PUtil;

public class RSClient {
	final static Logger logger = Logger.getLogger(RSClient.class);
	public static int cookie;

	public RSClient() throws UnknownHostException, IOException {
		// Read cookie from a file else set to a default value of 0
		cookie = 0;

	}

	public void register() throws IOException {
		Socket socket = new Socket(P2PUtil.getLocalIpAddress(), GlobalConstants.RS_PORT);
		DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
		BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String sentence;
		sentence = "Register P2P-DI/1.0\r\n";
		// sentence += "Length: " + "1024\r\n";
		sentence += GlobalConstants.HEADER_COOKIE + " " + cookie + "\r\n";
		toServer.writeBytes(sentence);
		String clientSentence = fromServer.readLine();
		System.out.println("Received: " + clientSentence);
		clientSentence = fromServer.readLine();
		System.out.println("Received: " + clientSentence);
		// TODO Extract cookie from server
		socket.close();
	}

	public void leave() {
	}

	public void pQuery() {
	}

	public void keepAlive() {
	}

	private void setCookie() {
	}

}
