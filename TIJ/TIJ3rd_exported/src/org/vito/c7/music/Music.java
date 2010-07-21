//: c07:music:Music.java
// Inheritance & upcasting.
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
package org.vito.c7.music;
import org.vito.simpletest.*;

public class Music {
  private static Test monitor = new Test();
  public static void tune(Instrument i) {
    // ...
    i.play(Note.MIDDLE_C);
  }
  public static void main(String[] args) {
    Wind flute = new Wind();
    tune(flute); // Upcasting
    monitor.expect(new String[] {
      "Wind.play() Middle C"
    });
  }
} ///:~
