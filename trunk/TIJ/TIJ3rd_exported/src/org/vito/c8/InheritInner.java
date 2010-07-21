package org.vito.c8;

//: c08:InheritInner.java
// Inheriting an inner class.
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.

class WithInner {
  class Inner {}
}

/**
 * 继承一个内部类.
 */
public class InheritInner extends WithInner.Inner {
  // InheritInner() {} // Won't compile
  InheritInner(WithInner wi) {
    wi.super();
  }
  public static void main(String[] args) {
    WithInner wi = new WithInner();
    InheritInner ii = new InheritInner(wi);
  }
} ///:~
