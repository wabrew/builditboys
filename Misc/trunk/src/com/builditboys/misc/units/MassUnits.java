package com.builditboys.misc.units;

public class MassUnits extends AbstractUnit {

	public static final MassUnits KILOGRAM =
			new MassUnits("kilogram", "kilogramss", "kg", 1.0, null);
	
	

	public static final MassUnits POUND =
			new MassUnits("pound", "pounds", "lb", 0.45359237, KILOGRAM);

	// --------------------------------------------------------------------------------
	
	private MassUnits (String name, String plural, String abbreviation,
						double conversionFactor, AbstractUnit baseUnit) {
		super(name, plural, abbreviation, UnitKindEnum.MASS,
			  conversionFactor, baseUnit);
	}

}
