package util;

import java.util.Objects;

public class RFC {
	int RFCNumber;
	String title;
	String host;
	int TTL = 7200;

	public RFC() {
		super();
	}

	public RFC(int rfcNumber, String title) {
		super();
		this.RFCNumber = rfcNumber;
		this.title = title;
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
