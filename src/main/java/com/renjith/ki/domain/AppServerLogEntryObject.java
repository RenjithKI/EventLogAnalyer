package com.renjith.ki.domain;

/**
 * @author Renjith
 *
 */
public class AppServerLogEntryObject {

	private String id;
	private String state;
	
	private String type;	
	private String host;
	
	private long timestamp;

	public String getId() {
		return id;
	}

	public String getState() {
		return state;
	}

	public String getType() {
		return type;
	}

	public String getHost() {
		return host;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "EventObject2 [id=" + id + ", state=" + state + ", type=" + type + ", host=" + host + ", timestamp="
				+ timestamp + "]";
	}
	
}