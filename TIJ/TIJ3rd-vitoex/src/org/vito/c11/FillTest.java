package org.vito.c11;

//: c11:FillTest.java
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
import org.vito.util.*;
import java.util.*;

public class FillTest {
	/**
	 * 随机字符串生成器,每个串长度为7.
	 */
	private static Generator sg = new Arrays2.RandStringGenerator(7);

	public static void main(String[] args) {
		List list = new ArrayList();
		Collections2.fill(list, sg, 5);
		System.out.println(list + "\n");
		
		List list2 = new ArrayList();
		Collections2.fill(list2, Collections2.capitals, 5);
		System.out.println(list2 + "\n");
		
		Set set = new HashSet();
		Collections2.fill(set, sg, 25);
		System.out.println(set + "\n");
		
		Map m = new HashMap();
		Collections2.fill(m, Collections2.rsp, 5);
		System.out.println(m + "\n");
		
		Map m2 = new HashMap();
		Collections2.fill(m2, Collections2.geography, 5);
		System.out.println(m2);
	}
} // /:~