package rs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import util.Constants;
import util.P2PUtil;

public class RSClient {
	final static Logger logger = Logger.getLogger(RSClient.class);
	public static int cookie;

	public RSClient() throws UnknownHostException, IOException {
		// Read cookie from a file else set to a default value of 0
		cookie = P2PUtil.getCookieFromFile();

	}

	public void register() throws IOException {
		Socket socket = new Socket(P2PUtil.getLocalIpAddress(), Constants.RS_PORT);
		DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
		BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String sentence;
		sentence = Constants.METHOD_REGISTER + Constants.PROTOCOL_VERSION + Constants.CR_LF;
		// sentence += "Length: " + "1024\r\n";
		sentence += Constants.HEADER_COOKIE + " " + cookie + "\r\n";
		sentence += "\r\n";
		sentence += "\r\n";
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
