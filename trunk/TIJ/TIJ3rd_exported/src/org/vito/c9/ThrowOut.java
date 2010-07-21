package org.vito.c9;

//: c09:ThrowOut.java
// {ThrowsException}
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
public class ThrowOut {
  public static void
  main(String[] args) throws Throwable {
    try {
      throw new Throwable();
    } catch(Exception e) {
      //catch(Throwable e){
      System.err.println("Caught in main()");
    }
  }
} ///:~
