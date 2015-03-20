package com.app.vim;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import com.app.cache.Constants;
import com.vmware.vim25.mo.ServiceInstance;

public class VimService {

	private static ServiceInstance instance = null;
	
	public static ServiceInstance getServiceInstance() throws Exception{
		if(instance == null ){
			try {
				instance =  new ServiceInstance(new URL("https://" +
				        Constants.VC_URL + "/sdk"), Constants.VC_UNAME, Constants.VC_PASS,
				        true);
			} catch (RemoteException e) {
				throw new Exception(e.getMessage());
			} catch (MalformedURLException e) {
				throw new Exception(e.getMessage());
			}
		}
		return instance;
	}
	public static void resetInstance(){
		instance = null;
	}
	
}
