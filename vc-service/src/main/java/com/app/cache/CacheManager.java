package com.app.cache;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;

import com.app.VimDTO;
import com.app.DashboardDTO;
import com.app.vim.VimService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

public class CacheManager {
	
	private static CacheManager manager = null;
	private Gson gson;
	private GsonBuilder builder;
	private String jsonStr;
	private static Logger logger = Logger.getLogger(CacheManager.class);
	
	/**
	 * Virtual Machine cache
	 */
	private LoadingCache<String, VimDTO> vimCache = null;
	
	
	public static CacheManager getInstance(){
		if(manager==null){
			manager = new CacheManager();
		}
		return manager;
	}
	
	private CacheManager(){
		logger.info("initializing CacheManager");
		initCacheLoader();
		logger.info("CacheManager initialized");
	}
	
	/**
	 * Initiate all loading cache with its load and removeListner
	 */
	private void initCacheLoader() {
		//vim
		vimCache = CacheBuilder.newBuilder().maximumSize(Constants.MAX_SIZE)
				.removalListener(new RemovalListener<String, VimDTO>() {
					@Override
					public void onRemoval(
							RemovalNotification<String, VimDTO> paramRemovalNotification) {
						VimDTO d = null;
						try {
							d = getVM();
						} catch (ExecutionException e1) {
							e1.printStackTrace();
						}
					}
				}).build(new CacheLoader<String, VimDTO>() {
					@Override
					public VimDTO load(String ref) throws Exception {
						loadVim();
						return vimCache.getIfPresent(ref);
					}
				});
	}
	
	
	public VimDTO getVM() throws ExecutionException {
		logger.debug("Fetching vm from cache");
		if (vimCache.asMap().isEmpty())
			loadVim();
		logger.debug("Fetched dashboard from cache");
		return vimCache.get("vim");
	}
	
	//get all Vm
	private void loadVim() {
		ServiceInstance si = VimService.getServiceInstance();
		vimCache.invalidateAll();
		try {
			ManagedEntity[] entities = new InventoryNavigator(si.getRootFolder()).searchManagedEntities("VirtualMachine");
			for (ManagedEntity entity : entities){
				VirtualMachine machine = (VirtualMachine)entity;
				VimDTO data = Utility.getAppVimData(machine);
				vimCache.put(data.getMoid(), data);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	//get all clusters
		private void loadCluster() {
			ServiceInstance si = VimService.getServiceInstance();
			vimCache.invalidateAll();
			try {
				ManagedEntity[] entities = new InventoryNavigator(si.getRootFolder()).searchManagedEntities("ClusterComputeResource");
				for (ManagedEntity entity : entities){
					VirtualMachine machine = (VirtualMachine)entity;
					VimDTO data = Utility.getAppVimData(machine);
					vimCache.put(data.getMoid(), data);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}

	public List<VimDTO> getAllVm(){
		List<VimDTO> list = new ArrayList<VimDTO>();
		if (vimCache.asMap().isEmpty())
			loadVim();
		for (Map.Entry<String, VimDTO> entry : vimCache.asMap().entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}
}
