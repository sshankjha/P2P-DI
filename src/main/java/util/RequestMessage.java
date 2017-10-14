package util;

import java.util.Map.Entry;

public class RequestMessage extends Message {
	private String method;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(method + " " + getProtocol() + "/" + getVersion() + "\r\n");
		for (Entry<String, String> entry : getHeaders().entrySet()) {
			result.append(entry.getKey() + ": " + entry.getValue() + "\r\n");
		}
		result.append("\r\n");
		result.append(getData());
		return result.toString();
	}
}
