package com.builditboys.misc.planning;

public class ResourceClaim {
	
	double amount;
	
	String resourceName;
	

	
	ResourceInterface resource;	

	ResourcePool fullfillingPool;

	// --------------------------------------------------------------------------------

	public ResourceClaim(String resourceName, double amount) {
		super();
		this.resourceName = resourceName;
		this.amount = amount;
	}
	
}
