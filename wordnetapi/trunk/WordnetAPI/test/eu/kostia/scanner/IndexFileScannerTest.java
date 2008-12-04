/**
 * 
 */
package eu.kostia.scanner;

import java.io.FileNotFoundException;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author c.cerbo
 * 
 */
public class IndexFileScannerTest {

	@BeforeClass
	public static void setSystemProperties() {
//		System.setProperty("java.util.logging.config.file", "./logging.properties");
	}

	@Test
	public void testAdj() throws Exception {
		String filename = "dict/index.adj";
		scan(filename);
	}

	@Test
	public void testAdv() throws Exception {
		String filename = "dict/index.adv";
		scan(filename);
	}

	@Test
	public void testNoun() throws Exception {
		String filename = "dict/index.noun";
		scan(filename);
	}

	@Test
	public void testVerb() throws Exception {
		String filename = "dict/index.verb";
		scan(filename);
	}

	private void scan(String filename) throws FileNotFoundException {
		IndexFileScanner sc = new IndexFileScanner(filename);
		List<IndexFileRow> indexWordList = sc.scan();
		Assert.assertTrue(indexWordList.size() > 0);
	}

}
