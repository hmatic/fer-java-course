package hr.fer.zemris.java.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * Servlet which generates PieChart using JFreeChart for OS usage survey.
 * Outputs generated chart to response output stream as .png image.
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/reportImage")
public class ReportImageServlet extends HttpServlet{
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PieDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset, "OS usage");
		BufferedImage objBufferedImage=chart.createBufferedImage(500,270);
		ByteArrayOutputStream bas = new ByteArrayOutputStream();
		        try {
		            ImageIO.write(objBufferedImage, "png", bas);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		byte[] byteArray=bas.toByteArray();
		
		resp.setContentType("image/png");
		resp.setContentLength(byteArray.length);
		resp.getOutputStream().write(byteArray);
	}
	
	/**
	 * Creates dataset for pie chart.
	 * @return pie dataset
	 */
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Windows", 81.73);
		result.setValue("MacOS", 13.16);
		result.setValue("Linux", 1.66);
		result.setValue("Others", 3.44);
		return result;
	}
	
	/**
	 * Creates JFreeChart object from dataset and title given in arguments.
	 * Method also provides additional chart styling.
	 * @param dataset chart dataset
	 * @param title chart title
	 * @return pie chart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
		return chart;
	}
}
