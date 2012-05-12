package com.builditboys.misc.cooking;

import org.simpleframework.xml.Element;

class SimpleXMLPhoto {

	@Element
	String caption;
	
	@Element
	String fileName;
	
	@Element
	int height;
	
	@Element
	int width;

	// --------------------------------------------------------------------------------
	// Constructors
	
	// Else you get a constructor not matched exception
	SimpleXMLPhoto() {
		
	}

	SimpleXMLPhoto(String caption, String fileName, int height, int width) {
		super();
		this.caption = caption;
		this.fileName = fileName;
		this.height = height;
		this.width = width;
	}

	// --------------------------------------------------------------------------------

	
	static SimpleXMLPhoto constructXMLPhoto (Photo photo) {
		SimpleXMLPhoto xmlPhoto = new SimpleXMLPhoto(photo.caption,
													 photo.fileName,
													 photo.height,
													 photo.width);	
		return xmlPhoto;
	}
	
	static Photo constructPhoto (SimpleXMLPhoto xmlPhoto) {
		Photo photo = new Photo(xmlPhoto.caption,
								xmlPhoto.fileName,
								xmlPhoto.height,
								xmlPhoto.width);
		return photo;
	}

}
