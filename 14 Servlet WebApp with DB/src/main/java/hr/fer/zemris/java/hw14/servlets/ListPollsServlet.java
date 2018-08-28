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

/**
 * Servlet which lists all available polls from database.
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/servleti/index.html")
public class ListPollsServlet extends HttpServlet {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Poll> polls = DAOProvider.getDao().getListOfPolls();
		req.setAttribute("polls", polls);

		req.getRequestDispatcher("/WEB-INF/pages/polls.jsp").forward(req, resp);	
	}
}
