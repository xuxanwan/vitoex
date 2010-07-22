package org.vito.c9;

//: c09:FullConstructors.java
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
import org.vito.simpletest.*;

class MyException extends Exception {
  public MyException() {}
  public MyException(String msg) { super(msg); }
}

public class FullConstructors {
  private static Test monitor = new Test();
  public static void f() throws MyException {
    System.out.println("Throwing MyException from f()");
    throw new MyException();
  }
  public static void g() throws MyException {
    System.out.println("Throwing MyException from g()");
    throw new MyException("Originated in g()");
  }
  public static void main(String[] args) {
    try {
      f();
    } catch(MyException e) {
      e.printStackTrace();
    }
    try {
      g();
    } catch(MyException e) {
      e.printStackTrace();
    }
    monitor.expect(new String[] {
      "Throwing MyException from f()",
      
      //"MyException",
      "org.vito.c9.MyException",
      
      //"%% \tat FullConstructors.f\\(.*\\)",
      "%% \tat org.vito.c9.FullConstructors.f\\(.*\\)",
      
      //"%% \tat FullConstructors.main\\(.*\\)",
      "%% \tat org.vito.c9.FullConstructors.main\\(.*\\)",
      
      "Throwing MyException from g()",      
      
      //"MyException: Originated in g()",
      "org.vito.c9.MyException: Originated in g()",
      
      //"%% \tat FullConstructors.g\\(.*\\)",
      "%% \tat org.vito.c9.FullConstructors.g\\(.*\\)",
      
      //"%% \tat FullConstructors.main\\(.*\\)"
      "%% \tat org.vito.c9.FullConstructors.main\\(.*\\)"
    });
  }
} ///:~
