package rs;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import rfc.RFCClient;
import rfc.RFCServer;
import util.Peer;
import util.RFC;

public class ClientMain3 {
	final static Logger logger = Logger.getLogger(ClientMain3.class);

	public static void main(String[] args) {
		try {
			RFCServer rfcServer = RFCServer.getInstance();
			RSClient rsClient = new RSClient(rfcServer.getListneningSocket());
			rsClient.register();
			rfcServer.addOwnRFC(8021, 10);
			TimeUnit.SECONDS.sleep(2);
			rsClient.pQuery();
			long startTime = System.currentTimeMillis();
			for (int i = 8031; i < 8061; i++) {
				getFileFromPeer(i);
			}
			for (int i = 8001; i < 8021; i++) {
				getFileFromPeer(i);
			}
			logger.warn("Time taken: " + (System.currentTimeMillis() - startTime) + " ms");
			System.out.println("Time taken: " + (System.currentTimeMillis() - startTime) + " ms");

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
						rfcClient.getRfc(peerRFC.getHost(), peerRFC.getPort(), RFCNumber);
						return;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}