/**
 * 
 */
package eu.kostia.scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import eu.kostia.entity.PartOfSpeech;
import eu.kostia.entity.SemanticRelationType;

/**
 * Scanner for <tt>index.&lt;pos&gt;</tt> files (where <tt>&lt;pos&gt;</tt> is noun , verb , adj and adv)
 * 
 * <pre>
 * Format: lemma  pos  synset_cnt  p_cnt  ptr_symbol_1  ...  ptr_symbol_(p_cnt)  sense_cnt  tagsense_cnt   synset_offset_1  ...  synset_offset_(synset_cnt)
 * </pre>
 * 
 * Where:
 * <ul>
 * <li>lemma: Lower case ASCII text of word or collocation. Collocations are formed by joining individual words with an
 * underscore (_ ) character.</li>
 * <li>pos: Syntactic category: n for noun files, v for verb files, a for adjective files, r for adverb files.</li>
 * <li>synset_cnt: Number of synsets that lemma is in. This is the number of senses of the word in WordNet. See Sense
 * Numbers below for a discussion of how sense numbers are assigned and the order of synset_offset s in the index files.</li>
 * <li>p_cnt: Number of different pointers that lemma has in all synsets containing it.</li>
 * <li>ptr_symbol: A space separated list of p_cnt different types of pointers that lemma has in all synsets containing
 * it. See wninput(5WN) for a list of pointer_symbol s. If all senses of lemma have no pointers, this field is omitted
 * and p_cnt is 0 .</li>
 * <li>sense_cnt: Same as sense_cnt above. This is redundant, but the field was preserved for compatibility reasons.</li>
 * <li>tagsense_cnt: Number of senses of lemma that are ranked according to their frequency of occurrence in semantic
 * concordance texts.</li>
 * <li>synset_offset: Byte offset in data.pos file of a synset containing lemma . Each synset_offset in the list
 * corresponds to a different sense of lemma in WordNet. synset_offset is an 8 digit, zero-filled decimal integer that
 * can be used with fseek(3) to read a synset from the data file. When passed to read_synset(3WN) along with the
 * syntactic category, a data structure containing the parsed synset is returned.</li>
 * </ul>
 * 
 * Example:
 * 
 * <pre>
 *  coverage n   3           2      @             +             3          2              13344664         05123760         06683784
 *  lemma    pos synset_cnt  p_cnt  ptr_symbol_1  ptr_symbol_2  sense_cnt  tagsense_cnt   synset_offset_1  synset_offset_2  synset_offset_3
 *  redundant: same of synset_cnt
 * </pre>
 * 
 * For more details see <a href="http://wordnet.princeton.edu/man/wndb.5WN">wndb</a>
 * 
 * @author c.cerbo
 * 
 */
public class IndexFileScanner {

	static final private Logger LOG = Logger.getLogger("IndexFileScanner");

	private String filename;

	public IndexFileScanner(String path) {
		this.filename = path;
	}

	public List<IndexFileRow> scan() throws FileNotFoundException {
		List<IndexFileRow> indexFileRows = new ArrayList<IndexFileRow>();

		Scanner docScanner = null;
		int count = 0;
		try {
			docScanner = new Scanner(new File(filename));

			while (docScanner.hasNextLine()) {
				count++;
				String line = docScanner.nextLine();
				if (line.startsWith(" ")) {
					continue;
				}

				// Line scanner
				processLine(indexFileRows, line);

			}
		} catch (Exception e) {
			throw new IllegalStateException("File :'" + filename + "', error at line nr. " + count + "'", e);
		} finally {
			if (docScanner != null) {
				docScanner.close();
			}
		}

		return indexFileRows;
	}

	private void processLine(List<IndexFileRow> indexFileRows, String line) {
		Scanner sc = null;
		IndexFileRow indexWord = new IndexFileRow();
		try {
			sc = new Scanner(line);

			String lemma = sc.next();
			indexWord.setLemma(lemma);

			PartOfSpeech pos = PartOfSpeech.fromSymbol(sc.next().charAt(0));
			indexWord.setPos(pos);

			int synset_cnt = sc.nextInt();
			Long[] synsetId = new Long[synset_cnt];

			int p_cnt = sc.nextInt();
			SemanticRelationType[] pointer = new SemanticRelationType[p_cnt];
			for (int i = 0; i < p_cnt; i++) {
				String pointerSymbol = sc.next();
				pointer[i] = SemanticRelationType.fromSymbol(pointerSymbol);
			}
			indexWord.setPointer(pointer);

			// sense_cnt ist redundant: same of synset_cnt
			sc.nextInt();

			int tagsense_cnt = sc.nextInt();
			indexWord.setTagsense(tagsense_cnt);

			for (int i = 0; i < synset_cnt; i++) {
				synsetId[i] = sc.nextLong();
			}
			indexWord.setSynsetId(synsetId);

			// add to list
			if (LOG.isLoggable(Level.FINEST)) {
				LOG.log(Level.FINEST, indexWord.toString());
			}
			indexFileRows.add(indexWord);
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
	}

}
