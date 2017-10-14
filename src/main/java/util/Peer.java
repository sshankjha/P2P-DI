package util;

import java.util.Date;
import java.util.Objects;

public class Peer {
	private int cookie;
	private String hostname;
	// RFC Server
	private int portNumber;
	// Active-Inactive Flag
	private Boolean status;
	private int TTL = 7200;
	private Date lastRegistrationTime;

	public Peer() {
		super();
		status = true;
		lastRegistrationTime = new Date();
	}

	public int getCookie() {
		return cookie;
	}

	public void setCookie(int cookie) {
		this.cookie = cookie;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public int getTTL() {
		return TTL;
	}

	public void setTTL(int tTL) {
		TTL = tTL;
	}

	public Date getLastRegistrationTime() {
		return lastRegistrationTime;
	}

	public void setLastRegistrationTime(Date lastRegistrationTime) {
		this.lastRegistrationTime = lastRegistrationTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(hostname, cookie, portNumber);
	}

	@Override
	public boolean equals(Object otherObject) {
		if (otherObject == this)
			return true;
		if (!(otherObject instanceof Peer)) {
			return false;
		}
		Peer peer = (Peer) otherObject;
		return cookie == peer.cookie && Objects.equals(hostname, peer.hostname);
	}

}
