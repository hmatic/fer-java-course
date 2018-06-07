package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Collections;

import javax.swing.JComponent;

/**
 * Models bar chart component painting.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class BarChartComponent extends JComponent {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Bar chart data and characteristics.
	 */
	private BarChart chart;
	
	/**
	 * Gap from top side.
	 */
	private static final int TOP_GAP = 30;
	/**
	 * Gap from right side.
	 */
	private static final int RIGHT_GAP = 15;
	/**
	 * Gap from left side.
	 */
	private static final int LEFT_GAP = 15;
	/**
	 * Gap from bottom side.
	 */
	private static final int BOTTOM_GAP = 15;
	/**
	 * Length of marks on grid.
	 */
	private static final int MARK_LENGTH = 4;
	/**
	 * Bar chart default font.
	 */
	private static final Font DEFAULT_FONT = new Font("Helvetica", Font.PLAIN, 15);
	/**
	 * Distance from value to axis.
	 */
	private static final int VALUE_DIST_FROM_AXIS = 5;
	/**
	 * Color of description font.
	 */
	private static final Color DESCRIPTION_COLOR = Color.BLACK;
	/**
	 * Color of values.
	 */
	private static final Color VALUES_COLOR = Color.BLACK;
	/**
	 * Color of grid lines.
	 */
	private static final Color GRID_COLOR = Color.LIGHT_GRAY;
	/**
	 * Color of axis lines.
	 */
	private static final Color AXIS_COLOR = Color.GRAY;
	/**
	 * Dimensions of arrows on axis.
	 */
	private static final int ARROW_DIMENSIONS = 5;
	
	/**
	 * Default constructor for bar chart component.
	 * @param chart chart data and characteristics
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		Dimension size = getSize();
		Collections.sort(chart.getValues());
		
		graphics.setFont(DEFAULT_FONT);
		FontMetrics fm = graphics.getFontMetrics();
		

		
		int leftGap = fm.getAscent() + 2*LEFT_GAP + fm.stringWidth(String.valueOf(chart.getYMax())) + VALUE_DIST_FROM_AXIS;
		int bottomGap = 2*fm.getAscent() + 2*BOTTOM_GAP+10;
	
		// Number of rows and cols
		int rows = (int) Math.ceil((double)(chart.getYMax()-chart.getYMin())/chart.getYSpacing());
		int cols = chart.getValues().size();

		// Coordinates of chart origin
		int startX = leftGap;
		int startY = (int)(size.getHeight())-bottomGap;
		
		// Row height and column width in barchart
		int rowHeight = (int) ((size.getHeight()-TOP_GAP-bottomGap)/rows);
		int colWidth = (int) ((size.getWidth()-RIGHT_GAP-leftGap)/cols);
		
		int chartWidth = cols*colWidth;
		int chartHeight = rows*rowHeight;
		
		paintGrid(graphics, startX, startY, rows, cols, rowHeight, colWidth);
		
		paintAxis(graphics, startX, startY, startX, startY-chartHeight-10); //X-axis
		paintAxis(graphics, startX, startY, startX+chartWidth+10, startY); //Y-axis

		graphics.setColor(DESCRIPTION_COLOR);
		paintXDescription(graphics, startX, fm, (int)size.getHeight(), chartWidth);
		paintYDescription(graphics, fm, chartHeight);
		graphics.setColor(VALUES_COLOR);
		paintXValues(graphics, startX, startY, fm, colWidth);
		paintYValues(graphics, chart.getYMin(), chart.getYMax(), chart.getYSpacing(),
				startX, startY, rowHeight, fm);
		
		paintBars(graphics, colWidth, rowHeight, startX, startY);
	}
	
	/**
	 * Paints description of X-axis on bar chart.
	 * 
	 * @param graphics reference to Graphics2D object
	 * @param startX x coordinate of chart origin
	 * @param fm font metrics
	 * @param windowHeight height of whole window
	 * @param chartWidth width of chart
	 */
	private void paintXDescription(Graphics2D graphics, int startX, FontMetrics fm, 
			int windowHeight, int chartWidth ) {
		int XDescWidth = fm.stringWidth(chart.getXDesc());
		graphics.drawString(chart.getXDesc(), 
				startX+(chartWidth/2)-(XDescWidth/2), 
				(int)(windowHeight-BOTTOM_GAP));
	}
	
	/**
	 * Paints description of Y-axis on bar chart.
	 * 
	 * @param graphics reference to Graphics2D object
	 * @param fm font metrics
	 * @param chartHeight height of chart
	 */
	private void paintYDescription(Graphics2D graphics, FontMetrics fm, int chartHeight) {
		AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
		AffineTransform defaultTransform = graphics.getTransform();
		graphics.setTransform(at);
		int YDescWidth = fm.stringWidth(chart.getYDesc());
		graphics.drawString(chart.getYDesc(),
				-TOP_GAP-(chartHeight/2)-(YDescWidth/2), 
				LEFT_GAP+fm.getAscent());
		graphics.setTransform(defaultTransform);
		
	}

	/**
	 * Paints value bars on bar chart.
	 * 
	 * @param graphics reference to Graphics2D object
	 * @param colWidth width of column
	 * @param rowHeight height of row
	 * @param startX x coordinate of chart origin
	 * @param startY y coordinate of chart origin
	 */
	private void paintBars(Graphics2D graphics,  int colWidth, int rowHeight, int startX, int startY) {
		graphics.setColor(Color.ORANGE);
		int x = startX+1;
		for(XYValue value : chart.getValues()) {
			int height;
			if(value.getY()%chart.getYSpacing()==0) {
				height = (value.getY()/chart.getYSpacing()) * rowHeight;
			} else {
				height = ((int)(value.getY()/chart.getYSpacing())+1)*rowHeight;
			}
			graphics.fillRect(x, startY-height, colWidth-2, height);
			x+=colWidth;
		}
	}
	
	/**
	 * Paints values of X-axis on bar chart.
	 * 
	 * @param graphics reference to Graphics2D object
	 * @param startX x coordinate of chart origin
	 * @param startY y coordinate of chart origin
	 * @param fm font metrics
	 * @param colWidth width of column
	 */
	private void paintXValues(Graphics2D graphics, int startX, int startY, FontMetrics fm, int colWidth) {
		int x = startX+(colWidth/2);
		int y = startY + ((int)fm.getLineMetrics("0", graphics).getAscent()) + VALUE_DIST_FROM_AXIS;
		String xValue;
		for(XYValue value : chart.getValues()) {
			xValue = String.valueOf(value.getX());
			graphics.drawString(xValue, x-(fm.stringWidth(xValue)/2), y);
			x+=colWidth;
		}
	}
	
	/**
	 * Paints values of Y-axis on bar chart.
	 * 
	 * @param graphics reference to Graphics2D object 
	 * @param yMin minimum value on y-axis
	 * @param yMax maximum value on y-axis
	 * @param ySpacing spacing between two values on y-axis
	 * @param startX x coordinate of chart origin
	 * @param startY y coordinate of chart origin
	 * @param rowHeight height of row
	 * @param fm font metrics
	 */
	private void paintYValues(Graphics2D graphics, int yMin, int yMax, int ySpacing, 
			int startX, int startY, int rowHeight, FontMetrics fm) {
		int x;
		int y = startY + ((int)fm.getLineMetrics("0", graphics).getAscent())/2 - 1;
		
		if(!((yMax-yMin)%ySpacing==0)) {
			yMax = ((int)(yMax/ySpacing)+1)*ySpacing;
		}
		
		for(int i = yMin; i <= yMax; i+=ySpacing ) {
			x=startX-fm.stringWidth(String.valueOf(i))-VALUE_DIST_FROM_AXIS;
			graphics.drawString(String.valueOf(i), x, y);
			y-=rowHeight;
		}
	}
	
	/**
	 * Paints grid lines on bar chart.
	 * 
	 * @param graphics reference to Graphics2D object
	 * @param startX x coordinate of chart origin
	 * @param startY y coordinate of chart origin
	 * @param rows number of rows
	 * @param cols number of columns
	 * @param rowHeight height of row
	 * @param colWidth width of column
	 */
	private void paintGrid(Graphics2D graphics, int startX, int startY, 
			int rows, int cols, int rowHeight, int colWidth) {
		graphics.setColor(GRID_COLOR);
		
		// Draws horizontal
		int x2=startX+(cols*colWidth)+MARK_LENGTH;
		for(int i = 0; i < rows+1; i++) {
			int y=startY-(i*rowHeight);
			graphics.drawLine(startX-MARK_LENGTH, y, x2, y);
		}
		
		//Draws vertical
		int y2=startY-(rows*rowHeight)-MARK_LENGTH;
		for(int i = 0; i < cols+1; i++) {
			int x=startX+(i*colWidth);
			graphics.drawLine(x, startY+MARK_LENGTH, x, y2);
		}
	}
	
	/**
	 * Paints axis line on bar chart.
	 * @param graphics reference to Graphics2D object
	 * @param startX x coordinate of chart origin
	 * @param startY y coordinate of chart origin
	 * @param endX x coordinate of axis end
	 * @param endY y coordinate of axis end
	 */
	private void paintAxis(Graphics2D graphics, int startX, int startY, int endX, int endY) {
		graphics.setColor(AXIS_COLOR);
		drawArrowLine(graphics, startX, startY, endX, endY, ARROW_DIMENSIONS, ARROW_DIMENSIONS);
	}
	
	/**
	 * Helper method which paints line with arrow ending.
	 * Method calculations taken from StackOverFlow.
	 * 
	 * @param graphics
	 * @param x1 x coordinate of line start
	 * @param y1 y coordinate of line start
	 * @param x2 x coordinate of line end
	 * @param y2 y coordinate of line end
	 * @param arrowWidth width of arrow
	 * @param arrowHeight height of arrow
	 */
	private static void drawArrowLine(Graphics graphics, int x1, int y1, int x2, int y2,
			int arrowWidth, int arrowHeight) {
	    int dx = x2 - x1; 
	    int dy = y2 - y1;
	    double D = Math.sqrt(dx*dx + dy*dy);
	    double xm = D - arrowWidth, xn = xm, ym = arrowHeight, yn = -arrowHeight, x;
	    double sin = dy / D, cos = dx / D;

	    x = xm*cos - ym*sin + x1;
	    ym = xm*sin + ym*cos + y1;
	    xm = x;

	    x = xn*cos - yn*sin + x1;
	    yn = xn*sin + yn*cos + y1;
	    xn = x;

	    int[] xpoints = {x2, (int) xm, (int) xn};
	    int[] ypoints = {y2, (int) ym, (int) yn};

	    graphics.drawLine(x1, y1, x2, y2);
	    graphics.fillPolygon(xpoints, ypoints, 3);
	}
}
