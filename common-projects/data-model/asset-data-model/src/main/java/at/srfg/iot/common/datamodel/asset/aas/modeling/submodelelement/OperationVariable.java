package at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.types.DirectionEnum;
import at.srfg.iot.common.datamodel.asset.aas.modeling.SubmodelElement;

@Entity
@Table(name="aas_submodel_element_operation_variable")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public class OperationVariable extends SubmodelElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Column(name="direction")
	private DirectionEnum direction;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="operation_value")
	private SubmodelElement value;

	public OperationVariable() {
		// default
	}

	public OperationVariable(String idShort, Operation operation, DirectionEnum direction) {
		setIdShort(idShort);
		setSubmodel(operation.getSubmodel());
		this.direction = direction;
		operation.addOperationVariable(this);
		
	}


	/**
	 * @return the value
	 */
	public SubmodelElement getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(SubmodelElement value) {
		if ( value.getKind()!=Kind.Type ) {
			throw new IllegalArgumentException("Provided element shall be of kind TYPE!");
		}
		this.value = value;
	}
	/**
	 * @return the direction
	 */
	public DirectionEnum getDirection() {
		return direction;
	}
	/**
	 * @param direction the direction to set
	 */
	public void setDirection(DirectionEnum direction) {
		this.direction = direction;
	}
	@Override
	public Optional<Referable> asInstance(Referable parent) {
		if ( isInstance()) {
			return Optional.empty();
		}
		if (Operation.class.isInstance(parent)) {
			OperationVariable instance = new OperationVariable(getIdShort(), Operation.class.cast(parent), getDirection());
			instance.setKind(Kind.Instance);
			instance.setCategory(getCategory());
			instance.setDescription(getDescription());
			instance.setSemanticElement(this);
			// use the operation variable's value which must be a submodel element of kind "Type"
			instance.setValue(getValue());
			return Optional.of(instance);
		}
			
		throw new IllegalStateException("Provided parent must be a Operation");
	}
}
