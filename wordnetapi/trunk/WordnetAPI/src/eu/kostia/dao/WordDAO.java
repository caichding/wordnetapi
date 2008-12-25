/**
 * 
 */
package eu.kostia.dao;

import java.util.*;

import javax.persistence.*;

import eu.kostia.entity.*;

/**
 * @author c.cerbo
 * 
 */
public class WordDAO extends AbstractDAO<Word, String> {

	@Override
	protected Class<Word> getEntityClass() {
		return Word.class;
	}

	public Word findById(WordPK wordPK) {
		return findById(wordPK.toString());
	}

	/**
	 * Find words by lemma
	 * 
	 * @param lemma
	 *            A Lemma (case insensitive)
	 * @return The words that correspond to the lemma
	 */
	@SuppressWarnings("unchecked")
	public List<Word> findByLemma(String lemma) {
		EntityManager em = createTransactionalEntityManager();
		try {
			Query q = em
					.createQuery("SELECT t FROM Word t WHERE LOWER(t.lemma) = :lemma");
			q.setParameter("lemma", lemma.toLowerCase());

			return q.getResultList();
		} finally {
			closeTransactionalEntityManager(em);
		}
	}

	/**
	 * Find words by lemma and semantic relation type
	 * 
	 * @param lemma
	 *            A Lemma (case insensitive)
	 * @param relationType
	 *            A semantic relation type
	 * @return The words that correspond to the lemma and the relation type
	 */
	public List<Word> findBySemanticRelationType(String lemma,
			SemanticRelationType relationType) {
		// TODO do in a more efficient way
		List<Word> words = findByLemma(lemma);
		List<Word> results = new ArrayList<Word>();
		for (Word word : words) {
			Set<Relation> synset = word.getSynset();
			for (Relation relation : synset) {
				if (relation.getSemanticRelationType() == relationType) {
					Word target = relation.getTarget();
					results.add(target);
				}
			}
		}

		return results;
	}

	@SuppressWarnings("unchecked")
	public boolean existsLemma(String lemma) {
		// In this case a native query is slightly faster
		EntityManager em = createTransactionalEntityManager();
		try {
			String sql = "SELECT COUNT(lemma) FROM Word WHERE LEMMA = '"
					+ lemma + "'";
			Query q = em.createNativeQuery(sql);

			Long count = (Long) ((Vector) q.getSingleResult()).get(0);
			return count > 0;
		} finally {
			closeTransactionalEntityManager(em);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Word> find(Long synsetOffset, PartOfSpeech pos) {
		EntityManager em = createTransactionalEntityManager();
		try {
			Query q = em
					.createQuery("SELECT t FROM Word t WHERE t.id LIKE :id ORDER BY t.id");
			String id = String.valueOf(synsetOffset) + pos.getSymbol() + "%";
			q.setParameter("id", id);

			return q.getResultList();
		} finally {
			closeTransactionalEntityManager(em);
		}
	}

}
