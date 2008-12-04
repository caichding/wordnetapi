package eu.kostia.entity;

import java.io.*;
import java.util.*;

import javax.persistence.*;

/**
 * This is the entity to start the search of synsets
 * 
 * @author c.cerbo
 * 
 */
@Entity
public class Word implements Serializable, Comparable<Word> {

	private static final long serialVersionUID = 1L;

	/**
	 * sysset_offset-pos-word_nr
	 * 
	 * @see WordPK
	 */
	@Id
	private String id;

	private String lemma;

	@ManyToOne(cascade = CascadeType.REFRESH)
	private LexicalType lexicalType;

	@OneToMany
	private Set<Relation> synset;

	@Lob
	private String definition;

	// TODO sense ranking

	public Word() {
		synset = new TreeSet<Relation>();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param aId
	 *            the id to set
	 */
	public void setId(String aId) {
		id = aId;
	}

	/**
	 * @return the lemma
	 */
	public String getLemma() {
		return lemma;
	}

	/**
	 * @param aLemma
	 *            the lemma to set
	 */
	public void setLemma(String aLemma) {
		lemma = aLemma;
	}

	/**
	 * @return the lexicalType
	 */
	public LexicalType getLexicalType() {
		return lexicalType;
	}

	/**
	 * @param aLexicalType
	 *            the lexicalType to set
	 */
	public void setLexicalType(LexicalType aLexicalType) {
		lexicalType = aLexicalType;
	}

	/**
	 * @return the synonymSet
	 */
	public Set<Relation> getSynset() {
		return synset;
	}

	/**
	 * @param aSynonymSet
	 *            the synonymSet to set
	 */
	public void setSynset(Set<Relation> aSynonymSet) {
		synset = aSynonymSet;
	}

	public void addSynonym(Relation synonym) {
		synset.add(synonym);
	}

	/**
	 * @return the definition
	 */
	public String getDefinition() {
		return definition;
	}

	/**
	 * @param aDefinition
	 *            the definition to set
	 */
	public void setDefinition(String aDefinition) {
		definition = aDefinition;
	}

	@Override
	public int compareTo(Word o) {
		String thisLemma = getLemma();
		String otherLemma = o.getLemma();
		return thisLemma.compareTo(otherLemma);
	}

}
