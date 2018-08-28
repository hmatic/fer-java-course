package hr.fer.zemris.java.gui.charts;

/**
 * Models value on bar chart. Each value has x and y coordinates.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class XYValue implements Comparable<XYValue>{
	/**
	 * X coordinate.
	 */
	private final int x;
	/**
	 * Y coordinate.
	 */
	private final int y;
	
	/**
	 * Default constructor for XYValue.
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter for X.
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Getter for Y.
	 * @return y
	 */
	public int getY() {
		return y;
	}

	@Override
	public int compareTo(XYValue other) {
		return Integer.compare(this.x, other.getX());
	}



	
}
