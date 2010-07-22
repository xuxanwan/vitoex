package org.vito.chap3;

import org.vito.simpletest.Test;

public class EqualsMethod {
  static Test monitor = new Test();
  public static void main(String[] args) {
    Integer n1 = new Integer(47);
    Integer n2 = new Integer(47);
    System.out.println(n1.equals(n2));
    monitor.expect(new String[] {
      "true"
    });
  }
} ///:~
