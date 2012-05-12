package com.builditboys.misc.cooking;

import java.util.Comparator;


class Note {
	
	String who;
	
	long when;
	
	String text;
	
	// --------------------------------------------------------------------------------
	// Constructor
	
	Note(String who, long when, String text) {
		super();
		if (who == null) {
			throw new IllegalArgumentException("Acknowledgement has null who");
		}
		this.who = who;
		this.when = when;
		this.text = text;
	}

	// --------------------------------------------------------------------------------
	// Comparators
	
	static int compareLong (long l1, long l2) {
		if (l1 < l2) {
			return -1;
		}
		else if (l1 > l2) {
			return +1;
		}
		else return 0;
	}

	static class TimeAscendingComparator implements Comparator<Note> {
		public int compare(Note note1, Note note2) {
			return compareLong(note1.when,note2.when);
		}
	}
	
	static Comparator<Note> timeAscendingComparator = new TimeAscendingComparator();

	// --------------------------------------------------------------------------------

	public String toString () {
		return "Note: " + who + ", " + when + ", " + text;
	}
	
}
