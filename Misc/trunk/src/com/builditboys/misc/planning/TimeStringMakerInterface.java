package com.builditboys.misc.planning;

public interface TimeStringMakerInterface {
	
	String makeDurationTimeString (long time);
	
	String makeRelativeTimeString (long time, long relativeAdjustment);
	
	String makeAbsoluteTimeString (long time, long absoluteAdjustment);

}
