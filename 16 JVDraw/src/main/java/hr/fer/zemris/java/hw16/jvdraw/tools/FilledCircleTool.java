package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.models.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;

/**
 * Tool implementation for drawing filled circles.
 * FilledCircles are drawn by clicking once to determine 
 * circle center, and clicking again to determine circle radius 
 * calculated from circle center to mouse position
 * While mouse is moving tool renders circle.
 * FilledCircle is filled with background color and had foreground
 * color stroke.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class FilledCircleTool extends ToolAdapter {
	/**
	 * Foreground color provider.
	 */
	private IColorProvider fgColorProvider;
	/**
	 * Background color provider.
	 */
	private IColorProvider bgColorProvider;
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
	 * Default constructor for FilledCircleTool.
	 * @param fgColorProvider foreground color provider
	 * @param bgColorProvider background color provider
	 * @param model drawing model
	 * @param canvas drawing canvas
	 */
	public FilledCircleTool(IColorProvider fgColorProvider, IColorProvider bgColorProvider, DrawingModel model, JDrawingCanvas canvas) {
		super();
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
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
			model.add(new FilledCircle(x1,y1,r,fgColorProvider.getCurrentColor(), bgColorProvider.getCurrentColor()));
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
			int r = (int)Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
			g2d.setColor(bgColorProvider.getCurrentColor());
			g2d.fillOval(x1-r, y1-r, 2*r, 2*r);
			g2d.setColor(fgColorProvider.getCurrentColor());
			g2d.drawOval(x1-r, y1-r, 2*r, 2*r);
		}
	}
}
