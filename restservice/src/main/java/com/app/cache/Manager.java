package com.app.cache;

public class Manager {
	
	private static Manager manager = null;
	
	public static Manager getInstance(){
		if(manager==null){
			manager = new Manager();
		}
		return manager;
	}
	private Manager(){
		
	}
}
