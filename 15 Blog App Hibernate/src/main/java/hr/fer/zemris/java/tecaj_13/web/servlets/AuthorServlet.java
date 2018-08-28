package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
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
 * Servlet responsible for displaying pages that include: 
 * user's index page with all user's post, single blog post page, 
 * editor for editing or adding new posts.
 * Examples of URLs handled by this servlet:
 * "/servleti/author/hrvoje/" - list of all hrvoje's posts
 * "/servleti/author/hrvoje/23" - displays post with ID=23
 * "/servleti/author/hrvoje/new" - editor for adding new post to hrvoje's blog
 * "/servleti/author/hrvoje/edit/23" - editor for editing post with ID=23
 * 
 * Sends 404 if URL has more than 3 parameters after "author" parameter.
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo().substring(1);
		String[] pathParts = path.split("/");
		
		List<BlogUser> user = DAOProvider.getDAO().getUserByNick(pathParts[0]);
		if(user.isEmpty()) {
			req.setAttribute("error", "Author with this username does not exist.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		req.setAttribute("user", user.get(0));
		
		if(pathParts.length==1) {  // RENDERS LIST OF USERS POSTS
			List<BlogEntry> userEntries = DAOProvider.getDAO()
					.getUsersBlogEntries(user.get(0));
			
			req.setAttribute("entries", userEntries);
			req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
		} else if(pathParts.length==2) {  // RENDERS EITHER "NEW POST" EDITOR OR SINGLE POST PAGE
			if(pathParts[1].equals("new")) {
				if(!user.get(0).getNick().equals(req.getSession().getAttribute("current.user.nick"))) {
					req.setAttribute("error", "You are not authorized to add new post on this blog.");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}
				
				req.setAttribute("action", "New");
				req.getRequestDispatcher("/WEB-INF/pages/editor.jsp").forward(req, resp);
				return;
			}
			
			long entryID;
			try {
				entryID = Long.parseLong(pathParts[1]);
			} catch(NumberFormatException e) {
				req.setAttribute("error", "But you must provide ID of post as number.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			List<BlogEntry> post = DAOProvider.getDAO().getBlogEntry(entryID);
			
			if(post.isEmpty()) {
				req.setAttribute("error", "Blog post with this ID does not exist.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			
			req.setAttribute("post", post.get(0));
			req.getRequestDispatcher("/WEB-INF/pages/post.jsp").forward(req, resp);
		} else if(pathParts.length==3) {   // RENDERS "EDIT POST" EDITOR
			if(!pathParts[1].equals("edit")) {
				req.setAttribute("error", "Invalid action. You can only edit or add new posts on your blog.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			
			if(!user.get(0).getNick().equals(req.getSession().getAttribute("current.user.nick"))) {
				req.setAttribute("error", "You are not authorized to edit this post.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			
			long entryID;
			try {
				entryID = Long.parseLong(pathParts[2]);
			} catch(NumberFormatException e) {
				req.setAttribute("error", "But you must provide ID of post as number.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			List<BlogEntry> post = DAOProvider.getDAO().getBlogEntry(entryID);
			
			if(post.isEmpty()) {
				req.setAttribute("error", "Blog post with this ID does not exist.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			if(!post.get(0).getCreator().equals(user)) {
				req.setAttribute("error", "You are not authorized to edit this post.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			
			req.setAttribute("action", "Edit");
			req.setAttribute("post", post.get(0));
			req.getRequestDispatcher("/WEB-INF/pages/editor.jsp").forward(req, resp);
		} else {  
			resp.sendError(404);
		}
	}
}
	
	
