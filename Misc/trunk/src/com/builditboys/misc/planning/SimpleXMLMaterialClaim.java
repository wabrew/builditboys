package com.builditboys.misc.planning;

import org.simpleframework.xml.Element;

import com.builditboys.misc.units.AbstractUnit;

class SimpleXMLMaterialClaim {
	
	@Element
	double amount;
	
	@Element
	String units;

	@Element
	String materialName;
	
	// --------------------------------------------------------------------------------

	// Else you get a constructor not matched exception
	public SimpleXMLMaterialClaim() {
		super();
	}

	public SimpleXMLMaterialClaim(String MaterialName, double amount, String units) {
		super();
		this.materialName = MaterialName;
		this.amount = amount;
		this.units = units;
	}	
	
	// --------------------------------------------------------------------------------

	static SimpleXMLMaterialClaim constructXMLMaterialClaim (MaterialClaim claim) {
		return new SimpleXMLMaterialClaim(claim.materialName, claim.amount, claim.units.name);
	}
	
	static MaterialClaim constructMaterialClaim (SimpleXMLMaterialClaim xmlClaim) {
		return new MaterialClaim(xmlClaim.materialName, xmlClaim.amount,
				                 AbstractUnit.getUnitNamed(xmlClaim.units));
	}


}
