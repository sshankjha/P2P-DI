package rfc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import util.Constants;
import util.Message;
import util.MessageUtility;
import util.P2PUtil;
import util.RFC;
import util.ResponseMessage;

public class RFCClient {

	public void rfcQuery(String peerName, int peerPort) throws UnknownHostException, IOException {
		Socket socket = new Socket(peerName, peerPort);
		DataOutputStream toPeer = new DataOutputStream(socket.getOutputStream());
		BufferedReader fromPeer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String sentence;
		sentence = Constants.METHOD_RFCQUERY + " " + Constants.PROTOCOL_VERSION + Constants.CR_LF;
		sentence += Constants.CR_LF;
		toPeer.writeBytes(sentence);
		ResponseMessage response = MessageUtility.extractResponse(fromPeer);
		fetchRFCList(response);
		socket.close();
	}

	private void fetchRFCList(Message response) throws NumberFormatException, IOException {
		// Verify - clear the peer list first or add to the previous one
		List<RFC> rfcListFromPeer = new ArrayList<RFC>();
		String data = response.getData();
		String[] values = data.split(Constants.SEPARATOR);
		for (int iter = 0; iter < values.length; iter += 4) {
			if (values[iter + 2] != P2PUtil.getLocalIpAddress()) {
				rfcListFromPeer.add(new RFC(Integer.parseInt(values[iter]), values[iter + 1], values[iter + 2]));
			}
		}
		RFCServer.getInstance().addPeerRFC(rfcListFromPeer);
	}

	public void getRfc(String peerName, int peerPort, String fileName) throws UnknownHostException, IOException {
		Socket socket = new Socket(peerName, peerPort);
		DataOutputStream toPeer = new DataOutputStream(socket.getOutputStream());
		BufferedReader fromPeer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String sentence;
		sentence = Constants.METHOD_GETRFC + " " + Constants.PROTOCOL_VERSION + " " + fileName + " " + Constants.CR_LF;
		sentence += Constants.CR_LF;
		toPeer.writeBytes(sentence);
		ResponseMessage response = MessageUtility.extractResponse(fromPeer);
		String dataFromResponse = response.getData();
		socket.close();
	}

}
