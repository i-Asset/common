package at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.SubmodelElement;

@Entity
@Table(name="aas_submodel_element_relationship")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public class RelationshipElement extends SubmodelElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="subject_element_id", nullable = false)
	private ReferableElement first;
	@ManyToOne
	@JoinColumn(name="object_element_id", nullable =false)
	private ReferableElement second;

	public RelationshipElement() {
		// default
	}
	public RelationshipElement(SubmodelElementContainer container) {
		super(container);
	}
	/**
	 * Convenience constructor. Creates and assigns the {@link RelationshipElement} as
	 * a direct child element to the provided {@link SubmodelElementContainer}.
	 * @param idShort
	 * @param submodel
	 */
	public RelationshipElement(String idShort, SubmodelElementContainer submodel) {
		super(idShort, submodel);
	}
	/**
	 * @return the first
	 */
	public ReferableElement getFirst() {
		return first;
	}
	/**
	 * @param first the first to set
	 */
	public void setFirst(ReferableElement first) {
		this.first = first;
	}
	/**
	 * @return the second
	 */
	public ReferableElement getSecond() {
		return second;
	}
	/**
	 * @param second the second to set
	 */
	public void setSecond(ReferableElement second) {
		this.second = second;
	}
	@Override
	public Optional<Referable> asInstance() {
		if ( isInstance()) {
			return Optional.of(this);
		}
		RelationshipElement instance = new RelationshipElement();
		instance.setIdShort(getIdShort());
		instance.setKind(Kind.Instance);
		instance.setCategory(getCategory());
		instance.setDescription(getDescription());
		instance.setSemanticElement(this);
		// set the value qualifier
		return Optional.of(instance);
	}
	
}
