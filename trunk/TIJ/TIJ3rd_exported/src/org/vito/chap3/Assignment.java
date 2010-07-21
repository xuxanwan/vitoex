package org.vito.chap3;

//: c03:Assignment.java
// Assignment with objects is a bit tricky.
import org.vito.simpletest.*;

class Number {
	int i;
}
/**
 * 赋值, 用对象给对象赋值, 是把对象的引用进行传递.
 * @author vito
 *
 */
public class Assignment {
	static Test monitor = new Test();
	
	public static void main(String[] args) {
		Number n1 = new Number();
		Number n2 = new Number();
		n1.i = 9;
		n2.i = 47;
		System.out.println("1: n1.i: " + n1.i +	", n2.i: " + n2.i);
		n1 = n2;  // 对象间的赋值,传递 引用
		System.out.println("2: n1.i: " + n1.i +	", n2.i: " + n2.i);
		n1.i = 27; //由于赋值行为操作的是一个对象的引用, 修改n1的同时也改变了n2
		System.out.println("3: n1.i: " + n1.i +	", n2.i: " + n2.i);
		
		monitor.expect(new String[] {
			"1: n1.i: 9, n2.i: 47",
			"2: n1.i: 47, n2.i: 47",
			"3: n1.i: 27, n2.i: 27"
		});
	}
} ///:~



