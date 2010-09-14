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
		System.out.println("\u563F\u563f");
		String str = "Hello"; 
		char []ch = str.toCharArray();
		str = str.replace('H', 'h');
		//System.out.println(ch[-1]);
		System.out.println(str);
		
		System.out.println('\0');
		System.out.println("Test a 'con'");
		
		String a = "add dd";
		String b = a;
		//b = "bbbbb";
		System.out.println(a);
		System.out.println(b);
		
		Test test = new Test();
		System.out.println("The class of " + test + " is " + test.getClass().getName());
		System.out.println("The class of Test is " + Test.class.getName());
	}

}
