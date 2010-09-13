package org.vito.c10;

//: c10:Shapes.java
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
import org.vito.simpletest.*;

/**
 * 
 * 多态和向上转型的示例.
 * 基类Shape,派生类Circle,Square,Triangle.
 *
 */

class Shape {
	//传递this,间接使用toString()打印类标示符.
	void draw() {
		System.out.println(this + ".draw()");
	}
}

class Circle extends Shape {
	public String toString() {
		return "Circle";
	}
}

class Square extends Shape {
	public String toString() {
		return "Square";
	}
}

class Triangle extends Shape {
	public String toString() {
		return "Triangle";
	}
}

public class Shapes {
	private static Test monitor = new Test();

	public static void main(String[] args) {
		// Array of Object, not Shape:
		Object[] shapeList = { new Shape(), new Circle(), new Square(), new Triangle() };
		for (int i = 0; i < shapeList.length; i++)
			((Shape) shapeList[i]).draw(); // Must cast
		
//		monitor.expect(new String[] { "Circle.draw()", "Square.draw()",
//				"Triangle.draw()" });
	}
} // /:~
