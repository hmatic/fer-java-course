package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * Models FilledCircle geometrical object.
 * Circle has center coordinates, radius, stroke color and fill color.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class FilledCircle extends GeometricalObject {
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
	private Color fgColor;
	/**
	 * Circle fill color.
	 */
	private Color bgColor;
	
	/**
	 * Default constructor for FilledCircle.
	 * @param x x coordinate of circle center
	 * @param y y coordinate of circle center
	 * @param radius circle radius
	 * @param fgColor stroke color
	 * @param bgColor fill color
	 */
	public FilledCircle(int x, int y, int radius, Color fgColor, Color bgColor) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.fgColor = fgColor;
		this.bgColor = bgColor;
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
	 * Getter for circle stroke color.
	 * @return stroke color
	 */
	public Color getFgColor() {
		return fgColor;
	}

	/**
	 * Getter for circle fill color.
	 * @return fill color
	 */
	public Color getBgColor() {
		return bgColor;
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
	 * Setter for circle stroke color.
	 * @param fgColor new circle stroke color
	 */
	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
		notifyObjectChanged(this);
	}
	
	/**
	 * Setter for circle fill color.
	 * @param bgColor new circle fill color
	 */
	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
		notifyObjectChanged(this);
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}
	
	@Override
	public String toString() {
		return String.format("Filled circle (%d,%d), %d, #%02X%02X%02X", 
				x, y, radius, bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue());
	}
}
