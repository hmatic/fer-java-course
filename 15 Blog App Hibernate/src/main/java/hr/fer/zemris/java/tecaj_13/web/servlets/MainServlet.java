package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
/**
 * Servlet which gets all blog users from database
 * and dispatches request forward to main.jsp.
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BlogUser> users = DAOProvider.getDAO().getBlogUsers();
		req.setAttribute("users", users);
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}
}
