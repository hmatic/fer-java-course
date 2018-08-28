package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.web.forms.RegisterForm;

/**
 * Servlet which handles user registration.
 * On GET method it renders register form, while
 * on POST method it creates new blog user if input data
 * has passed validation. Uses {@link RegisterForm} for validation.
 * If input does not pass validation, it renders 
 * form with same data and appropriate error messages.
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RegisterForm registerForm = new RegisterForm();
		registerForm.fillFromRequest(req);
		registerForm.validate();
		if(registerForm.hasErrors()) {
			req.setAttribute("form", registerForm);
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
		} else {
			DAOProvider.getDAO().newBlogUser(registerForm.toBlogUser());
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
		}
		
	}
}
