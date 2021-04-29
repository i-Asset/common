package at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;

@Entity
@Table(name="aas_submodel_element_reference")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public class ReferenceElement extends DataElement<ReferableElement> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ReferenceElement() {
		// default
	}
	public ReferenceElement(SubmodelElementContainer container) {
		super(container);
	}
	/**
	 * Convenience constructor. Creates and assigns the {@link ReferenceElement} as
	 * a direct child element to the provided {@link SubmodelElementContainer}.
	 * @param idShort
	 * @param submodel
	 */
	public ReferenceElement(String idShort, SubmodelElementContainer submodel) {
		super(idShort, submodel);
	}

	/**
	 * String based value of the property
	 */
	@ManyToOne
	@JoinColumn(name="reference_element_id", nullable = false)
	private ReferableElement value;

	/**
	 * @return the value
	 */
	public ReferableElement getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(ReferableElement value) {
		this.value = value;
	}
	/**
	 * Value might be provided as reference element
	 * @return
	 */
	@JsonIgnore
	public Optional<Reference> getValueeAsReference() {
		if ( value instanceof Reference) {
			return Optional.of(Reference.class.cast(value));
		}
		return Optional.empty();
	}
	@Override
	public Optional<Referable> asInstance(Referable parent) {
		if ( isInstance()) {
			return Optional.empty();
		}
		if (SubmodelElementContainer.class.isInstance(parent)) {
			ReferenceElement instance = new ReferenceElement(getIdShort(), SubmodelElementContainer.class.cast(parent));
			instance.setKind(Kind.Instance);
			instance.setCategory(getCategory());
			instance.setDescription(getDescription());
			instance.setSemanticElement(this);
			// set the value qualifier
			return Optional.of(instance);
		}
			
		throw new IllegalStateException("Provided parent must be a SubmodelElementContainer");
	}

}
