package com.builditboys.misc.units;


public class TimeUnits extends AbstractUnit {

	public static final TimeUnits SECOND =      
			new TimeUnits("second", "seconds", "sec", 1.0, null);

	public static final TimeUnits MILLISECOND = 
			new TimeUnits("millisecond", "milliseconds", "msec", 1.0E-3, SECOND);

	public static final TimeUnits MICROSECOND = 
			new TimeUnits("microsecond", "microseconds", "usec", 1.0E-6, SECOND);

	public static final TimeUnits NANOSECOND = 
			new TimeUnits("nanosecond", "nanoseconds", "nsec", 1.0E-9, SECOND);
	
	public static final TimeUnits MINUTE =      
			new TimeUnits("minute", "minutess", "min", 60.0, SECOND);
	
	public static final TimeUnits HOUR =      
			new TimeUnits("hour", "hours", "hr", 60.0 * 60.0, SECOND);
	
	public static final TimeUnits DAY =      
			new TimeUnits("day", "days", "dy", 24.0, HOUR);
	
	public static final TimeUnits YEAR =      
			new TimeUnits("year", "years", "yr", 365.25, DAY);
	
	
	//--------------------------------------------------------------------------------
    // Constructor
	
	private TimeUnits (String name, String plural, String abbreviation,
					   double conversionFactor, AbstractUnit baseUnit) {
		super(name, plural, abbreviation,
			  UnitKindEnum.TIME, conversionFactor, baseUnit);
	}
	
}

