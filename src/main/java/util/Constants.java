package util;

import java.io.File;

public final class Constants {

	public static final int RS_PORT = 65432;// RS Server Port
	// RS Server IP
	public static final String RS_IP = NetworkUtils.getLocalAddress();
	public static final String COOKIE = "cookie";
	public static final String PROTOCOL_VERSION = "P2P-DI/1.0";
	public static final String CR_LF = "\r\n";
	public static final String HEADER_COOKIE = "COOKIE";
	public static final String HEADER_RFCPORT = "RFCPORT";
	public static final int STATUS_OK = 200;
	public static final int STATUS_ERROR = 400;
	public static final String METHOD_REGISTER = "Register";
	public static final String METHOD_PQUERY = "PQuery";
	public static final String METHOD_KEEPALIVE = "KeepAlive";
	public static final String METHOD_LEAVE = "Leave";
	public static final String METHOD_RFCQUERY = "RFCQuery";
	public static final String METHOD_GETRFC = "GetRFC";
	public static final String HEADER_RFCNUMBER = "RFCNUMBER";
	public static final String SEPARATOR = "_";
	public static final String RESOURCE_PATH = "main" + File.separator + "resources" + File.separator;
	public static final String RFC_PATH = "rfc" + File.separator;
	public static final String RFC_FILE_EXTENSION = ".txt";

}
