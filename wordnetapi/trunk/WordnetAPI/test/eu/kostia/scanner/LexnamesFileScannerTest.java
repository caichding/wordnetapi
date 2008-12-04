/**
 * 
 */
package eu.kostia.scanner;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import eu.kostia.dao.LexicalTypeDAO;
import eu.kostia.entity.LexicalType;

/**
 * @author c.cerbo
 * 
 */
public class LexnamesFileScannerTest {

	@BeforeClass
	public static void setSystemProperties() {
//		System.setProperty("java.util.logging.config.file", "./logging.properties");
	}

	/**
	 * Test method for {@link eu.kostia.scanner.LexnamesFileScanner#populateDB()}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void findById() throws Exception {
		LexicalTypeDAO dao = new LexicalTypeDAO();
		LexicalType l = dao.findById(18L);
		Assert.assertEquals("noun.person", l.getName());
	}

}
