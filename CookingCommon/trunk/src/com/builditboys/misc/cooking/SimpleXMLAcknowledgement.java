package com.builditboys.misc.cooking;

import org.simpleframework.xml.Element;

class SimpleXMLAcknowledgement {

	@Element(required=false)
	String who;
	
	@Element(required=false)
	String organization;
	
	@Element(required=false)
	String where;
	
	@Element(required=false)
	String locator;

	// --------------------------------------------------------------------------------
	// Constructors
	
	// Else you get a constructor not matched exception
	SimpleXMLAcknowledgement () {
		super();
	}
	
	SimpleXMLAcknowledgement(String who, String organization, String where, String locator) {
		super();
		this.who = who;
		this.organization = organization;
		this.where = where;
		this.locator = locator;
	}	
	
	// --------------------------------------------------------------------------------
	
	static SimpleXMLAcknowledgement constructXMLAcknowledgement (Acknowledgement acknowledgement) {
		SimpleXMLAcknowledgement xmlAcknowledgement =
				new SimpleXMLAcknowledgement(acknowledgement.who,
											 acknowledgement.organization,
											 acknowledgement.where,
											 acknowledgement.locator); 
		return xmlAcknowledgement;
	}
	
	static Acknowledgement constructAcknowledgement (SimpleXMLAcknowledgement xmlAcknowledgement) {
		Acknowledgement acknowledgement =
				new Acknowledgement(xmlAcknowledgement.who,
								    xmlAcknowledgement.organization,
									xmlAcknowledgement.where,
									xmlAcknowledgement.locator);
		return acknowledgement;
	}

}
