package main;

import org.apache.log4j.Logger;

import rfc.RFCServer;
import rs.RSClient;

public class ClientMainB {
	final static Logger logger = Logger.getLogger(ClientMainB.class);

	public static void main(String[] args) {
		try {
			// Start - Mandatory handshake and server initialization code
			RFCServer rfcServer = RFCServer.getInstance();
			RSClient rsClient = new RSClient();
			rsClient.register();
			// Start - Mandatory handshake and server initialization code
			rfcServer.addSingleOwnRFC(1);
			rfcServer.addSingleOwnRFC(2);
			Thread.sleep(20 * 1000);
			logger.info("Sending Leave message");
			rsClient.leave();
			logger.info("Exiting");
			System.exit(0);

		} catch (Exception e) {
			logger.error(e);
		}
	}

}
