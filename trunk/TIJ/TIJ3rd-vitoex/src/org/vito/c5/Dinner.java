package org.vito.c5;

//: c05:Dinner.java
// Uses the library.
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
import org.vito.simpletest.*;
import org.vito.c5.dessert.*;

public class Dinner {
  static Test monitor = new Test();
  public Dinner() {
   System.out.println("Dinner constructor");
  }
  public static void main(String[] args) {
    Cookie x = new Cookie();
    // x.bite(); // Can't access
    monitor.expect(new String[] {
      "Cookie constructor"
    });
  }
} ///:~
