package rs;

import org.apache.log4j.Logger;

public class ClientMain {
	final static Logger logger = Logger.getLogger(ClientMain.class);

	public static void main(String[] args) {
		try {
			RSClient client = new RSClient();
			client.register();
		} catch (Exception e) {
			logger.error(e);
		}
	}

}
