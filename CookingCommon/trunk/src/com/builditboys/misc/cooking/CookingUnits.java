package com.builditboys.misc.cooking;

import com.builditboys.misc.units.AbstractUnit;
import com.builditboys.misc.units.VolumeUnits;
import com.builditboys.misc.units.MassUnits;

class CookingUnits extends AbstractUnit {
	
	// --------------------------------------------------------------------------------

	static final CookingUnits TEASPOON =
			new CookingUnits("teaspoon", "teaspoons", "tsp", 
							 UnitKindEnum.VOLUME, 237.0 / 48.0, VolumeUnits.MILLILITER);
	
	static final CookingUnits TABLESPOON =
			new CookingUnits("tablespoon", "tablespoons", "Tbsp", 
							 UnitKindEnum.VOLUME, 3.0, TEASPOON);

	static final CookingUnits CUP =
			new CookingUnits("cup", "cups", "C", 
							 UnitKindEnum.VOLUME, 16.0, TABLESPOON);
	
	static final CookingUnits PINT =
			new CookingUnits("pint", "pints", "pt", 
							 UnitKindEnum.VOLUME, 2.0, CUP);	
	
	static final CookingUnits QUART =
			new CookingUnits("quart", "quarts", "qt", 
							 UnitKindEnum.VOLUME, 4.0, CUP);
	
	static final CookingUnits GALLON =
			new CookingUnits("gallon", "gallons", "gal", 
							 UnitKindEnum.VOLUME, 4.0, QUART);
	
	static final CookingUnits OUNCE_VOLUME =
			new CookingUnits("ounce", "ounces", "oz", 
							 UnitKindEnum.VOLUME, 8.0, CUP);
	

	
	static final CookingUnits OUNCE_WEIGHT =
			new CookingUnits("ounce", "ounces", "oz", 
							 UnitKindEnum.VOLUME, 1.0 / 16.0, MassUnits.POUND);
	

	
	// --------------------------------------------------------------------------------
	
	private CookingUnits (String name, String plural, String abbreviation,
						  UnitKindEnum kind, double conversionFactor, AbstractUnit baseUnit) {
		super(name, plural, abbreviation, kind, conversionFactor, baseUnit);
	}

}
