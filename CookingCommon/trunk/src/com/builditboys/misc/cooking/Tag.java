package com.builditboys.misc.cooking;

class Tag {
	
	private String kind;
	
	private String value;

	// --------------------------------------------------------------------------------
	// Constructor

	Tag(String kind, String value) {
		super();
		if (kind == null) {
			throw new IllegalArgumentException("Tag has null kind");
		}
		if (value == null) {
			throw new IllegalArgumentException("Tag has null value");
		}
		this.kind = canonicalizeKind(kind);
		this.value = canonicalizeValue(value);
	}
	
	// --------------------------------------------------------------------------------

	String getKind() {
		return kind;
	}

	void setKind(String kind) {
		this.kind = canonicalizeKind(kind);
	}	
	
	String getValue() {
		return value;
	}

	void setValue(String value) {
		this.value = canonicalizeValue(value);
	}	
	
	// --------------------------------------------------------------------------------

	public String toString () {
		return "Tag: " + kind + " = " + value;
	}
	
	// --------------------------------------------------------------------------------

	boolean isKindMatch(Tag otherTag) {
		return (kind == otherTag.kind);
	}
	
	// --------------------------------------------------------------------------------

	String canonicalizeKind(String original) {
		return canonicalize(original);
	}
	
	String canonicalizeValue(String original) {
		return canonicalize(original);
	}

	
	private String canonicalize(String original){
	    if(original.length() == 0)
	        return original;
	    return original.substring(0, 1).toUpperCase()
	    	   + original.substring(1).toLowerCase();
	}
	
}
