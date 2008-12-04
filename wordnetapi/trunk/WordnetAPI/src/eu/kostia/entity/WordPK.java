package eu.kostia.entity;


/**
 * This class helps to build the id for {@link Word} but it isn't really used ad
 * id
 * 
 * @author c.cerbo
 * 
 */
public class WordPK {
	private long synsetOffset;

	private PartOfSpeech partOfSpeech;

	private int wordIndex;

	public WordPK() {
		super();
	}

	public WordPK(long synsetOffset, PartOfSpeech partOfSpeech, int wordIndex) {
		super();
		this.synsetOffset = synsetOffset;
		this.partOfSpeech = partOfSpeech;
		this.wordIndex = wordIndex;
	}

	public long getSynsetOffset() {
		return synsetOffset;
	}

	public void setSynsetOffset(long synsetOffset) {
		this.synsetOffset = synsetOffset;
	}

	public PartOfSpeech getPartOfSpeech() {
		return partOfSpeech;
	}

	public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	public int getWordIndex() {
		return wordIndex;
	}

	public void setWordIndex(int wordIndex) {
		this.wordIndex = wordIndex;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((partOfSpeech == null) ? 0 : partOfSpeech.hashCode());
		result = prime * result + (int) (synsetOffset ^ (synsetOffset >>> 32));
		result = prime * result + wordIndex;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof WordPK)) {
			return false;
		}
		WordPK other = (WordPK) obj;
		if (partOfSpeech == null) {
			if (other.partOfSpeech != null) {
				return false;
			}
		} else if (!partOfSpeech.equals(other.partOfSpeech)) {
			return false;
		}
		if (synsetOffset != other.synsetOffset) {
			return false;
		}
		if (wordIndex != other.wordIndex) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.valueOf(getSynsetOffset())
				+ String.valueOf(getPartOfSpeech().getSymbol())
				+ String.valueOf(getWordIndex());
	}

}
