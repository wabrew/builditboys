package com.builditboys.misc.cooking;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import static com.builditboys.misc.cooking.SimpleXMLTestObject.*;

public class SimpleXMLTest {

	public static void main (String args[]) throws Exception {
		testSimpleXML();
	}
	
	public static void testSimpleXML () throws Exception {
		Serializer serializer = new Persister();
		File dir = new File("Z:/Users/Bill/recipes");
		File file = new File(dir, "object.xml");

		SimpleXMLTestObject originalObject = new SimpleXMLTestObject("xx", 42, AnEnum.B);
		SimpleXMLTestObject restoredObject = null;
		
		System.out.println(originalObject);
		originalObject.show();
		
		System.out.println("Writing " + file);
		serializer.write(originalObject, file);
		System.out.println("Wrote " + file);
		
		restoredObject = serializer.read(SimpleXMLTestObject.class, file);
		System.out.println(restoredObject);
		restoredObject.show();
				
	}

}
