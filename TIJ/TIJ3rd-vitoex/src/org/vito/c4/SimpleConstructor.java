package org.vito.c4;

//: c04:SimpleConstructor.java
// Demonstration of a simple constructor.
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
import org.vito.simpletest.*;

class Rock {
    Rock() { // This is the constructor
        System.out.println("Creating Rock");
    }
}

public class SimpleConstructor {
    static Test monitor = new Test();
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.print("i = " + i +"\t");
            new Rock();
        }
        /*
        monitor.expect(new String[] {
          "Creating Rock",
          "Creating Rock",
          "Creating Rock",
          "Creating Rock",
          "Creating Rock",
          "Creating Rock",
          "Creating Rock",
          "Creating Rock",
          "Creating Rock",
          "Creating Rock"
        });*/
        monitor.expect(new String[] {
            "i = 0\tCreating Rock",
            "i = 1\tCreating Rock",
            "i = 2\tCreating Rock",
            "i = 3\tCreating Rock",
            "i = 4\tCreating Rock",
            "i = 5\tCreating Rock",
            "i = 6\tCreating Rock",
            "i = 7\tCreating Rock",
            "i = 8\tCreating Rock",
            "i = 9\tCreating Rock"
        });
    }
} ///:~
