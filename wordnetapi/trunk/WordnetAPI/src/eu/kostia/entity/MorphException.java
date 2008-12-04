package eu.kostia.entity;

import java.io.*;
import java.util.*;

import javax.persistence.*;

@Entity
/*
 * An instance of this class contains an inflected form of a word or collocation
 * and one or more base forms.
 */
public class MorphException implements Serializable {

	private static final long serialVersionUID = 100L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String inflectedForm;

	private String[] baseForms;

	@Enumerated(value = EnumType.STRING)
	private PartOfSpeech partOfSpeech;

	public MorphException() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInflectedForm() {
		return inflectedForm;
	}

	public void setInflectedForm(String inflectedForm) {
		this.inflectedForm = inflectedForm;
	}

	/**
	 * Returns the baseforms in a predictable iteration order
	 * 
	 * @return One or more different base forms.
	 */
	public String[] getBaseForms() {
		return baseForms;
	}

	public void setBaseForms(String[] baseForms) {
		this.baseForms = baseForms;
	}

	public PartOfSpeech getPartOfSpeech() {
		return partOfSpeech;
	}

	public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	@Override
	public String toString() {
		return getInflectedForm() + " " + Arrays.toString(getBaseForms()) + " "
				+ getPartOfSpeech();
	}

}
