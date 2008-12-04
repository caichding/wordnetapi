package eu.kostia.main;

import java.util.*;

import eu.kostia.dao.*;
import eu.kostia.entity.*;
import eu.kostia.morph.*;

public class WordnetDictionary {

	/**
	 * Lookup for a word in the dictionary after performing morphological
	 * normalization
	 * 
	 * @param form
	 *            The term to search
	 * @return The results
	 */
	public List<Word> lookup(String form) {
		Morphy morphy = new Morphy();
		String[] lemmas = morphy.stem(form);
		if (lemmas == null) {
			return null;
		}

		List<Word> results = new ArrayList<Word>();
		WordDAO wordDAO = new WordDAO();
		for (String lemma : lemmas) {
			results.addAll(wordDAO.findByLemma(lemma));
		}

		return results;
	}
}
