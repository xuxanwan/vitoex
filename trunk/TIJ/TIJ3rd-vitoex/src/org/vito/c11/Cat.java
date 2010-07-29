//: c11:Cat.java
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
package org.vito.c11;

/**
 * 
 * @author vito
 *
 */
public class Cat {
	private int catNumber;
	
	/**
	 * 给定猫编号的构造方法.
	 * @param i
	 */
	public Cat(int i) {
		catNumber = i;
	}
	
	/**
	 * 打印当前猫的编号.
	 */
	public void id() {
		System.out.println("Cat #" + catNumber);
	}
} // /:~
