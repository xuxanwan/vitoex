package org.vito.c5;

//: c05:ChocolateChip.java
// Can't use package-access member from another package.
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
import org.vito.simpletest.*;
import org.vito.c5.dessert.*;

public class ChocolateChip extends Cookie {
  private static Test monitor = new Test();
  public ChocolateChip() {
   System.out.println("ChocolateChip constructor");
  }
  public static void main(String[] args) {
    ChocolateChip x = new ChocolateChip();
    // x.bite(); // Can't access bite
    x.biteWithPro();
    
    /*
    monitor.expect(new String[] {
      "Cookie constructor",
      "ChocolateChip constructor"
    });*/
  }
} ///:~
