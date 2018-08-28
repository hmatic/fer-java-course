package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.editors.CircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * Models Circle geometrical object.
 * Circle has center coordinates, radius and color.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class Circle extends GeometricalObject {
	/**
	 * X coordinate of circle center.
	 */
	private int x;
	/**
	 * Y coordinate of circle center.
	 */
	private int y;
	/**
	 * Circle radius.
	 */
	private int radius;
	/**
	 * Circle stroke color.
	 */
	private Color color;

	/**
	 * Default constructor for Circle.
	 * @param x x coordinate of circle center
	 * @param y y coordinate of circle center
	 * @param radius circle radius
	 * @param color circle stroke color
	 */
	public Circle(int x, int y, int radius, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = color;
	}

	/**
	 * Getter for X coordinate of circle center.
	 * @return X coordinate of circle center
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter for Y coordinate of circle center.
	 * @return Y coordinate of circle center
	 */
	public int getY() {
		return y;
	}

	/**
	 * Getter for circle radius.
	 * @return circle radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Getter for circle color.
	 * @return circle color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for X coordinate of circle center.
	 * @param x new X coordinate of circle center.
	 */
	public void setX(int x) {
		this.x = x;
		notifyObjectChanged(this);
	}

	/**
	 * Setter for Y coordinate of circle center.
	 * @param y new Y coordinate of circle center.
	 */
	public void setY(int y) {
		this.y = y;
		notifyObjectChanged(this);
	}
	
	/**
	 * Setter for circle radius.
	 * @param radius new circle radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
		notifyObjectChanged(this);
	}

	/**
	 * Setter for circle color.
	 * @param color new circle color
	 */
	public void setColor(Color color) {
		this.color = color;
		notifyObjectChanged(this);
	}



	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
		
	}
	
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}
	
	@Override
	public String toString() {
		return String.format("Circle(%d,%d), %d", x, y, radius);
	}
}
