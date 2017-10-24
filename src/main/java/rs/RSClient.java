package rs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.Constants;
import util.Message;
import util.MessageUtility;
import util.P2PUtil;
import util.Peer;
import util.ResponseMessage;

public class RSClient {
	final static Logger logger = Logger.getLogger(RSClient.class);
	public int cookie;
	int rfcServerPortNumber;

	public static List<Peer> peerList = new ArrayList<Peer>();

	public void register() {
		try {
			Socket socket = new Socket(Constants.RS_IP, Constants.RS_PORT);
			DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String sentence;
			sentence = Constants.METHOD_REGISTER + " " + Constants.PROTOCOL_VERSION + Constants.CR_LF;
			sentence += Constants.HEADER_COOKIE + " " + cookie + Constants.CR_LF;
			sentence += Constants.HEADER_RFCPORT + " " + rfcServerPortNumber + Constants.CR_LF;
			sentence += Constants.CR_LF;
			sentence += Constants.CR_LF;
			toServer.writeBytes(sentence);
			ResponseMessage response = MessageUtility.extractResponse(fromServer);
			cookie = Integer.parseInt(response.getHeader(Constants.HEADER_COOKIE));
			setCookie();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RSClient(int rfcServerPortNumber) throws UnknownHostException, IOException {
		// Read cookie from a file else set to a default value of 0
		cookie = P2PUtil.getCookieFromFile();
		this.rfcServerPortNumber = rfcServerPortNumber;

	}

	public void leave() {
		try {
			Socket socket = new Socket(Constants.RS_IP, Constants.RS_PORT);
			DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String sentence;
			sentence = Constants.METHOD_LEAVE + " " + Constants.PROTOCOL_VERSION + Constants.CR_LF;
			sentence += Constants.HEADER_COOKIE + " " + cookie + Constants.CR_LF;
			sentence += Constants.CR_LF;
			sentence += Constants.CR_LF;
			toServer.writeBytes(sentence);
			ResponseMessage response = MessageUtility.extractResponse(fromServer);
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pQuery() {
		try {
			Socket socket = new Socket(Constants.RS_IP, Constants.RS_PORT);
			DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String sentence;
			sentence = Constants.METHOD_PQUERY + " " + Constants.PROTOCOL_VERSION + Constants.CR_LF;
			sentence += Constants.HEADER_COOKIE + " " + cookie + Constants.CR_LF;
			sentence += Constants.CR_LF;
			sentence += Constants.CR_LF;
			toServer.writeBytes(sentence);
			ResponseMessage response = MessageUtility.extractResponse(fromServer);
			if (response.getStatus().equals(String.valueOf(Constants.STATUS_OK))) {
				updatePeerList(response);
				// List<Peer> peerlist =
				// P2PUtil.deserialzePeerList(response.getData());
				cookie = Integer.parseInt(response.getHeader(Constants.HEADER_COOKIE));
				setCookie();
			} else {
				logger.info("No active peers present");
			}
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updatePeerList(Message msg) {
		// Verify - clear the peer list first or add to the previous one
		peerList.clear();
		String data = msg.getData();
		String[] values = data.split(Constants.SEPARATOR);
		for (int iter = 0; iter < values.length; iter += 3) {
			peerList.add(
					new Peer(values[iter], Integer.parseInt(values[iter + 1]), Integer.parseInt(values[iter + 2])));
		}
	}

	public void keepAlive() {
		try {
			Socket socket = new Socket(Constants.RS_IP, Constants.RS_PORT);
			DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String sentence;
			sentence = Constants.METHOD_KEEPALIVE + " " + Constants.PROTOCOL_VERSION + Constants.CR_LF;
			sentence += Constants.HEADER_COOKIE + " " + cookie + Constants.CR_LF;
			sentence += Constants.CR_LF;
			sentence += Constants.CR_LF;
			toServer.writeBytes(sentence);
			ResponseMessage response = MessageUtility.extractResponse(fromServer);
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setCookie() {
		try {
			P2PUtil.setCookieInFile(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getPortForHost(String hostName) {
		for (Peer peer : peerList) {
			if (peer.getHostname().equalsIgnoreCase(hostName)) {
				return peer.getPortNumber();
			}
		}
		System.out.println("Could not find port number for host "+ hostName);
		return 0;
	}
}
