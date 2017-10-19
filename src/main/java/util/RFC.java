package util;

import java.util.Objects;

public class RFC {

	int RFCNumber;
	String title;
	String host;
	int TTL = 7200;

	public int getRFCNumber() {
		return RFCNumber;
	}

	public void setRFCNumber(int rFCNumber) {
		RFCNumber = rFCNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public RFC(int rfcNumber, String title, String host) {
		super();
		this.RFCNumber = rfcNumber;
		this.title = title;
		this.host = host;
	}

	@Override
	public int hashCode() {
		return Objects.hash(RFCNumber, title);
	}

	@Override
	public boolean equals(Object otherObject) {
		if (otherObject == this)
			return true;
		if (!(otherObject instanceof RFC)) {
			return false;
		}
		RFC rfcObj = (RFC) otherObject;
		return RFCNumber == rfcObj.RFCNumber && Objects.equals(title, rfcObj.title);
	}
}
