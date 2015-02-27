package com.app;

import java.util.ArrayList;
import java.util.List;

import com.vmware.vim25.mo.Datacenter;

public class DatacenterDTO {

	private String name;
	private String type;
	private String moid;
	private ArrayList<TaskDeatils> taskDeatils = new ArrayList<TaskDeatils>();
	private ArrayList<ClusterDTO> clusterList = new ArrayList<ClusterDTO>();
	private ArrayList<HostDTO> hostList = new ArrayList<HostDTO>();
	private ArrayList<DatastoreDTO> dsList = new ArrayList<DatastoreDTO>();
	private ArrayList<VimDTO> vmList = new ArrayList<VimDTO>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public ArrayList<TaskDeatils> getTaskDeatils() {
		return taskDeatils;
	}
	public void setTaskDeatils(ArrayList<TaskDeatils> taskDeatils) {
		this.taskDeatils = taskDeatils;
	}
	public ArrayList<ClusterDTO> getClusterList() {
		return clusterList;
	}
	public void setClusterList(ArrayList<ClusterDTO> clusterList) {
		this.clusterList = clusterList;
	}
	public ArrayList<HostDTO> getHostList() {
		return hostList;
	}
	public void setHostList(ArrayList<HostDTO> hostList) {
		this.hostList = hostList;
	}
	public ArrayList<DatastoreDTO> getDsList() {
		return dsList;
	}
	public void setDsList(ArrayList<DatastoreDTO> dsList) {
		this.dsList = dsList;
	}
	public ArrayList<VimDTO> getVmList() {
		return vmList;
	}
	public void setVmList(ArrayList<VimDTO> vmList) {
		this.vmList = vmList;
	}
	
	
	
//	private void test(){
//		Datacenter datacenter = null;
//		datacenter.getName();
//		datacenter.getRecentTasks();
//		datacenter.getMOR().getType();
//		datacenter.getMOR().getVal();
//	}
}
