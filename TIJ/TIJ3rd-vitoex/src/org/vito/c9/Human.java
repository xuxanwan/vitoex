package org.vito.c9;

//: c09:Human.java
// Catching exception hierarchies.
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
import org.vito.simpletest.*;

class Annoyance extends Exception {}
class Sneeze extends Annoyance {}

/*
 * 把派生类异常放在前面, 基类异常放在后面
 * @author vito
 *
 */
public class Human {
  private static Test monitor = new Test();
  public static void main(String[] args) {
    try {
      throw new Sneeze();
//    } catch(Sneeze s) {
//      System.err.println("Caught Sneeze");
    } catch(Annoyance a) {
      System.err.println("Caught Annoyance");
    }
    /*
    monitor.expect(new String[] {
      "Caught Sneeze"
    }); */
  }
} ///:~
