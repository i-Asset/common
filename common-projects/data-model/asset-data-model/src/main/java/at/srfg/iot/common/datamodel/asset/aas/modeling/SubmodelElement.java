package at.srfg.iot.common.datamodel.asset.aas.modeling;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.common.HasDataSpecification;
import at.srfg.iot.common.datamodel.asset.aas.common.HasKind;
import at.srfg.iot.common.datamodel.asset.aas.common.HasSemantics;
import at.srfg.iot.common.datamodel.asset.aas.common.Qualifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.SubmodelElementCollection;
import at.srfg.iot.common.datamodel.asset.api.ISubmodel;
import at.srfg.iot.common.datamodel.asset.api.ISubmodelElement;
@JsonInclude(value = Include.NON_NULL)
@Entity
@Table(name="aas_submodel_element")
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "model_element_id")
public abstract class SubmodelElement extends ReferableElement 
	implements Referable, HasSemantics, HasKind, HasDataSpecification, Qualifiable, ISubmodelElement {
	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;

	@Column(name="kind")
	private Kind kind;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "semantic_id")
	private ReferableElement semanticElement;
	
//	@Embedded
//	private Identifier semanticIdentification;
//	
	@ManyToMany
	@JoinTable(
		name="aas_submodel_element_qualifier", 
		joinColumns={@JoinColumn(name="model_element_id")}, 
		inverseJoinColumns={@JoinColumn(name="constraint_id")})
	private List<Constraint> qualifier;
	
	@ManyToMany
	@JoinTable(
		name = "aas_submodel_element_data_spec", 
		joinColumns = {	@JoinColumn(name = "model_element_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "spec_element_id") })
	private List<ReferableElement> dataSpecification;

	@JsonIgnore
	@ManyToOne(optional = false, cascade = {CascadeType.MERGE})
	@JoinColumn(name = "submodel_id", referencedColumnName = "model_element_id")
	private Submodel submodel;
	
	@Transient
	private Boolean derived;
	
	public SubmodelElement() {
		// default constructor
	}
	/**
	 * Constructor for creating a {@link SubmodelElement} as child element of the provided parent element.
	 * The mandatory <code>idShort</code> must be set separately!
	 * child element of a {@link SubmodelElementContainer}.
	 * @param container The parent element, either a {@link Submodel} or {@link SubmodelElementCollection}
	 */
	public SubmodelElement(SubmodelElementContainer container) {
		this("undefined", container);
	
	}
	public SubmodelElement(String idShort, SubmodelElementContainer container) {
		if ( container.getSubmodel() == null) {
			throw new IllegalStateException("Parent Element not initialized!");
			
		}
		setIdShort(idShort);
		setSubmodel(container.getSubmodel());
		container.addSubmodelElement(this);
	}	
	/**
	 * Transient attribute indicating that a submodel element has been 
	 * derived from a parent Submodel of {@link Kind#Type} (referenced
	 * by {@link Submodel#getSemanticElement()})
	 * @return
	 */
	public Boolean getDerived() {
		return derived;
	}
	public void setDerived(Boolean derived) {
		this.derived = derived;
	}
	@Override
	public Kind getKind() {
		return kind;
	}

	@Override
	public void setKind(Kind kind) {
		this.kind = kind;
	}

	@JsonIgnore
	public Referable getSemanticElement() {
		return semanticElement;
	}
	/**
	 * The semantic element may point to 
	 * <ul>
	 * <li>ConceptDescription
	 * <li>GlobalReference (e.g. external taxonomy)
	 * <li>SubmodelElement of same type and {@link Kind#Type}
	 * </ul>
	 */
	public void setSemanticElement(Referable semanticId) {
		this.semanticElement = ReferableElement.class.cast(semanticId);
	}

	/**
	 * @return the qualifier
	 */
	public List<Constraint> getQualifier() {
		return qualifier;
	}

	/**
	 * @param qualifier the qualifier to set
	 */
	public void setQualifier(List<Constraint> qualifier) {
		this.qualifier = qualifier;
	}

	/**
	 * @return the dataSpecification
	 */
	public List<ReferableElement> getDataSpecification() {
		return dataSpecification;
	}

	/**
	 * @param dataSpecification the dataSpecification to set
	 */
	public void setDataSpecification(List<ReferableElement> dataSpecification) {
		this.dataSpecification = dataSpecification;
	}

	@JsonIgnore
	/**
	 * @return the submodel
	 */
	public ISubmodel getSubmodel() {
		return submodel;
	}

	/**
	 * @param submodel the submodel to set
	 */
	public void setSubmodel(ISubmodel submodel) {
		if (Submodel.class.isInstance(submodel)) {
			this.submodel = Submodel.class.cast(submodel);
		}
	}

	/**
	 * 
	 * @param submodel
	 */
	public void setParentElement(SubmodelElementContainer container) {
		if (container instanceof Submodel) {
			Submodel submodel = (Submodel) container;
			super.setParentElement(submodel);
			setSubmodel(submodel);
			submodel.addChildElement(this);
		}
		else if (container instanceof SubmodelElementCollection) {
			SubmodelElementCollection collection = (SubmodelElementCollection) container;
			super.setParentElement(collection);
			setSubmodel(collection.getSubmodel());
		}
	}
//	@Override
//	public Identifier getSemanticIdentifier() {
//		return semanticIdentification;
//	}
//	public void setSemanticIdentifier(Identifier identifier) {
//		this.semanticIdentification = identifier;
//	}
	
}
