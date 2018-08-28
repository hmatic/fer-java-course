package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Singleton class which knows which provider 
 * is used when accessing data persistence layer.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class DAOProvider {
	/**
	 * Instance of DAO object.
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Getter for DAO instance.
	 * @return DAO instance
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}