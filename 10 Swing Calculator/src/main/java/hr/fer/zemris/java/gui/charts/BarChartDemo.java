package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.Util;

/**
 * Demonstration for bar chart component drawing.<br>
 * Program accepts one argument which is a path to the bar chart configuration file.<br>
 * Configuration file must have 6 lines with following content:<br>
 * 1. line: X-axis description<br>
 * 2. line: Y-axis description<br>
 * 3. line: values separated with spaces<br>
 * 4. line: minimum y-axis value<br>
 * 5. line: maximum y-axis value<br>
 * 6. line: spacing between two values on y-axis<br>
 * <br>
 * Configuration file example:<br>
 * Number of people in the car<br>
 * Frequency<br>
 * 1,8 2,20 4,10 5,4 3,34<br>
 * 0<br>
 * 22<br>
 * 2<br>
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class BarChartDemo extends JFrame {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for BarChartDemo.
	 * 
	 * @param lines lines of configuration file
	 * @param name name of file path
	 */
	public BarChartDemo(List<String> lines, String name) {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(name);
		setLocation(100, 100);
		setSize(500, 500);
		initGUI(lines, name);
	}

	/**
	 * Initializes GUI.
	 * 
	 * @param lines lines of configuration file
	 * @param name name of file path
	 */
	private void initGUI(List<String> lines, String name) {
		getContentPane().setLayout(new BorderLayout());
		int yMin = 0;
		int yMax = 0;
		int ySpacing = 0;
		String xDesc = lines.get(0);
		String yDesc = lines.get(1);
		List<XYValue> values = null;
		try {
			values = parseValues(lines.get(2));
			yMin = Integer.parseInt(lines.get(3));
			yMax = Integer.parseInt(lines.get(4));
			ySpacing = Integer.parseInt(lines.get(5));
		} catch (Exception e) {
			System.out.println("Config file is not formatted correctly.");
			System.exit(1);
		}
		
		JLabel heading = new JLabel(name);
		heading.setHorizontalAlignment(JTextField.CENTER);
		heading.setOpaque(false);
		heading.setForeground(Color.GRAY);
		add(heading, BorderLayout.PAGE_START);

		BarChart chart = new BarChart(values, xDesc, yDesc, yMin, yMax, ySpacing);
		add(new BarChartComponent(chart), BorderLayout.CENTER);
	}


	/**
	 * Program entry point.
	 * @param args arguments listed in class description
	 * @throws IOException if problem occurs with IO operations
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Program accepts only 1 argument which is path to config file.");
			System.exit(1);
		}
		String argument = args[0].trim();

		Path path=null;
		try {
			path = Paths.get(argument);
		} catch(InvalidPathException e) {
			System.out.println("Argument is not valid representation of path.");
		}
		
		if (!Files.isReadable(path) || Files.isDirectory(path)) {
			System.out.println("This file is either directory or not readable.");
			System.exit(1);
		}
		
		List<String> lines = Files.readAllLines(path);
		String fileName = path.toAbsolutePath().toString();
		
		SwingUtilities.invokeLater(() -> new BarChartDemo(lines, fileName).setVisible(true));
	}
	
	
	/**
	 * Parses coordinate values separated by spaces into list of XYValue objects.
	 * 
	 * @param values x,y values
	 * @return list of XYValues
	 */
	private List<XYValue> parseValues(String values) {
		List<XYValue> result = new ArrayList<>();
		
		String[] coordinates = values.split("\\s+");
		
		for (String coordinate : coordinates) {
			result.add(Util.parseCoordinates(coordinate, XYValue::new));
		}
		return result;
	}
	
}