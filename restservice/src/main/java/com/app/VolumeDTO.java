package com.app;

import java.util.List;

public class VolumeDTO {
	public String id;
	public String name;
	public String type;
	public long size;
	public long usedSize;
	public long freeSize;
	public int dsCount;
	public String status;
	public List<DatastoreDTO> datastores;
}
