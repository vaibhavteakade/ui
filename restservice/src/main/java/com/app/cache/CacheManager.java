package com.app.cache;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;

import com.app.DashboardDTO;
import com.app.DatastoreDTO;
import com.app.EventDTO;
import com.app.TaskDTO;
import com.app.VolumeDTO;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class CacheManager {
	
	private static CacheManager manager = null;
	private Gson gson;
	private GsonBuilder builder;
	private String jsonStr;
	private String dataRoot="D:\\tutorials\\trunck\\ui\\restservice\\src\\main\\resources";
	private static Logger logger = Logger.getLogger(CacheManager.class);
	
	/**
	 * Dashboard cache
	 */
	private LoadingCache<String, DashboardDTO> dashboardCache = null;
	/**
	 * volume cache
	 */
	private LoadingCache<String, VolumeDTO> volumeCache = null;
	/**
	 * volume cache
	 */
	private LoadingCache<String, DatastoreDTO> datastoreCache = null;
	/**
	 * volume cache
	 */
	private LoadingCache<String, Set<EventDTO>> eventCache = null;
	/**
	 * volume cache
	 */
	private LoadingCache<String, Set<TaskDTO>> taskCache = null;
	
	
	public static CacheManager getInstance(){
		if(manager==null){
			manager = new CacheManager();
		}
		return manager;
	}
	
	private CacheManager(){
		logger.info("initializing CacheManager");
		init();
		logger.info("CacheManager initialized");
	}

	private void init() {
		builder = new GsonBuilder();
		builder.setPrettyPrinting();
		gson = builder.create();
		initCacheLoader();
		
	}
	/**
	 * Initiate all loading cache with its load and removeListner
	 */
	private void initCacheLoader() {
		/**
		 * Cache to manage dashboard objects
		 */
		dashboardCache = CacheBuilder.newBuilder().maximumSize(Constants.MAX_SIZE)
				.removalListener(new RemovalListener<String, DashboardDTO>() {
					@Override
					public void onRemoval(
							RemovalNotification<String, DashboardDTO> paramRemovalNotification) {
						DashboardDTO d = null;
						try {
							d = getdash();
						} catch (ExecutionException e1) {
							e1.printStackTrace();
						}
						if (d != null ) {
							jsonStr = gson.toJson(d);
							try {
								Utility.writeJsonFile(dataRoot, Constants.DASHBOARD_JSON,
										jsonStr);
							} catch (FileNotFoundException e) {
								logger.error("Unable to write " + Constants.DASHBOARD_JSON
										+ " Reason :" + e);
							} catch (IOException e) {
								logger.error("Unable to write " + Constants.DASHBOARD_JSON
										+ " Reason :" + e);
							}
						} else {
							logger.error("No Cluster found to generate Cluster.json");
						}
					}
				}).build(new CacheLoader<String, DashboardDTO>() {
					@Override
					public DashboardDTO load(String ref) throws Exception {
						loadDashboardInfo();
						return dashboardCache.getIfPresent(ref);
					}
				});
		
		/**
		 * Cache to manage dashboard objects
		 */
		volumeCache = CacheBuilder.newBuilder().maximumSize(Constants.MAX_SIZE)
				.removalListener(new RemovalListener<String, VolumeDTO>() {

					@Override
					public void onRemoval(
							RemovalNotification<String, VolumeDTO> paramRemovalNotification) {
						Set<VolumeDTO> volumes = getAllVolumes();
						if (volumes != null && !volumes.isEmpty()) {
							jsonStr = gson.toJson(volumes);
							try {
								Utility.writeJsonFile(dataRoot, Constants.VOLUMES_JSON,
										jsonStr);
							} catch (FileNotFoundException e) {
								logger.error("Unable to write " + Constants.VOLUMES_JSON
										+ " Reason :" + e);
							} catch (IOException e) {
								logger.error("Unable to write " + Constants.VOLUMES_JSON
										+ " Reason :" + e);
							}
						} else {
							logger.error("Unable to generate volumse.json");
						}
					}
				}).build(new CacheLoader<String, VolumeDTO>() {
					@Override
					public VolumeDTO load(String ref) throws Exception {
						loadVolumes();
						return volumeCache.getIfPresent(ref);
					}
				});
		
		/**
		 * datastore cache
		 */
		datastoreCache = CacheBuilder.newBuilder().maximumSize(Constants.MAX_SIZE)
				.removalListener(new RemovalListener<String, DatastoreDTO>() {

					@Override
					public void onRemoval(
							RemovalNotification<String, DatastoreDTO> paramRemovalNotification) {
						Set<DatastoreDTO> volumes = getAllDatastore();
						if (volumes != null && !volumes.isEmpty()) {
							jsonStr = gson.toJson(volumes);
							try {
								Utility.writeJsonFile(dataRoot, Constants.DATASTORE_JSON,
										jsonStr);
							} catch (FileNotFoundException e) {
								logger.error("Unable to write " + Constants.DATASTORE_JSON
										+ " Reason :" + e);
							} catch (IOException e) {
								logger.error("Unable to write " + Constants.DATASTORE_JSON
										+ " Reason :" + e);
							}
						} else {
							logger.error("Unable to generate volumse.json");
						}
					}
				}).build(new CacheLoader<String, DatastoreDTO>() {
					@Override
					public DatastoreDTO load(String ref) throws Exception {
						loadDS();
						return datastoreCache.getIfPresent(ref);
					}
				});
	}
	
	/**
	 * @return dashboardinfo
	 * @throws ExecutionException 
	 */
	public DashboardDTO getdash() throws ExecutionException {
		logger.debug("Fetching dashboard from cache");
		if (dashboardCache.asMap().isEmpty())
			loadDashboardInfo();
		logger.debug("Fetched dashboard from cache");
		return dashboardCache.get("dash");
	}
	
	/**
	 * Load cluster Json String is used as key to cluster objects
	 */
	private void loadDashboardInfo() {
		dashboardCache.invalidateAll();
		logger.info("Fetching data from " + Constants.DASHBOARD_JSON);
		try {
			jsonStr = Utility
					.readJsonFile(this.dataRoot, Constants.DASHBOARD_JSON);
		} catch (FileNotFoundException e) {
			logger.error("Failed to read " + Constants.DASHBOARD_JSON
					+ " Reason :" + e);
		} catch (IOException e) {
			logger.error("Failed to read " + Constants.DASHBOARD_JSON
					+ " Reason :" + e);
		}
		if (jsonStr.isEmpty())
			return;
		logger.info("Fetched data from " + Constants.DASHBOARD_JSON);
		try {
			
			DashboardDTO dashboardDTO = gson.fromJson(jsonStr, DashboardDTO.class);
			dashboardCache.put("dash", dashboardDTO);
		} catch (Exception e) {
			logger.error("Unable to load clusters Reason :" + e);
		}
	}
	
	/////////////////////VOLUME/////////////////////////////////////////////////
	
	private void loadVolumes() {
		volumeCache.invalidateAll();
		Map<String, VolumeDTO> map = new HashMap<String, VolumeDTO>();
		logger.info("Fetching data from " + Constants.VOLUMES_JSON);
		try {
			jsonStr = Utility.readJsonFile(this.dataRoot, Constants.VOLUMES_JSON);
		} catch (FileNotFoundException e) {
			logger.error("Failed to read  " + Constants.VOLUMES_JSON + " Reason :"
					+ e);
		} catch (IOException e) {
			logger.error("Failed to read  " + Constants.VOLUMES_JSON + " Reason :"
					+ e);
		}
		if (jsonStr.isEmpty())
			return;
		logger.info("Fetched data from " + Constants.VOLUMES_JSON);
		try {
			Type listType = new TypeToken<ArrayList<VolumeDTO>>() {
			}.getType();
			List<VolumeDTO> nodeList = gson.fromJson(jsonStr, listType);
			for (VolumeDTO VolumeDTO : nodeList) {
				map.put(VolumeDTO.id, VolumeDTO);
			}
			volumeCache.putAll(map);
		} catch (Exception e) {
			logger.error("unable to load nodes Reason : " + e);
		}
	}
	
	/**
	 * gives all cached node objects
	 * @return
	 */
	public Set<VolumeDTO> getAllVolumes() {
		logger.debug("Fetching nodes from cache");
		if (volumeCache.asMap().isEmpty())
			loadVolumes();
		Set<VolumeDTO> nodes = new HashSet<VolumeDTO>();
		for (Map.Entry<String, VolumeDTO> entry : volumeCache.asMap().entrySet()) {
			nodes.add(entry.getValue());
		}
		logger.debug("Fetched volumes from cache");
		return nodes;
	}
	
	/////////////////////////////////Datastore//////////////////////////////////
	public Set<DatastoreDTO> getAllDatastore(){
		if (datastoreCache.asMap().isEmpty())
			loadDS();
		Set<DatastoreDTO> nodes = new HashSet<DatastoreDTO>();
		for (Map.Entry<String, DatastoreDTO> entry : datastoreCache.asMap().entrySet()) {
			nodes.add(entry.getValue());
		}
		logger.debug("Fetched volumes from cache");
		return nodes;
	}
	
	public DatastoreDTO reconfigureDs(DatastoreDTO dto){
		if(datastoreCache.asMap().isEmpty())
			loadDS();
		
		datastoreCache.put(dto.name, dto);
		return dto;
	}
	private void loadDS() {
		datastoreCache.invalidateAll();
		Map<String, DatastoreDTO> map = new HashMap<String, DatastoreDTO>();
		logger.info("Fetching data from " + Constants.DATASTORE_JSON);
		try {
			jsonStr = Utility.readJsonFile(this.dataRoot, Constants.DATASTORE_JSON);
		} catch (FileNotFoundException e) {
			logger.error("Failed to read  " + Constants.DATASTORE_JSON + " Reason :"
					+ e);
		} catch (IOException e) {
			logger.error("Failed to read  " + Constants.DATASTORE_JSON + " Reason :"
					+ e);
		}
		if (jsonStr.isEmpty())
			return;
		logger.info("Fetched data from " + Constants.DATASTORE_JSON);
		try {
			Type listType = new TypeToken<ArrayList<DatastoreDTO>>() {
			}.getType();
			List<DatastoreDTO> nodeList = gson.fromJson(jsonStr, listType);
			for (DatastoreDTO datastoreDTO : nodeList) {
				map.put(datastoreDTO.id, datastoreDTO);
			}
			datastoreCache.putAll(map);
		} catch (Exception e) {
			logger.error("unable to load nodes Reason : " + e);
		}
	}
	
}
