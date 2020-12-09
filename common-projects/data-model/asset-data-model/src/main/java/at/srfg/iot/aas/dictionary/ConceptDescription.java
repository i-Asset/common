package at.srfg.iot.aas.dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import at.srfg.iot.aas.basic.GlobalReference;
import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.common.HasDataSpecification;
import at.srfg.iot.aas.common.Identifiable;
import at.srfg.iot.aas.common.Referable;
import at.srfg.iot.aas.common.referencing.IdentifiableElement;
import at.srfg.iot.aas.common.referencing.ReferableElement;
import at.srfg.iot.classification.model.ConceptBase.ConceptType;

@Entity
@Table(name="aas_concept_description")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("ConceptDescription")
@PrimaryKeyJoinColumn(name="model_element_id")
public class ConceptDescription extends IdentifiableElement implements Referable, Identifiable, HasDataSpecification {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Data Specification refers to a collection of {@link ReferableElement}s
	 */
	@ManyToMany
	@JoinTable(name = "aas_concept_description_data_spec", 
		joinColumns = {			@JoinColumn(name = "model_element_id") }, 
		inverseJoinColumns = { 	@JoinColumn(name = "spec_element_id") })
	private List<ReferableElement> dataSpecification;
	/**
	 * A concept description may point to (external) references
	 */
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
	@JoinTable(name = "aas_concept_description_case_of", 
		joinColumns = {			@JoinColumn(name = "model_element_id") }, 
		inverseJoinColumns = { 	@JoinColumn(name = "caseof_element_id") })
	private List<GlobalReference> caseOf;
	
	@Transient
	private Map<String, Object> additionalData;
	/**
	 * Default constructor required for JPA / JSON
	 */
	public ConceptDescription() {
		// default
	}
	public ConceptDescription(Identifier identifier) {
		this.setIdentification(identifier);
	}
	

	public List<GlobalReference> getCaseOf() {
		if ( caseOf == null) {
			caseOf = new ArrayList<>();
		}
		return caseOf;
	}
	public void setCaseOf(List<GlobalReference> caseOf) {
		this.caseOf = caseOf;
	}
	/**
	 * Check whether the concept description "is a" case
	 * of the given {@link ConceptType}
	 * @param type The expected {@link ConceptType} 
	 * @return
	 */
	public boolean isCaseOfType(ConceptType type) {
		if ( caseOf != null && !caseOf.isEmpty()) {
			Optional<GlobalReference> ref = caseOf.stream().filter(new Predicate<GlobalReference>() {

				@Override
				public boolean test(GlobalReference t) {
					return t.getReferencedType().equals(type);
				}
			}).findFirst();
			return ref.isPresent();
		}
		return false;
	}
	public void addCaseOf(GlobalReference identifier) {
		if ( ! getCaseOf().contains(identifier)) {
			getCaseOf().add(identifier);
		}
	}
	/**
	 * getter for the data specification
	 * @see GetHasDataSpecification
	 */
	@Override
	public List<ReferableElement> getDataSpecification() {
		return dataSpecification;
	}

	@Override
	public void setDataSpecification(List<ReferableElement> dataSpecElement) {
		this.dataSpecification = dataSpecElement;
	}

}
