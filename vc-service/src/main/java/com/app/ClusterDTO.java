package com.app;

import java.util.ArrayList;

import com.vmware.vim25.mo.ClusterComputeResource;

public class ClusterDTO {

	private String type;
	private String moid;
	private String name;
	private int totalCpu;
	private long totalMemory;
	private int numHosts;
	
	private ArrayList<DatastoreDTO> datastores = new ArrayList<DatastoreDTO>();
	private ArrayList<HostDTO> hosts = new ArrayList<HostDTO>();
	private ArrayList<VimDTO> vms = new ArrayList<VimDTO>();
	
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
	public int getTotalCpu() {
		return totalCpu;
	}
	public void setTotalCpu(int totalCpu) {
		this.totalCpu = totalCpu;
	}
	public long getTotalMemory() {
		return totalMemory;
	}
	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}
	public int getNumHosts() {
		return numHosts;
	}
	public void setNumHosts(int numHosts) {
		this.numHosts = numHosts;
	}
	public ArrayList<DatastoreDTO> getDatastores() {
		return datastores;
	}
	public void setDatastores(ArrayList<DatastoreDTO> datastores) {
		this.datastores = datastores;
	}
	public ArrayList<HostDTO> getHosts() {
		return hosts;
	}
	public void setHosts(ArrayList<HostDTO> hosts) {
		this.hosts = hosts;
	}
	public ArrayList<VimDTO> getVms() {
		return vms;
	}
	public void setVms(ArrayList<VimDTO> vms) {
		this.vms = vms;
	}
	
	
	
	
	
	
//	public static void main(){
//		ClusterComputeResource cluster = new ClusterComputeResource(null, null);
//		cluster.getName();
//		cluster.getSummary().totalCpu;
//		cluster.getSummary().totalMemory;
//		cluster.getSummary().numHosts;
//		
//	}
}
