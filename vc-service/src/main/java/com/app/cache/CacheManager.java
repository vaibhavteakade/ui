package com.app.cache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;

import com.app.ClusterDTO;
import com.app.DatacenterDTO;
import com.app.DatastoreDTO;
import com.app.HostDTO;
import com.app.VimDTO;
import com.app.vim.VimService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

public class CacheManager {

	private static CacheManager manager = null;
	private Gson gson = new Gson();
	private GsonBuilder builder;
	private String jsonStr;
	private static Logger logger = Logger.getLogger(CacheManager.class);
	public static String dataRoot = "D:\\vaibhav_projects\\angular_examples\\ui\\vc-service\\src\\main\\resources";
	/**
	 * Virtual Machine cache
	 */
	private LoadingCache<String, VimDTO> vimCache = null;
	private LoadingCache<String, DatastoreDTO> datastoreCache = null;
	private LoadingCache<String, HostDTO> HostCache = null;
	private LoadingCache<String, ClusterDTO> clusterCache = null;
	private LoadingCache<String, DatacenterDTO> datacenterCache = null;

	public static CacheManager getInstance() {
		if (manager == null) {
			manager = new CacheManager();
		}
		return manager;
	}

	private CacheManager() {
		logger.info("initializing CacheManager");
		initCacheLoader();
		// loadDC();
		// loadCluster();
		// loadHost();
		// loadDatastore();
		// loadVim();
		logger.info("CacheManager initialized");
	}

	/**
	 * Initiate all loading cache with its load and removeListner
	 */
	private void initCacheLoader() {
		// vim
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

		// datacenter
		// vim
		datacenterCache = CacheBuilder.newBuilder()
				.maximumSize(Constants.MAX_SIZE)
				.removalListener(new RemovalListener<String, DatacenterDTO>() {
					@Override
					public void onRemoval(
							RemovalNotification<String, DatacenterDTO> paramRemovalNotification) {
						DatacenterDTO d = null;
						try {
							d = getDC();
						} catch (ExecutionException e1) {
							e1.printStackTrace();
						}
					}
				}).build(new CacheLoader<String, DatacenterDTO>() {
					@Override
					public DatacenterDTO load(String ref) throws Exception {
						loadVim();
						return datacenterCache.getIfPresent(ref);
					}
				});
		// cluster
		clusterCache = CacheBuilder.newBuilder()
				.maximumSize(Constants.MAX_SIZE)
				.removalListener(new RemovalListener<String, ClusterDTO>() {
					@Override
					public void onRemoval(
							RemovalNotification<String, ClusterDTO> paramRemovalNotification) {
						ClusterDTO d = null;
						try {
							d = getCluster();
						} catch (ExecutionException e1) {
							e1.printStackTrace();
						}
					}
				}).build(new CacheLoader<String, ClusterDTO>() {
					@Override
					public ClusterDTO load(String ref) throws Exception {
						loadVim();
						return clusterCache.getIfPresent(ref);
					}
				});

		// Hostsystem

		HostCache = CacheBuilder.newBuilder().maximumSize(Constants.MAX_SIZE)
				.removalListener(new RemovalListener<String, HostDTO>() {
					@Override
					public void onRemoval(
							RemovalNotification<String, HostDTO> paramRemovalNotification) {
						HostDTO d = null;
						try {
							d = getHost();
						} catch (ExecutionException e1) {
							e1.printStackTrace();
						}
					}
				}).build(new CacheLoader<String, HostDTO>() {
					@Override
					public HostDTO load(String ref) throws Exception {
						loadHost();
						return HostCache.getIfPresent(ref);
					}
				});

		// datastore

		datastoreCache = CacheBuilder.newBuilder()
				.maximumSize(Constants.MAX_SIZE)
				.removalListener(new RemovalListener<String, DatastoreDTO>() {
					@Override
					public void onRemoval(
							RemovalNotification<String, DatastoreDTO> paramRemovalNotification) {
						DatastoreDTO d = null;
						try {
							d = getdatastore();
						} catch (ExecutionException e1) {
							e1.printStackTrace();
						}
					}
				}).build(new CacheLoader<String, DatastoreDTO>() {
					@Override
					public DatastoreDTO load(String ref) throws Exception {
						loadDatastore();
						return datastoreCache.getIfPresent(ref);
					}
				});

	}

