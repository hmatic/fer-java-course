package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.models.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;

/**
 * Tool implementation for drawing circles.
 * FilledCircles are drawn by clicking once to determine 
 * circle center, and clicking again to determine circle radius 
 * calculated from circle center to mouse position.
 * While mouse is moving tool renders circle.
 * Circle has foreground colored stroke.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class CircleTool extends ToolAdapter {
	/**
	 * Color provider for circle color.
	 */
	private IColorProvider colorProvider;
	/**
	 * Drawing model.
	 */
	private DrawingModel model;
	/**
	 * Drawing canvas.
	 */
	private JDrawingCanvas canvas;
	
	/**
	 * X coordinate of circle center.
	 */
	private int x1;
	/**
	 * Y coordinate of circle center.
	 */
	private int y1;
	/**
	 * X coordinate of second mouse click.
	 */
	private int x2;
	/**
	 * Y coordinate of second mouse click.
	 */
	private int y2;
	
	/**
	 * Flag tracks if its first mouse or second click.
	 */
	boolean mousePressed = false;
	
	/**
	 * Default constructor for CircleTool.
	 * @param colorProvider provider for circle color
	 * @param model drawing model
	 * @param canvas drawing canvas
	 */
	public CircleTool(IColorProvider colorProvider, DrawingModel model, JDrawingCanvas canvas) {
		super();
		this.colorProvider = colorProvider;
		this.model = model;
		this.canvas = canvas;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!mousePressed) {
			x1 = e.getX();
			y1 = e.getY();
			mousePressed = true;
		} else {
			x2 = e.getX();
			y2 = e.getY();
			int r = (int)Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
			model.add(new Circle(x1,y1,r,colorProvider.getCurrentColor()));
			canvas.repaint();
			mousePressed = false;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(mousePressed) {
			x2 = e.getX();
			y2 = e.getY();
			canvas.repaint();
		}		
	}


	@Override
	public void paint(Graphics2D g2d) {
		if(mousePressed) {
			g2d.setColor(colorProvider.getCurrentColor());
			int r = (int)Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
			g2d.drawOval(x1-r, y1-r, 2*r, 2*r);
		}
	}

}
