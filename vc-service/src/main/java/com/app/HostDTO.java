package com.app;

import com.vmware.vim25.mo.HostSystem;

public class HostDTO {

	private String type;
	private String moid;
	private String name;
	private String iscsiSupported;
	private String shutdownSupported;
	private String bootTime;
	private String connectionState;
	private String managementServerIp;
	private String maxEVCModeKey;
	private String powerState;
	
	
	
	public String getPowerState() {
		return powerState;
	}
	public void setPowerState(String powerState) {
		this.powerState = powerState;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMoid() {
		return moid;
	}
	public void setMoid(String moid) {
		this.moid = moid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIscsiSupported() {
		return iscsiSupported;
	}
	public void setIscsiSupported(String iscsiSupported) {
		this.iscsiSupported = iscsiSupported;
	}
	public String getShutdownSupported() {
		return shutdownSupported;
	}
	public void setShutdownSupported(String shutdownSupported) {
		this.shutdownSupported = shutdownSupported;
	}
	public String getBootTime() {
		return bootTime;
	}
	public void setBootTime(String bootTime) {
		this.bootTime = bootTime;
	}
	public String getConnectionState() {
		return connectionState;
	}
	public void setConnectionState(String connectionState) {
		this.connectionState = connectionState;
	}
	public String getManagementServerIp() {
		return managementServerIp;
	}
	public void setManagementServerIp(String managementServerIp) {
		this.managementServerIp = managementServerIp;
	}
	public String getMaxEVCModeKey() {
		return maxEVCModeKey;
	}
	public void setMaxEVCModeKey(String maxEVCModeKey) {
		this.maxEVCModeKey = maxEVCModeKey;
	}
	
	
	
}