	// //////////// Virtual Machine /////////////////////////////////
	public VimDTO getVM() throws ExecutionException {
		logger.debug("Fetching vm from cache");
		if (vimCache.asMap().isEmpty())
			loadVim();
		logger.debug("Fetched dashboard from cache");
		return vimCache.get("vim");
	}

	// get all Vm
	private void loadVim() {
		if (Utility.USE_MOCK) {
			try {
				Type listType = new TypeToken<ArrayList<VimDTO>>() {
				}.getType();
				String jsonString = Utility.readJsonFile(dataRoot
						+ File.separator, "vms.json");
				List<VimDTO> vms = gson.fromJson(jsonString, listType);
				for (VimDTO dto : vms)
					vimCache.put(dto.getMoid(), dto);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {

			ServiceInstance si = VimService.getServiceInstance();
			vimCache.invalidateAll();
			try {
				ManagedEntity[] entities = new InventoryNavigator(
						si.getRootFolder())
						.searchManagedEntities("VirtualMachine");
				for (ManagedEntity entity : entities) {
					VirtualMachine machine = (VirtualMachine) entity;
					VimDTO data = Utility.getAppVimData(machine);
					vimCache.put(data.getMoid(), data);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public List<VimDTO> getAllVm() {
		List<VimDTO> list = new ArrayList<VimDTO>();
		if (vimCache.asMap().isEmpty())
			loadVim();
		for (Map.Entry<String, VimDTO> entry : vimCache.asMap().entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}

	// //////////// Datacenter /////////////////////////////////
	public DatacenterDTO getDC() throws ExecutionException {
		logger.debug("Fetching DC from cache");
		if (datacenterCache.asMap().isEmpty())
			loadDC();
		logger.debug("Fetched dc from cache");
		return datacenterCache.get("vim");
	}

	// get all DC
	private void loadDC() {
		if (Utility.USE_MOCK) {
			try {
				Type listType = new TypeToken<ArrayList<DatacenterDTO>>() {
				}.getType();
				String jsonString = Utility.readJsonFile(dataRoot
						+ File.separator, "datacenter.json");
				List<DatacenterDTO> dcList = gson
						.fromJson(jsonString, listType);
				for (DatacenterDTO dto : dcList)
					datacenterCache.put(dto.getMoid(), dto);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			ServiceInstance si = VimService.getServiceInstance();
			datacenterCache.invalidateAll();
			System.out.println("load DC Start " + datacenterCache.size());
			try {
				ManagedEntity[] entities = new InventoryNavigator(
						si.getRootFolder()).searchManagedEntities("Datacenter");
				for (ManagedEntity entity : entities) {
					Datacenter machine = (Datacenter) entity;
					DatacenterDTO data = Utility.getDatacenterValues(machine);
					datacenterCache.put(data.getMoid(), data);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println("load DC complete " + datacenterCache.size());
		}
	}

	public List<DatacenterDTO> getAllDC() {
		List<DatacenterDTO> list = new ArrayList<DatacenterDTO>();
		if (datacenterCache.asMap().isEmpty())
			loadDC();
		for (Map.Entry<String, DatacenterDTO> entry : datacenterCache.asMap()
				.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}

	// ////////////////////////Cluster//////////////////////////////////////
	public ClusterDTO getCluster() throws ExecutionException {
		logger.debug("Fetching cluster from cache");
		if (clusterCache.asMap().isEmpty())
			loadDC();
		logger.debug("Fetched cluster from cache");
		return clusterCache.get("cluster");
	}

	private void loadCluster() {
		if (Utility.USE_MOCK) {
			try {
				Type listType = new TypeToken<ArrayList<ClusterDTO>>() {
				}.getType();
				String jsonString = Utility.readJsonFile(dataRoot
						+ File.separator, "cluster.json");
				List<ClusterDTO> dcList = gson.fromJson(jsonString, listType);
				for (ClusterDTO dto : dcList)
					clusterCache.put(dto.getMoid(), dto);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			ServiceInstance si = VimService.getServiceInstance();
			clusterCache.invalidateAll();
			System.out.println("loadCluster Start ");
			try {
				ManagedEntity[] entities = new InventoryNavigator(
						si.getRootFolder())
						.searchManagedEntities("ClusterComputeResource");
				for (ManagedEntity entity : entities) {
					ClusterComputeResource machine = (ClusterComputeResource) entity;
					ClusterDTO data = Utility.getClusterValues(machine);
					clusterCache.put(data.getMoid(), data);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println("loadCluster End " + clusterCache.size());
		}
	}

	public List<ClusterDTO> getAllCluster() {
		List<ClusterDTO> list = new ArrayList<ClusterDTO>();
		if (clusterCache.asMap().isEmpty())
			loadCluster();
		for (Map.Entry<String, ClusterDTO> entry : clusterCache.asMap()
				.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}

	// //////////////////////////Host System
	// //////////////////////////////////////////
	public HostDTO getHost() throws ExecutionException {
		logger.debug("Fetching host from cache");
		if (HostCache.asMap().isEmpty())
			loadHost();
		logger.debug("Fetched host from cache");
		return HostCache.get("host");
	}

	private void loadHost() {
		if (Utility.USE_MOCK) {
			try {
				Type listType = new TypeToken<ArrayList<HostDTO>>() {
				}.getType();
				String jsonString = Utility.readJsonFile(dataRoot
						+ File.separator, "host.json");
				List<HostDTO> cList = gson.fromJson(jsonString, listType);
				for (HostDTO dto : cList)
					HostCache.put(dto.getMoid(), dto);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			ServiceInstance si = VimService.getServiceInstance();
			HostCache.invalidateAll();
			System.out.println("loadHost Start");
			try {
				ManagedEntity[] entities = new InventoryNavigator(
						si.getRootFolder()).searchManagedEntities("HostSystem");
				for (ManagedEntity entity : entities) {
					HostSystem machine = (HostSystem) entity;
					HostDTO data = Utility.getHostValues(machine);
					HostCache.put(data.getMoid(), data);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println("loadHost End " + HostCache.size());
		}
	}

	public List<HostDTO> getAllHost() {
		List<HostDTO> list = new ArrayList<HostDTO>();
		if (HostCache.asMap().isEmpty())
			loadHost();
		for (Map.Entry<String, HostDTO> entry : HostCache.asMap().entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}

	// ///////////////////////////Datastore//////////////////////////////////////////////////
	public DatastoreDTO getdatastore() throws ExecutionException {
		logger.debug("Fetching datastore from cache");
		if (datastoreCache.asMap().isEmpty())
			loadDatastore();
		logger.debug("Fetched datastore from cache");
		return datastoreCache.get("datastore");
	}

	private void loadDatastore() {
		if (Utility.USE_MOCK) {
			try {
				Type listType = new TypeToken<ArrayList<DatastoreDTO>>() {
				}.getType();
				String jsonString = Utility.readJsonFile(dataRoot
						+ File.separator, "datastore.json");
				List<DatastoreDTO> cList = gson.fromJson(jsonString, listType);
				for (DatastoreDTO dto : cList)
					datastoreCache.put(dto.getModid(), dto);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			ServiceInstance si = VimService.getServiceInstance();
			datastoreCache.invalidateAll();
			System.out.println("loadDatastore Start");
			try {
				ManagedEntity[] entities = new InventoryNavigator(
						si.getRootFolder()).searchManagedEntities("Datastore");
				for (ManagedEntity entity : entities) {
					Datastore machine = (Datastore) entity;
					DatastoreDTO data = Utility.getDatastoreValues(machine);
					datastoreCache.put(data.getModid(), data);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println("loadDatastore End " + datacenterCache.size());
		}
	}

	public List<DatastoreDTO> getAllDatastore() {
		List<DatastoreDTO> list = new ArrayList<DatastoreDTO>();
		if (datastoreCache.asMap().isEmpty())
			loadDatastore();
		for (Map.Entry<String, DatastoreDTO> entry : datastoreCache.asMap()
				.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}
}
