package util;

import java.util.Objects;

public class RFC {

	int RFCNumber;
	String host;
	int port;

	int TTL = 7200;

	public int getRFCNumber() {
		return RFCNumber;
	}

	public void setRFCNumber(int rFCNumber) {
		RFCNumber = rFCNumber;
	}

	public String getTitle() {
		return String.valueOf(RFCNumber) + Constants.RFC_FILE_EXTENSION;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public RFC() {
		super();
	}

	public RFC(int rfcNumber, String host, int port) {
		super();
		this.RFCNumber = rfcNumber;
		this.host = host;
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public int hashCode() {
		return Objects.hash(RFCNumber);
	}

	@Override
	public boolean equals(Object otherObject) {
		if (otherObject == this)
			return true;
		if (!(otherObject instanceof RFC)) {
			return false;
		}
		RFC rfcObj = (RFC) otherObject;
		return RFCNumber == rfcObj.RFCNumber;
	}
}
