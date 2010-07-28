package org.vito.c4;

import org.vito.simpletest.Test;

/**
 * 即使是形式参数的顺序不同,也可以区分重载.
 * 但是不推荐,会使代码难以维护.
 * @author vito
 *
 */
public class OverloadingOrder {
	static Test monitor = new Test();

	static void print(String s, int i) {
		System.out.println("String: " + s + ", int: " + i);
	}

	static void print(int i, String s) {
		System.out.println("int: " + i + ", String: " + s);
	}

	public static void main(String[] args) {
		print("String first", 11);
		print(99, "Int first");
		monitor.expect(new String[] { "String: String first, int: 11",
				"int: 99, String: Int first" });
	}
} // /:~
