package eu.kostia.entity;

public enum PartOfSpeech {
	NOUN(1, 'n', "noun"), VERB(2, 'v', "verb"), ADJECTIVE(3, 'a', "adj"), ADVERB(
			4, 'r', "adv"), ADJECTIVE_SATELLITE(5, 's', "advs");

	private char symbol;
	private String abbreviation;
	private int code;

	private PartOfSpeech(int code, char symbol, String abbreviation) {
		this.symbol = symbol;
		this.code = code;
		this.abbreviation = abbreviation;
	}

	public int getCode() {
		return code;
	}

	public char getSymbol() {
		return symbol;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public static PartOfSpeech fromSymbol(char symbol) {
		for (PartOfSpeech pos : values()) {
			if (symbol == pos.getSymbol()) {
				return pos;
			}
		}

		throw new IllegalArgumentException("Symbol '" + symbol + "' unknown!");
	}

	public static PartOfSpeech fromCode(int code) {
		for (PartOfSpeech pos : values()) {
			if (code == pos.getCode()) {
				return pos;
			}
		}

		throw new IllegalArgumentException("Code '" + code + "' unknown!");
	}

}
