package util;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class NetworkUtils {

	private static NetworkUtils instance;
	private String hostname;

	private NetworkUtils() {
		super();
	}

	public static synchronized NetworkUtils getInstance() throws UnknownHostException {
		if (instance == null) {
			instance = new NetworkUtils();
			instance.hostname = Inet4Address.getLocalHost().getHostAddress();
		}
		return instance;
	}

	public static String getLocalAddress() throws UnknownHostException {
		return NetworkUtils.getInstance().hostname;
	}
}
