//: c11:Dog.java
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
package org.vito.c11;

public class Dog {
	private int dogNumber;
	
	/**
	 * 给定狗编号的构造方法.
	 * @param i
	 */
	public Dog(int i) {
		dogNumber = i;
	}
	
	/**
	 * 打印当前狗的编号.
	 */
	public void id() {
		System.out.println("Dog #" + dogNumber);
	}
} // /:~
