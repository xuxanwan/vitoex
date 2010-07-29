//: c11:CatsAndDogs.java
// Simple container example.
// {ThrowsException}
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
package org.vito.c11;

import java.util.*;

/**
 * "容器的缺点：未知类型"的示例.
 * 将对象的引用加入容器时就丢失了类型的信息,在使用容器中的元素前必须要做类型转换操作.
 * Cat和Dog被添入容器，然后再取出来.
 * @author vito
 *
 */
public class CatsAndDogs {
	public static void main(String[] args) {
		List cats = new ArrayList();
		for (int i = 0; i < 7; i++){
			cats.add(new Cat(i));
		}
		
		// Not a problem to add a dog to cats:
		cats.add(new Dog(7));		
		
		for (int i = 0; i < cats.size(); i++){
			((Cat) cats.get(i)).id();  //得到的只是Object的引用，需要做类型转换为Cat
		}
		// Dog is detected only at run time
	}
} // /:~
