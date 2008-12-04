/**
 * 
 */
package eu.kostia.dao;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import oracle.toplink.essentials.config.*;
import eu.kostia.entity.*;
import eu.kostia.scanner.*;
import eu.kostia.util.*;

/**
 * @author c.cerbo
 * 
 */
public class CreateWordnetDB {

	/**
	 * Native Sql commands for indexes creation
	 */

	private static final String CREATE_MORPHEXCEPTION_IDX = "CREATE INDEX MORPHEXCEPTION_IDX ON MORPHEXCEPTION (INFLECTEDFORM, PARTOFSPEECH)";

	private static final String CREATE_LEMMA_IDX = "CREATE INDEX LEMMA_IDX ON WORD (LEMMA)";

	static private final Logger LOG = Logger.getLogger(CreateWordnetDB.class
			.getSimpleName());

	private String dictPath;

	public CreateWordnetDB(String dictPath) {
		this.dictPath = dictPath;
	}

	public void start() throws FileNotFoundException {
		H2DatabaseUtil.dropDatabase();
		initDAOProperties();

		populateMorphologicallyExceptions();
		populateLexicalTypes();
		populateWords();
		createIndexes();
		populateSynsets();
	}

	private void initDAOProperties() {
		DAOProperties daoProperties = DAOProperties.getInstance();
		daoProperties.put("toplink.ddl-generation", "create-tables");

		String jdbcUrlValue = "jdbc:h2:" + H2DatabaseUtil.dbpath() + "/"
				+ PersistenceConstants.DB_NAME;
		daoProperties.put(TopLinkProperties.JDBC_URL, jdbcUrlValue);

		System.out.println("DAOProperties: " + DAOProperties.getInstance());
	}

	/**
	 * JPA offers no way to create indexs, there we have to do it with native
	 * sql
	 */
	private void createIndexes() {
		H2DatabaseUtil.executeCommand(CREATE_LEMMA_IDX);
		H2DatabaseUtil.executeCommand(CREATE_MORPHEXCEPTION_IDX);
	}

	/**
	 * Put into DB the content of the data.<pos> files
	 * 
	 * @throws FileNotFoundException
	 */
	void populateWords() throws FileNotFoundException {
		WordDAO wordDAO = new WordDAO();

		wordDAO.removeAll();

		LexicalTypeDAO lexicalTypeDAO = new LexicalTypeDAO();

		for (PartOfSpeech pos : PartOfSpeech.values()) {
			// Satellite adjectives have no exception file
			if (pos == PartOfSpeech.ADJECTIVE_SATELLITE) {
				continue;
			}

			String filename = dictPath + File.separator + "data."
					+ pos.getAbbreviation();
			DataFileScanner dataScanner = new DataFileScanner(filename);
			scannerToDB(wordDAO, lexicalTypeDAO, dataScanner);
		}
	}

	/**
	 * Put the content of a scanned data file into DB
	 */
	private void scannerToDB(WordDAO wordDAO, LexicalTypeDAO lexicalTypeDAO,
			DataFileScanner dataScanner) throws FileNotFoundException {
		System.out.print(">>> Scanning file " + dataScanner.getFile() + "... ");
		List<DataFileRow> rows = dataScanner.scan();
		System.out.println("writing into DB.");
		for (DataFileRow dataRow : rows) {
			long synset_offset = dataRow.getSynsetId();
			Integer lexicalTypeId = dataRow.getLexFilenum();
			LexicalType lexicalType = lexicalTypeDAO.findById(lexicalTypeId
					.longValue());

			PartOfSpeech pos = lexicalType.getPartOfSpeech();

			String definition = dataRow.getGlossary();

			String[] lemmas = dataRow.getLemmas();
			for (int i = 0; i < lemmas.length; i++) {
				Word word = new Word();

				WordPK wordId = new WordPK(synset_offset, pos, i);
				word.setId(wordId.toString());

				String lemma = lemmas[i];
				word.setLemma(lemma);

				word.setLexicalType(lexicalType);

				word.setDefinition(definition);

				// Persist
				log(word);
				wordDAO.insert(word);
			}
		}
	}

