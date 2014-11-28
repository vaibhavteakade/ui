package com.app.rest;

import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.app.DashboardDTO;
import com.app.VolumeDTO;
import com.app.cache.Manager;
import com.google.gson.Gson;

@Path("/dashboard")
public class Dashboard {
	@GET
	@Path("/info")
	//@Produces(MediaType.APPLICATION_JSON)
	public String getDashboardDetails() throws ExecutionException {
		Gson gson = new Gson();
		Manager manager = Manager.getInstance();
		DashboardDTO dto = manager.getdash();
		return gson.toJson(dto);
	}
	
	@GET
	@Path("/vol")
	public String getVolumes(){
		Gson gson = new Gson();
		Manager manager = Manager.getInstance();
		Set<VolumeDTO> volumes = manager.getAllVolumes();
		return gson.toJson(volumes);
	}
	
	@GET
	@Path("/vol/{name}")
	public String getVolumes(@PathParam("name")String name){
		Gson gson = new Gson();
		VolumeDTO dto = null;
			Manager manager = Manager.getInstance();
			Set<VolumeDTO> volumes = manager.getAllVolumes();
			for(VolumeDTO vDto : volumes){
				if(vDto.name.equalsIgnoreCase(name))
				dto = vDto;
			}
		if(dto==null)
			return "volume not found";
		
		return gson.toJson(dto);
	}
	
	@GET
	@Path("/ds")
	public String getDatastores(){
		return null;
	}
	
	@GET
	@Path("/ds/{name}")
	public String getDatastores(@PathParam("name")String name){
		return null;
	}
	
	public static void main(String[] args){
		
	}
}
