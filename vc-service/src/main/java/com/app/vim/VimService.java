package com.app.vim;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import com.app.cache.Constants;
import com.vmware.vim25.mo.ServiceInstance;

public class VimService {

	private static ServiceInstance instance = null;
	
	public static ServiceInstance getServiceInstance(){
		if(instance == null ){
			try {
				instance =  new ServiceInstance(new URL(
				        Constants.VC_URL), Constants.VC_UNAME, Constants.VC_PASS,
				        true);
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
}
