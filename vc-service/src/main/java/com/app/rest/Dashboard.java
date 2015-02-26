package com.app.rest;

import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.app.DashboardDTO;
import com.app.cache.CacheManager;
import com.google.gson.Gson;

@Path("/dashboard")
public class Dashboard {
//	@GET
//	@Path("/info")
//	//@Produces(MediaType.APPLICATION_JSON)
//	public String getDashboardDetails() throws ExecutionException {
//		Gson gson = new Gson();
//		CacheManager manager = CacheManager.getInstance();
//		DashboardDTO dto = manager.getdash();
//		return gson.toJson(dto);
//	}
//	
//	@GET
//	@Path("/vol")
//	public String getVolumes(){
//		Gson gson = new Gson();
//		CacheManager manager = CacheManager.getInstance();
//		Set<VolumeDTO> volumes = manager.getAllVolumes();
//		return gson.toJson(volumes);
//	}
//	
//	@GET
//	@Path("/vol/{name}")
//	public String getVolumes(@PathParam("name")String name){
//		Gson gson = new Gson();
//		VolumeDTO dto = null;
//			CacheManager manager = CacheManager.getInstance();
//			Set<VolumeDTO> volumes = manager.getAllVolumes();
//			for(VolumeDTO vDto : volumes){
//				if(vDto.name.equalsIgnoreCase(name))
//				dto = vDto;
//			}
//		if(dto==null)
//			return "volume not found";
//		
//		return gson.toJson(dto);
//	}
//	/////////////////////////////////DATASTORE/////////////////////////////////////////
//	/**
//	 * get all datastores
//	 * @return
//	 */
//	@GET
//	@Path("/ds")
//	public String getDatastores(){
//		Gson gson = new Gson();
//		CacheManager manager = CacheManager.getInstance();
//		Set<DatastoreDTO> dsLst = manager.getAllDatastore();
//		return gson.toJson(dsLst);
//	}
//	
//	/**
//	 * get datastore by name
//	 * @param name
//	 * @return
//	 */
//	@GET
//	@Path("/ds/{name}")
//	public String getDatastores(@PathParam("name")String name){
//		Gson gson = new Gson();
//		DatastoreDTO dto = null;
//			CacheManager manager = CacheManager.getInstance();
//			Set<DatastoreDTO> dsLst = manager.getAllDatastore();
//			for(DatastoreDTO dsDto : dsLst){
//				if(dsDto.name.equalsIgnoreCase(name))
//				dto = dsDto;
//			}
//		if(dto==null)
//			return "datastore not found";
//		
//		return gson.toJson(dto);
//	}
//	
//	@POST
//	@Path("/ds/create")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public String createDS(DatastoreDTO ds){
//		Gson gson = new Gson();
//		System.out.println(ds.toString());
//		DatastoreDTO dto = null;
//			CacheManager manager = CacheManager.getInstance();
//			Set<DatastoreDTO> dsLst = manager.getAllDatastore();
//			for(DatastoreDTO dsDto : dsLst){
//				if(dsDto.name.equalsIgnoreCase(ds.name))
//				dto = dsDto;
//			}
//		if(dto!=null)
//			return "Datastore name already exists...";
//		
//		return gson.toJson(manager.reconfigureDs(ds));
//	}
//	
//	@POST
//	@Path("/ds/reconfig")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public String reconfigureDS(DatastoreDTO ds){
//		Gson gson = new Gson();
//		CacheManager manager = CacheManager.getInstance();
//		return gson.toJson(manager.reconfigureDs(ds));
//	}
//	
//	/////////////////////////////END DATASTORE ///////////////////////////////////////////
//	@GET
//	@Path("/cpu/info")
//	public String cpuInfo(){
//		return null;
//	}
//	
//	public static void main(String[] args){
//		
//	}
}
