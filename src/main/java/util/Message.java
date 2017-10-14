package util;

import java.util.HashMap;
import java.util.Map;

public class Message {
	String protocol;
	String version;
	Map<String, String> headers = new HashMap<String, String>();
	String hostname;
	String data = "";

	public String getHeader(String key) {
		String value = null;
		if (headers.containsKey(key)) {
			value = headers.get(key);
		}
		return value;
	}
}
