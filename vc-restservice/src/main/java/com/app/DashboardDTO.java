package com.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.app.cache.CacheManager;
import com.app.cache.Utility;
import com.google.gson.Gson;

public class DashboardDTO {
	public Integer memory_total;
	public Integer memory_free;
	public Integer memory_used;
	public Integer handlers;
	public Integer threads;
	public Integer processes;
	public Date up_time;
	public Cpu cpu;
	public String productVersion;
	public String serialNumber;
	@Override
	public String toString() {
		return "DashboardDTO [memory_total=" + memory_total + ", memory_free="
				+ memory_free + ", memory_used=" + memory_used + ", handlers="
				+ handlers + ", threads=" + threads + ", processes="
				+ processes + ", up_time=" + up_time + ", cpu=" + cpu
				+ ", productVersion=" + productVersion + ", serialNumber="
				+ serialNumber + "]";
	}
	public static void main (String[] args){
		
		// Dashboard generation code
		DashboardDTO dashboardDTO = new DashboardDTO();
		Cpu cpu = new Cpu();
		cpu.overHead = 58;
		cpu.usage = 74;
		dashboardDTO.cpu = cpu;
		dashboardDTO.handlers = 159753;
		dashboardDTO.memory_free = 60;
		dashboardDTO.memory_total = 115;
		dashboardDTO.memory_used = 115-60;
		dashboardDTO.processes = 15825;
		dashboardDTO.productVersion = "1.1.5";
		dashboardDTO.serialNumber = "XMTR-58958-CTX";
		dashboardDTO.threads = 587456;
		dashboardDTO.up_time = new Date();
		
		
//		Utility.init();
//		Manager manager = Manager.getInstance();
//		try {
//			System.out.println(manager.getdash().toString());
//		} catch (ExecutionException e) {
//			e.printStackTrace();
		
		//VOLUME GENERATION CODE
		List<VolumeDTO> list = new ArrayList<VolumeDTO>();
		List<DatastoreDTO> dsList = new ArrayList<DatastoreDTO>();
		Random rand = new Random();
		for(int i = 1 ; i < 36;i++){
			VolumeDTO dto = new VolumeDTO();
			dto.dsCount = rand.nextInt(25) + 5;
			dto.size = rand.nextInt(500) + 140;
			dto.freeSize = rand.nextInt((int) (dto.size - 10)) + 25;
			dto.id = i+1+"";
			dto.name = "vol_" + (i + 1);
			dto.status = "ONLINE";
			dto.type = "ssd";
			dto.usedSize = dto.size - dto.freeSize;
			List<DatastoreDTO> dslst = new ArrayList<DatastoreDTO>();
			int ds = rand.nextInt(6) + 1;
			for(int j=1;j<ds;j++){
				DatastoreDTO dsdto = new DatastoreDTO();
				dsdto.vmCount = rand.nextInt(25) + 5;
				dsdto.capacity = rand.nextInt(500) + 140;
				dsdto.free = rand.nextInt((int) (dsdto.capacity - 10)) + 25;
				dsdto.id = "ds_"+UUID.randomUUID().toString();
				dsdto.name = "ds_"+dsdto.id.substring(dsdto.id.length() -13);
				dsdto.isDedup = true;
				dsdto.type = "ssd";
				dsdto.used = dsdto.capacity - dsdto.free;
				dsList.add(dsdto);
				dslst.add(dsdto);
			}
			dto.datastores = dslst;
			list.add(dto);
		}
		for(int k=1;k<35;k++){
			if(k%6==0)
				list.get(k).status = "OFFLINE";
		}
		
	
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(dsList));
		System.out.println(gson.toJson(list));
		System.out.println(gson.toJson(dashboardDTO));
//		}
	}
}
