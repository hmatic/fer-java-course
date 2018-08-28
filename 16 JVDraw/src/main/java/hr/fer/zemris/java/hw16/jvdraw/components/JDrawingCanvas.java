 package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.models.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

/**
 * Swing component used for displaying graphical objects in JVDraw.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class JDrawingCanvas extends JComponent {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	/**
	 * Drawing model.
	 */
	private DrawingModel model;
	/**
	 * Geometrical object painter.
	 */
	private GeometricalObjectPainter painter = new GeometricalObjectPainter();
	/**
	 * Reference to main program.
	 */
	private JVDraw jvdraw;
	/**
	 * Rendering hints for antialiasing.
	 */
	RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	
	/**
	 * Default constructor for JDrawingCanvas.
	 * Adds drawing model listeners which repaint whole canvas upon change in drawing model.
	 * @param model drawing model
	 * @param jvdraw reference to main program
	 */
	public JDrawingCanvas(DrawingModel model, JVDraw jvdraw) {
		super();
		this.model = model;
		this.jvdraw = jvdraw;
		
		model.addDrawingModelListener(new DrawingModelListener() {
			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				repaint();
			}
			
			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				repaint();
			}
			
			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				repaint();
			}
		});
		
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHints(rh);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		painter.setGraphics(g2d);
		for(int i=0, modelSize=model.getSize(); i<modelSize; i++) {
			model.getObject(i).accept(painter);
		}
		
		jvdraw.getCurrentTool().paint(g2d);
	}
}
