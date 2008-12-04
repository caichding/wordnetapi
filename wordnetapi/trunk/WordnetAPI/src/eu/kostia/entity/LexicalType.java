/**
 * 
 */
package eu.kostia.entity;

import javax.persistence.*;

/**
 * @author c.cerbo
 * 
 */
@Entity
public class LexicalType {

	@Id
	private Long id;

	@Enumerated(value = EnumType.STRING)
	private PartOfSpeech partOfSpeech;

	private String name;

	public LexicalType() {
		super();
	}

	public LexicalType(Long id, String name, PartOfSpeech partOfSpeech) {
		super();
		this.id = id;
		this.name = name;
		this.partOfSpeech = partOfSpeech;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param aId
	 *            the id to set
	 */
	public void setId(Long aId) {
		id = aId;
	}

	/**
	 * @return the partOfSpeech
	 */
	public PartOfSpeech getPartOfSpeech() {
		return partOfSpeech;
	}

	/**
	 * @param aPartOfSpeech
	 *            the partOfSpeech to set
	 */
	public void setPartOfSpeech(PartOfSpeech aPartOfSpeech) {
		partOfSpeech = aPartOfSpeech;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param aName
	 *            the name to set
	 */
	public void setName(String aName) {
		name = aName;
	}

	@Override
	public String toString() {
		return getPartOfSpeech() + " " + getName();
	}

}
