package org.vito.test;

public class ExceptionOrder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			int x = 1/0;
		}finally{
			System.out.println("Cleaning up...");			
		}
	}

}
