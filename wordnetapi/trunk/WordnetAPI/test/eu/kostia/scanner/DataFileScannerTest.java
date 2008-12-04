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
public class DataFileScannerTest {

	@BeforeClass
	public static void setSystemProperties() {
//		System.setProperty("java.util.logging.config.file", "./logging.properties");
	}

	@Test
	public void scanAdj() throws Exception {
		String filename = "dict/data.adj";
		scan(filename);
	}

	@Test
	public void scanAdv() throws Exception {
		String filename = "dict/data.adv";
		scan(filename);
	}

	@Test
	public void scanNoun() throws Exception {
		String filename = "dict/data.noun";
		scan(filename);
	}

	@Test
	public void scanVerb() throws Exception {
		String filename = "dict/data.verb";
		scan(filename);
	}

	private void scan(String filename) throws FileNotFoundException {
		DataFileScanner sc = new DataFileScanner(filename);
		List<DataFileRow> dataFileRows = sc.scan();
		Assert.assertTrue(dataFileRows.size() > 0);
	}

}
