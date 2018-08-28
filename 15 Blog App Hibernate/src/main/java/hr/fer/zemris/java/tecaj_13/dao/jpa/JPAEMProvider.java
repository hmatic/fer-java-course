package hr.fer.zemris.java.tecaj_13.dao.jpa;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * Entity Manager provider. 
 * Uses Entity Manager factory provided from JPAEMFProvider.
 * Stores Entity Manager objects into ThreadLocal map.
 * This provider is used in DAO implementation to get Entity Manager.
 * @author Hrvoje Matic
 *
 */
public class JPAEMProvider {

	/**
	 * Thread localized map of Entity Managers.
	 */
	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * Getter for Entity Manager of current thread. 
	 * If current thread has no entity manager, new one will be created.
	 * @return entity manager
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if(em==null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}

	/**
	 * Commit transaction and close entity manager.
	 * @throws DAOException if any error occurs
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		if(em==null) {
			return;
		}
		DAOException dex = null;
		try {
			em.getTransaction().commit();
		} catch(Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch(Exception ex) {
			if(dex!=null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if(dex!=null) throw dex;
	}
	
}