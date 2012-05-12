package com.builditboys.misc.cooking;

import org.simpleframework.xml.Element;

class SimpleXMLTag {
	
	@Element
	String kind;
	
	@Element
	String value;

	// --------------------------------------------------------------------------------
	// Constructors
	
	// Else you get a constructor not matched exception
	SimpleXMLTag() {
		super();
	}

	SimpleXMLTag(String kind, String value) {
		super();
		this.kind = kind;
		this.value = value;
	}
	
	SimpleXMLTag(Tag tag) {
		super();
		kind = tag.getKind();
		value = tag.getValue();
	}

	// --------------------------------------------------------------------------------

	static SimpleXMLTag constructXMLTag (Tag tag) {
		SimpleXMLTag xmlTag = new SimpleXMLTag(tag);
		return xmlTag;
	}
	
	static Tag constructTag (SimpleXMLTag xmlTag) {
		Tag tag = new Tag(xmlTag.kind, xmlTag.value);
		return tag;
	}

}
