package com.app.cache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.app.ClusterDTO;
import com.app.DatacenterDTO;
import com.app.DatastoreDTO;
import com.app.HostDTO;
import com.app.TaskDeatils;
import com.app.VimDTO;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.InventoryView;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

public class Utility {
	public static Logger logger = Logger.getLogger(Utility.class);
	/**
	 * @param path
	 * @param fileName
	 * @param data
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static synchronized void writeJsonFile(String path, String fileName,
			String data) throws FileNotFoundException, IOException {
		FileOutputStream fileOutputStream = null;
		File file;
		try {
			file = new File(path + File.separator + fileName);
			fileOutputStream = new FileOutputStream(file, false);
			/**
			 * if file does not exists, then create it
			 */
			if (!file.exists()) {
				file.createNewFile();
			}
			/**
			 * get the data in bytes
			 */
			byte[] contentInBytes = data.getBytes();
			fileOutputStream.write(contentInBytes);
			logger.info(fileName + " Updated successfully");
		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}
	}

	/**
	 * @param path
	 * @param fileName
	 * @return string
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static synchronized String readJsonFile(String path, String fileName)
			throws IOException, FileNotFoundException {
		logger.debug("Reading file :" + path + File.separator + fileName);
		StringBuilder fileContent = new StringBuilder();
		FileReader fileReader = null;
		BufferedReader br = null;
		try {
			fileReader = new FileReader(path + File.separator + fileName);
			br = new BufferedReader(fileReader);
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				fileContent.append(sCurrentLine);
			}
		} finally {
			br.close();
			fileReader.close();
		}
		return fileContent.toString();
	}

	/**
	 * configure log4j properties
	 * 
	 * @param path
	 */
	public static void configureLog4j(String path) {
		Logger rootLogger = Logger.getRootLogger();
		if (!rootLogger.getAllAppenders().hasMoreElements()) {
			rootLogger.setLevel(Level.INFO);
			rootLogger.addAppender(new ConsoleAppender(new PatternLayout(
					"%d %p [%c{1}] - %m%n")));

			FileAppender fa = new FileAppender();
			fa.setName("FileLogger");
			fa.setFile(path + File.separator + "mock_server.log");
			fa.setLayout(new PatternLayout("%d %p [%c{1}] - %m%n"));
			fa.setThreshold(Level.DEBUG);
			fa.setAppend(true);
			fa.activateOptions();
			rootLogger.addAppender(fa);
		}
	}

	/**
	 * initialze Utile 
	 */
		public static void init() {
			String resourceLocation = Utility.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			String parentFolder = new File(resourceLocation).getParent();
			System.out.println(parentFolder);
				Utility.configureLog4j(parentFolder + File.separator + "logs");
		}
		
		public static VimDTO getAppVimData(VirtualMachine machine){
			VimDTO data = new VimDTO();
			data.setName(machine.getName());
			data.setAppHeartbeatStatus(machine.getGuest().appHeartbeatStatus);
			data.setGuestFullName(machine.getGuest().guestFullName);
			data.setHostName(machine.getGuest().hostName);
			data.setIpAddress(machine.getGuest().ipAddress);
			data.setMemoryMB(machine.getConfig().hardware.memoryMB);
			data.setNumCPU(machine.getConfig().hardware.numCPU);
			data.setConnectionState(machine.getRuntime().connectionState.name());
			data.setMaxCpuUsage(machine.getRuntime().maxCpuUsage);
			data.setMaxMemoryUsage(machine.getRuntime().maxMemoryUsage);
			data.setType(machine.getMOR().getType());
			data.setMoid(machine.getMOR().getVal());
			return data;
		}
		
		public static DatastoreDTO getDatastoreValues(Datastore datastore){
			DatastoreDTO dto = new DatastoreDTO();
			dto.setName(datastore.getName());
			dto.setAccessible(datastore.getSummary().accessible + "");
			dto.setCapacity(datastore.getSummary().capacity);
			dto.setFreeSpace(datastore.getSummary().freeSpace);
			dto.setType(datastore.getMOR().getType());
			dto.setModid(datastore.getMOR().getVal());
			dto.setDs_type(datastore.getSummary().type);
			return dto;
		}
		
		//fetch inventory tree
		public static HostDTO getHostValues(HostSystem hostSystem){
			 HostDTO dto = new HostDTO();
			 dto.setType(hostSystem.getMOR().getType());
			 dto.setMoid(hostSystem.getMOR().getVal());
			 dto.setName(hostSystem.getName());
			 dto.setIscsiSupported(hostSystem.getCapability().iscsiSupported + "");
			 dto.setConnectionState(hostSystem.getRuntime().connectionState.name());
			 dto.setShutdownSupported(hostSystem.getCapability().shutdownSupported + "");
			 dto.setBootTime(hostSystem.getRuntime().bootTime.toString());
			 dto.setPowerState(hostSystem.getRuntime().powerState.name());
			 dto.setManagementServerIp(hostSystem.getSummary().managementServerIp.toString());
			 dto.setMaxEVCModeKey(hostSystem.getSummary().maxEVCModeKey.toString());
			return dto;
		}
		

		public static ClusterDTO getClusterValues(ClusterComputeResource cluster) throws InvalidProperty, RuntimeFault, RemoteException{
			 ClusterDTO dto = new ClusterDTO();
			 dto.setName(cluster.getName());
			 dto.setTotalCpu(cluster.getSummary().totalCpu);
			 dto.setTotalMemory(cluster.getSummary().totalMemory);
			 dto.setNumHosts(cluster.getSummary().numHosts);
			 
			 ManagedEntity[] entities = new InventoryNavigator(cluster).searchManagedEntities("Datastore");
			 ArrayList<DatastoreDTO> dsDtos = new ArrayList<DatastoreDTO>();
			 for(ManagedEntity entity : entities){
				 Datastore datastore = (Datastore)entity;
				 dsDtos.add(getDatastoreValues(datastore));
			 }
			 dto.setDatastores(dsDtos);
			 
			 ManagedEntity[] hosts = new InventoryNavigator(cluster).searchManagedEntities("HostSystem");
			 ArrayList<HostDTO> hostlist = new ArrayList<HostDTO>();
			 for(ManagedEntity entity : entities){
				 HostSystem host = (HostSystem)entity;
				 hostlist.add(getHostValues(host));
			 }
			 dto.setHosts(hostlist);
			 
			 ManagedEntity[] vms = new InventoryNavigator(cluster).searchManagedEntities("VirtualMachine");
			 ArrayList<VimDTO> vmList = new ArrayList<VimDTO>();
			 for(ManagedEntity entity : entities){
				 VirtualMachine vm = (VirtualMachine)entity;
				 vmList.add(getAppVimData(vm));
			 }
			 dto.setVms(vmList);
			 
			return dto;
		}
		
		public static DatacenterDTO getDatacenterValues(Datacenter datacenter) throws InvalidProperty, RuntimeFault, RemoteException{
			DatacenterDTO dto = new DatacenterDTO();
			dto.setType(datacenter.getMOR().getType());
			dto.setMoid(datacenter.getMOR().getVal());
			dto.setName(datacenter.getName());
			
			
			Task[] tasks = datacenter.getRecentTasks();
			ArrayList<TaskDeatils> tks = new ArrayList<TaskDeatils>(); 
			for(Task task : tasks){
				TaskDeatils deatils = new TaskDeatils();
				deatils.setCancelable(task.getTaskInfo().cancelable);
				deatils.setCompleteTime(task.getTaskInfo().completeTime.toString());
				deatils.setDescription(task.getTaskInfo().description.message);
				deatils.setKey(task.getTaskInfo().key);
				deatils.setName(task.getTaskInfo().name);
				tks.add(deatils);
			}
			dto.setTaskDeatils(tks);
			
			 ManagedEntity[] clusters = new InventoryNavigator(datacenter).searchManagedEntities("ClusterComputeResource");
			 ArrayList<ClusterDTO> cList = new ArrayList<ClusterDTO>();
			 for(ManagedEntity entity : clusters){
				 ClusterComputeResource cluster = (ClusterComputeResource)entity;
				 cList.add(getClusterValues(cluster));
			 }
			 dto.setClusterList(cList);
			
			 ManagedEntity[] entities = new InventoryNavigator(datacenter).searchManagedEntities("Datastore");
			 ArrayList<DatastoreDTO> dsDtos = new ArrayList<DatastoreDTO>();
			 for(ManagedEntity entity : entities){
				 Datastore datastore = (Datastore)entity;
				 dsDtos.add(getDatastoreValues(datastore));
			 }
			 dto.setDsList(dsDtos);
			 
			 ManagedEntity[] hosts = new InventoryNavigator(datacenter).searchManagedEntities("HostSystem");
			 ArrayList<HostDTO> hostlist = new ArrayList<HostDTO>();
			 for(ManagedEntity entity : entities){
				 HostSystem host = (HostSystem)entity;
				 hostlist.add(getHostValues(host));
			 }
			 dto.setHostList(hostlist);
			 
			 ManagedEntity[] vms = new InventoryNavigator(datacenter).searchManagedEntities("VirtualMachine");
			 ArrayList<VimDTO> vmList = new ArrayList<VimDTO>();
			 for(ManagedEntity entity : entities){
				 VirtualMachine vm = (VirtualMachine)entity;
				 vmList.add(getAppVimData(vm));
			 }
			 dto.setVmList(vmList);
			
			return dto;
		}
		
		
}
