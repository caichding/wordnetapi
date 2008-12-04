package eu.kostia.util;

import java.io.*;
import java.net.*;

import javax.persistence.*;

import eu.kostia.dao.*;

public class H2DatabaseUtil {
	/**
	 * file to locate the db in the classpath
	 */
	static private final String DB_PATH_FILE = PersistenceConstants.DB_NAME
			+ ".path.db";

	static public void executeCommand(String command) {
		EntityManagerFactory emf = null;
		EntityManager em = null;

		try {
			emf = Persistence.createEntityManagerFactory(
					PersistenceConstants.DB_NAME, DAOProperties.getInstance());

			em = emf.createEntityManager();
			em.getTransaction().begin();

			em.createNativeQuery(command).executeUpdate();

			em.flush();
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
			if (emf != null) {
				emf.close();
			}
		}
	}

	static public void dropDatabase() {
		File dbDir = new File(dbpath());
		File[] dbFiles = dbDir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// don't delete the db path file
				if (DB_PATH_FILE.equals(name)) {
					return false;
				}

				return name.startsWith(PersistenceConstants.DB_NAME)
						&& name.endsWith(".db");
			}

		});
		for (File file : dbFiles) {
			boolean success = file.delete();
			if (!success) {
				throw new IllegalStateException("Impossible to remove file "
						+ file);
			}
		}
	}

	static public String dbpath() {
		try {
			URL url = H2DatabaseUtil.class.getResource("/" + DB_PATH_FILE);
			String urlString = url.getFile();
			int endindex = urlString.lastIndexOf("/" + DB_PATH_FILE);
			return urlString.substring(0, endindex);
		} catch (Throwable e) {
			throw new IllegalStateException("Database files not found!");
		}
	}

	public static void main(String[] args) {
		System.out.println(dbpath());
	}
}
