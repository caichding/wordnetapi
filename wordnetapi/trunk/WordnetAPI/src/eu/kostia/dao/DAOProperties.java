package eu.kostia.dao;

import java.util.*;

import javax.persistence.*;

import oracle.toplink.essentials.config.*;
import eu.kostia.util.*;

/**
 * Additional properties to use when creating the factory. The values of these
 * properties override any values that may have been configured elsewhere.
 * 
 * When you create a new instance of {@link EntityManagerFactory}, remember to
 * pass always this object to the persistence factory, for example:
 * 
 * <pre>
 * EntityManagerFactory emf = Persistence.createEntityManagerFactory(
 * 		PersistenceConstants.DB_NAME, DAOProperties.getInstance());
 * </code>
 */
public class DAOProperties extends HashMap<String, String> {

	private static final long serialVersionUID = 100L;

	protected DAOProperties() {
		super();
		initDefaultProperties();
	}

	protected void initDefaultProperties() {
		// This hack assure that that the db files have a relative path
		String jdbcUrlValue = System.getProperty("wordnetapi.toplink.jdbc.url");
		if (jdbcUrlValue == null) {
			jdbcUrlValue = "jdbc:h2:" + H2DatabaseUtil.dbpath() + "/"
					+ PersistenceConstants.DB_NAME + ";IFEXISTS=TRUE";
		}

		put(TopLinkProperties.JDBC_URL, jdbcUrlValue);
	}

	private static class SingletonHolder {
		private final static DAOProperties INSTANCE = new DAOProperties();
	}

	public static DAOProperties getInstance() {
		return SingletonHolder.INSTANCE;
	}

}