	/**
	 * Put into DB the synonyms sets (contained in data.<pos> files)
	 * 
	 * @throws FileNotFoundException
	 */
	void populateSynsets() throws FileNotFoundException {
		System.out.print("Start populating Synsets");
		WordDAO wordDAO = new WordDAO();
		LexicalTypeDAO lexTypeDAO = new LexicalTypeDAO();

		for (PartOfSpeech pos : PartOfSpeech.values()) {
			// Satellite adjectives have no exception file
			if (pos == PartOfSpeech.ADJECTIVE_SATELLITE) {
				continue;
			}

			String filename = dictPath + File.separator + "data."
					+ pos.getAbbreviation();
			DataFileScanner dataScanner = new DataFileScanner(filename);
			System.out.print(">>> Scanning file " + dataScanner.getFile()
					+ "... ");
			List<DataFileRow> rows = dataScanner.scan();
			System.out.println("writing into DB.");

			ConsoleProgressBar progressBar = new ConsoleProgressBar(pos
					.toString(), rows.size());
			progressBar.setMargin(10);
			int count = -1;
			for (DataFileRow row : rows) {
				count++;
				progressBar.setCurrent(count);

				try {
					long synset_offset = row.getSynsetId();

					Integer lexId = row.getLexFilenum();
					LexicalType lexType = lexTypeDAO
							.findById(lexId.longValue());
					PartOfSpeech sourcePos = lexType.getPartOfSpeech();

					// sources and targets are indexes one-based
					int[] sources = row.getSource();
					int[] targets = row.getTarget();
					Long[] targetSynsetOffsets = row.getTargetSynsetOffsets();
					PartOfSpeech[] targetPos = row.getTargetPos();
					SemanticRelationType[] relationType = row.getPointer();
					int n = sources.length;
					String[] lemmas = row.getLemmas();
					int numOfLemmas = lemmas.length;

					// add relation SAME between the lemmas in a Synset
					for (int i = 0; i < numOfLemmas; i++) {
						for (int j = 0; j < lemmas.length; j++) {
							if (i == j) {
								continue;
							}

							WordPK sourceWordPK = new WordPK(synset_offset,
									sourcePos, i);
							Word sourceWord = wordDAO.findById(sourceWordPK);

							WordPK targetWordPK = new WordPK(synset_offset,
									sourcePos, j);
							Word targetWord = wordDAO.findById(targetWordPK);

							Relation relation = new Relation();
							relation.setTarget(targetWord);
							relation
									.setSemanticRelationType(SemanticRelationType.SAME);

							sourceWord.addSynonym(relation);
							wordDAO.update(sourceWord);
						}
					}

					for (int i = 0; i < n; i++) {
						if (sources[i] == 0 && targets[i] == 0) {
							// If source and target are 0, then all the elments
							// in the synset are related with each other
							for (int h = 0; h < numOfLemmas; h++) {
								WordPK sourceWordPK = new WordPK(synset_offset,
										sourcePos, h);
								Word sourceWord = wordDAO
										.findById(sourceWordPK);

								List<Word> targetWords = wordDAO.find(
										targetSynsetOffsets[i], targetPos[i]);
								for (Word targetWord : targetWords) {
									Relation relation = new Relation();
									relation.setTarget(targetWord);
									relation
											.setSemanticRelationType(relationType[i]);

									sourceWord.addSynonym(relation);
									wordDAO.update(sourceWord);
								}

							}
						} else {
							WordPK sourceWordPK = new WordPK(synset_offset,
									sourcePos, sources[i] - 1);
							Word sourceWord = wordDAO.findById(sourceWordPK);

							WordPK targetWordPK = new WordPK(
									targetSynsetOffsets[i], targetPos[i],
									targets[i] - 1);
							Word targetWord = wordDAO.findById(targetWordPK);

							Relation relation = new Relation();
							relation.setTarget(targetWord);
							relation.setSemanticRelationType(relationType[i]);

							sourceWord.addSynonym(relation);
							wordDAO.update(sourceWord);
						}
					}

				} catch (Throwable e) {
					String message = "Error at line " + row.getRownum()
							+ " in file " + dataScanner.getFile()
							+ ". Synset_offset: " + row.getSynsetId();
					throw new IllegalStateException(message, e);
				}
			} // end for each row
		}// end for each pos
	}

	/**
	 * @param aWord
	 */
	private void log(Word word) {
		if (LOG.isLoggable(Level.FINEST)) {
			String msg = word.getId() + word.getLemma() + " ("
					+ word.getLexicalType() + "): " + word.getDefinition();
			LOG.log(Level.FINEST, msg);
		}
	}

	/**
	 * Put into DB the content of the file lexnames
	 */
	void populateLexicalTypes() throws FileNotFoundException {
		System.out.println(">>> Populating lexical types...");
		LexnamesFileScanner sc = new LexnamesFileScanner(dictPath);
		sc.populateDB();
	}

	/**
	 * Put into DB the data of <part of speech>.exc files
	 */
	void populateMorphologicallyExceptions() throws FileNotFoundException {
		System.out.println(">>> Populating morphologically exceptions...");
		MorphExceptionDAO morphExceptionDAO = new MorphExceptionDAO();
		morphExceptionDAO.populateDB(dictPath);
	}

	public static void main(String... args) throws FileNotFoundException {
		String dictPath = "dict";

		if (args.length == 2) {
			dictPath = args[0];
		}

		CreateWordnetDB createDB = new CreateWordnetDB(dictPath);

		long start = System.currentTimeMillis();
		createDB.start();
		long elapsed = System.currentTimeMillis() - start;
		System.out.println(">>> Elapsed time: "
				+ TimeUtil.formatMilliseconds(elapsed));
	}

}
