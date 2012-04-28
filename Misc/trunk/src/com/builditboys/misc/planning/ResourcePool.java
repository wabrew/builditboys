package com.builditboys.misc.planning;

public class ResourcePool {
	
	int availableUnits;
	
	ResourceInterface resource;
	
	String getName () {
		return resource.getName();
	}

}
