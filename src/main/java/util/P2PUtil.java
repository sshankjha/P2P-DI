package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import rs.RSClient;

public class P2PUtil {

	final static Logger logger = Logger.getLogger(P2PUtil.class);

	public static String getLocalIpAddress() throws UnknownHostException {
		String address = Inet4Address.getLocalHost().getHostAddress();
		return address;
	}

	public static String getCookieFileName() throws UnknownHostException {
		return GlobalConstants.COOKIE + "_" + getLocalIpAddress();
	}

	// Code taken from :
	// https://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
	public static int getCookieFromFile() throws UnknownHostException {
		int cookie = 0;
		String cookieFileName = getCookieFileName();
		try (BufferedReader br = new BufferedReader(new FileReader(cookieFileName))) {

			String sCurrentLine;

			sCurrentLine = br.readLine();
			cookie = Integer.parseInt(sCurrentLine);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cookie;
	}

	public static void setCookieInFile() throws UnknownHostException {
		int cookie = RSClient.cookie;
		String cookieFileName = getCookieFileName();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(cookieFileName))) {

			bw.write(String.valueOf(cookie));

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

}
