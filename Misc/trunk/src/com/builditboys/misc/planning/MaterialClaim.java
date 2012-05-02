package com.builditboys.misc.planning;

import com.builditboys.misc.units.AbstractUnit;

public class MaterialClaim {

	double amount;
	
	AbstractUnit units;

	String materialName;
	
	
	
	MaterialInterface material;



	// --------------------------------------------------------------------------------
			
	public MaterialClaim(String materialName, double amount, AbstractUnit units) {
		super();
		this.materialName = materialName;
		this.amount = amount;
		this.units = units;
	}


}
