package com.builditboys.misc.cooking;

public class Photo {
	
	String caption;
	
	String fileName;
	
	int height;
	int width;

	// --------------------------------------------------------------------------------
	// Constructor

	public Photo(String caption, String fileName, int height, int width) {
		super();
		this.caption = caption;
		this.fileName = fileName;
		this.height = height;
		this.width = width;
	}
	
	// --------------------------------------------------------------------------------
	
	public String toString () {
		return "Photo: " + caption + ", " + fileName + ", " + height + ", " + width;
	}

}
