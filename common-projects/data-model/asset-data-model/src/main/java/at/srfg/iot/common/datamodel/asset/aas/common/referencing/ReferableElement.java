package at.srfg.iot.common.datamodel.asset.aas.common.referencing;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import at.srfg.iot.common.datamodel.asset.aas.basic.Asset;
import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.AssetAdministrationShellDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.SubmodelDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.common.HasSemantics;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.dictionary.ConceptDictionary;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Blob;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.File;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Operation;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.OperationVariable;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.ReferenceElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.RelationshipElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.SubmodelElementCollection;
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
	@Type(value=EventElement.class, 								name="Event"),
	@Type(value=Operation.class, 							name="Operation"),
	@Type(value=OperationVariable.class, 					name="OperationVariable"),
	@Type(value=RelationshipElement.class, 					name="RelationshipElement"),
	// non persistent: Reference
	@Type(value=Reference.class,							name="Reference"),
	// non persistent: Descriptors
	@Type(value=AssetAdministrationShellDescriptor.class,	name="AssetAdministrationShellDescriptor"),
	@Type(value=SubmodelDescriptor.class,					name="SubmodelDescriptor"),
})


@Entity
@Table(name="aas_referable")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="model_type")
public abstract class ReferableElement implements Referable, Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * The Primary Key for each {@link ReferableElement}
	 */
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="model_element_id")
	private Long elementId;
	/**
	 * Optional categorization for each {@link ReferableElement}
	 */
	@Column(name="category", length=100)
	private String category;
	/**
	 * Local Identifier - see {@link Referable#getIdShort()} 
	 */
	@Column(name="id_short", length = 100, nullable = false)
	private String idShort = "";
	/**
	 * Element storing the creation date of the element
	 */
	@JsonIgnore
	@Column(name="created")
	private LocalDateTime created;
	/**
	 * Element holding the last modification date
	 */
	@JsonIgnore
	@Column(name="modified")
	private LocalDateTime modified;
	/**
	 * Element storing the foreign key to the parent element
	 */
	@JsonIgnore
	@XmlTransient
	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name="parent_element_id", referencedColumnName = "model_element_id")
	private ReferableElement parentElement;
	/**
	 * Navigable collection, returns all children of the current element
	 */
	@JsonIgnore
	@XmlTransient
	@OneToMany(mappedBy = "parentElement", cascade = {CascadeType.ALL})
	private List<ReferableElement> childElements;
	/**
	 * Readonly attribute, stores the {@link KeyElementsEnum}
	 * @see #getModelType()
	 */
	@Column(name="model_type", insertable = false, updatable = false)
	private String modelType;
	/**
	 * Map of {@link ReferableDescription} elements, one entry per language
	 */
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "id.modelElement", fetch=FetchType.EAGER)
	@MapKey(name="id.language")
	private Map<String, ReferableDescription> descriptionMap = new HashMap<String, ReferableDescription>(3);
	
	/**
	 * Getter for the element identifier
	 * @return
	 */
	public Long getElementId() {
		return elementId;
	}
	/**
	 * Setter for the element identifier
	 * @param elementId
	 */
	protected void setElementId(Long elementId) {
		this.elementId = elementId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getIdShort() {
		return idShort;
	}
	public void setIdShort(@NotNull String idShort) {
		this.idShort = idShort;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	public LocalDateTime getModified() {
		return modified;
	}
	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}
	/**
	 * Getter for the parent element - used to navigate in the model
	 */
	@JsonIgnore
	public ReferableElement getParentElement() {
		return parentElement;
	}
	@JsonIgnore
	public boolean hasParentReference() {
		return (parentElement != null && Reference.class.isInstance(parentElement));
	}
	public void setParent(ReferableElement referable) {
		this.parentElement = referable;
//		referable.addChild(this);
	}
	/**
	 * Setter for the parent element
	 * <p>
	 * Subclasses may override for type checks
	 * </p>
	 * @param parent The parent {@link ReferableElement} element
	 */
	public void setParentElement(ReferableElement parent) {
		// keep track of parent/child relationship
		this.parentElement = parent;
		//parent.addChild(this);
	}
	/** 
	 * Denotes the ModelElement type
	 */
	public final KeyElementsEnum getModelType() {
		try {
			return KeyElementsEnum.valueOf(getClass().getSimpleName());
		} catch (Exception e) {
			return null;
		}
	}
	public void setDescription(Collection<ReferableDescription> descriptions) {
		if (descriptions != null ) {
			for (ReferableDescription desc : descriptions) {
				setDescription(desc.getLanguage(), desc.getDescription());
			}
		}
	}
	/**
	 * Helper method storing a language dependent description or
	 * label for the element
	 * @param language The ISO language code 
	 * @param description
	 */
	public void setDescription(String language, String description) {
		if ( language != null) {
			ReferableDescription desc = descriptionMap.get(language);
			if ( desc != null ) {
				desc.setDescription(description);
			}
			else {
				this.descriptionMap.put(language, new ReferableDescription(this, language, description));
			}
		}
	}
	/**
	 * Helper method providing access to the element's {@link ReferableDescription} by language
	 * @param language the ISO Language code, e.g. de, en 
	 * @return the description
	 */
	public ReferableDescription getDescription(String language) {
		return this.descriptionMap.get(language);
	}
	/**
	 * Getter for the description
	 * @return
	 */
	public Collection<ReferableDescription> getDescription() {
		if (this.descriptionMap == null) {
			return null;
		}
		return this.descriptionMap.values();
	}
	/**
	 * Helper method to maintain the 
	 * @param child
	 */
	protected void addChild(ReferableElement child) {
		// keep track of the parent/child relationship
		child.setParentElement(this);
		// maintain the childElements collection
		if (this.childElements == null) {
			this.childElements = new ArrayList<ReferableElement>();
		}
		// T
		this.childElements.add(child);
	}
	protected boolean removeChild(ReferableElement child) {
		return getChildElements().remove(child);
	}
	/**
	 * Obtain the list of child elements
	 * @return
	 */
	protected List<ReferableElement> getChildElements() {
		if ( this.childElements == null ) {
			this.childElements = new ArrayList<ReferableElement>();
		}
		return this.childElements;
	}

	public <T extends Referable> List<T> getChildElements(Class<T> clazz) {
		if ( ! getChildElements().isEmpty()) {
			return getChildElements().stream()
					.filter(new Predicate<ReferableElement>() {
						
						@Override
						public boolean test(ReferableElement t) {
							return clazz.isInstance(t);
						}})
					// map to desired class
					.map(new Function<ReferableElement, T>() {
						
						@Override
						public T apply(ReferableElement t) {
							return clazz.cast(t);
						}
					})
					// provide the result
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
	public Optional<Referable> getChildElement(String idShort) {
		return getChildElements().stream()
				// filter for appropriate elements
				.filter(new Predicate<ReferableElement>() {

					@Override
					public boolean test(ReferableElement t) {
						if ( t instanceof Reference ) {
							Reference refToElement = (Reference)t;
							return refToElement.getLastKey().getIdType() == IdType.IdShort && 
								   refToElement.getLastKey().getValue().equals(idShort) ;
						}
						return idShort.equals(t.getIdShort());
					}})
				// use the first element
				.findFirst()
				// map to optional
				.flatMap(new Function<ReferableElement, Optional<Referable>>() {

					@Override
					public Optional<Referable> apply(ReferableElement t) {
						if (t != null) {
							return Optional.of(t);
						}
						else {
							return Optional.empty();
						}
					}
				});
		
	}
	public Optional<Referable> getChildElement(Reference semanticReference) {
		return getChildElements().stream()
			// filter for appropriate elements
			.filter(new Predicate<ReferableElement>() {
	
				@Override
				public boolean test(ReferableElement t) {
					if ( HasSemantics.class.isInstance(t)) {
						HasSemantics hasSemantics = HasSemantics.class.cast(t);
						if ( hasSemantics.getSemanticId() !=null) {
							return hasSemantics.getSemanticId().equals(semanticReference);
						}
					}
					return false;
				}})
			// use the first element
			.findFirst()
			// map to optional
			.flatMap(new Function<ReferableElement, Optional<Referable>>() {
	
				@Override
				public Optional<Referable> apply(ReferableElement t) {
					if (t != null) {
						return Optional.of(t);
					}
					else {
						return Optional.empty();
					}
				}
			});
				
	}
	public <T extends Referable> Optional<T> getChildElement(Reference semanticReference, Class<T> clazz) {
		return getChildElements().stream()
			// filter for appropriate elements
			.filter(new Predicate<ReferableElement>() {
	
				@Override
				public boolean test(ReferableElement t) {
					if ( clazz.isInstance(t)) {
						if ( HasSemantics.class.isInstance(t)) {
							HasSemantics hasSemantics = HasSemantics.class.cast(t);
							if ( hasSemantics.getSemanticId() !=null) {
								return hasSemantics.getSemanticId().equals(semanticReference);
							}
						}
					}
					return false;
				}})
			// use the first element
			.findFirst()
			// map to optional
			.flatMap(new Function<ReferableElement, Optional<T>>() {
	
				@Override
				public Optional<T> apply(ReferableElement t) {
					if (t != null) {
						return Optional.of(clazz.cast(t));
					}
					else {
						return Optional.empty();
					}
				}
			});
				
	}

	public <T extends Referable> Optional<T> getChildElement(String idShort, Class<T> clazz) {
		return getChildElements().stream()
					// filter for appropriate elements
					.filter(new Predicate<Referable>() {

						@Override
						public boolean test(Referable t) {
							if ( clazz.isInstance(t)) {
								return idShort.equals(t.getIdShort());
							}
							return false;
						}})
					// use the first element
					.findFirst()
					// map to optional
					.flatMap(new Function<Referable, Optional<T>>() {

						@Override
						public Optional<T> apply(Referable t) {
							if (t != null) {
								return Optional.of(clazz.cast(t));
							}
							else {
								return Optional.empty();
							}
						}
					});
	}
	protected boolean removeChildElement(String idShort) {
		Optional<Referable> toDelete = getChildElement(idShort, Referable.class);
		if ( toDelete.isPresent()) {
			// 
			return removeChildElement(toDelete.get());
		}
		return false;
	}
	public void addChildElement(Referable child) {
		throw new IllegalStateException("Current Referable does not support children!");
	}
	public boolean removeChildElement(Referable child) {
		throw new IllegalStateException("Current Element does not support children");
	}
	
	/**
	 * 
	 * @return the list of direct children
	 */
	@JsonIgnore
	@Override
	public List<Referable> getChildren() {
		if (this.childElements == null) {
			this.childElements = new ArrayList<ReferableElement>();
		}
		return new ArrayList<Referable>(this.childElements);
	}
	/**
	 * set created and modified date on creation
	 */
	@PrePersist
	private void prePersist() {
		setCreated(LocalDateTime.now());
		setModified(LocalDateTime.now());
	}
	/**
	 * set modified date to new value on update
	 */
	@PreUpdate
	private void preUpdate() {
//		setCreated(LocalDateTime.now());
		setModified(LocalDateTime.now());
	}
	/**
	 * 
	 */
	public String toString() {
		return getModelType().name() + ": " + getId();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elementId == null) ? 0 : elementId.hashCode());
		result = prime * result + ((idShort == null) ? 0 : idShort.hashCode());
		result = prime * result + ((modelType == null) ? 0 : modelType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReferableElement other = (ReferableElement) obj;
		if (elementId == null) {
			if (other.elementId != null)
				return false;
		} else if (!elementId.equals(other.elementId))
			return false;
		if (idShort == null) {
			if (other.idShort != null)
				return false;
		} else if (!idShort.equals(other.idShort))
			return false;
		if (modelType == null) {
			if (other.modelType != null)
				return false;
		} else if (!modelType.equals(other.modelType))
			return false;
		return true;
	}
}
