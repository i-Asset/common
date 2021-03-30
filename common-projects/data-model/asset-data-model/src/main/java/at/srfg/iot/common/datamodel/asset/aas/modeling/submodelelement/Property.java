package at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.types.DataTypeEnum;

@Entity
@Table(name="aas_submodel_element_property")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public class Property extends DataElement<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Default constructor
	 */
	public Property() {
		
	}
	public Property(SubmodelElementContainer container) {
		super(container);
	}
	@Transient
	@JsonIgnore
	private Consumer<String> consumer;
	@Transient
	@JsonIgnore
	private Supplier<String> supplier;

	/**
	 * Convenience constructor. Creates and assigns the {@link Property} as
	 * a direct child element to the provided {@link SubmodelElementContainer}.
	 * @param idShort
	 * @param submodel
	 */
	public Property(String idShort, SubmodelElementContainer submodel) {
		super(idShort, submodel);
	}
	/**
	 * String based value of the property
	 */
	@Column(name="property_value")
	private String value;
	/**
	 * Denotes the type of the property value, e.g. STRING, DOUBLE, REAL_MEASURE, QUANTITY
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="property_value_qualifier")
	private DataTypeEnum valueQualifier;
	
	/**
	 * @return the value
	 */
	public String getValue() {
		if ( supplier!=null) {
			// when supplier function present
			return supplier.get();
		}
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		if ( consumer!=null) {
			// when consumer function present
			consumer.accept(value);
		}
		this.value = value;
	}
	/**
	 * @return the valueQualifier
	 */
	public DataTypeEnum getValueQualifier() {
		return valueQualifier;
	}
	/**
	 * @param valueQualifier the valueQualifier to set
	 */
	public void setValueQualifier(DataTypeEnum valueQualifier) {
		this.valueQualifier = valueQualifier;
	}
	
	// TODO: add value Id Reference
	
	public void setGetter(Supplier<String> supplier) {
		this.supplier = supplier;
	}
	public void setSetter(Consumer<String> consumer) {
		this.consumer = consumer;
	}
	@Override
	public Optional<Referable> asInstance() {
		if ( isInstance()) {
			return Optional.of(this);
		}
		Property instance = new Property();
		instance.setIdShort(getIdShort());
		instance.setKind(Kind.Instance);
		instance.setCategory(getCategory());
		instance.setDescription(getDescription());
		instance.setSemanticElement(this);
		// set the value qualifier
		instance.setValueQualifier(getValueQualifier());
		return Optional.of(instance);
	}

}
