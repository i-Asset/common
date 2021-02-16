package at.srfg.iot.common.datamodel.asset.aas.common.types;

import at.srfg.iot.common.datamodel.asset.aas.dictionary.ConceptDescription;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;
/**
 * The category is a value that gives further meta information w.r.t. 
 * to the class of the element. It affects the expected existence 
 * of attributes and the applicability of constraints.
 * 
 * The current enum provides the constants used mainly with {@link ConceptDescription}
 * elements.
 * 
 * @author dglachs
 *
 */
public enum CategoryEnum {
	/**
	 * A constant is a {@link Property} with a value that 
	 * does not change over time
	 */
	CONSTANT,
	/**
	 * A parameter property is a property that is once set and then 
	 * typically does not change over time.
	 */
	PARAMETER,
	/**
	 * A variable property is a property that is calculated during 
	 * runtime, i.e. its value is a runtime value.
	 */
	VARIABLE,
	VALUE,
	PROPERTY,
	QUALIFIER,
	APPLICATION_CLASS,
	CAPABILITY,
	DOCUMENT,
	COLLECTION,
	/**
	 * Used with {@link ConceptDescription} elements for Entity submodel Elements
	 */
	ENTITY,
	/**
	 * Used with {@link ConceptDescription} elements for {@link EventElement}
	 */
	EVENT,
	STRING_TRANLATABLE,
	FUNCTION,
	REFERENCE,
	VIEW,
	;
}
