/**
 * 
 */
package eu.kostia.scanner;

import java.util.Arrays;
import eu.kostia.entity.PartOfSpeech;
import eu.kostia.entity.SemanticRelationType;

/**
 * @author c.cerbo
 * 
 */
public class IndexFileRow {

	private String lemma;
	private PartOfSpeech pos;
	private SemanticRelationType[] pointer;
	private int tagsense;
	private Long[] synsetId;

	/**
	 * @return the lemma
	 */
	public String getLemma() {
		return lemma;
	}

	/**
	 * @param aLemma the lemma to set
	 */
	public void setLemma(String aLemma) {
		lemma = aLemma;
	}

	/**
	 * @return the pos
	 */
	public PartOfSpeech getPos() {
		return pos;
	}

	/**
	 * @param aPos the pos to set
	 */
	public void setPos(PartOfSpeech aPos) {
		pos = aPos;
	}

	/**
	 * @return the pointer
	 */
	public SemanticRelationType[] getPointer() {
		return pointer;
	}

	/**
	 * @param aPointer the pointer to set
	 */
	public void setPointer(SemanticRelationType[] aPointer) {
		pointer = aPointer;
	}

	/**
	 * @return the tagsense
	 */
	public int getTagsense() {
		return tagsense;
	}

	/**
	 * @param aTagsense the tagsense to set
	 */
	public void setTagsense(int aTagsense) {
		tagsense = aTagsense;
	}

	/**
	 * @return the synsetId
	 */
	public Long[] getSynsetId() {
		return synsetId;
	}

	/**
	 * @param aSynsetId the synsetId to set
	 */
	public void setSynsetId(Long[] aSynsetId) {
		synsetId = aSynsetId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getLemma() + " " + getPos() + " " + getTagsense() + " " + Arrays.toString(getPointer()) + " " + Arrays.toString(getSynsetId());
	}
}
