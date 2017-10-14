package util;

import java.util.Map.Entry;

public class RequestMessage extends Message {
	String method;

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(method + " " + protocol + "/" + version + "\r\n");
		for (Entry<String, String> entry : headers.entrySet()) {
			result.append(entry.getKey() + ": " + entry.getValue() + "\r\n");
		}
		result.append("\r\n");
		result.append(data);
		return result.toString();
	}
}
