package com.builditboys.misc.units;

public class VolumeUnits  extends AbstractUnit {
	
	public static final VolumeUnits LITER =
			new VolumeUnits("liter", "liters", "l", 1.0, null);

	public static final VolumeUnits MILLILITER =
			new VolumeUnits("milliliter", "milliliters", "ml", 1.0E-3, LITER);

	// --------------------------------------------------------------------------------
	
	private VolumeUnits (String name, String plural, String abbreviation,
						double conversionFactor, AbstractUnit baseUnit) {
		super(name, plural, abbreviation, UnitKindEnum.VOLUME,
			  conversionFactor, baseUnit);
	}

}
