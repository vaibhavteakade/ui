package com.app.inventory;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import com.app.inventory.CI;
import com.app.inventory.DCI;
import com.app.inventory.InventoryItem;
import com.app.inventory.InventoryLlist;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.InventoryDescription;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.InventoryView;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

public class Inventory {
  ServiceInstance instance = null;
  /**
   * @param args
   * @throws MalformedURLException
   * @throws RemoteException
   */
//  public static void main(String[] args) throws RemoteException, MalformedURLException {
//    Inventory inventory = new Inventory();
//    inventory.getInventory();
//  }

  public InventoryLlist getInventory() throws RemoteException, MalformedURLException{
    ServiceInstance instance =  new ServiceInstance(new URL(
        "https://cs002-vc/sdk"), "root", "vmware",
        true);

    InventoryLlist inventoryLlist = new InventoryLlist();
    ManagedEntity[] dcList = new InventoryNavigator(instance.getRootFolder()).searchManagedEntities("Datacenter");
    for (ManagedEntity entity : dcList){
      inventoryLlist.dacenters.add(getDatacenters(entity));
    }
    return inventoryLlist;
  }

  public DCI getDatacenters(ManagedEntity entity) throws InvalidProperty, RuntimeFault, RemoteException{
    DCI dci = new DCI();
    Datacenter datacenter = (Datacenter)entity;
    dci.datacenter.name = datacenter.getName();
    dci.datacenter.type = datacenter.getMOR().getType();
    dci.datacenter.value = datacenter.getMOR().getVal();

    ManagedEntity[] clusters = new InventoryNavigator(entity).searchManagedEntities("ClusterComputeResource");
    for (ManagedEntity cluster : clusters){
      dci.clusters.add(getCluster(cluster));
    }
    return dci;
  }

  public CI getCluster(ManagedEntity entity) throws InvalidProperty, RuntimeFault, RemoteException{
    CI ci = new CI();
    ClusterComputeResource resource = (ClusterComputeResource) entity;
    ci.cluster.name = resource.getName();
    ci.cluster.type = resource.getMOR().getType();
    ci.cluster.value = resource.getMOR().getVal();

    ManagedEntity[] dsList = new InventoryNavigator(entity).searchManagedEntities("Datastore");
    for(ManagedEntity ds : dsList){
      Datastore datastore = (Datastore) ds;
      InventoryItem dsi = new InventoryItem();
      dsi.type = datastore.getMOR().getType();
      dsi.value = datastore.getMOR().getVal();
      dsi.name = datastore.getName();
      ci.datastore.add(dsi);
      }


    ManagedEntity[] hsList = new InventoryNavigator(entity).searchManagedEntities("HostSystem");
    for(ManagedEntity hs : hsList){
      HostSystem hostSystem = (HostSystem) hs;
      InventoryItem dsi = new InventoryItem();
      dsi.type = hostSystem.getMOR().getType();
      dsi.value = hostSystem.getMOR().getVal();
      dsi.name = hostSystem.getName();
      ci.hosts.add(dsi);
      }

    ManagedEntity[] vmList = new InventoryNavigator(entity).searchManagedEntities("VirtualMachine");
    for(ManagedEntity vm : vmList){
      VirtualMachine virtualMachine = (VirtualMachine) vm;
      InventoryItem dsi = new InventoryItem();
      dsi.type = virtualMachine.getMOR().getType();
      dsi.value = virtualMachine.getMOR().getVal();
      dsi.name = virtualMachine.getName();
      ci.vms.add(dsi);
      }

    return ci;
  }

}
