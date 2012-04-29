package com.builditboys.misc.units;

public class WeightUnits extends AbstractUnit {

	public static final WeightUnits KILOGRAM =
			new WeightUnits("kilogram", "kilogramss", "kg", 1.0, null);
	
	

	public static final WeightUnits POUND =
			new WeightUnits("pound", "pounds", "lb", 0.45359237, KILOGRAM);

	// --------------------------------------------------------------------------------
	
	public WeightUnits (String name, String plural, String abbreviation,
						double conversionFactor, AbstractUnit baseUnit) {
		super(name, plural, abbreviation, UnitKindEnum.WEIGHT,
			  conversionFactor, baseUnit);
	}

}
