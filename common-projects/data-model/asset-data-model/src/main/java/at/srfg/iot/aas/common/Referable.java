package at.srfg.iot.aas.common;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import at.srfg.iot.aas.basic.Asset;
import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.basic.directory.AssetAdministrationShellDescriptor;
import at.srfg.iot.aas.basic.directory.SubmodelDescriptor;
import at.srfg.iot.aas.common.referencing.KeyElementsEnum;
import at.srfg.iot.aas.common.referencing.ReferableDescription;
import at.srfg.iot.aas.common.referencing.ReferableElement;
import at.srfg.iot.aas.common.referencing.Reference;
import at.srfg.iot.aas.dictionary.ConceptDictionary;
import at.srfg.iot.aas.modeling.submodelelement.Blob;
import at.srfg.iot.aas.modeling.submodelelement.EventElement;
import at.srfg.iot.aas.modeling.submodelelement.File;
import at.srfg.iot.aas.modeling.submodelelement.Operation;
import at.srfg.iot.aas.modeling.submodelelement.OperationVariable;
import at.srfg.iot.aas.modeling.submodelelement.Property;
import at.srfg.iot.aas.modeling.submodelelement.ReferenceElement;
import at.srfg.iot.aas.modeling.submodelelement.RelationshipElement;
import at.srfg.iot.aas.modeling.submodelelement.SubmodelElementCollection;
/**
 * Abstract base class for all model elements
 * @author dglachs
 *
 */
@JsonInclude(value = Include.NON_EMPTY)
@JsonTypeInfo(
		property="modelType",
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		defaultImpl = Reference.class)
@JsonSubTypes({
	@Type(value=AssetAdministrationShell.class,			 	name="AssetAdministrationShell"),
	@Type(value=Asset.class, 								name="Asset"),
	@Type(value=Submodel.class, 							name="Submodel"),
	@Type(value=SubmodelElementCollection.class, 			name="SubmodelElementCollection"),
	@Type(value=ConceptDictionary.class, 					name="ConceptDictionary"),
	@Type(value=Blob.class, 								name="Blob"),
	@Type(value=File.class, 								name="File"),
	@Type(value=Property.class, 							name="Property"),
	@Type(value=ReferenceElement.class, 					name="ReferenceElement"),
	@Type(value=EventElement.class, 						name="EventElement"),
	@Type(value=Operation.class, 							name="Operation"),
	@Type(value=OperationVariable.class, 					name="OperationVariable"),
	@Type(value=RelationshipElement.class, 					name="RelationshipElement"),
	// non persistent: Reference
	@Type(value=Reference.class,							name="Reference"),
	// non persistent: Descriptors
	@Type(value=AssetAdministrationShellDescriptor.class,	name="AssetAdministrationShellDescriptor"),
	@Type(value=SubmodelDescriptor.class,					name="SubmodelDescriptor"),
})

public interface Referable {
	/**
	 * Getter for the short id. The {@link ReferableElement#getIdShort()}
	 * returns the local identifier. The local identifier is unique in the
	 * actual context of the {@link Referable}, e.g. within the element 
	 * retrieved with {@link #getParentElement()}.
	 * The parent may be a 
	 * <ul>
	 * <li> {@link AssetAdministrationShell} for a Submodel
	 * <li> {@link Submodel}  for a SubmodelElement
	 * <li> {@link SubmodelElementCollection} for a SubmodelElement
	 * </ul>
	 * The idShort should be unique in the actual context!
	 * @return the short id.
	 */
	public String getIdShort();
	/**
	 * Setter for the short id. Unique identifier for the 
	 * element in the context of the {@link Referable} element.
	 * @param idShort
	 */
	public void setIdShort(String idShort);
	/** 
	 * Getter for the element's category
	 * @return
	 */
	public String getCategory();
	/**
	 * Setter for the category
	 * @param category
	 */
	public void setCategory(String category);
	/**
	 * Navigate to the parent element of the referable (if any)
	 * @return The parent {@link Referable}
	 */
	public Referable getParentElement();
	/**
	 * Add child element to the current {@link Referable}
	 * @return
	 * @throws IllegalStateException in case the referable does not support children
	 */
	public void addChildElement(Referable referable);
	public List<Referable> getChildren();
	/**
	 * Remove a child element 
	 * @param element
	 * @return
	 */
	boolean removeChildElement(Referable element);
	/**
	 * Retrieve all child elements of the requested type
	 */
	<T extends Referable> List<T> getChildElements(Class<T> clazz);
	/**
	 * Obtain a child element based on it's idShort
	 * @param idShort
	 * @return
	 */
	Optional<Referable> getChildElement(String idShort);
	/**
	 * Obtain a child element based on it's idShort
	 * @param idShort
	 * @param clazz The type of the requested element 
	 * @return The {@link Optional} holds the element when found or {@link Optional#empty()
 	 */
	<T extends Referable> Optional<T> getChildElement(String idShort, Class<T> clazz);
	/**
	 * Add a (preferred) description to the {@link Referable}
	 * @param language The language, e.g. "de", "en" 
	 * @param description The description
	 */
	public void setDescription(String language, String description);
	public Collection<ReferableDescription> getDescription();
	/**
	 * Retrieve the model type as {@link KeyElementsEnum}
	 * @return
	 */
	public KeyElementsEnum getModelType();
	@JsonIgnore
	default Optional<ReferableDescription> getFirstDescription() {
		if ( getDescription() != null && !getDescription().isEmpty()) {
			return getDescription().stream().findFirst();
		}
		return Optional.empty();
	}
	/**
	 * provide a {@link Reference} to the parent element
	 * @return
	 */
	default Reference getParent() {
		if ( getParentElement()== null) {
			return null;
		}
		if ( getParentElement() instanceof Reference) {
			return (Reference) getParentElement();
		}
		return new Reference(getParentElement());
	}
	/**
	 * Helper method for obtaining the {@link Reference} to the
	 * current {@link Referable}
	 * @return The reference pointing to the current referable
	 */
	@JsonIgnore
	default Reference asReference() {
		return new Reference(this);
	}
}
