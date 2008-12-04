package eu.kostia.scanner;

import java.util.*;

import eu.kostia.entity.*;

/**
 * Row Format:
 * 
 * <pre>
 * synset_offset  lex_filenum  ss_type  w_cnt  word_1  lex_id_1  ...  word_(w_cnt)  lex_id_(w_cnt)  p_cnt  pointer_symbol_1  synset_offset_1  pos_1  source/target_1  ...  pointer_symbol_(p_cnt)  synset_offset_(p_cnt)  pos_(p_cnt)  source/target_(p_cnt)  [frames]  |   gloss
 * </pre>
 * 
 * 
 * Example:
 * 
 * <pre>
 * 00001740       03           n        01     entity  0         003    -                 00001930         n      0000             -                 00002137         n      0000             -                 04424418         n      0000             | that which is perceived or known or inferred to have its own distinct existence (living or nonliving)
 * synset_offset  lex_filenum  ss_type  w_cnt  word_1  lex_id_1  p_cnt  pointer_symbol_1  synset_offset_1  pos_1  source/target_1  pointer_symbol_2  synset_offset_2  pos_2  source/target_2  pointer_symbol_3  synset_offset_3  pos_3  source/target_3  | gloss
 * 
 * 00006024       03           n        01     heterotroph 0         001    @                 00004475         n      0000      | an organism that depends on complex organic substances for nutrition  
 * synset_offset  lex_filenum  ss_type  w_cnt  word_1      lex_id_1  p_cnt  pointer_symbol_1  synset_offset_1  pos_1  source/target_1  | gloss
 * </pre>
 * 
 * @author c.cerbo
 * 
 */
public class DataFileRow {

	private Long synsetId;
	private Integer lexFilenum;
	private PartOfSpeech sourcePos;
	private String[] lemmas;
	private char[] lexId;
	private SemanticRelationType[] pointer;
	private Long[] targetSynsetOffsets;
	private PartOfSpeech[] targetPos;
	private int[] source;
	private int[] target;
	private Integer[] frameNumber; // in data.verb only
	private Integer[] wordNumber; // in data.verb only
	private String glossary;
	private int rownum;

	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}

	public Long getSynsetId() {
		return synsetId;
	}

	public void setSynsetId(Long synsetId) {
		this.synsetId = synsetId;
	}

	public Integer getLexFilenum() {
		return lexFilenum;
	}

	public void setLexFilenum(Integer lexFilenum) {
		this.lexFilenum = lexFilenum;
	}

	public PartOfSpeech getSourcePos() {
		return sourcePos;
	}

	public void setSourcePos(PartOfSpeech sourcePos) {
		this.sourcePos = sourcePos;
	}

	public String[] getLemmas() {
		return lemmas;
	}

	public void setLemmas(String[] word) {
		this.lemmas = word;
	}

	public char[] getLexId() {
		return lexId;
	}

	public void setLexId(char[] lexId) {
		this.lexId = lexId;
	}

	public SemanticRelationType[] getPointer() {
		return pointer;
	}

	public void setPointer(SemanticRelationType[] pointer) {
		this.pointer = pointer;
	}

	public Long[] getTargetSynsetOffsets() {
		return targetSynsetOffsets;
	}

	public void setTargetSynsetOffsets(Long[] targetSynsetOffsets) {
		this.targetSynsetOffsets = targetSynsetOffsets;
	}

	public PartOfSpeech[] getTargetPos() {
		return targetPos;
	}

	public void setTargetPos(PartOfSpeech[] targetPos) {
		this.targetPos = targetPos;
	}

	/**
	 * @return the source
	 */
	public int[] getSource() {
		return source;
	}

	/**
	 * @param aSource
	 *            the source to set
	 */
	public void setSource(int[] aSource) {
		source = aSource;
	}

	/**
	 * @return the target
	 */
	public int[] getTarget() {
		return target;
	}

	/**
	 * @param aTarget
	 *            the target to set
	 */
	public void setTarget(int[] aTarget) {
		target = aTarget;
	}

	/**
	 * @return the frameNumber
	 */
	public Integer[] getFrameNumber() {
		return frameNumber;
	}

	/**
	 * @param aFrameNumber
	 *            the frameNumber to set
	 */
	public void setFrameNumber(Integer[] aFrameNumber) {
		frameNumber = aFrameNumber;
	}

	/**
	 * @return the wordNumber
	 */
	public Integer[] getWordNumber() {
		return wordNumber;
	}

	/**
	 * @param aWordNumber
	 *            the wordNumber to set
	 */
	public void setWordNumber(Integer[] aWordNumber) {
		wordNumber = aWordNumber;
	}

	public String getGlossary() {
		return glossary;
	}

	public void setGlossary(String glossary) {
		this.glossary = glossary;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		final String BLANK = " ";
		sb.append(getSynsetId()).append(BLANK);
		sb.append(getLexFilenum()).append(BLANK);
		sb.append(getSourcePos()).append(BLANK);
		sb.append(Arrays.toString(getLemmas())).append(BLANK);
		sb.append(Arrays.toString(getLexId())).append(BLANK);
		sb.append(Arrays.toString(getPointer())).append(BLANK);
		sb.append(Arrays.toString(getTargetSynsetOffsets())).append(BLANK);
		sb.append(Arrays.toString(getTargetPos())).append(BLANK);
		sb.append(Arrays.toString(getSource())).append(BLANK);
		sb.append(Arrays.toString(getTarget())).append(BLANK);

		if (getFrameNumber() != null) {
			sb.append(Arrays.toString(getFrameNumber())).append(BLANK);
			sb.append(Arrays.toString(getWordNumber())).append(BLANK);
		}

		sb.append(" | ").append(getGlossary());

		return sb.toString();
	}

}
