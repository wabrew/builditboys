package com.builditboys.misc.utilities;

public class Math {
	
	public static int gcd (int a, int b) {
		int temp;
		while (b != 0) {
			temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}
}
