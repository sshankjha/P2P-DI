package rs;

import java.util.List;

import org.apache.log4j.Logger;

import rfc.RFCClient;
import rfc.RFCServer;
import util.Peer;
import util.RFC;

public class ClientCentralized {
	final static Logger logger = Logger.getLogger(ClientCentralized.class);

	public static void main(String[] args) {
		try {
			RFCServer rfcServer = RFCServer.getInstance();
			RSClient rsClient = new RSClient(rfcServer.getListneningSocket());
			rsClient.register();
			rfcServer.addOwnRFC(8001, 60);
			/*rsClient.pQuery();
			long startTime = System.currentTimeMillis();
			int startRFC = 8000;
			for (int i = 1; i < 61; i++) {
				getFileFromPeer(startRFC + i);
			}
			System.out.println("Time taken: " + (System.currentTimeMillis() - startTime));*/
			logger.info("Execution Complete");
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public static void getFileFromPeer(int RFCNumber) {
		RFCClient rfcClient = new RFCClient();
		List<RFC> ownRFCList = RFCServer.getRfcIndex().getOwnRFCList();
		for (RFC ownRFC : ownRFCList) {
			if (RFCNumber == ownRFC.getRFCNumber()) {
				logger.info("RFC already present in same client");
				return;
			}
		}
		try {
			List<Peer> peers = RSClient.peerList;
			for (Peer peer : peers) {
				rfcClient.rfcQuery(peer.getHostname(), peer.getPortNumber());
				logger.info("RfcQuery done for peer " + peer.getHostname() + ":" + peer.getPortNumber());
				List<RFC> peerRFCList = RFCServer.getRfcIndex().getPeerRFCList();
				for (RFC peerRFC : peerRFCList) {
					if (RFCNumber == peerRFC.getRFCNumber()) {
						logger.info("FileName " + peerRFC.getTitle() + " found at " + peerRFC.getHost());
						// change rfc number
						rfcClient.getRfc(peerRFC.getHost(), RSClient.getPortForHost(peerRFC.getHost()), RFCNumber);
						return;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
