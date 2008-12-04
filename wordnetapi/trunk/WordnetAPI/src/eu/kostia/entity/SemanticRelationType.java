/**
 * 
 */
package eu.kostia.entity;

/**
 * Pointers are used to represent the relations between the words in one synset
 * and another. Semantic pointers represent relations between word meanings, and
 * therefore pertain to all of the words in the source and target synsets.
 * Lexical pointers represent relations between word forms, and pertain only to
 * specific words in the source and target synsets. The following pointer types
 * are usually used to indicate lexical relations: Antonym, Pertainym,
 * Participle, Also See, Derivationally Related. The remaining pointer types are
 * generally used to represent semantic relations.
 * 
 * @author c.cerbo
 * 
 */
public enum SemanticRelationType {
	/**
	 * This relation doesn't exit in wordnet. It corresponds to that synsets
	 * that have more than a word, for example:
	 * 
	 * <pre>
	 * 00015388 03 n 06 animal 0 animate_being 0 beast 0 brute 0 creature 0 fauna 0 092 @ 00004475 n 0000 #m 01313093 n 0000 + 01617192 v 0502 + 01263445 a 0301 + 01263445 a 0302 + 01680756 v 0101 + 01680756 v 0102 -c 00057992 a 0000 -c 00147052 a 0000 -c 00160288 a 0000 -c 00214461 a 0000 -c 00313701 a 0000 -c 00314639 a 0000 -c 00315254 a 0000 -c 00315383 a 0000 -c 01427010 a 0000 -c 01488616 a 0000 -c 01904698 a 0000 -c 01958898 a 0000 -c 01959111 a 0000 -c 02252634 a 0000 -c 02252877 a 0000 -c 00227595 n 0000 -c 00320284 n 0000 &tilde; 01314388 n 0000 &tilde; 01314663 n 0000 &tilde; 01314781 n 0000 &tilde; 01314910 n 0000 &tilde; 01315213 n 0000 &tilde; 01315330 n 0000 &tilde; 01315581 n 0000 &tilde; 01315805 n 0000 &tilde; 01316288 n 0000 &tilde; 01316422 n 0000 &tilde; 01316949 n 0000 &tilde; 01317541 n 0000 &tilde; 01317916 n 0000 &tilde; 01318478 n 0000 &tilde; 01318660 n 0000 &tilde; 01318894 n 0000 &tilde; 01319001 n 0000 &tilde; 01319187 n 0000 &tilde; 01319467 n 0000 &tilde; 01320872 n 0000 &tilde; 01321230 n 0000 &tilde; 01321456 n 0000 &tilde; 01321579 n 0000 &tilde; 01323599 n 0000 &tilde; 01323781 n 0000 &tilde; 01324142 n 0000 &tilde; 01324305 n 0000 &tilde; 01324610 n 0000 &tilde; 01324799 n 0000 &tilde; 01324916 n 0000 &tilde; 01325060 n 0000 -c 01326291 n 0000 -c 01375204 n 0000 &tilde; 01384164 n 0000 &tilde; 01458842 n 0000 &tilde; 01466257 n 0000 &tilde; 01905661 n 0000 &tilde; 01908958 n 0000 &tilde; 02075612 n 0000 &tilde; 02152740 n 0000 &tilde; 02152881 n 0000 &tilde; 02152991 n 0000 &tilde; 02157206 n 0000 &tilde; 02157285 n 0000 &tilde; 02311060 n 0000 &tilde; 02384858 n 0000 &tilde; 02451575 n 0000 -c 04474466 n 0000 -c 04828925 n 0000 -c 05216365 n 0000 %s 05267548 n 0000 %p 05538625 n 0000 -c 05551318 n 0000 %p 05601198 n 0000 -c 05658826 n 0000 -c 07560903 n 0000 -c 09469285 n 0000 &tilde; 09893502 n 0000 &tilde; 10300303 n 0000 -c 10603959 n 0000 -c 10648033 n 0000 -c 13895852 n 0000 -c 14224547 n 0000 -c 00197423 v 0000 -c 00301856 v 0000 -c 00302130 v 0000 -c 01169589 v 0000 -c 01576478 v 0000 | a living organism characterized by voluntary movement
	 * </pre>
	 * 
	 * In this example "animal", "animate_being", "beast", "brute", "creature"
	 * and "fauna" are considered by wordnet semantically the same
	 */
	SAME("==", 0),

	ALSO_SEE("^", 1),

	ANTONYM("!", 2),

	ATTRIBUTE("=", 3),

	CAUSE(">", 4),

	DOMAIN_OF_SYNSET_TOPIC(";c", 5),

	MEMBER_OF_THIS_DOMAIN_TOPIC("-c", 6),

	DERIVATIONALLY_RELATED_FORM("+", 7),

	/**
	 * for adjectives: Pertainym (pertains to noun), for adverbs: Derived from
	 * adjective
	 */
	DERIVED_FROM_ADJECTIVE_PERTAINYM("\\", 8),

	ENTAILMENT("*", 9),

	HYPERNYM("@", 10),

	HYPONYM("~", 11),

	INSTANCE_HYPERNYM("@i", 12),

	INSTANCE_HYPONYM("~i", 13),

	MEMBER_HOLONYM("#m", 14),

	MEMBER_MERONYM("%m", 15),

	PARTICIPLE_OF_VERB("<", 16),

	PART_HOLONYM("#p", 17),

	PART_MERONYM("%p", 18),

	DOMAIN_OF_SYNSET_REGION(";r", 19),

	MEMBER_OF_THIS_DOMAIN_REGION("-r", 20),

	SIMILAR_TO("&", 21),

	SUBSTANCE_HOLONYM("#s", 22),

	SUBSTANCE_MERONYM("%s", 23),

	DOMAIN_OF_SYNSET_USAGE(";u", 24),

	MEMBER_OF_THIS_DOMAIN_USAGE("-u", 25),

	VERB_GROUP("$", 26),

	DOMAIN(";", 27),

	DOMAIN_MEMBER("-", 28);

	private String symbol;
	private int code;

	private SemanticRelationType(String symbol, int code) {
		this.symbol = symbol;
		this.code = code;
	}

	public String getSymbol() {
		return symbol;
	}

	public int getCode() {
		return code;
	}

	public static SemanticRelationType fromSymbol(String symbol) {
		for (SemanticRelationType pointer : values()) {
			if (symbol.equals(pointer.getSymbol())) {
				return pointer;
			}
		}

		throw new IllegalArgumentException("Symbol '" + symbol + "' unknown!");
	}

}
