package eu.kostia.dao;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import eu.kostia.entity.PartOfSpeech;
import eu.kostia.entity.Word;

public class WordDAOTest {

	@Test
	public void testFindByLemma() {
		WordDAO dao = new WordDAO();
		List<Word> words = dao.findByLemma("abandon");
		System.out.println("Test findByLemma");
		for (Word word : words) {
			System.out.println(word.getLemma() + ": (" + word.getLexicalType() + ") " + word.getDefinition());
		}
		Assert.assertTrue(words.size() > 0);
	}

	@Test
	public void testFindBySynsetOffsetAndPos() {
		WordDAO dao = new WordDAO();
		List<Word> words = dao.find(5835747L, PartOfSpeech.NOUN);
		System.out.println("Test findBySynsetOffsetAndPos");
		for (Word word : words) {
			System.out.println(word.getLemma() + ": (" + word.getLexicalType() + ") " + word.getDefinition());
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
