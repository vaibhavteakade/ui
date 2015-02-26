package com.app;

import com.vmware.vim25.mo.Datastore;

public class DatastoreDTO {

	private String name;
	private String accessible;
	private long capacity;
	private long freeSpace;
	private String ds_type;
	private String url;
	private String type;
	private String modid;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccessible() {
		return accessible;
	}
	public void setAccessible(String accessible) {
		this.accessible = accessible;
	}
	public long getCapacity() {
		return capacity;
	}
	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}
	public long getFreeSpace() {
		return freeSpace;
	}
	public void setFreeSpace(long freeSpace) {
		this.freeSpace = freeSpace;
	}
	public String getDs_type() {
		return ds_type;
	}
	public void setDs_type(String ds_type) {
		this.ds_type = ds_type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getModid() {
		return modid;
	}
	public void setModid(String modid) {
		this.modid = modid;
	}
	
	
	
	
//	public void test(){
//		Datastore datastore = new Datastore(null, null);
//		
//		datastore.getName();
//		datastore.getSummary().accessible;
//		datastore.getSummary().capacity;
//		datastore.getSummary().freeSpace;
//		datastore.getSummary().type;
//		datastore.getSummary().url;
//		
//	}
}
