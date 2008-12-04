/**
 * 
 */
package eu.kostia.main;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import eu.kostia.entity.Word;

/**
 * @author c.cerbo
 * 
 */
public class WordnetDictionaryTest {

	/**
	 * Test method for {@link eu.kostia.main.WordnetDictionary#lookup(java.lang.String)}.
	 */
	@Test
	public void testLookup() {
		WordnetDictionary dict = new WordnetDictionary();
		List<Word> words = dict.lookup("conceptualization");
		Assert.assertEquals(2, words.size());
	}

	@Test
	public void caseInsensitive() {
		WordnetDictionary dict = new WordnetDictionary();

		List<Word> words00 = dict.lookup("axes");
		List<Word> words01 = dict.lookup("Axes");

		int n = 12;
		Assert.assertEquals(n, words00.size());
		Assert.assertEquals(n, words01.size());
	}

}
