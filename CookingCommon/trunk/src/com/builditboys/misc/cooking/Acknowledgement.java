package com.builditboys.misc.cooking;

class Acknowledgement {

	// The person or organization that is being acknowledged
	String who;
	
	// The organization if any
	String organization;
	
	// An optional reference to where the information came from
	String where;
	
	// An optional URL to the reference
	String locator;

	// --------------------------------------------------------------------------------
	// Constructor
	
	Acknowledgement(String who, String organization, String where, String locator) {
		super();
		this.who = who;
		this.organization = organization;
		this.where = where;
		this.locator = locator;
	}
	
	// --------------------------------------------------------------------------------

	public String toString () {
		return "Acknowledgement: " + who + ", " + organization + ", " + where + ", " + locator;
	}

	
}
