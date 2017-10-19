package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RFCIndex {
	List<RFC> ownRFCList;
	List<RFC> peerRFCList;

	public List<RFC> getOwnRFCList() {
		return ownRFCList;
	}

	public void setOwnRFCList(List<RFC> ownRFCList) {
		this.ownRFCList = ownRFCList;
	}

	public List<RFC> getPeerRFCList() {
		return peerRFCList;
	}

	public void setPeerRFCList(List<RFC> peerRFCList) {
		this.peerRFCList = peerRFCList;
	}

	public RFCIndex() {
		ownRFCList = Collections.synchronizedList(new ArrayList<RFC>());
		peerRFCList = Collections.synchronizedList(new ArrayList<RFC>());
	}
	// TODO Add Methods to implement combine, search, decrease TTL
}
