//: c11:MouseTrap.java
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
package org.vito.c11;

/**
 * 抓老鼠~
 * @author vito
 *
 */
public class MouseTrap {
	/**
	 * 将传递的Object参数,类型转换为Mouse.
	 * 打印老鼠的当前编号.
	 * @param m 
	 */
	static void caughtYa(Object m) {
		Mouse mouse = (Mouse) m; // Cast from Object
//		System.out.println(m);
		System.out.println("Mouse: " + mouse.getNumber());
	}
} // /:~