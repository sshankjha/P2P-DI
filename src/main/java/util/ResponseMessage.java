package util;

import java.util.Map.Entry;

public class ResponseMessage extends Message {
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(getProtocol() + "/" + getVersion() + " " + status + "\r\n");
		for (Entry<String, String> entry : getHeaders().entrySet()) {
			result.append(entry.getKey() + ": " + entry.getValue() + "\r\n");
		}
		result.append("\r\n");
		result.append(getData());
		return result.toString();
	}
}
