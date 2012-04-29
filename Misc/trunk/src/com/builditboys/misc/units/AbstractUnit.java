package com.builditboys.misc.units;

import java.util.EnumMap;
import java.util.Map;
import static com.builditboys.misc.units.TimeUnits.*;
import static com.builditboys.misc.units.LengthUnits.*;
import static com.builditboys.misc.units.VolumeUnits.*;
import static com.builditboys.misc.units.AngleUnits.*;
import static com.builditboys.misc.units.WeightUnits.*;

public abstract class AbstractUnit {

	public static enum UnitKindEnum { 
		TIME(SECOND),
		LENGTH(METER),
		AREA(null),
		VOLUME(LITER),
		MASS(null),
		WEIGHT(KILOGRAM),
		ANGLE(RADIAN),
		VELOCITY(null),
		ANGULAR_VELOCITY(null),
		POWER(null),
		ENERGY(null);
		
		public final AbstractUnit baseUnit;
		
		UnitKindEnum (AbstractUnit baseUnit) {
			this.baseUnit = baseUnit;
		}	
	};
		
	public static Map<UnitKindEnum, AbstractUnit> unitKindToBaseUnitMap = new EnumMap<UnitKindEnum, AbstractUnit>(UnitKindEnum.class);

	static {
		unitKindToBaseUnitMap.put(UnitKindEnum.TIME, SECOND);
	}
	
	//--------------------------------------------------------------------------------
	
	// The name of the unit
	public final String name;
	
	// The plural name of the unit
	public final String plural;
	
	// The abbreviation for the unit
	public final String abbreviation;
	
	// The kind of unit, weight, length, etc.
	public final UnitKindEnum kind;

	// Conversion factor to convert a unit to the common unit
	public final double conversionFactor;
	
	// The conversion factor converts to this
	public final AbstractUnit baseUnit;
	
	
	// The conversion factor from the unit to its base unit; computed on demand
	private double baseConversionFactor;
	
	//--------------------------------------------------------------------------------

	public AbstractUnit (String name, String plural, String abbreviation,
				         UnitKindEnum kind, double conversionFactor, AbstractUnit baseUnit) {
		this.name = name;
		this.plural = plural;
		this.abbreviation = abbreviation;
		this.kind = kind;
		this.conversionFactor = conversionFactor;	
		this.baseUnit = baseUnit;
	}
	
	//--------------------------------------------------------------------------------

	public static double convert (double val, AbstractUnit fromUnit, AbstractUnit toUnit) {
		if (fromUnit.kind != toUnit.kind) {
			throw new IllegalArgumentException("Units are different kinds: "
											   + fromUnit + " " + toUnit);
		}
		
		// same unit, no conversion necessary
		if (fromUnit == toUnit) {
			return val;
		}
		
		if (fromUnit.baseUnit != toUnit.baseUnit) {
			throw new IllegalStateException("Base unit mismatch");
		}
		
		// same conversion factor, no conversion necessary
		double fromConversionFactor = fromUnit.conversionFactor;
		double toConversionFactor = toUnit.conversionFactor;
		if (fromConversionFactor == toConversionFactor) {
			return val;
		}
		
		// do the conversion
		double prod;
		double quot;
		prod = val;
		if (fromConversionFactor != 1.0) {
			prod = prod * fromConversionFactor;
		}
		quot = prod;
		if (toConversionFactor != 1.0) {
			quot = quot / toConversionFactor;
		}
		return quot;
	}
	
	public static long convert (long val, AbstractUnit fromUnit, AbstractUnit toUnit) {
		if (fromUnit.kind != toUnit.kind) {
			throw new IllegalArgumentException("Units are different kinds: "
											   + fromUnit + " " + toUnit);
		}

		// same unit, no conversion necessary
		if (fromUnit == toUnit) {
			return val;
		}
		
		if (fromUnit.baseUnit != toUnit.baseUnit) {
			throw new IllegalStateException("Base unit mismatch");
		}
		
		// same conversion factor, no conversion necessary
		double fromConversionFactor = fromUnit.conversionFactor;
		double toConversionFactor = toUnit.conversionFactor;
		if (fromConversionFactor == toConversionFactor) {
			return val;
		}
		
		// do the conversion
		double prod;
		double quot;
		prod = (double) val;
		if (fromConversionFactor != 1.0) {
			prod = prod * fromConversionFactor;
		}
		quot = prod;
		if (toConversionFactor != 1.0) {
			quot = quot / toConversionFactor;
		}
		return (long) quot;
	}

	public static int convert (int val, AbstractUnit fromUnit, AbstractUnit toUnit) {
		if (fromUnit.kind != toUnit.kind) {
			throw new IllegalArgumentException("Units are different kinds: "
											   + fromUnit + " " + toUnit);
		}

		// same unit, no conversion necessary
		if (fromUnit == toUnit) {
			return val;
		}
		
		if (fromUnit.baseUnit != toUnit.baseUnit) {
			throw new IllegalStateException("Base unit mismatch");
		}
		
		// same conversion factor, no conversion necessary
		double fromConversionFactor = fromUnit.conversionFactor;
		double toConversionFactor = toUnit.conversionFactor;
		if (fromConversionFactor == toConversionFactor) {
			return val;
		}
		
		// do the conversion
		double prod;
		double quot;
		prod = (double) val;
		if (fromConversionFactor != 1.0) {
			prod = prod * fromConversionFactor;
		}
		quot = prod;
		if (toConversionFactor != 1.0) {
			quot = quot / toConversionFactor;
		}
		return (int) quot;
	}

}

