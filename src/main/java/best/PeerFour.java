package best;

import org.apache.log4j.Logger;

import rfc.RFCServer;
import rs.RSClient;
import util.P2PUtil;

public class PeerFour {

	final static Logger logger = Logger.getLogger(PeerFour.class);

	public static void main(String[] args) {
		try {
			// Start - Mandatory handshake and server initialization code
			RFCServer rfcServer = RFCServer.getInstance();
			RSClient rsClient = new RSClient();
			rsClient.register();
			rfcServer.addOwnRFC(8031, 10);
			Thread.sleep(2 * 1000);
			// Start - Mandatory handshake and server initialization code
			String timeTaken = "";
			long startTime = System.currentTimeMillis();
			for (int i = 8041; i < 8061; i++) {
				P2PUtil.getFileFromPeer(i);
				timeTaken += (System.currentTimeMillis() - startTime) + ",";
			}
			for (int i = 8001; i < 8031; i++) {
				P2PUtil.getFileFromPeer(i);
				timeTaken += (System.currentTimeMillis() - startTime) + ",";
			}
			System.out.println("Time taken: " + timeTaken);
			System.exit(0);

		} catch (Exception e) {
			logger.error(e);
		}
	}

}
