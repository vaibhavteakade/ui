package com.app.rest;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.app.ClusterDTO;
import com.app.DatacenterDTO;
import com.app.DatastoreDTO;
import com.app.HostDTO;
import com.app.UserDTO;
import com.app.VimDTO;
import com.app.cache.CacheManager;
import com.app.cache.Constants;
import com.app.cache.Utility;
import com.app.inventory.Inventory;
import com.app.inventory.InventoryLlist;
import com.app.vim.VimService;
import com.google.gson.Gson;
import com.vmware.vim25.mo.ServiceInstance;
@Path("/appData")
public class AppData {
	
	@GET
	@Path("/mock/{value}")
	public String setMock(@PathParam("value")Boolean value) throws Exception{
		Gson gson = new Gson();
		Utility.USE_MOCK = value;
		try {
			CacheManager.getInstance().resetCache();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return Utility.USE_MOCK + "";
	}
	
	@GET
	@Path("/dc/{value}")
	public String seDcd(@PathParam("value")String value) throws Exception{
		Gson gson = new Gson();
		value = Utility.cleanParam(value);
		CacheManager manager;
		List<DatacenterDTO> dcs  = null;;
		try {
			manager = CacheManager.getInstance();
			dcs = manager.getAllDC();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		DatacenterDTO dto = new DatacenterDTO();
		for (DatacenterDTO datacenterDTO : dcs) {
			if(datacenterDTO.getMoid().equalsIgnoreCase(value))
				dto = datacenterDTO;
		}
		return gson.toJson(dto);
	}
	
	@GET
	@Path("/vms")
	public String getVms() throws Exception{
		Gson gson = new Gson();
		CacheManager manager;
		List<VimDTO> vims = null;
		try {
			manager = CacheManager.getInstance();
			vims = manager.getAllVm();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return gson.toJson(vims);
	}
	
	@GET
	@Path("/host")
	public String getHosts() throws Exception{
		Gson gson = new Gson();
		
		CacheManager manager;
		List<HostDTO> vims;
		try {
			manager = CacheManager.getInstance();
			vims = manager.getAllHost();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	
		return gson.toJson(vims);
	}
	
	@GET
	@Path("/ds")
	public String getDss() throws Exception{
		Gson gson = new Gson();
		CacheManager manager;
		List<DatastoreDTO> ds = null;
		try {
			manager = CacheManager.getInstance();
			ds = manager.getAllDatastore();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	
		return gson.toJson(ds);
	}
	
	@GET
	@Path("/dc")
	public String getDCs() throws Exception{
		Gson gson = new Gson();
		CacheManager manager = null;
		List<DatacenterDTO> dcs;
		try {
			manager = CacheManager.getInstance();
			dcs = manager.getAllDC();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return gson.toJson(dcs);
	}
	
	@GET
	@Path("/cluster")
	public String getClusters() throws Exception{
		Gson gson = new Gson();
		CacheManager manager;
		List<ClusterDTO> clusters;
		try {
			manager = CacheManager.getInstance();
			clusters = manager.getAllCluster();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
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
	
	@GET
	@Path("/count")
	public String getVcCount(){
		Gson gson = new Gson();
		try {
			return gson.toJson(CacheManager.getInstance().getObjectCount());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public String login(UserDTO userDTO) throws Exception{
		Gson gson = new Gson();
		System.out.println(userDTO.toString());
		Constants.VC_PASS = userDTO.getPassword();
		Constants.VC_UNAME = userDTO.getUserName();
		Constants.VC_URL = userDTO.getVcUrl();
		VimService.resetInstance();
		ServiceInstance instance;
		try {
			instance = VimService.getServiceInstance();
		} catch (Exception e) {
			System.out.println("Complete with fault");
			userDTO.setAuthenticated("Invalid Username passowrd");
			userDTO.setPassword("");
			return gson.toJson(userDTO);
		}
		
		if(instance == null)
		  return "false";
		
		try {
			CacheManager.getInstance().resetCache();
		} catch (Exception e) {
			//
		}
		Utility.USE_MOCK = false;
		System.out.println("Complete with going live");
		userDTO.setAuthenticated("true");
		userDTO.setPassword("");
		return gson.toJson(userDTO);
	}
	
//	public static void main(String[] args){
//		UserDTO dto = new UserDTO();
//		dto.setPassword("vmware");
//		dto.setAuthenticated("false");
//		dto.setUserName("root");
//		dto.setVcUrl("cs002-vc");
//		Gson gson = new Gson();
//		System.out.println(gson.toJson(dto));
//	}
}
