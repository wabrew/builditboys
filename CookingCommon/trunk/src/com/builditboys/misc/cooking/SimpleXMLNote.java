package com.builditboys.misc.cooking;

import org.simpleframework.xml.Element;

class SimpleXMLNote {

	@Element
	String who;
	
	@Element
	long when;
	
	@Element
	String text;
	
	// --------------------------------------------------------------------------------
	// Constructors
	
	// Else you get a constructor not matched exception
	SimpleXMLNote() {
		super();
	}
	
	SimpleXMLNote(String who, long when, String text) {
		super();
		this.who = who;
		this.when = when;
		this.text = text;
	}	
	
	// --------------------------------------------------------------------------------

	static SimpleXMLNote constructXMLNote (Note note) {
		SimpleXMLNote xmlNote = new SimpleXMLNote(note.who, note.when, note.text);	
		return xmlNote;
	}
	
	static Note constructNote (SimpleXMLNote xmlNote) {
		Note note = new Note(xmlNote.who, xmlNote.when, xmlNote.text);
		return note;
	}
}
