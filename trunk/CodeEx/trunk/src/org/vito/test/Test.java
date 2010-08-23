package org.vito.test;

/**
 * 
 * @author vito
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String []strArray = new String[3];
		for(String s : strArray){
			System.out.println(s);
		}
		
		System.out.println(Math.log10(2147483648d)/Math.log10(2));
		System.out.println(Math.log10(12870d)/Math.log10(2));
		
		System.out.println("Hello " + 33);
	}

}
