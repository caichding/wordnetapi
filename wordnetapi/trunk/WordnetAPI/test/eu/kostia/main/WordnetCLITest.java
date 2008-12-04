package eu.kostia.main;

import java.util.*;

import org.junit.*;

import eu.kostia.entity.*;

public class WordnetCLITest {
	@BeforeClass
	public static void setSystemProperties() {
		// System.setProperty("java.util.logging.config.file",
		// "./logging.properties");
	}

	@Test(timeout = 10000)
	public void testMain() {
		long start = System.currentTimeMillis();
		WordnetCLI.main("axes");
		long elapsed = (System.currentTimeMillis() - start) / 1000;
		System.out.println("Elapsed seconds: " + elapsed);

	}

	@Test
	public void getResult00() {
		WordnetCLI cli = new WordnetCLI("conceptualization");
		List<Word> words = cli.getResult();
		Assert.assertEquals(2, words.size());

		Word cognition = selectNounCognition(words);

		Set<Relation> synset = cognition.getSynset();
		Assert.assertEquals(7, synset.size());
		assertContains(synset, "perception");
	}

	@Test
	public void getResult01() {
		WordnetCLI cli = new WordnetCLI("axes");
		List<Word> words = cli.getResult();
		Assert.assertEquals(12, words.size());

		int numOfSynset = 0;
		for (Word w : words) {
			numOfSynset += w.getSynset().size();
		}

		Assert.assertEquals(109, numOfSynset);
	}

	@Test
	public void caseInsensitive() {
		WordnetCLI cli00 = new WordnetCLI("axes");
		List<Word> words00 = cli00.getResult();
		Assert.assertEquals(12, words00.size());

		WordnetCLI cli01 = new WordnetCLI("Axes");
		List<Word> words01 = cli01.getResult();
		Assert.assertEquals(12, words01.size());
		Assert.assertEquals(words00.size(), words01.size());
	}

	private void assertContains(Set<Relation> aSynset, String expected) {
		for (Relation relation : aSynset) {
			String lemma = relation.getTarget().getLemma();
			if (lemma.equals(expected)) {
				return;
			}
		}

		Assert.fail("Synset doesn't contain lemma '" + expected + "'");

	}

	private Word selectNounCognition(List<Word> words) {
		Word cognition = null;
		for (Word w : words) {
			if ("noun.cognition".equals(w.getLexicalType().getName())) {
				cognition = w;
				break;
			}
		}
		return cognition;
	}
}
