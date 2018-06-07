package hr.fer.zemris.java.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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
 * Servlet which creates PieChart based on results acquired 
 * from voting results and voting definition files.
 * Writes created PieChart to response output stream as .png image.
 * Will return appropriate error message if no voting results file exists.
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	/**
	 * Relative path to voting definition storage file.
	 */
	private static final String VOTE_DEFINITION_PATH = "/WEB-INF/glasanje-definicija.txt";
	/**
	 * Relative path to voting results storage file.
	 */
	private static final String VOTE_RESULTS_PATH = "/WEB-INF/glasanje-rezultati.txt";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Integer> results = new HashMap<>();
		Map<String, String> bands = new HashMap<>();
		for(String line : AppUtils.loadLines(req, VOTE_DEFINITION_PATH)) {
			String[] lineParts = line.split("\t");
			bands.put(lineParts[0], lineParts[1]);
		}
		if(Files.exists(Paths.get(req.getServletContext().getRealPath(VOTE_RESULTS_PATH)))) {
			for(String line : AppUtils.loadLines(req, VOTE_RESULTS_PATH)) {
				String[] lineParts = line.split("\t");
				String resultID = lineParts[0];
				if(bands.containsKey(resultID)) {
					results.put(lineParts[0], Integer.parseInt(lineParts[1]));
				}
			}
		} else {
			resp.getOutputStream().write("No data available.".getBytes(StandardCharsets.UTF_8));
			return;
		}
		
		Map<String, Integer> voteResults = new HashMap<>();
		for(Map.Entry<String, Integer> entry : results.entrySet()) {
			if(entry.getValue()>0) {
				voteResults.put(bands.get(entry.getKey()), entry.getValue());
			}
		}
		
		PieDataset dataset = createDataset(voteResults);
		JFreeChart chart = createChart(dataset, "Rezultati glasovanja");
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
	 * Creates JFreeChart object with given dataset and title.
	 * Also sets other styling attributes.
	 * @param dataset pie dataset
	 * @param title piechart title
	 * @return JFreeChart object
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
		return chart;
	}

	/**
	 * Creates dataset for PieChart.
	 * Results must be given in map with name as key and result as value.
	 * @param results map of results
	 * @return pie dataset
	 */
	private PieDataset createDataset(Map<String, Integer> results) {
		DefaultPieDataset resultsDataset = new DefaultPieDataset();
		for(Map.Entry<String, Integer> entry : results.entrySet()) {
			resultsDataset.setValue(entry.getKey(), entry.getValue());
		}
		return resultsDataset;
	}
}
