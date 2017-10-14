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
import util.MessageUtility;
import util.P2PUtil;
import util.ResponseMessage;

public class RSClient {
	final static Logger logger = Logger.getLogger(RSClient.class);
	public static int cookie;
	public static String hostname;

	public RSClient() throws UnknownHostException, IOException {
		// Read cookie from a file else set to a default value of 0
		hostname = P2PUtil.getLocalIpAddress();
		cookie = P2PUtil.getCookieFromFile();

	}

	public void register() throws IOException {
		Socket socket = new Socket(hostname, Constants.RS_PORT);
		DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
		BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String sentence;
		sentence = Constants.METHOD_REGISTER + " " + Constants.PROTOCOL_VERSION + Constants.CR_LF;
		sentence += Constants.HEADER_COOKIE + " " + cookie + Constants.CR_LF;
		sentence += Constants.CR_LF;
		sentence += Constants.CR_LF;
		toServer.writeBytes(sentence);
		ResponseMessage response = MessageUtility.extractResponse(fromServer);
		cookie = Integer.parseInt(response.getHeader(Constants.HEADER_COOKIE));
		setCookie();
		logger.info(response);
		socket.close();
	}

	public void leave() {
	}

	public void pQuery() {
	}

	public void keepAlive() {
	}

	private void setCookie() throws UnknownHostException {
		P2PUtil.setCookieInFile();
	}

}
