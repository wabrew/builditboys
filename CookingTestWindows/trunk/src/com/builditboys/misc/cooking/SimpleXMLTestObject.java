package com.builditboys.misc.cooking;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root
public class SimpleXMLTestObject {

	
	enum AnEnum { A, B };
	
	@Element
	String stringVar;
	
	@Element
	int intVar;
		
	@Element
	AnEnum enumVar;
		
	public SimpleXMLTestObject() {
		
	}
	
	public SimpleXMLTestObject(String stringVar, int intVar, AnEnum enumVar) {
		super();
		this.stringVar = stringVar;
		this.intVar = intVar;
		this.enumVar = enumVar;
	}


	void show () {
		System.out.println("A C1 object");
		System.out.println("stringVar = " + stringVar);
		System.out.println("intVar = " + intVar);
		System.out.println("enumVar = " + enumVar);
	}
	

}
