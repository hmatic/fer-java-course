package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.web.forms.CommentForm;
/**
 * Servlet which creates new blog comment using POST method.
 * It uses {@link CommentForm} to validate form's input data.
 * If there are no input error, it creates new comment in 
 * database and redirects user to comment's parent blog post.
 * @author Hrvoje Matic
 *
 */
@WebServlet("/servleti/comment")
public class CommentServlet extends HttpServlet {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CommentForm commentForm = new CommentForm();
		commentForm.fillFromRequest(req);
		commentForm.validate();
		
		long postID = Long.parseLong(req.getParameter("postID"));
		
		if(commentForm.hasErrors()) {
			BlogEntry post = DAOProvider.getDAO().getBlogEntry(postID).get(0);
			req.setAttribute("post", post);
			req.setAttribute("user", post.getCreator());
			req.setAttribute("commentForm", commentForm);
			req.getRequestDispatcher("/WEB-INF/pages/post.jsp").forward(req, resp);
		} else {
			String user = req.getParameter("user");
			BlogComment comment = commentForm.toBlogComment();
			comment.setBlogEntry(DAOProvider.getDAO().getBlogEntry(postID).get(0));
			DAOProvider.getDAO().newBlogComment(comment);
			resp.sendRedirect(req.getContextPath() + "/servleti/author/" + user + "/" + postID);
		}
	}
}
