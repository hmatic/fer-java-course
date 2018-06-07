package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for setting background color of whole application.
 * Sets background color given in parameter "bgcolor" as session 
 * attribute named "pickedBgCol". Redirects back to index.jsp.
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/setcolor")
public class BgColorServlet extends HttpServlet {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = req.getParameter("bgcolor");
		req.getSession(true).setAttribute("pickedBgCol", color);
		resp.sendRedirect(req.getContextPath() + "/index.jsp");
	}
}
