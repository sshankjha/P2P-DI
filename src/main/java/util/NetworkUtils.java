package util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class NetworkUtils {

	private static NetworkUtils instance;
	private String hostname;

	private NetworkUtils() {
		super();
	}

	public static synchronized NetworkUtils getInstance() throws UnknownHostException {
		if (instance == null) {
			instance = new NetworkUtils();
			try {
				String interfaceName = "eth0";
				NetworkInterface networkInterface;
				networkInterface = NetworkInterface.getByName(interfaceName);
				Enumeration<InetAddress> inetAddress = networkInterface.getInetAddresses();
				InetAddress currentAddress;
				currentAddress = inetAddress.nextElement();
				while (inetAddress.hasMoreElements()) {
					currentAddress = inetAddress.nextElement();
					if (currentAddress instanceof Inet4Address && !currentAddress.isLoopbackAddress()) {
						instance.hostname = currentAddress.toString().replaceAll("/", "");
					}
				}
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return instance;
	}

	public static String getLocalAddress() {
		try {
			return NetworkUtils.getInstance().hostname;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
