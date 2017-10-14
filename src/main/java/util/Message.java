package util;

import java.util.HashMap;
import java.util.Map;

public class Message {
	private String protocol;
	private String version;
	private Map<String, String> headers = new HashMap<String, String>();
	private String hostname;
	private String data = "";

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHeader(String key) {
		String value = null;
		if (headers.containsKey(key)) {
			value = headers.get(key);
		}
		return value;
	}
}
