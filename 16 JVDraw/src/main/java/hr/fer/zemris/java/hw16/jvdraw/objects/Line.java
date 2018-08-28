package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.editors.LineEditor;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * Models Line geometrical object.
 * Line has starting coordinates, ending coordinates and color.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class Line extends GeometricalObject {
	/** 
	 * X coordinate of starting point.
	 */
	private int x1;
	/** 
	 * Y coordinate of starting point.
	 */
	private int y1;
	/** 
	 * X coordinate of ending point.
	 */
	private int x2;
	/** 
	 * Y coordinate of ending point.
	 */
	private int y2;
	/**
	 * Line color.
	 */
	private Color color;
		
	/**
	 * Default constructor for Line.
	 * @param x1 starting point x
	 * @param y1 starting point y
	 * @param x2 ending point x
	 * @param y2 ending point y
	 * @param color line color
	 */
	public Line(int x1, int y1, int x2, int y2, Color color) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
	}
	
	/**
	 * Getter for x1.
	 * @return x1
	 */
	public int getX1() {
		return x1;
	}
	
	/**
	 * Getter for y1.
	 * @return y1
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * Getter for x2.
	 * @return x2
	 */
	public int getX2() {
		return x2;
	}

	/**
	 * Getter for y2.
	 * @return y2
	 */
	public int getY2() {
		return y2;
	}

	/**
	 * Getter for color.
	 * @return line color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for x1.
	 * @param x1 new x1
	 */
	public void setX1(int x1) {
		this.x1 = x1;
		notifyObjectChanged(this);
	}

	/**
	 * Setter for y1.
	 * @param y1 new y1
	 */
	public void setY1(int y1) {
		this.y1 = y1;
		notifyObjectChanged(this);
	}

	/**
	 * Setter for x2.
	 * @param x2 new x2
	 */
	public void setX2(int x2) {
		this.x2 = x2;
		notifyObjectChanged(this);
	}

	/**
	 * Setter for y2.
	 * @param y2 new y2
	 */
	public void setY2(int y2) {
		this.y2 = y2;
		notifyObjectChanged(this);
	}
	
	/**
	 * Setter for line color.
	 * @param color new line color
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
		return new LineEditor(this);
	}
	
	@Override
	public String toString() {
		return String.format("Line(%d,%d)-(%d,%d)", x1, y1, x2, y2);
	}
}
