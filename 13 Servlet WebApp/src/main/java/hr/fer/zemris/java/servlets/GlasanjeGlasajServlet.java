package hr.fer.zemris.java.servlets;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for voting in voting part of application. 
 * If voting results storage file does not exist, this servlet will create one.
 * Servlet will coordinate with voting definition file and create proper results file.
 * If ID given in parameter is invalid, appropriate error message will be displayed.
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
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
	/**
	 * Relative path to error page.
	 */
	private static final String ERROR_PAGE_PATH = "/WEB-INF/pages/errorPage.jsp";
	
	@Override
	protected synchronized void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Integer> results = new HashMap<>();
		for(String line : loadLines(req, VOTE_DEFINITION_PATH)) {
			String[] lineParts = line.split("\t");
			if(!line.isEmpty()) {
				results.put(lineParts[0], Integer.valueOf(0));
			}
		}
		String filePath = req.getServletContext().getRealPath(VOTE_RESULTS_PATH);
		if(!Files.exists(Paths.get(filePath))) {
			Files.createFile(Paths.get(filePath));
		}
		
		for(String line : loadLines(req, VOTE_RESULTS_PATH)) {
			String[] lineParts = line.split("\t");
			if(lineParts.length==2) {
				results.put(lineParts[0], Integer.parseInt(lineParts[1]));
			}
		}
		
		String voteID = req.getParameter("id");
		if(results.containsKey(voteID)) {
			int currentVoteCount = results.get(voteID);
			results.put(voteID, currentVoteCount+1);
		} else {
			req.setAttribute("errorMsg", "Error: Option with given id is not available in this poll.");
			req.getRequestDispatcher(ERROR_PAGE_PATH).forward(req, resp);
			return;
		}
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(req.getServletContext().getRealPath(VOTE_RESULTS_PATH)), "utf-8"))) {
			for(Map.Entry<String, Integer> entry : results.entrySet()) {
				writer.write(entry.getKey() + "\t" + entry.getValue() + "\n");
			}
		}
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
	
	/**
	 * Helper method used to load lines of text file given by relative path in second argument.
	 * Path will be resolved according to servlet context real path.
	 * @param req http request
	 * @param filePath path relative to app root
	 * @return list of lines from input file
	 * @throws IOException if error occurs during IO operation
	 */
	private List<String> loadLines(HttpServletRequest req, String filePath) throws IOException {
		String fileName = req.getServletContext().getRealPath(filePath);
		return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
	}
	
}
