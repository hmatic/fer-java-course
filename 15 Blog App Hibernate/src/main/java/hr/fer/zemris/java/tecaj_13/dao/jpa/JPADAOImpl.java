package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * DAO implementation using JPA technology. 
 * This implementation acquires Entity Manager from JPAEMProvider.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class JPADAOImpl implements DAO {

	@Override
	public void newBlogUser(BlogUser user) {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(user);
		em.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getBlogUsers() {
		List<BlogUser> userList = JPAEMProvider.getEntityManager()
				.createQuery("select u from BlogUser u")
				.getResultList();
		return userList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BlogEntry> getUsersBlogEntries(BlogUser user) {
		List<BlogEntry> entryList = JPAEMProvider.getEntityManager()
				.createQuery("select e from BlogEntry e where e.creator=:creator")
				.setParameter("creator", user)
				.getResultList();
		return entryList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getUserByNick(String nick) {
		List<BlogUser> blogUser = JPAEMProvider.getEntityManager()
				.createQuery("select u from BlogUser u where u.nick=:nick")
				.setParameter("nick", nick)
				.getResultList();
				
		return blogUser;
	}

	@Override
	public void newBlogEntry(BlogEntry entry) {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(entry);
		em.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogEntry> getBlogEntry(long id) {
		List<BlogEntry> entryList = JPAEMProvider.getEntityManager()
				.createQuery("select e from BlogEntry e where e.id=:id")
				.setParameter("id", id)
				.getResultList();
		return entryList;
	}

	@Override
	public void newBlogComment(BlogComment comment) {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(comment);
		em.close();
	}

}