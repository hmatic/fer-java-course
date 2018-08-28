package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which redirects project index to voting index.
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/index.html")
public class IndexServlet extends HttpServlet {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
	}
}
