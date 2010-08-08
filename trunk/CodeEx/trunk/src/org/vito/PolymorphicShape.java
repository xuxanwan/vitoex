package org.vito;

import org.vito.dummy.Point;
import org.vito.dummy.Shape;

/**
 * Intercepted from Clean Code listing 6-6.
 * 
 * Here the area() method is polymorphic. No Geometry class is necessary. So if
 * I add a new shape, none of the existing functions are affected, but if I add
 * a new function all of the shapes must be changed!
 *
 * @author vito
 * 
 */
public class PolymorphicShape {
	public class Square implements Shape {
		private Point topLeft;
		private double side;

		public double area() {
			return side * side;
		}
	}

	public class Rectangle implements Shape {
		private Point topLeft;
		private double height;
		private double width;

		public double area() {
			return height * width;
		}
	}

	public class Circle implements Shape {
		private Point center;
		private double radius;
		public final double PI = 3.141592653589793;

		public double area() {
			return PI * radius * radius;
		}
	}
}
