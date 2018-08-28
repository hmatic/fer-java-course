package hr.fer.zemris.java.hw16.jvdraw.visitors;

import java.awt.Rectangle;

import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * GeometricalObjectVisitor which calculates bounding box for current model.
 * Bounding box is Rectangle object in which all objects can fit.
 * Bounding box is used for export action.
 * @author Hrvoje Matic
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {
	/**
	 * Bounding box.
	 */
	private Rectangle boundingBox;

	/**
	 * Getter for bounding box.
	 * @return bounding box
	 */
	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	@Override
	public void visit(Line line) {
		calculateBoundingBox(line.getX1(), line.getY1(), line.getX2(), line.getY2());
	}

	@Override
	public void visit(Circle circle) {
		int radius = circle.getRadius();
		int x1 = circle.getX() - radius;
		int y1 = circle.getY() - radius;
		int x2 = circle.getX() + radius;
		int y2 = circle.getY() + radius;
		
		calculateBoundingBox(x1, y1, x2, y2);
		
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		int radius = filledCircle.getRadius();
		int x1 = filledCircle.getX() - radius;
		int y1 = filledCircle.getY() - radius;
		int x2 = filledCircle.getX() + radius;
		int y2 = filledCircle.getY() + radius;
		
		calculateBoundingBox(x1, y1, x2, y2);	
	}
	
	/**
	 * Generic method for calculating bounding boxes of each object.
	 * Takes top-left coordinate (x1, y1) and bottom-right coordinate (x2, y2).
	 * @param x1 x of top-left
	 * @param y1 y of top-left
	 * @param x2 x of bottom-right
	 * @param y2 y of bottom-right
	 */
	private void calculateBoundingBox(int x1, int y1, int x2, int y2) {
		if(boundingBox == null) {
			boundingBox = new Rectangle(x1, y1, x2-x1, y2-y1);
		}
		
		int minX = boundingBox.x;
		int maxX = boundingBox.x + boundingBox.width;
		
		minX = Math.min(x1, minX);
		minX = Math.min(x2, minX);
		maxX = Math.max(x1, maxX);
		maxX = Math.max(x2, maxX);
		
		
		int minY = boundingBox.y;
		int maxY = boundingBox.y + boundingBox.height;
		
		minY = Math.min(y1, minY);
		minY = Math.min(y2, minY);
		maxY = Math.max(y1, maxY);
		maxY = Math.max(y2, maxY);
		
		boundingBox.setBounds(minX, minY, maxX-minX, maxY-minY);
	}
}
