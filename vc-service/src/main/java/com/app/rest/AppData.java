package com.app.rest;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.app.ClusterDTO;
import com.app.DatacenterDTO;
import com.app.DatastoreDTO;
import com.app.HostDTO;
import com.app.VimDTO;
import com.app.cache.CacheManager;
import com.app.cache.Utility;
import com.app.inventory.Inventory;
import com.app.inventory.InventoryLlist;
import com.google.gson.Gson;
@Path("/appData")
public class AppData {
	
	@GET
	@Path("/mock/{value}")
	public boolean setMock(@PathParam("value")Boolean value){
		Gson gson = new Gson();
		Utility.USE_MOCK = value;
		return Utility.USE_MOCK;
	}
	
	@GET
	@Path("/vms")
	public String getVms(){
		Gson gson = new Gson();
		CacheManager manager = CacheManager.getInstance();
		List<VimDTO> vims = manager.getAllVm();
		return gson.toJson(vims);
	}
	
	@GET
	@Path("/host")
	public String getHosts(){
		Gson gson = new Gson();
		CacheManager manager = CacheManager.getInstance();
		List<HostDTO> vims = manager.getAllHost();
		return gson.toJson(vims);
	}
	
	@GET
	@Path("/ds")
	public String getDss(){
		Gson gson = new Gson();
		CacheManager manager = CacheManager.getInstance();
		List<DatastoreDTO> ds = manager.getAllDatastore();
		return gson.toJson(ds);
	}
	
	@GET
	@Path("/dc")
	public String getDCs(){
		Gson gson = new Gson();
		CacheManager manager = CacheManager.getInstance();
		List<DatacenterDTO> dcs = manager.getAllDC();
		return gson.toJson(dcs);
	}
	
	@GET
	@Path("/cluster")
	public String getClusters(){
		Gson gson = new Gson();
		CacheManager manager = CacheManager.getInstance();
		List<ClusterDTO> clusters = manager.getAllCluster();
		return gson.toJson(clusters);
	}
	
	@GET
	@Path("/inventory")
	public String getInventory() throws RemoteException, MalformedURLException{
		Gson gson = new Gson();
		Inventory inventory = new Inventory();
		InventoryLlist llist = inventory.getInventory();
		return gson.toJson(llist);
	}
	
	public static void main(String[] args){
		AppData appData = new AppData();
		System.out.println(appData.getVms());
	}
}
