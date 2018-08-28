package hr.fer.zemris.java.hw16.jvdraw.visitors;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;

import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * GeometricalObjectVisitor used for saving JVD files.
 * JVD files consist of lines representing geometrical objects.
 * Each line starts with object keyword (LINE, CIRCLE, FCIRCLE) 
 * followed by object properties.
 * Example of JVD file:
 * FCIRCLE 266 113 154 0 0 0 255 255 255
 * LINE 246 236 353 313 0 0 0
 * CIRCLE 277 246 252 0 0 0
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
public class GeometricalObjectSaver implements GeometricalObjectVisitor {
	/**
	 * PrintWriter used to write to file.
	 */
	private PrintWriter out;
	
	/**
	 * Default constructor for GeometricalObjectSaver.
	 * @param filePath path to file
	 */
	public GeometricalObjectSaver(Path filePath) {
		try {
			out = new PrintWriter(filePath.toAbsolutePath().toString());
		} catch (FileNotFoundException e) {
		}
	}

	@Override
	public void visit(Line line) {
		Color lineColor = line.getColor();
		String output = String.format("LINE %d %d %d %d %d %d %d",
				line.getX1(), line.getY1(), line.getX2(), line.getY2(),
				lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue());
		
		out.println(output);
	}

	@Override
	public void visit(Circle circle) {
		Color circleColor = circle.getColor();
		String output = String.format("CIRCLE %d %d %d %d %d %d",
				circle.getX(), circle.getY(), circle.getRadius(),
				circleColor.getRed(), circleColor.getGreen(), circleColor.getBlue());
		
		out.println(output);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		Color fgColor = filledCircle.getFgColor();
		Color bgColor = filledCircle.getBgColor();
		String output = String.format("FCIRCLE %d %d %d %d %d %d %d %d %d",
				filledCircle.getX(), filledCircle.getY(), filledCircle.getRadius(),
				fgColor.getRed(), fgColor.getGreen(), fgColor.getBlue(),
				bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue());
		
		out.println(output);
	}
	
	/**
	 * Closes PrintWriter stream.
	 */
	public void close() {
		out.close();
	}

}
