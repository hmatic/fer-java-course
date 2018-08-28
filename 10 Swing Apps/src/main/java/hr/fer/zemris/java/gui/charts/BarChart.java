package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Models bar chart data and characteristics.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class BarChart {
	/**
	 * Bar chart values.
	 */
	private List<XYValue> values;
	/**
	 * Description on x-axis.
	 */
	private String xDesc;
	/**
	 * Description on y-axis.
	 */
	private String yDesc;
	/**
	 * Minimum value on y-axis.
	 */
	private int yMin;
	/**
	 * Maximum value on y-axis.
	 */
	private int yMax;
	/**
	 * Spacing between two values on y-axis.
	 */
	private int ySpacing;
	
	/**
	 * Default constructor for BarChart.
	 * 
	 * @param values values
	 * @param xDesc x-axis description
	 * @param yDesc y-axis description
	 * @param yMin y-axis minimum
	 * @param yMax y-axis maximum
	 * @param ySpacing spacing on y-axis
	 */
	public BarChart(List<XYValue> values, String xDesc, String yDesc, 
			int yMin, int yMax, int ySpacing) {
		this.values = values;
		this.xDesc = xDesc;
		this.yDesc = yDesc;
		this.yMin = yMin;
		this.yMax = yMax;
		this.ySpacing = ySpacing;
	}

	/**
	 * Getter for values list.
	 * @return values list
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Getter for x-axis description.
	 * @return x-axis description.
	 */
	public String getXDesc() {
		return xDesc;
	}
	
	/**
	 * Getter for y-axis description.
	 * @return y-axis description.
	 */
	public String getYDesc() {
		return yDesc;
	}

	/**
	 * Getter for y-axis minimum.
	 * @return y-axis minimum
	 */
	public int getYMin() {
		return yMin;
	}
	
	/**
	 * Getter for y-axis maximum.
	 * @return y-axis maximum
	 */
	public int getYMax() {
		return yMax;
	}

	/**
	 * Getter for spacing between two values on y-axis.
	 * @return y-axis spacing
	 */
	public int getYSpacing() {
		return ySpacing;
	}
	
}
