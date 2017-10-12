package util;

import java.util.Date;

public class Peer {
	int cookie;
	String hostname;
	int portNumber;
	// Active-Inactive Flag
	Boolean status;
	int TTL;
	Date lastRegistrationTime;

	public Peer() {
		super();
	}
	// TODO Override Equals Method

}
