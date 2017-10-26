package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import rfc.RFCClient;
import rfc.RFCServer;
import rs.RSClient;

public class P2PUtil {

	final static Logger logger = Logger.getLogger(P2PUtil.class);

	public static String getLocalIpAddress() throws UnknownHostException {
		return NetworkUtils.getLocalAddress();
	}

	public static String getCookieFileName() throws UnknownHostException {
		return Constants.COOKIE + "_" + P2PUtil.getLocalIpAddress();
	}

	/**
	 * Code referenced from :
	 * https://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
	 **/
	public static int getCookieFromFile() throws UnknownHostException {
		int cookie = 0;
		String cookieFileName = getCookieFileName();
		try (BufferedReader br = new BufferedReader(new FileReader(cookieFileName))) {

			String sCurrentLine;

			sCurrentLine = br.readLine();
			cookie = Integer.parseInt(sCurrentLine);

		} catch (Exception e) {
			logger.error(e);
		}
		return cookie;
	}

	public static void setCookieInFile(int cookienum) throws UnknownHostException {
		String cookieFileName = getCookieFileName();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(cookieFileName))) {

			bw.write(String.valueOf(cookienum));

		} catch (Exception e) {

			logger.error(e);

		}
	}

	public static String getFileNameFromRFCNumber(int rfcNumber) {
		return Constants.RFC_FILE_PREFIX + rfcNumber + Constants.RFC_FILE_EXTENSION;
	}

	public static void sendRFC(DataOutputStream toServer, int rfcNumber) throws UnknownHostException {
		try (BufferedReader br = new BufferedReader(
				new FileReader(Constants.RFC_PATH + getFileNameFromRFCNumber(rfcNumber)))) {
			StringBuilder data = new StringBuilder("");
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				data.append(sCurrentLine + Constants.CR_LF);
			}
			System.out.println(Base64.getEncoder().encode(data.toString().getBytes()));
			toServer.write(Base64.getEncoder().encode(data.toString().getBytes()));
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public static void saveRFCFile(String fomrServer, int rfcNumber) throws UnknownHostException {
		try (BufferedWriter bw = new BufferedWriter(
				new FileWriter(Constants.RFC_PATH + getFileNameFromRFCNumber(rfcNumber)))) {
			bw.write(new String(Base64.getDecoder().decode(fomrServer)));
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public static void getFileFromPeer(int RFCNumber) throws UnknownHostException, IOException {
		RSClient rsClient = new RSClient();
		rsClient.pQuery();
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
				logger.debug("RfcQuery done for peer " + peer.getHostname() + ":" + peer.getPortNumber());
				List<RFC> peerRFCList = RFCServer.getRfcIndex().getPeerRFCList();
				for (RFC peerRFC : peerRFCList) {
					if (RFCNumber == peerRFC.getRFCNumber()) {
						logger.debug("FileName " + peerRFC.getTitle() + " found at " + peerRFC.getHost());
						// change rfc number
						boolean downloadSuccessful = rfcClient.getRfc(peerRFC.getHost(),
								RSClient.getPortForHost(peerRFC.getHost()), RFCNumber);
						if (downloadSuccessful) {
							return;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public static int getPortNumberInVCLRange() {
		Random rand = new Random();
		int value = rand.nextInt(Constants.RFC_PORT_RANGE + 1);
		return Constants.RFC_PORT_START + value;
	}
}
