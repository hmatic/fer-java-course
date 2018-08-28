package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface of data persistence layer. 
 * Declares all methods used to communicate with data layer of application.
 * @author Hrvoje Matic
 * @version 1.0
 */
public interface DAO {
	/**
	 * Get all blog users from database.
	 * @return list of all blog users
	 */
	List<BlogUser> getBlogUsers();

	/**
	 * Get all blog entries of a single user provided in argument.
	 * @param user blog user
	 * @return list of blog entries from specified user
	 */
	List<BlogEntry> getUsersBlogEntries(BlogUser user);
	
	/**
	 * Get Blog User object from database based on nick provided in argument.
	 * Returned as list of blog users that will either be empty or have 1 item.
	 * @param nick user nick
	 * @return blog user
	 */
	List<BlogUser> getUserByNick(String nick);
	
	/**
	 * Get blog entry from database based on id provided in argument.
	 * Returned as list of blog entries that will either be empty or have 1 item.
	 * @param id id of blog entry
	 * @return blog entry as list
	 */
	List<BlogEntry> getBlogEntry(long id);
	
	/**
	 * Create new blog entry.
	 * @param entry new blog entry
	 */
	void newBlogEntry(BlogEntry entry);
	
	/**
	 * Create new blog user.
	 * @param user new blog user
	 */
	void newBlogUser(BlogUser user);

	/**
	 * Create new blog comment.
	 * @param comment new blog comment
	 */
	void newBlogComment(BlogComment comment);
	
	
	
}