package eu.kostia.dao;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import eu.kostia.entity.*;

public class MorphExceptionDAO extends AbstractDAO<MorphException, String> {

	public MorphException find(String inflectedForm, PartOfSpeech partOfSpeech) {
		EntityManager em = createTransactionalEntityManager();
		try {
			Query q = em
					.createQuery("SELECT t FROM MorphException t WHERE t.inflectedForm = :inflectedForm AND t.partOfSpeech = :partOfSpeech ");
			q.setParameter("inflectedForm", inflectedForm);
			q.setParameter("partOfSpeech", partOfSpeech);

			try {
				return (MorphException) q.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
		} finally {
			closeTransactionalEntityManager(em);
		}
	}

	public void populateDB(String dictionaryFolder)
			throws FileNotFoundException {

		for (PartOfSpeech pos : PartOfSpeech.values()) {
			// Satellite adjectives have no exception file
			if (pos == PartOfSpeech.ADJECTIVE_SATELLITE) {
				continue;
			}

			File file = new File(dictionaryFolder + File.separator
					+ pos.getAbbreviation() + ".exc");

			Scanner docScr = null;
			int count = 1;
			try {
				docScr = new Scanner(file);
				count = 1;
				while (docScr.hasNextLine()) {
					String line = docScr.nextLine();
					Scanner lineScr = null;
					try {
						lineScr = new Scanner(line);
						String inflectedForm = lineScr.next();

						Set<String> baseforms = new LinkedHashSet<String>();
						while (lineScr.hasNext()) {
							baseforms.add(lineScr.next());
						}

						MorphException morphException = new MorphException();
						morphException.setPartOfSpeech(pos);
						morphException.setInflectedForm(inflectedForm);
						morphException.setBaseForms(baseforms
								.toArray(new String[0]));

						try {
							insert(morphException);
						} catch (PersistenceException e) {
							System.err.println(e.getMessage());
						}
						count++;
					} finally {
						if (lineScr != null) {
							lineScr.close();
						}
					}
				}

			} catch (NoSuchElementException exc) {
				throw new NoSuchElementException("file: " + file + "; line: "
						+ count);
			} finally {
				if (docScr != null) {
					docScr.close();
				}

			}
		}
	}

	@Override
	protected Class<MorphException> getEntityClass() {
		return MorphException.class;
	}
}
