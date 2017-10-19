package rs;

import java.util.List;

import org.apache.log4j.Logger;

import rfc.RFCClient;
import rfc.RFCServer;
import util.Peer;
import util.RFC;

public class ClientMain {
	final static Logger logger = Logger.getLogger(ClientMain.class);
	RFCClient rfcClient = new RFCClient();
	static RSClient rsClient = new RSClient();

	public static void main(String[] args) {
		try {
			rsClient.register();
			rsClient.keepAlive();
			rsClient.pQuery();
			rsClient.leave();
			rsClient.register();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void getFileFromPeer(String fileName) {
		List<RFC> ownRFCList = RFCServer.getRfcIndex().getOwnRFCList();
		for (RFC ownRFC : ownRFCList) {
			if (fileName.equalsIgnoreCase(ownRFC.getTitle())) {
				logger.info("RFC already present in same client");
				return;
			}
		}
		try {
			List<Peer> peers = RSClient.peerList;
			for (Peer peer : peers) {
				rfcClient.rfcQuery(peer.getHostname(), peer.getPortNumber());
				logger.info("RfcQuery done for peer " + peer);
				List<RFC> peerRFCList = RFCServer.getRfcIndex().getPeerRFCList();
				for (RFC peerRFC : peerRFCList) {
					if (fileName.equalsIgnoreCase(peerRFC.getTitle())) {
						logger.info("FileName " + fileName + " found");
						// change rfc number
						rfcClient.getRfc(peerRFC.getHost(), rsClient.getPortForHost(peerRFC.getHost()), 0, fileName);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
