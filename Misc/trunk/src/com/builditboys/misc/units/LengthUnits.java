package com.builditboys.misc.units;


public class LengthUnits extends AbstractUnit {
	
	public static final LengthUnits METER =
			new LengthUnits("meter", "meters", "m", 1.0, null);

	public static final LengthUnits DECIMETER = 
			new LengthUnits("decimeter", "decimeters", "dm", 1.0E-1, METER);

	public static final LengthUnits CENTIMETER = 
			new LengthUnits("centimeter", "centimeters", "cm", 1.0E-2, METER);

	public static final LengthUnits MILLIMETER = 
			new LengthUnits("millimeter", "millimeters", "mm", 1.0E-3, METER);

	public static final LengthUnits MICROMETER = 
			new LengthUnits("micrometer", "micrometers", "um", 1.0E-6, METER);
	
	public static final LengthUnits KILOMETER = 
			new LengthUnits("kilometer", "kilometers", "km", 1.0E3, METER);
	
	
	public static final LengthUnits FOOT =
			new LengthUnits("foot", "feet", "ft", 0.3048, METER);

	public static final LengthUnits INCH =
			new LengthUnits("inch", "inches", "in", 1.0/12.0, FOOT);

	public static final LengthUnits YARD =
			new LengthUnits("yard", "yards", "yd", 3.0, FOOT);

	public static final LengthUnits MILE =
			new LengthUnits("mile", "miles", "mi", 5280.0, FOOT);
	
	
	//--------------------------------------------------------------------------------
    // Constructor
	
	private LengthUnits (String name, String plural, String abbreviation,
						 double conversionFactor, AbstractUnit baseUnit) {
		super(name, plural, abbreviation,
			  UnitKindEnum.LENGTH, conversionFactor, baseUnit);
	}
		
}

