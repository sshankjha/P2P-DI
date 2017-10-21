package rs;

import java.util.List;

import org.apache.log4j.Logger;

import rfc.RFCClient;
import rfc.RFCServer;
import util.Peer;
import util.RFC;

public class ClientMain {
	final static Logger logger = Logger.getLogger(ClientMain.class);

	public static void main(String[] args) {
		try {
			RFCServer rfcServer = RFCServer.getInstance();
			RSClient rsClient = new RSClient(rfcServer.getListneningSocket());
			rsClient.register();
			rsClient.keepAlive();
			rsClient.pQuery();
			getFileFromPeer("firstrfc.txt");
			//rfcServer.addOwnRFC(1, "firstrfc.txt");
			logger.info("Execution Complete");
			// rsClient.leave();
			// rsClient.register();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public static void getFileFromPeer(String fileName) {
		RFCClient rfcClient = new RFCClient();
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
						rfcClient.getRfc(peerRFC.getHost(), RSClient.getPortForHost(peerRFC.getHost()), 0, fileName);
						return;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
