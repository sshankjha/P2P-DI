package main;

import java.io.IOException;

import org.apache.log4j.Logger;

import rs.RSServer;

public class ServerMain {
	final static Logger logger = Logger.getLogger(ServerMain.class);

	public static void main(String[] args) throws IOException, InterruptedException {
		logger.info("Starting RS Server");
		RSServer.getInstance().listen();
		Thread.sleep(40 * 1000);
		//System.exit(0);
	}
}
