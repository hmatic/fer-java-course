package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * This class provides stored Entity Manager Factory to its clients.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class JPAEMFProvider {
	/**
	 * Entity Manager Factory object.
	 */
	public static EntityManagerFactory emf;
	
	/**
	 * Getter for entity manager factory instance.
	 * @return entity manager factory
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Setter for entity manager factory.
	 * @param emf entity manager factory
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}