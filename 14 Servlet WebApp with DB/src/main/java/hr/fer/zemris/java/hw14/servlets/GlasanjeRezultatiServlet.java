package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Servlet which calculates results of the poll.
 * It gets poll options from database, sorts them 
 * descending and determines winner or winners if more 
 * than 1 option has same amount of votes.
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
	/**
	 * Serialization ID.
	 */
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
		List<PollOption> results = DAOProvider.getDao().getPollResults(pollID);
		Collections.sort(results);
		Collections.reverse(results);
		
		if(results.isEmpty()) {
			req.setAttribute("error", "Poll with this ID does not exist or has no available results.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		List<PollOption> winners = new ArrayList<>();
		int winnerVoteCount = results.get(0).getVotesCount();
		for(PollOption result : results) {
			if(result.getVotesCount()==winnerVoteCount) {
				winners.add(result);
			}
		}
		
		req.setAttribute("results", results);
		req.setAttribute("winners", winners);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
	
}
