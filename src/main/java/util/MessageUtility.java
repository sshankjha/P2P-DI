package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

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

	private static void setResponseStatusAndVersion(BufferedReader responseStream, ResponseMessage response)
			throws IOException {
		String method = responseStream.readLine();
		String[] values = method.split(" ");
		response.protocol = values[0].split("/")[0];
		response.version = values[0].split("/")[1];
		response.status = values[1];
	}

	private static HashMap<String, String> extractHeaders(BufferedReader request) throws IOException {
		HashMap<String, String> headers = new HashMap<String, String>();
		String currentLine;
		while ((currentLine = request.readLine()) != null && !currentLine.isEmpty()) {
			String[] values = currentLine.split(" ");
			headers.put(values[0], values[1]);
		}
		return headers;
	}

	private static String extractData(BufferedReader request) throws IOException {
		String currentLine;
		StringBuffer data = new StringBuffer("");
		while ((currentLine = request.readLine()) != null && !currentLine.isEmpty()) {
			data.append(currentLine);
		}
		return data.toString();
	}

	public static RequestMessage extractRequest(BufferedReader requestStream) {
		RequestMessage request = new RequestMessage();
		try {
			setRequestMethodAndVersion(requestStream, request);
			request.headers = extractHeaders(requestStream);
			request.data = extractData(requestStream);
		} catch (Exception e) {
			logger.error("Error parsing request");
			logger.error(e);
			request = null;
		}
		return request;
	}

	public static ResponseMessage extractResponse(BufferedReader responseStream) {
		ResponseMessage response = new ResponseMessage();
		try {
			setResponseStatusAndVersion(responseStream, response);
			response.headers = extractHeaders(responseStream);
			response.data = extractData(responseStream);
		} catch (Exception e) {
			logger.error("Error parsing request");
			logger.error(e);
			response = null;
		}
		return response;
	}

}
