package com.builditboys.misc.units;

public class AngleUnits extends AbstractUnit {
	
	public static final AngleUnits RADIAN =
			new AngleUnits("radian", "radians", "rad", 1.0, null);
	

	public static final AngleUnits DEGREE =
			new AngleUnits("degree", "degrees", "deg", (Math.PI/180.0), RADIAN);
	

	//--------------------------------------------------------------------------------
	// Constructor

	private AngleUnits (String name, String plural, String abbreviation,
						double conversionFactor, AbstractUnit baseUnit) {
		super(name, plural, abbreviation, 
			  UnitKindEnum.ANGLE, conversionFactor, baseUnit);
	}
	
	//--------------------------------------------------------------------------------
	

}

