package com.builditboys.misc.planning;

public class ResourceClaim {
	
	ResourceInterface resource;
	
	int count;
	
	ResourcePool fullfillingPool;
	
	String getName() {
		return resource.getName();
	}

}
