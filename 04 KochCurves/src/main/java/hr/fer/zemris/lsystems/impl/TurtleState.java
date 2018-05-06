package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Describes turtle state. Turtle is described by its current position,
 * angle it is facing, color of its trail and distance it makes with one step.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class TurtleState {
	/**
	 * Current position of turtle.
	 */
	private Vector2D origin;
	/**
	 * Angle starting from x-axis that turtle is facing, represented by Vector2D with length of 1 unit.
	 */
	private Vector2D angle;
	/**
	 * Color of turtle trail.
	 */
	private Color color;
	/**
	 * Distance turtle makes by one step.
	 */
	private double distance;
	
	/**
	 * Default constructor for TurtleState.
	 * 
	 * @param origin current position
	 * @param angle turtle angle
	 * @param color trail color
	 * @param distance step distance
	 */
	public TurtleState(Vector2D origin, Vector2D angle, Color color, double distance) {
		super();
		this.origin = origin;
		this.angle = angle;
		this.color = color;
		this.distance = distance;
	}
	
	/**
	 * Makes a copy of current turtle state and returns it.
	 * @return copy of current turtle state
	 */
	public TurtleState copy() {
		return new TurtleState(origin.copy(), angle.copy(), color, distance);
	}

	/**
	 * Getter for current position.
	 * @return current position.
	 */
	public Vector2D getOrigin() {
		return origin;
	}
	
	/**
	 * Getter for angle.
	 * @return turtle angle.
	 */
	public Vector2D getAngle() {
		return angle;
	}

	/**
	 * Getter for turtle trail color.
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Getter for turtle step distance.
	 * @return distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Setter for turtle position.
	 * @param origin turtle position
	 */
	public void setOrigin(Vector2D origin) {
		this.origin = origin;
	}

	/**
	 * Setter for turtle angle.
	 * @param angle angle turtle faces
	 */
	public void setAngle(Vector2D angle) {
		this.angle = angle;
	}

	/**
	 * Setter for turtle trail color.
	 * @param color color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Setter for turtle step distance.
	 * @param distance step distance
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

}
