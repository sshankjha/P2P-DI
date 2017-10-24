package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.UnknownHostException;
import java.util.Base64;

import org.apache.log4j.Logger;

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
			// System.out.println(Base64.getEncoder().encode(data.toString().getBytes()));
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
}
