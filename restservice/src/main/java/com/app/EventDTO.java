package com.app;

import java.util.Date;

public class EventDTO {
	public String event_id;
	public Date creationTime;
	public String target;
	
	public enum TYPES {DASHBOARD,VOLUME,TASK,DATASTORE};
}
