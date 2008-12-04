/**
 * 
 */
package eu.kostia.scanner;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import eu.kostia.entity.*;

/**
 * Scanner for <tt>data.&lt;pos&gt;</tt> files (where <tt>&lt;pos&gt;</tt> is
 * noun , verb , adj and adv)
 * 
 * <pre>
 * Format: synset_offset  lex_filenum  ss_type  w_cnt  word_1  lex_id_1  ...  word_(w_cnt)  lex_id_(w_cnt)  p_cnt  ptr_1  word_lex_id_1  pos_1  frames_1  ...  ptr_(p_cnt)  word_lex_id_(p_cnt)  pos_(p_cnt)  frames_(p_cnt)  |   gloss
 * </pre>
 * 
 * Where:
 * <ul>
 * <li></li>
 * <li></li>
 * <li></li>
 * <li></li>
 * <li></li>
 * <li></li>
 * <li></li>
 * <li></li>
 * <li></li>
 * </ul>
 * 
 * 
 * For more details see <a
 * href="http://wordnet.princeton.edu/man/wndb.5WN">wndb</a>
 * 
 * @author c.cerbo
 * 
 */
public class DataFileScanner {

	static final private Logger LOG = Logger.getLogger("DataFileScanner");

	private File file;

	public DataFileScanner(String filename) {
		file = new File(filename);
		if (!file.getName().startsWith("data")) {
			throw new IllegalArgumentException(
					"Filename has to start with 'data'");
		}
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	public List<DataFileRow> scan() throws FileNotFoundException {
		List<DataFileRow> dataFileRows = new ArrayList<DataFileRow>();

		Scanner docScanner = null;
		int count = 0;
		try {
			docScanner = new Scanner(file);

			while (docScanner.hasNextLine()) {
				count++;
				String line = docScanner.nextLine();
				if (line.startsWith(" ")) {
					continue;
				}

				// Line scanner
				DataFileRow row = processLine(line);
				row.setRownum(count);
				dataFileRows.add(row);

			}
		} catch (Exception e) {
			throw new IllegalStateException("File :'" + file.getAbsolutePath()
					+ "', error at line nr. " + count + "'", e);
		} finally {
			if (docScanner != null) {
				docScanner.close();
			}
		}

		return dataFileRows;
	}

	/**
	 * <pre>
	 * synset_offset lex_filenum ss_type w_cnt word_1 lex_id_1 ...  word_(w_cnt) lex_id_(w_cnt) p_cnt ptr_1 word_lex_id_1 pos_1 frames_1  ... ptr_(p_cnt)  word_lex_id_(p_cnt)  pos_(p_cnt)  frames_(p_cnt) | gloss
	 * </pre>
	 */
	private DataFileRow processLine(String line) {
		Scanner sc = null;
		DataFileRow dataFileRow = new DataFileRow();
		try {
			sc = new Scanner(line);
			long synset_offset = sc.nextLong();
			dataFileRow.setSynsetId(synset_offset);

			int lex_filenum = sc.nextInt();
			dataFileRow.setLexFilenum(lex_filenum);

			PartOfSpeech ss_type = PartOfSpeech.fromSymbol(sc.next().charAt(0));
			dataFileRow.setSourcePos(ss_type);

			int w_cnt = Integer.parseInt(sc.next(), 16);
			String[] word = new String[w_cnt];
			char[] lex_id = new char[w_cnt];
			for (int i = 0; i < w_cnt; i++) {
				word[i] = sc.next();
				lex_id[i] = sc.next().charAt(0);
			}
			dataFileRow.setLemmas(word);
			dataFileRow.setLexId(lex_id);

			int p_cnt = sc.nextInt();
			SemanticRelationType[] pointer = new SemanticRelationType[p_cnt];
			Long[] word_lex_id = new Long[p_cnt];
			PartOfSpeech[] lex_pos = new PartOfSpeech[p_cnt];
			int[] source = new int[p_cnt];
			int[] target = new int[p_cnt];
			for (int i = 0; i < p_cnt; i++) {
				pointer[i] = SemanticRelationType.fromSymbol(sc.next());
				word_lex_id[i] = sc.nextLong();
				lex_pos[i] = PartOfSpeech.fromSymbol(sc.next().charAt(0));
				String source_target = sc.next();
				source[i] = Integer.parseInt(source_target.substring(0, 2), 16);
				target[i] = Integer.parseInt(source_target.substring(2, 4), 16);
			}
			dataFileRow.setPointer(pointer);
			dataFileRow.setTargetSynsetOffsets(word_lex_id);
			dataFileRow.setTargetPos(lex_pos);
			dataFileRow.setSource(source);
			dataFileRow.setTarget(target);

			// Frames only for verbs

			// Pipeline (|) or frames before glossar definition
			String temp = sc.next();

			// Frames only for verbs
			if (!temp.equals("|")) {
				int f_cnt = Integer.parseInt(temp);
				Integer[] frameNumber = new Integer[f_cnt];
				Integer[] wordNumber = new Integer[f_cnt];

				for (int i = 0; i < f_cnt; i++) {
					sc.next(); // char '+'
					frameNumber[i] = Integer.parseInt(sc.next(), 16);
					wordNumber[i] = Integer.parseInt(sc.next(), 16);
				}

				dataFileRow.setFrameNumber(frameNumber);
				dataFileRow.setWordNumber(wordNumber);

				// Pipeline (|) before glossar definition
				sc.next();
			}

			StringBuilder glossar = new StringBuilder();
			while (sc.hasNext()) {
				glossar.append(sc.next());
				if (sc.hasNext()) {
					glossar.append(" ");
				}
			}
			dataFileRow.setGlossary(glossar.toString());

			// add to list
			if (LOG.isLoggable(Level.FINEST)) {
				LOG.log(Level.FINEST, dataFileRow.toString());
			}

			return dataFileRow;
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
	}
}
