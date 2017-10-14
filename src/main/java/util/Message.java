package util;

import java.util.HashMap;
import java.util.Map;

public class Message {
	String protocol;
	String version;
	Map<String, String> headers = new HashMap<String, String>();
	String hostname;
	String data = "";
}
