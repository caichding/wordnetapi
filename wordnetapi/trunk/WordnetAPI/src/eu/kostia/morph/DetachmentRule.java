package eu.kostia.morph;

import eu.kostia.entity.*;

public class DetachmentRule {
	private PartOfSpeech partOfSpeech;
	private String suffix;
	private String ending;

	/**
	 * Constructor
	 * 
	 * @param partOfSpeech
	 * @param suffix
	 * @param ending
	 */
	public DetachmentRule(PartOfSpeech partOfSpeech, String suffix,
			String ending) {
		super();
		this.partOfSpeech = partOfSpeech;
		this.suffix = suffix;
		this.ending = ending;
	}

	public PartOfSpeech getPartOfSpeech() {
		return partOfSpeech;
	}

	public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getEnding() {
		return ending;
	}

	public void setEnding(String ending) {
		this.ending = ending;
	}

	/**
	 * If a word ends with the suffix, it is stripped from the word and the
	 * corresponding ending is added
	 * 
	 * @param inflectedform
	 * @return A new form of the word, else <tt>null</tt>
	 */
	public String apply(String inflectedform) {
		if (inflectedform.endsWith(getSuffix())) {
			int endindex = inflectedform.lastIndexOf(getSuffix());
			return inflectedform.substring(0, endindex) + getEnding();
		}

		return null;

	}

}
