package eu.kostia.morph;

import static eu.kostia.entity.PartOfSpeech.*;

import java.util.*;

import eu.kostia.dao.*;
import eu.kostia.entity.*;

/**
 * Wordnet Stemmer algorithmus as described in <a
 * href="http://wordnet.princeton.edu/man/morphy.7WN.html"
 * >http://wordnet.princeton.edu/man/morphy.7WN.html</a>
 * 
 * Steps:
 * <ol>
 * <li>First checks for exceptions (see *.exc files, that contains in each row
 * the inflected and the base form)</li>
 * <li>If nothing is found, apply the rules of detachment recursively until the
 * the resulting string is found in the WordNet dictionary in the specified
 * syntactic category</li>
 * <li>If nothing was in the dictionary, return the word as it is</li>
 * </ol>
 * 
 * @author c.cerbo
 */
public class Morphy {

	private final static List<DetachmentRule> DETACHMENT_RULES = new ArrayList<DetachmentRule>();
	static {
		DETACHMENT_RULES.add(new DetachmentRule(NOUN, "s", ""));
		DETACHMENT_RULES.add(new DetachmentRule(NOUN, "ses", "s"));
		DETACHMENT_RULES.add(new DetachmentRule(NOUN, "xes", "x"));
		DETACHMENT_RULES.add(new DetachmentRule(NOUN, "zes", "z"));
		DETACHMENT_RULES.add(new DetachmentRule(NOUN, "ches", "ch"));
		DETACHMENT_RULES.add(new DetachmentRule(NOUN, "shes", "sh"));
		DETACHMENT_RULES.add(new DetachmentRule(NOUN, "men", "man"));
		DETACHMENT_RULES.add(new DetachmentRule(NOUN, "ies", "y"));
		DETACHMENT_RULES.add(new DetachmentRule(VERB, "s", ""));
		DETACHMENT_RULES.add(new DetachmentRule(VERB, "ies", "y"));
		DETACHMENT_RULES.add(new DetachmentRule(VERB, "es", "e"));
		DETACHMENT_RULES.add(new DetachmentRule(VERB, "es", ""));
		DETACHMENT_RULES.add(new DetachmentRule(VERB, "ed", "e"));
		DETACHMENT_RULES.add(new DetachmentRule(VERB, "ed", ""));
		DETACHMENT_RULES.add(new DetachmentRule(VERB, "ing", "e"));
		DETACHMENT_RULES.add(new DetachmentRule(VERB, "ing", ""));
		DETACHMENT_RULES.add(new DetachmentRule(ADJECTIVE, "er", ""));
		DETACHMENT_RULES.add(new DetachmentRule(ADJECTIVE, "est", ""));
		DETACHMENT_RULES.add(new DetachmentRule(ADJECTIVE, "er", "e"));
		DETACHMENT_RULES.add(new DetachmentRule(ADJECTIVE, "est", "e"));
	}

	private MorphExceptionDAO morphExceptionDao;
	private WordDAO wordDAO;

	public Morphy() {
		morphExceptionDao = new MorphExceptionDAO();
		wordDAO = new WordDAO();
	}

	/**
	 * Given an inflected form (case insensitive) of a word, identify the base
	 * forms. The most terms have only a base form, but for example axes has
	 * three base forms: ax, axe and axis.
	 * 
	 * @param inflectedForm
	 *            The inflected form (case insensitive)
	 * 
	 * @return The set of different base forms
	 */
	public String[] stem(String inflectedForm) {
		String inflectedFormLower = inflectedForm.toLowerCase();
		Set<String> baseforms = new HashSet<String>();

		// If the term exists as it is, add it to baseforms. A term maybe a
		// baseform but also an inflectedForm for other words
		if (wordDAO.existsLemma(inflectedFormLower)) {
			baseforms.add(inflectedFormLower);
		}

		// First search in the exceptions table, and if found, return it
		searchIntoExceptionsTable(inflectedFormLower, baseforms);

		// If not, apply the detachment rules recursively until the the
		// resulting string is found in the WordNet dictionary
		applyDetachmentRules(inflectedFormLower, baseforms);

		if (baseforms.size() > 0) {
			return baseforms.toArray(new String[baseforms.size()]);
		}

		return null;
	}

	private void applyDetachmentRules(String inflectedFormLower,
			Set<String> baseforms) {
		for (PartOfSpeech partOfSpeech : PartOfSpeech.values()) {
			for (DetachmentRule rule : DETACHMENT_RULES) {
				if (rule.getPartOfSpeech() != partOfSpeech) {
					continue;
				}

				String newform = rule.apply(inflectedFormLower);
				if (newform != null) {
					if (wordDAO.existsLemma(newform)) {
						baseforms.add(newform);
					}
				}
			}
		}
	}

	private void searchIntoExceptionsTable(String inflectedFormLower,
			Set<String> baseforms) {
		for (PartOfSpeech partOfSpeech : PartOfSpeech.values()) {
			MorphException exc = morphExceptionDao.find(inflectedFormLower,
					partOfSpeech);
			if (exc != null) {
				String[] excBaseforms = exc.getBaseForms();
				for (String excBaseform : excBaseforms) {
					baseforms.add(excBaseform);
				}
			}
		}
	}
}
