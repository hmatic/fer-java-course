package hr.fer.zemris.java.hw16.jvdraw.visitors;

import java.awt.Graphics2D;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * GeometricalObjectVisitor used for painting objects on canvas.
 * Recieves Graphics2D reference and uses it to paint objects.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {
	/**
	 * Canvas graphics object.
	 */
	private Graphics2D graphics;

	/**
	 * Setter for graphics.
	 * @param graphics graphics
	 */
	public void setGraphics(Graphics2D graphics) {
		this.graphics = graphics;
	}
	
	@Override
	public void visit(Line line) {
		graphics.setColor(line.getColor());
		graphics.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
	}

	@Override
	public void visit(Circle circle) {
		graphics.setColor(circle.getColor());
		int x = circle.getX();
		int y = circle.getY();
		int r = circle.getRadius();
		graphics.drawOval(x-r, y-r, 2*r, 2*r);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		int x = filledCircle.getX();
		int y = filledCircle.getY();
		int r = filledCircle.getRadius();
		
		graphics.setColor(filledCircle.getBgColor());
		graphics.fillOval(x-r, y-r, 2*r, 2*r);
		graphics.setColor(filledCircle.getFgColor());
		graphics.drawOval(x-r, y-r, 2*r, 2*r);
		
	}



}
