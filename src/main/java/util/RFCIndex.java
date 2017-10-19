package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RFCIndex {
	List<RFC> ownRFCList;
	List<RFC> peerRFCList;

	public RFCIndex() {
		ownRFCList = Collections.synchronizedList(new ArrayList<RFC>());
		peerRFCList = Collections.synchronizedList(new ArrayList<RFC>());
	}
	// TODO Add Methods to implement combine, search, decrease TTL
}
