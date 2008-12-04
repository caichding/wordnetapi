/**
 * 
 */
package eu.kostia.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Semantic relation between a source and a target word of type {@link SemanticRelationType}
 * 
 * @author c.cerbo
 * 
 */
@Entity
public class Relation implements Comparable<Relation>, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Word target;

	@Enumerated(value = EnumType.STRING)
	private SemanticRelationType semanticRelationType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Word getTarget() {
		return target;
	}

	public void setTarget(Word target) {
		this.target = target;
	}

	public SemanticRelationType getSemanticRelationType() {
		return semanticRelationType;
	}

	public void setSemanticRelationType(SemanticRelationType semanticRelationType) {
		this.semanticRelationType = semanticRelationType;
	}

	@Override
	public int compareTo(Relation o) {
		int thisVal = getSemanticRelationType().getCode();
		int anotherVal = o.getSemanticRelationType().getCode();
		return (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
	}

	@Override
	public String toString() {

		return getTarget().getLemma() + " (relation: " + getSemanticRelationType() + ")";
	}

}
