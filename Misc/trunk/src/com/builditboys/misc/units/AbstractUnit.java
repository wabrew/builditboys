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
	private double derivedBaseConversionFactor;
	private AbstractUnit derivedBaseUnit;
	
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
	// Finding the correct conversion
	
	// Conversions should form a tree for each kind of unit.
	// Setting up the base conversions takes place on demand.  It
	// sets up short cut links from a unit to the base of its tree.
	
	private void setupBaseConversion () {
		// Already setup, nothing to do
		if (derivedBaseUnit != null) {
			return;
		}
		
		// At the base of the tree, trivial setup, you are the derived base
		if (baseUnit == null) {
			derivedBaseConversionFactor = 1.0;
			derivedBaseUnit = this;
			return;
		}
		
		// Recursively setup your base unit and then setup yourself
		baseUnit.setupBaseConversion();
		derivedBaseConversionFactor = conversionFactor * baseUnit.derivedBaseConversionFactor;
		derivedBaseUnit = baseUnit.derivedBaseUnit;
	}
	

	//--------------------------------------------------------------------------------
	// Make sure the units look ok
	
	private static void prepareConversion (AbstractUnit fromUnit, AbstractUnit toUnit) {
		// make sure you are converting compatible units, e.g., length to length
		if (fromUnit.kind != toUnit.kind) {
			throw new IllegalArgumentException("Units are different kinds:"
											   + " " + fromUnit + " is a " + fromUnit.kind
											   + " " + toUnit + " is a " + toUnit.kind);
		}
		
		fromUnit.setupBaseConversion();
		toUnit.setupBaseConversion();
		
		// make sure the conversion tree is well connected, both units need
		// to bottom out on the same fundamental base unit
		if (fromUnit.derivedBaseUnit != toUnit.derivedBaseUnit) {
			throw new IllegalStateException("Base unit mismatch:"
											+ " " + fromUnit + " -> " + fromUnit.derivedBaseUnit
											+ " " + toUnit + " -> " + toUnit.derivedBaseUnit);
		}
	}
	
	//--------------------------------------------------------------------------------
	// Converters
	
	public static double convert (double val, AbstractUnit fromUnit, AbstractUnit toUnit) {	
		// same unit, no conversion necessary
		if (fromUnit == toUnit) {
			return val;
		}
		
		prepareConversion(fromUnit, toUnit);

		double fromConversionFactor = fromUnit.derivedBaseConversionFactor;
		double toConversionFactor = toUnit.derivedBaseConversionFactor;

		// same conversion factor, no conversion necessary
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
		// same unit, no conversion necessary
		if (fromUnit == toUnit) {
			return val;
		}
		
		prepareConversion(fromUnit, toUnit);

		double fromConversionFactor = fromUnit.derivedBaseConversionFactor;
		double toConversionFactor = toUnit.derivedBaseConversionFactor;

		// same conversion factor, no conversion necessary
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
		return Math.round(quot);
	}

	public static int convert (int val, AbstractUnit fromUnit, AbstractUnit toUnit) {
		// same unit, no conversion necessary
		if (fromUnit == toUnit) {
			return val;
		}
		
		prepareConversion(fromUnit, toUnit);

		double fromConversionFactor = fromUnit.derivedBaseConversionFactor;
		double toConversionFactor = toUnit.derivedBaseConversionFactor;

		// same conversion factor, no conversion necessary
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
		return (int) Math.round(quot);
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

