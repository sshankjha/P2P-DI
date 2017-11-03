package centralized;

import org.apache.log4j.Logger;

import main.ClientMainA;
import rfc.RFCServer;
import rs.RSClient;

public class CentralizedPeer {

	final static Logger logger = Logger.getLogger(CentralizedPeer.class);

	public static void main(String[] args) {
		try {
			// Start - Mandatory handshake and server initialization code
			RFCServer rfcServer = RFCServer.getInstance();
			RSClient rsClient = new RSClient();
			rsClient.register();
			// Start - Mandatory handshake and server initialization code
			rfcServer.addOwnRFC(8001, 60);

		} catch (Exception e) {
			logger.error(e);
		}
	}

}
