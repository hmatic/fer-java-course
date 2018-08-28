package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.models.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * Tool implementation for drawing lines.
 * Lines are drawn by clicking once to determine starting coordinate
 * and clicking another time to determine ending coordinate.
 * While mouse is moving tool renders line from starting point to mouse position.
 * Lines use foreground color.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class LineTool extends ToolAdapter {
	/**
	 * Color provider for line color.
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
	 * X of starting coordinate.
	 */
	private int x1;
	/**
	 * Y of starting coordinate.
	 */
	private int y1;
	/**
	 * X of ending coordinate.
	 */
	private int x2;
	/**
	 * Y of ending coordinate.
	 */
	private int y2;
	
	/**
	 * Flag tracks if its first mouse or second click.
	 */
	boolean mousePressed = false;
	
	/**
	 * Default constructor for LineTool.
	 * @param colorProvider color provider
	 * @param model drawing model
	 * @param canvas drawing canvas
	 */
	public LineTool(IColorProvider colorProvider, DrawingModel model, JDrawingCanvas canvas) {
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
			
			model.add(new Line(x1,y1,x2,y2,colorProvider.getCurrentColor()));
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
			g2d.drawLine(x1, y1, x2, y2);
		}
	}
}
