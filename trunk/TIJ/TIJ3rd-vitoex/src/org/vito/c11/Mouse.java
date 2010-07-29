//: c11:Mouse.java
// Overriding toString().
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
package org.vito.c11;

/**
 * 重载了Object.toString()方法.
 * @author vito
 *
 */
public class Mouse {
	private int mouseNumber;

	public Mouse(int i) {
		mouseNumber = i;
	}

	/**
	 * Override Object.toString():
	 */
	public String toString() {
		return "This is Mouse #" + mouseNumber;
	}

	public int getNumber() {
		return mouseNumber;
	}
} // /:~
