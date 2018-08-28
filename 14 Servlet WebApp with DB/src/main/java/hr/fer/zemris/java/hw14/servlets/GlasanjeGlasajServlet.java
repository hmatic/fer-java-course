package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;

/**
 * Servlet used for voting in voting part of application. 
 * Increments votesCount value of selected option in database and 
 * redirects to appropriate poll results.
 * 
 * @author Hrvoje Matic
 * @version 2.0
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected synchronized void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long id;
		try {
			id = Long.parseLong(req.getParameter("id"));
		} catch (NumberFormatException e) {
			req.setAttribute("error", "ID must be a number.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		DAOProvider.getDao().incrementOptionVoteCount(id);
		int pollID = DAOProvider.getDao().getPollID(id);
		
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollID);
	}
	
	
	
}
