package eu.kostia.dao;

import java.util.*;

import org.junit.*;

import eu.kostia.entity.*;

public class WordDAOTest {

	@Test
	public void testFindByLemma() {
		WordDAO dao = new WordDAO();
		String lemma = "abandon";
		List<Word> words = dao.findByLemma(lemma);
		System.out.println("Test findByLemma");
		for (Word word : words) {
			System.out.println(word.getLemma() + ": (" + word.getLexicalType()
					+ ") " + word.getDefinition());
			Set<Relation> relations = word.getSynset();
			for (Relation relation : relations) {
				System.out.println("\t" + relation.getSemanticRelationType()
						+ "\t" + relation.getTarget().getLemma());
			}
		}
		Assert.assertTrue(words.size() > 0);
	}

	@Test
	public void testFindBySemanticRelationType() {
		WordDAO dao = new WordDAO();
		List<Word> words = dao.findBySemanticRelationType("conceptualization",
				SemanticRelationType.HYPERNYM);
		System.out.println("Test FindBySemanticRelationType");
		for (Word word : words) {
			System.out.println(word.getLemma() + ": (" + word.getLexicalType()
					+ ") " + word.getDefinition());
		}
		Assert.assertTrue(words.size() == 4);
		assertContains(words, "creating_by_mental_acts", "construct",
				"conception", "concept");
	}

	private void assertContains(List<Word> words, String... lemmas) {
		for (Word word : words) {
			String currentLemma = word.getLemma();
			for (String lemma : lemmas) {
				if (lemma.equals(currentLemma)) {
					return;
				}
			}

			Assert.fail("'" + currentLemma + "' not in list");
		}
	}

	@Test
	public void testFindBySynsetOffsetAndPos() {
		WordDAO dao = new WordDAO();
		List<Word> words = dao.find(5835747L, PartOfSpeech.NOUN);
		System.out.println("Test findBySynsetOffsetAndPos");
		for (Word word : words) {
			System.out.println(word.getLemma() + ": (" + word.getLexicalType()
					+ ") " + word.getDefinition());
		}
		Assert.assertTrue(words.size() > 0);
	}

	@Test
	public void existsLemma00() {
		WordDAO dao = new WordDAO();
		Assert.assertTrue(dao.existsLemma("state"));
		Assert.assertFalse(dao.existsLemma("costantino"));
	}

	@Test
	public void existsLemma01() {
		WordDAO dao = new WordDAO();
		Assert.assertTrue(dao.findByLemma("state").size() > 0);
		Assert.assertFalse(dao.findByLemma("costantino").size() > 0);
	}

	@Test
	public void caseInsensitive() {
		WordDAO dao = new WordDAO();

		List<Word> words00 = dao.findByLemma("axis");
		List<Word> words01 = dao.findByLemma("Axis");

		int n = 6;
		Assert.assertEquals(n, words00.size());
		Assert.assertEquals(n, words01.size());
	}

}
