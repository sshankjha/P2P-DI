package util;

import java.util.Map;

public class RequestMessage {
	String protocol;
	String version;
	String method;
	Map<String, String> headers;
	String hostname;
	String data;
}
