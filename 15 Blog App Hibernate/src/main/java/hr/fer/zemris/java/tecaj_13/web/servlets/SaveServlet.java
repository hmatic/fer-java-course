package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet responsible for editing existing and adding new blog posts using POST method.
 * Will determine appropriate action based on existence of post determined by postID from parameter.
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/servleti/save")
public class SaveServlet extends HttpServlet {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String postID = req.getParameter("postID");
		boolean isNewPost = postID.isEmpty();
		BlogEntry entry;
		if(isNewPost) {
			entry = new BlogEntry();
			Date now = new Date();
			entry.setCreatedAt(now);
			entry.setLastModifiedAt(now);
		} else {
			long entryID;
			try {
				entryID = Long.parseLong(postID);
			} catch(NumberFormatException e) {
				req.setAttribute("error", "But you must provide ID of post as number.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			
			List<BlogEntry> entryList = DAOProvider.getDAO().getBlogEntry(entryID);
			if(entryList.isEmpty()) {
				req.setAttribute("error", "Post with given ID does not exist.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			entry = entryList.get(0);
			entry.setLastModifiedAt(new Date());
		}
		
		entry.setTitle(req.getParameter("title"));
		entry.setText(req.getParameter("text"));
		
		String userNick = req.getParameter("user");
		List<BlogUser> blogUser = DAOProvider.getDAO().getUserByNick(userNick);
		if(blogUser.isEmpty()) {
			req.setAttribute("error", "User with given nick does not exist.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		entry.setCreator(blogUser.get(0));
		
		if(isNewPost) {
			DAOProvider.getDAO().newBlogEntry(entry);
		}
		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + userNick);
		
	}
}
