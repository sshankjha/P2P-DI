package util;

import java.util.Map.Entry;

public class ResponseMessage extends Message {
	String status;

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(status + " " + protocol + "/" + version + "\r\n");
		for (Entry<String, String> entry : headers.entrySet()) {
			result.append(entry.getKey() + ": " + entry.getValue() + "\r\n");
		}
		result.append("\r\n");
		result.append(data);
		return result.toString();
	}
}
