package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Servlet which generates data for rendering single poll.
 * This servlet generates list of options that you can vote for together 
 * with their IDs so you can construct proper voting links.
 * If poll with given ID does not exist or has no options, 
 * request will be dispatched to appropriate error page.
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {
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
		List<PollOption> options = DAOProvider.getDao().getPollOptions(pollID);
		Poll pollInfo = DAOProvider.getDao().getPoll(pollID);
		
		if(options.isEmpty()) {
			req.setAttribute("error", "Poll with this ID does not exist or has no options.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		req.setAttribute("options", options);
		req.setAttribute("pollInfo", pollInfo);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
	
	
		
	
}
