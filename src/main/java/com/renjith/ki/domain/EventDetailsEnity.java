package com.renjith.ki.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * @author Renjith
 *
 */
@Entity
public class EventDetailsEnity {
	
	@Id
	private String eventid;
	private long eventDuration;
	
	@Column(nullable = true)
	private String type;
	
	@Column(nullable = true)	
	private String host;	
	private boolean alert;		
	
	public String getEventid() {
		return eventid;
	}
	public void setEventid(String eventid) {
		this.eventid = eventid;
	}
	public long getEventDuration() {
		return eventDuration;
	}
	public void setEventDuration(long eventDuration) {
		this.eventDuration = eventDuration;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public boolean isAlert() {
		return alert;
	}
	public void setAlert(boolean alert) {
		this.alert = alert;
	}
	@Override
	public String toString() {
		return "EventDetailsEnity [eventid=" + eventid + ", eventDuration=" + eventDuration + ", type=" + type
				+ ", host=" + host + ", alert=" + alert + "]";
	}	
}