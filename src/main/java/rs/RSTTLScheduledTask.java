package rs;

import java.io.IOException;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import util.Peer;

public class RSTTLScheduledTask extends TimerTask {
	final static Logger logger = Logger.getLogger(RSTTLScheduledTask.class);

	public void run() {
		try {
			logger.debug("Running scheduler to decrease TTL Values");
			synchronized (RSServer.getInstance().getPeerList()) {
				for (Peer peer : RSServer.getInstance().getPeerList()) {
					peer.setTTL(peer.getTTL() - 60);
				}
			}

			synchronized (RSServer.getInstance().getPeerList()) {
				for (Peer peer : RSServer.getInstance().getPeerList()) {
					if (peer.getTTL() <= 0) {
						peer.setStatus(false);
					}
				}
			}
		} catch (IOException e) {
			logger.error(e);
		}
	}
}