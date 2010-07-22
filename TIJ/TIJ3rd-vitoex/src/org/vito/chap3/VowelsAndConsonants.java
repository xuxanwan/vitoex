package org.vito.chap3;

//: c03:VowelsAndConsonants.java
// Demonstrates the switch statement.
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
import org.vito.simpletest.*;

public class VowelsAndConsonants {  // 元音与辅音
  static Test monitor = new Test();
  public static void main(String[] args) {
    for(int i = 0; i < 100; i++) {
      /*
       * Math.random()会产生0到1之间的一个值，
       * 所以只需将其乘以想获得的数字范围的上界（对于英语字母，这个数字是26），
       * 再加上作为偏移量的数字范围下界，就可以得到合适的随机数
       */
      char c = (char)(Math.random() * 26 + 'a');
      System.out.print(c + ": ");
      switch(c) {
        case 'a':
        case 'e':
        case 'i':
        case 'o':
        case 'u': System.out.println("vowel");
                  break;
        case 'y':
        case 'w': System.out.println("Sometimes a vowel");
                  break;
        default:  System.out.println("consonant");
      }
      /*
      monitor.expect(new String[] {
    		  "%% [aeiou]: vowel|[yw]: Sometimes a vowel|" + 
    		  "[^aeiouyw]: consonant"}, Test.AT_LEAST);*/
    }    
  }
} ///:~
