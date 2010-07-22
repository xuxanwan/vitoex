package org.vito.chap3;

//: c03:Overflow.java
// Surprise! Java lets you overflow.
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
import org.vito.simpletest.*;

/**
 * 如果对两个足够大的int值执行乘法运算，结果就会溢出.
 * 
 * @author vito
 *
 */
public class Overflow {
  static Test monitor = new Test();
  public static void main(String[] args) {
    int big = 0x7fffffff; // max int value
    System.out.println("big = " + big);
    int bigger = big * 4;
    System.out.println("bigger = " + bigger);
    monitor.expect(new String[] {
      "big = 2147483647",
      "bigger = -4"
    });
  }
} ///:~
