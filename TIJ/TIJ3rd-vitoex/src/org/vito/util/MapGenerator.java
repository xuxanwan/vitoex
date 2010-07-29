//: com:bruceeckel:util:MapGenerator.java
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
package org.vito.util;

/**
 * Map(对应/映射)生成器接口.
 * @author vito
 *
 */
public interface MapGenerator {
	/**
	 * 生成下一项名值对(Pair).
	 */
	Pair next();
} // /:~
