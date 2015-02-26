package com.app.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.app.VimDTO;
import com.app.cache.CacheManager;
import com.google.gson.Gson;
@Path("/appData")
public class AppData {
	@GET
	@Path("/vms")
	public String getVms(){
		Gson gson = new Gson();
		CacheManager manager = CacheManager.getInstance();
		List<VimDTO> vims = manager.getAllVm();
		return gson.toJson(vims);
	}
	
	public static void main(String[] args){
		AppData appData = new AppData();
		appData.getVms();
	}
}
