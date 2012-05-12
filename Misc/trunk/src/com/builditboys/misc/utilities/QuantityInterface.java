package com.builditboys.misc.utilities;

public interface QuantityInterface {
	
/*
	QuantityInterface make(byte b);
	QuantityInterface make(short s);
	QuantityInterface make(int i);
	QuantityInterface make(long l);
	QuantityInterface make(float f);
	QuantityInterface make(double f);
	
	QuantityInterface add(int i);
	QuantityInterface sub(int i);
	QuantityInterface mul(int i);
	QuantityInterface div(int i);
	QuantityInterface reciprocal();
	
	QuantityInterface parseQuantity (String string);

*/	
	
	QuantityInterface add(QuantityInterface f);

	QuantityInterface sub(QuantityInterface f);

	QuantityInterface mul(QuantityInterface f);

	QuantityInterface div(QuantityInterface f);
	
	
	QuantityInterface negate();
	
	QuantityInterface abs();

	
	int compareTo (QuantityInterface f);
	
	boolean equals (Object other);

	int hashCode();
	
	String toString();
		
	
	byte byteValue();
	short shortValue();
	int intValue();
	long longValue();
	float floatValue();	
	double doubleValue();
	
}
