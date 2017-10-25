package rs;

import java.io.IOException;

import org.apache.log4j.Logger;

public class ServerMain {
	final static Logger logger = Logger.getLogger(ServerMain.class);

	public static void main(String[] args) throws IOException {
		logger.warn("Starting Listener");
		RSServer.getInstance().listen();
	}
}
