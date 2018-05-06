package hr.fer.zemris.math;

/**
 * Represents vector in 2D space. It is radius-vector which always starts in (0,0) and ends in (x,y).
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class Vector2D {
	/**
	 * Coordinate on x-axis.
	 */
	private double x;
	/**
	 * Coordinate on y-axis.
	 */
	private double y;
	
	/**
	 * Constant used as a threshold for comparison of vectors.
	 */
	private static double THRESHOLD = 0.00001;
	
	/**
	 * Default constructor.
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public Vector2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter for x coordinate.
	 * @return x coordinate
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Getter for y coordinate.
	 * @return y coordinate
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Translates vector adding other vector to current vector.<br>
	 * This method changes current vector.
	 * @param offset vector used for translation
	 */
	public void translate(Vector2D offset) {
		x = x + offset.getX();
		y = y + offset.getY();
	}
	
	/**
	 * Translates vector adding other vector to current vector.<br>
	 * This method creates new vector without effecting current one.
	 * @param offset vector used for translation
	 */
	public Vector2D translated(Vector2D offset) {
		double newX = x + offset.getX();
		double newY = y + offset.getY();
		return new Vector2D(newX, newY);
	}
	
	/**
	 * Rotates vector for a given angle in degrees. Rotation is done in positive mathematical direction. <br>
	 * This method changes current vector.
	 * @param angle angle in degrees.
	 */
	public void rotate(double angle) {
		double angleRad = Math.toRadians(angle);
		double newX = (Math.cos(angleRad)*x)-(Math.sin(angleRad)*y);
		double newY = (Math.sin(angleRad)*x)+(Math.cos(angleRad)*y);
		x = newX;
		y = newY;
	}
	
	/**
	 * Rotates vector for a given angle in degrees. Rotation is done in positive mathematical direction. <br>
	 * This method creates new vector without effecting current one.
	 * @param angle angle in degrees.
	 */
	public Vector2D rotated(double angle) {
		double angleRad = Math.toRadians(angle);
		double newX = (Math.cos(angleRad)*x)-(Math.sin(angleRad)*y);
		double newY = (Math.sin(angleRad)*x)+(Math.cos(angleRad)*y);
		return new Vector2D(newX, newY);
	}
	
	/**
	 * Scales vector by multiplying its member variables with coefficient. <br>
	 * This method changes current vector.
	 * @param scaler scaling coefficient
	 */
	public void scale(double scaler) {
		x = x*scaler;
		y = y*scaler;
	}
	
	/**
	 * Scales vector by multiplying its member variables with coefficient. <br>
	 * This method creates new vector without effecting current one.
	 * @param scaler scaling coefficient
	 */
	public Vector2D scaled(double scaler) {
		double newX = x*scaler;
		double newY = y*scaler;
		return new Vector2D(newX, newY);
	}
	
	/**
	 * Makes a copy of current vector and returns it.
	 * @return copied vector
	 */
	public Vector2D copy() {
		return new Vector2D(x,y);
	}
	
	/**
	 * Checks if two vectors are same by comparing their x and y coordinates.<br>
	 * Also checks if other object is of type Vector2D.
	 * @return true if vectors are same, false otherwise
	 */
	@Override
	public boolean equals(Object arg0) {
		if(!(arg0 instanceof Vector2D)) return false;
		
		Vector2D other = (Vector2D) arg0;
		if(Math.abs(x-other.getX())<THRESHOLD && Math.abs(y-other.getY())<THRESHOLD) return true;
		
		return false;
	}
}
