package hr.fer.zemris.java.hw16.jvdraw.visitors;

import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * Visitor interface for GeometricalObjects.
 * See Visitor design pattern.
 * @author Hrvoje Matic
 * @version 1.0
 */
public interface GeometricalObjectVisitor {
	/**
	 * Actions performed when Line is visited.
	 * @param line reference to line object
	 */
	void visit(Line line);
	/**
	 * Actions performed when Circle is visited.
	 * @param circle reference to circle object
	 */
	void visit(Circle circle);
	/**
	 * Actions performed when FilledCircle is visited.
	 * @param filledCircle reference to filled circle object
	 */
	void visit(FilledCircle filledCircle);
}
