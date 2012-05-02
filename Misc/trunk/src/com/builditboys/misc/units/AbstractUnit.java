package com.builditboys.misc.units;

import java.util.HashMap;
import java.util.Map;
import static com.builditboys.misc.units.TimeUnits.*;
import static com.builditboys.misc.units.LengthUnits.*;
import static com.builditboys.misc.units.VolumeUnits.*;
import static com.builditboys.misc.units.AngleUnits.*;
import static com.builditboys.misc.units.MassUnits.*;

public abstract class AbstractUnit {

	//--------------------------------------------------------------------------------
	// Statics
	
	protected static enum UnitKindEnum { 
		TIME(SECOND),
		LENGTH(METER),
		AREA(null),
		VOLUME(LITER),
		MASS(KILOGRAM),
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
		
	private static Map<String, AbstractUnit> unitNameMap = 
				new HashMap<String, AbstractUnit>();

	//--------------------------------------------------------------------------------
	// Instance variables
	
	// The name of the unit
	public final String name;
	
	// The plural name of the unit
	public final String plural;
	
	// The abbreviation for the unit
	public final String abbreviation;
	
	// The kind of unit, weight, length, etc.
	private final UnitKindEnum kind;

	// Conversion factor to convert a unit to the common unit
	private final double conversionFactor;
	
	// The conversion factor converts to this
	private final AbstractUnit baseUnit;
	
	
	// The conversion factor from the unit to its base unit; computed on demand
	private double baseConversionFactor;
	
	//--------------------------------------------------------------------------------
	// Constructor
	
	protected AbstractUnit (String name, String plural, String abbreviation,
				            UnitKindEnum kind, double conversionFactor, AbstractUnit baseUnit) {
		this.name = name;
		this.plural = plural;
		this.abbreviation = abbreviation;
		this.kind = kind;
		this.conversionFactor = conversionFactor;	
		this.baseUnit = baseUnit;
		
		unitNameMap.put(name, this);
	}
	
	//--------------------------------------------------------------------------------
	// Lookup
	
	public static AbstractUnit getUnitNamed(String name) {
		AbstractUnit unit = unitNameMap.get(name);
		if (unit == null) {
			throw new IllegalArgumentException("No unit named " + name);
		}
		return unitNameMap.get(name);
	}
	
	//--------------------------------------------------------------------------------
	// Converters
	
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
	
	//--------------------------------------------------------------------------------
	// Convert to/from
	
	public double convertTo (double val, AbstractUnit toUnit) {
		return convert(val, this, toUnit);
	}
	
	public double convertFrom (double val, AbstractUnit fromUnit) {
		return convert(val, fromUnit, this);
	}
	
	public long convertTo (long val, AbstractUnit toUnit) {
		return convert(val, this, toUnit);
	}
	
	public long convertFrom (long val, AbstractUnit fromUnit) {
		return convert(val, fromUnit, this);
	}

	
	public int convertTo (int val, AbstractUnit toUnit) {
		return convert(val, this, toUnit);
	}
	
	public int convertFrom (int val, AbstractUnit fromUnit) {
		return convert(val, fromUnit, this);
	}

}

