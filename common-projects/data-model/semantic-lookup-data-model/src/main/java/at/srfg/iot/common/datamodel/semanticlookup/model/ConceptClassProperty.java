package at.srfg.iot.common.datamodel.semanticlookup.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="concept_class_property")
public class ConceptClassProperty {
	@JsonIgnore
	@EmbeddedId
	private ConceptClassPropertyPK pk;
	@Column(name="value_constraint")
	boolean valueConstraint = false;
	@ManyToOne
	@JoinColumn(name="class_id", insertable = false, updatable = false)
	private ConceptClass conceptClass;
	
	@ManyToOne( cascade = {CascadeType.ALL, CascadeType.REMOVE})
	@JoinColumn(name="property_id", insertable = false, updatable = false)
	private ConceptProperty property;
	@JsonIgnore
	@OneToMany(mappedBy = "classProperty", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST})
	private List<ConceptClassPropertyValue> values;
	
	public ConceptClassProperty() {
		// default constructor
	}
	public ConceptClassProperty(ConceptClass conceptClass, ConceptProperty property) {
		this.pk = new ConceptClassPropertyPK(conceptClass, property);
		this.conceptClass = conceptClass;
		this.property = property;
		this.valueConstraint = false;
	}
	@Transient
	public Set<ConceptPropertyValue> getPropertyValues() {
		if ( values == null ) {
			return new HashSet<>(); 
		}
		return values.stream().map(t -> t.getValue()).collect(Collectors.toSet());
	}

	public void addPropertyValue(ConceptPropertyValue v) {
		if ( values == null) {
			values = new ArrayList<>();
		}
		ConceptClassPropertyValue value = new ConceptClassPropertyValue(this,v);
		if ( !values.contains(value) ) {
			values.add(value);
		}
	}
	public boolean removePropertyValue(ConceptPropertyValue v) {
		if ( values != null && !values.isEmpty()) {
			Optional<ConceptClassPropertyValue> cv = values.stream().filter(new Predicate<ConceptClassPropertyValue>() {

				@Override
				public boolean test(ConceptClassPropertyValue t) {
					return t.getValue().equals(v);
				}
			}).findAny();
			if ( cv.isPresent()) {
				return this.values.remove(cv.get());
			}
		}
		return false;
	}
	public List<ConceptClassPropertyValue> getValues() {
		return values;
	}


	public void setValues(List<ConceptClassPropertyValue> values) {
		this.values = values;
	}


	public boolean isValueConstraint() {
		return valueConstraint;
	}


	public void setValueConstraint(boolean valueConstraint) {
		this.valueConstraint = valueConstraint;
	}


	public ConceptClass getConceptClass() {
		return conceptClass;
	}


	public ConceptProperty getProperty() {
		return property;
	}

}
