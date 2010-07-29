//: com:bruceeckel:util:Collections2.java
// To fill any type of container using a generator object.
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
package org.vito.util;

import java.util.*;

public class Collections2 {
	/**
	 * Fill an array using a generator:
	 * @param c Collection
	 * @param gen Generator
	 * @param count
	 */
	public static void fill(Collection c, Generator gen, int count) {
		for (int i = 0; i < count; i++)
			c.add(gen.next());
	}
	
	/**
	 * 填充一个Map映射.
	 * @param m Map
	 * @param gen MapGenerator
	 * @param count
	 */
	public static void fill(Map m, MapGenerator gen, int count) {
		for (int i = 0; i < count; i++) {
			Pair p = gen.next();
			m.put(p.key, p.value);
		}
	}
	
	/**
	 * 随机字符串对的生成器.
	 * @author vito
	 *
	 */
	public static class RandStringPairGenerator implements MapGenerator {
		private Arrays2.RandStringGenerator gen;
		
		/**
		 * 给定字符串长度的构造方法.
		 * @param len
		 */
		public RandStringPairGenerator(int len) {
			gen = new Arrays2.RandStringGenerator(len);
		}
		
		/**
		 * 生成字符串映射的下一项.
		 */
		public Pair next() {
			return new Pair(gen.next(), gen.next());
		}
	}

	// Default object so you don't have to create your own:
	/**
	 * 随机字符串配对的生成器,每个字符串长度为10.
	 */
	public static RandStringPairGenerator rsp = new RandStringPairGenerator(10);

	/**
	 * 字符串配对的生成器
	 * @author vito
	 *
	 */
	public static class StringPairGenerator implements MapGenerator {
		private int index = -1;
		private String[][] d;
		
		/**
		 * 给定一个2维字符串数组的构造方法.
		 * @param data
		 */
		public StringPairGenerator(String[][] data) {
			d = data;
		}
		
		/**
		 * 生成下一项配对.
		 * 第二维的第一个下标是对应的键,第二个下标是对应的值.
		 */
		public Pair next() {
			// Force the index to wrap:
			index = (index + 1) % d.length;
			return new Pair(d[index][0], d[index][1]);
		}

		public StringPairGenerator reset() {
			index = -1;
			return this;
		}
	}

	// Use a predefined dataset:
	/**
	 * 字符串对的生成器,生成国家,首都的对应关系.
	 */
	public static StringPairGenerator geography = new StringPairGenerator(
			CountryCapitals.pairs);

	/**
	 * Produce a sequence from a 2D array:
	 * @author vito
	 *
	 */
	public static class StringGenerator implements Generator {
		private String[][] d;
		private int position;
		private int index = -1;
		
		/**
		 * 构造方法,给定一个2维数组和第二维的位置下标.
		 * @param data 2维数组
		 * @param pos 第二维的位置下标
		 */
		public StringGenerator(String[][] data, int pos) {
			d = data;
			position = pos;
		}
		
		/**
		 * 以给定的第二维下标,生成下一项.
		 */
		public Object next() {
			// Force the index to wrap:
			index = (index + 1) % d.length;
			return d[index][position];
		}

		public StringGenerator reset() {
			index = -1;
			return this;
		}
	}

	// Use a predefined dataset:
	/**
	 * 字符串生成器,生成国家名.
	 */
	public static StringGenerator countries = new StringGenerator(
			CountryCapitals.pairs, 0);
	
	/**
	 * 字符串生成器,生成首都名.
	 */
	public static StringGenerator capitals = new StringGenerator(
			CountryCapitals.pairs, 1);
} // /:~
