package at.srfg.iot.aas.basic;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import at.srfg.iot.aas.common.referencing.IdentifiableElement;
import at.srfg.iot.classification.model.ConceptBase;
import at.srfg.iot.classification.model.ConceptBase.ConceptType;

@Entity
@Table(name="aas_global_reference")
@DiscriminatorValue("GlobalReference")
@PrimaryKeyJoinColumn(name="model_element_id")
public class GlobalReference extends IdentifiableElement {
	@Column(name="referenced_concept_type")
	private ConceptType referencedType;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GlobalReference() {
		// default
	}
	public GlobalReference(Identifier identifier) {
		setIdentification(identifier);
	}
	public GlobalReference(ConceptBase base) {
		setIdentification(new Identifier(base.getConceptId()));
		setReferencedType(base.getBaseType());
	}
	/**
	 * Specifies the kind of the referenced concept from the taxonomy.
	 * Possible Values are:
	 * <ul>
	 * <li>ConceptClass
	 * <li>ConceptProperty
	 * <li>ConceptPropertyUnit
	 * <li>ConceptPropertyValue
	 * </ul>
	 * @See {@link ConceptBase#getBaseType()} 
	 * 
	 */
	public ConceptType getReferencedType() {
		return referencedType;
	}
	
	public void setReferencedType(ConceptType referencedType) {
		this.referencedType = referencedType;
	}
	
}
