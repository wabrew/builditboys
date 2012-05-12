package com.builditboys.misc.cooking;

import java.util.Formatter;

import com.builditboys.misc.planning.TimeStringMakerInterface;
import com.builditboys.misc.units.TimeUnits;

public class TimeStringMaker implements TimeStringMakerInterface {
	
	Formatter formatter = new Formatter();
	
	long days;
	long hours;
	long minutes;
	
	// --------------------------------------------------------------------------------


	@Override
	public String makeDurationTimeString(long time) {
		// Clear the buffer
		((StringBuilder)formatter.out()).setLength(0);
		decomposeDHM(time);
		return formatter.format("Day %+d %2d:%02d", days, hours, minutes).toString();
	}

	@Override
	public String makeRelativeTimeString(long time, long relativeAdjustment) {
		// Clear the buffer
		((StringBuilder)formatter.out()).setLength(0);
		decomposeDHM(time + relativeAdjustment);
		formatter.format("Day % d %2d:%02d", days, hours, minutes);
		if (hours < 12) {
			formatter.format(" AM");
		}
		else {
			formatter.format(" PM");
		}
		return formatter.toString();
	}
	
	@Override
	public String makeAbsoluteTimeString(long time, long absoluteAdjustment) {
		// Clear the buffer
		((StringBuilder)formatter.out()).setLength(0);
		return formatter.format("%tc", time + absoluteAdjustment).toString();	
	}

	// --------------------------------------------------------------------------------

	void decomposeDHM (long time) {
		boolean isNegative = (time < 0);
		if (isNegative) {
			time = -time;
		}

		// original time is in milliseconds, round to minutes
//		time = ((time + (30 * 1000)) / (60 * 1000));
		time = TimeUnits.convert(time, TimeUnits.MILLISECOND, TimeUnits.MINUTE);
		
		minutes = time % 60;
		time = time / 60;
		hours = time % 24;
		time = time / 24;
		days = time;
		if (isNegative) {
			days = -days;
		}
	}


}
