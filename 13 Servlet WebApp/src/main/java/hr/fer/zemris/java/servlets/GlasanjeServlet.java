package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which generates data for voting part of application.
 * This servlet generates list of bands that you can vote for together 
 * with their IDs so you can construct proper voting links.
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Band> bands = new ArrayList<>();
			
		for(String line : AppUtils.loadLines(req, "/WEB-INF/glasanje-definicija.txt")) {
			String[] lineParts = line.split("\t");
			bands.add(new Band(lineParts[0], lineParts[1]));
		}
		
		req.setAttribute("bands", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
	
	/**
	 * Models band in voting list.
	 * @author Hrvoje Matic
	 * @version 1.0
	 */
	public static class Band {
		/**
		 * Band ID.
		 */
		private String id;
		/**
		 * Band name.
		 */
		private String name;
		/**
		 * Default constructor.
		 * @param id band ID
		 * @param name band name
		 */
		public Band(String id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		/**
		 * Getter for band ID.
		 * @return band ID
		 */
		public String getId() {
			return id;
		}
		/**
		 * Getter for band name.
		 * @return band name
		 */
		public String getName() {
			return name;
		}
		
		
	}
}
