package hr.fer.zemris.java.hw14.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

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

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Servlet which creates PieChart based on results acquired 
 * from database.
 * Writes created PieChart to response output stream as .png image.
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollID = 0;
		try {
			pollID = Long.parseLong(req.getParameter("pollID"));
		} catch (NumberFormatException e) {
			req.setAttribute("error", "Poll ID must be a number.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		PieDataset dataset = createDataset(DAOProvider.getDao().getPollResults(pollID));
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
	 * Results must be given in list of PollOptions.
	 * @param results list of results
	 * @return pie dataset
	 */
	private PieDataset createDataset(List<PollOption> results) {
		DefaultPieDataset resultsDataset = new DefaultPieDataset();
		for(PollOption result : results) {
			resultsDataset.setValue(result.getOptionTitle(), result.getVotesCount());
		}
		return resultsDataset;
	}
}
