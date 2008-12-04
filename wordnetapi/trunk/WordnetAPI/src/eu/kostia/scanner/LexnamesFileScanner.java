/**
 * 
 */
package eu.kostia.scanner;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import eu.kostia.dao.*;
import eu.kostia.entity.*;

/**
 * @author c.cerbo
 * 
 */
public class LexnamesFileScanner {

	static final private Logger LOG = Logger.getLogger("LexnamesFileScanner");

	private String dictionaryPath;

	public LexnamesFileScanner(String dictionaryPath) {
		this.dictionaryPath = dictionaryPath;
	}

	public void populateDB() throws FileNotFoundException {
		LexicalTypeDAO dao = new LexicalTypeDAO();
		dao.removeAll();

		Scanner docScanner = null;
		String lexnamesFilename = dictionaryPath
				+ System.getProperty("file.separator") + "lexnames";
		int count = 0;
		try {

			docScanner = new Scanner(new File(lexnamesFilename));

			while (docScanner.hasNextLine()) {
				count++;
				String line = docScanner.nextLine();
				if (line.startsWith(" ")) {
					continue;
				}

				// Line scanner
				LexicalType lexicograph = processLine(line);
				dao.insert(lexicograph);
			}
		} catch (Exception e) {
			throw new IllegalStateException("File :'" + lexnamesFilename
					+ "', error at line nr. " + count + "'", e);
		} finally {
			if (docScanner != null) {
				docScanner.close();
			}
		}
	}

	/**
	 * <pre>
	 * synset_offset lex_filenum ss_type w_cnt word_1 lex_id_1 ...  word_(w_cnt) lex_id_(w_cnt) p_cnt ptr_1 word_lex_id_1 pos_1 frames_1  ... ptr_(p_cnt)  word_lex_id_(p_cnt)  pos_(p_cnt)  frames_(p_cnt) | gloss
	 * </pre>
	 */
	private LexicalType processLine(String line) {
		Scanner sc = null;
		try {
			sc = new Scanner(line);
			Long id = sc.nextLong();
			String name = sc.next().trim();
			PartOfSpeech pos = PartOfSpeech.fromCode(sc.nextInt());

			LexicalType lexicograph = new LexicalType(id, name, pos);

			if (LOG.isLoggable(Level.FINEST)) {
				LOG.log(Level.FINEST, lexicograph.toString());
			}

			return lexicograph;
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
	}

}
