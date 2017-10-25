package main;

import org.apache.log4j.Logger;

import rfc.RFCServer;
import rs.RSClient;
import util.P2PUtil;

public class ClientMainA {
	final static Logger logger = Logger.getLogger(ClientMainA.class);

	public static void main(String[] args) {
		try {
			// Start - Mandatory handshake and server initialization code
			RFCServer rfcServer = RFCServer.getInstance();
			RSClient rsClient = new RSClient();
			rsClient.register();
			// Start - Mandatory handshake and server initialization code
			Thread.sleep(6 * 1000);
			P2PUtil.getFileFromPeer(1);
			Thread.sleep(20 * 1000);
			P2PUtil.getFileFromPeer(2);
			logger.info("Peer A Execution Complete");
			System.exit(0);

		} catch (Exception e) {
			logger.error(e);
		}
	}

}
