package org.vito.chap3;
//: c03:URShift.java
//Test of unsigned right shift.
//From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
//www.BruceEckel.com. See copyright notice in CopyRight.txt.
import org.vito.simpletest.Test;

/**
 * 如果对char、byte或者short类型的数值进行移位处理，那么在移位进行之前，它们会自动转换为int，
 * 并且得到的结果也是一个int类型的值。
 * @author vito
 *
 */
public class URShift {          // 无符号右移
  static Test monitor = new Test();
  public static void main(String[] args) {
    int i = -1;
    System.out.println(i >>>= 10);
    long l = -1;
    System.out.println(l >>>= 10);
    
    short s = -1;
    System.out.println(s >>>= 10);
    byte b = -1;
    System.out.println(b >>>= 10);
    
    b = -1;
    System.out.println(b>>>10);
    
    monitor.expect(new String[] {
      "4194303",
      "18014398509481983",
      "-1",
      "-1",
      "4194303"
    });
  }
} ///:~
