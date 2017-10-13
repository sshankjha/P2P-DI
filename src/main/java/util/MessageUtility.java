package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class MessageUtility {
	final static Logger logger = Logger.getLogger(MessageUtility.class);

	public static String createRegisterRequest() {
		return null;
	}

	private static void setRequestMethodAndVersion(BufferedReader requestStream, RequestMessage request)
			throws IOException {
		String method = requestStream.readLine();
		String[] values = method.split(" ");
		request.method = values[0];
		request.protocol = values[1].split("/")[0];
		request.version = values[1].split("/")[1];
	}

	private static HashMap<String, String> getRequestHeaders(BufferedReader request) throws IOException {
		HashMap<String, String> headers = new HashMap<String, String>();
		String currentLine;
		while ((currentLine = request.readLine()) != null && !currentLine.isEmpty()) {
		}
		return headers;
	}

	private static String getRequestData(BufferedReader request) {
		return null;
	}

	public static RequestMessage extractRequest(BufferedReader requestStream) {
		RequestMessage request = new RequestMessage();
		try {
			setRequestMethodAndVersion(requestStream, request);
			request.headers = getRequestHeaders(requestStream);
			request.data = getRequestData(requestStream);
		} catch (Exception e) {
			logger.error("Error parsing request");
			logger.error(e);
			request = null;
		}
		return request;
	}

}
