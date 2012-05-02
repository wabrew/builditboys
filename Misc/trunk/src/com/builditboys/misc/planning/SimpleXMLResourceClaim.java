package com.builditboys.misc.planning;

import org.simpleframework.xml.Element;

class SimpleXMLResourceClaim {

	@Element
	double amount;

	@Element
	String resourceName;
	
	// --------------------------------------------------------------------------------

	// Else you get a constructor not matched exception
	public SimpleXMLResourceClaim() {
		super();
	}

	public SimpleXMLResourceClaim(String resourceName, double amount) {
		super();
		this.resourceName = resourceName;
		this.amount = amount;
	}	
	
	// --------------------------------------------------------------------------------

	static SimpleXMLResourceClaim constructXMLResourceClaim (ResourceClaim claim) {
		return new SimpleXMLResourceClaim(claim.resourceName, claim.amount);
	}
	
	static ResourceClaim constructResourceClaim (SimpleXMLResourceClaim xmlClaim) {
		return new ResourceClaim(xmlClaim.resourceName, xmlClaim.amount);
	}
	
}
