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
import hr.fer.zemris.java.tecaj_13.web.forms.LoginForm;
/**
 * Servlet responsible for login using POST method.
 * Uses {@link LoginForm} to validate user input.
 * If input passes validation, adds user to current
 * session and redirects user to homepage.
 * Will render appropriate error page if trying to 
 * access this servlet with GET method.
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/servleti/main/login")
public class LoginServlet extends HttpServlet {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LoginForm login = new LoginForm();
		login.fillFromRequest(req);
		login.validate();
		
		if(login.hasErrors()) {
			List<BlogUser> users = DAOProvider.getDAO().getBlogUsers();
			req.setAttribute("users", users);
			req.setAttribute("loginForm", login);
			req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
		} else {
			BlogUser loggedInUser = login.getLoggedIn();
			req.getSession().setAttribute("current.user.id", loggedInUser.getId());
			req.getSession().setAttribute("current.user.fn", loggedInUser.getFirstName());
			req.getSession().setAttribute("current.user.ln", loggedInUser.getLastName());
			req.getSession().setAttribute("current.user.nick", loggedInUser.getNick());
			
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("error", "You can not access this site directly. Please use login form on homepage.");
		req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
	}
}
