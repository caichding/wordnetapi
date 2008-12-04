package eu.kostia.dao;

import java.util.logging.*;

import javax.persistence.*;

/**
 * 
 * @author c.cerbo
 * 
 * @param <E>
 *            Class of Entity
 * @param <I>
 *            Class of Entity Id (oft Long)
 */
public abstract class AbstractDAO<E, I> {
	static final protected Logger LOG = Logger.getLogger("DAO");

	public AbstractDAO() {
		super();
	}

	public void insert(E entity) {
		EntityManager em = createTransactionalEntityManager();
		try {
			em.persist(entity);
		} finally {
			closeTransactionalEntityManager(em);
		}

	}

	public E update(E entity) {
		EntityManager em = createTransactionalEntityManager();
		try {
			return em.merge(entity);
		} finally {
			closeTransactionalEntityManager(em);
		}

	}

	public int removeAll() {
		EntityManager em = createTransactionalEntityManager();
		Query q = em.createQuery("DELETE FROM "
				+ getEntityClass().getSimpleName());
		int deleted = q.executeUpdate();
		closeTransactionalEntityManager(em);

		return deleted;
	}

	public Long count() {
		EntityManager em = createEntityManager();
		Query q = em.createQuery("select count(o) from "
				+ getEntityClass().getSimpleName() + " as o");
		Long count = (Long) q.getSingleResult();
		return count;
	}

	public E findById(I id) throws NoResultException {
		EntityManager em = createTransactionalEntityManager();
		E result = em.find(getEntityClass(), id);
		closeTransactionalEntityManager(em);

		if (result == null) {
			throw new NoResultException("No result for id '" + id
					+ "' of entity " + getEntityClass().getSimpleName());
		}
		return result;
	}

	protected EntityManager createEntityManager() {
		EntityManagerFactory emf = createEntityManagerFactory();
		EntityManager em = emf.createEntityManager();

		return em;
	}

	protected void closeEntityManager(EntityManager em) {
		if (em == null) {
			return;
		}

		try {
			em.flush();
		} finally {
			em.close();
		}

	}

	protected EntityManager createTransactionalEntityManager() {
		EntityManagerFactory emf = createEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		return em;
	}

	protected void closeTransactionalEntityManager(EntityManager em) {
		if (em == null) {
			return;
		}

		try {
			em.flush();
			em.getTransaction().commit();
		} finally {
			em.close();
		}

	}

	private EntityManagerFactory createEntityManagerFactory() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(
				PersistenceConstants.DB_NAME, DAOProperties.getInstance());

		return emf;
	}

	protected abstract Class<E> getEntityClass();

}