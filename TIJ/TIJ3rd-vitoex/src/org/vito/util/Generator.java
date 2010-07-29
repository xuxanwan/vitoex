//: com:bruceeckel:util:Generator.java
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
package org.vito.util;

/**
 * 生成器接口.
 * @author vito
 *
 */
public interface Generator {
	/**
	 * 生成下一个项.
	 * @return Object 对象.
	 */
	Object next();
} // /:~
