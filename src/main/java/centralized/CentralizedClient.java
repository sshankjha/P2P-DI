package centralized;

import org.apache.log4j.Logger;

import rfc.RFCServer;
import rs.RSClient;
import util.P2PUtil;

public class CentralizedClient {

	final static Logger logger = Logger.getLogger(CentralizedClient.class);

	public static void main(String[] args) {
		try {
			// Start - Mandatory handshake and server initialization code
			RFCServer rfcServer = RFCServer.getInstance();
			RSClient rsClient = new RSClient();
			rsClient.register();
			// Start - Mandatory handshake and server initialization code
			String timeTaken = "";
			long startTime = System.currentTimeMillis();
			for (int i = 8001; i < 8061; i++) {
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
