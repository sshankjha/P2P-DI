package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

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

	public static void setCookieInFile() throws UnknownHostException {
		int cookie = RSClient.cookie;
		String cookieFileName = getCookieFileName();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(cookieFileName))) {

			bw.write(String.valueOf(cookie));

		} catch (Exception e) {

			logger.error(e);

		}
	}

	public static void sendRFC(DataOutputStream toServer, String fileName) throws UnknownHostException {
		try (BufferedReader br = new BufferedReader(new FileReader(Constants.RFC_PATH + fileName))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null && !sCurrentLine.isEmpty()) {
				toServer.writeBytes(sCurrentLine);
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public static void saveRFCFile(String fomrServer, String fileName) throws UnknownHostException {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(Constants.RFC_PATH + fileName))) {
			bw.write(String.valueOf(fomrServer));
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/*
	 * public static byte[] serialize(Object obj) throws IOException {
	 * ByteArrayOutputStream out = new ByteArrayOutputStream();
	 * ObjectOutputStream os = new ObjectOutputStream(out); os.writeObject(obj);
	 * return out.toByteArray(); }
	 * 
	 * public static void serializePeerList(List<Peer> list, DataOutputStream
	 * toServer) throws IOException {
	 * 
	 * byte[] data = serialize("Hello Mother!!"); toServer.write(data); }
	 * 
	 * public static Object deserialize(byte[] data) throws IOException,
	 * ClassNotFoundException { ByteArrayInputStream in = new
	 * ByteArrayInputStream(data); ObjectInputStream is = new
	 * ObjectInputStream(in); return is.readObject(); }
	 * 
	 * public static List<Peer> deserialzePeerList(String data) throws
	 * IOException, ClassNotFoundException {
	 * 
	 * List<Peer> peerList = null; Object obj = deserialize(data.getBytes());
	 * return peerList;
	 * 
	 * }
	 */

}
