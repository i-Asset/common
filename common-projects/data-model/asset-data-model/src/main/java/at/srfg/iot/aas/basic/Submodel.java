package at.srfg.iot.aas.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.aas.common.DirectoryEntry;
import at.srfg.iot.aas.common.HasDataSpecification;
import at.srfg.iot.aas.common.HasKind;
import at.srfg.iot.aas.common.HasSemantics;
import at.srfg.iot.aas.common.Identifiable;
import at.srfg.iot.aas.common.Qualifiable;
import at.srfg.iot.aas.common.Referable;
import at.srfg.iot.aas.common.SubmodelElementContainer;
import at.srfg.iot.aas.common.referencing.IdType;
import at.srfg.iot.aas.common.referencing.IdentifiableElement;
import at.srfg.iot.aas.common.referencing.Kind;
import at.srfg.iot.aas.common.referencing.ReferableElement;
import at.srfg.iot.aas.modeling.Constraint;
import at.srfg.iot.aas.modeling.SubmodelElement;
import at.srfg.iot.api.ISubmodel;
import at.srfg.iot.api.ISubmodelElement;

@Entity
@Table(name = "aas_submodel")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public class Submodel extends IdentifiableElement implements Referable, Identifiable, HasSemantics, HasKind, HasDataSpecification, Qualifiable, SubmodelElementContainer, DirectoryEntry, ISubmodel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the {@link Kind} of the element, either {@link Kind#Type}
	 * or {@link Kind#Instance}
	 */
	@Column(name = "kind")
	private Kind kind = Kind.Type;
	/**
	 * the link to the {@link ReferableElement} specifying 
	 * the semantic description of the current element 
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "semantic_id")
	private ReferableElement semanticElement;
//	
//	@Embedded
//	private Identifier semanticIdentification;

	/**
	 * List of {@link ReferableElement}s constituting 
	 * the {@link GetHasDataSpecification} of the current submodel
	 */
	@ManyToMany
	@JoinTable(name = "aas_submodel_data_spec", joinColumns = {
			@JoinColumn(name = "model_element_id") }, inverseJoinColumns = { @JoinColumn(name = "spec_element_id") })
	private List<ReferableElement> dataSpecification;
	/**
	 * The list of {@link Constraint} elements for defining
	 * the {@link Qualifiable} 
	 */
	@ManyToMany
	@JoinTable(name = "aas_submodel_qualifier", joinColumns = {
			@JoinColumn(name = "model_element_id") }, inverseJoinColumns = { @JoinColumn(name = "constraint_id") })
	private List<Constraint> qualifier;
	
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "id.modelElement", fetch=FetchType.LAZY)
	@MapKey(name="id.index")
	private Map<Integer, Endpoint> endpointMap = new HashMap<Integer, Endpoint>(1);
	
	@JsonIgnore
	public Map<Integer,Endpoint> getEndpoints() {
		return endpointMap;
	}

	@JsonIgnore
	public AssetAdministrationShell getAssetAdministrationShell() {
		if ( getParentElement() instanceof AssetAdministrationShell) {
			return (AssetAdministrationShell)getParentElement();
		}
		return null;
	}
	/**
	 * Default constructor
	 */
	public Submodel() {
		// default constructor
	}
	public Submodel(AssetAdministrationShell shell) {
		setParent(shell);
		shell.addChildElement(this);
	}
	/**
	 * Convenience constructor assigning a submodel to 
	 * the provided {@link AssetAdministrationShell}
	 * @param shell
	 */
	public Submodel(Identifier identifier, AssetAdministrationShell shell) {
		setParentElement(shell);
		shell.addChild(this);
		setIdentification(identifier);
		if ( identifier.getIdType().equals(IdType.IdShort)) {
			setIdShort(identifier.getId());
		}
	}
	public Submodel(String idShort, AssetAdministrationShell shell) {
		this(new Identifier(IdType.IdShort, idShort), shell);
	}
	@JsonIgnore
	@Override
	public Referable getSemanticElement() {
		return semanticElement;
	}
	@Override
	public void setSemanticElement(Referable semanticId) {
		this.semanticElement = ReferableElement.class.cast(semanticId);
	}
	@Override
	public List<Constraint> getQualifier() {
		return qualifier;
	}

	@Override
	public void setQualifier(List<Constraint> qualifier) {
		this.qualifier = qualifier;
	}

	@Override
	public Kind getKind() {
		return kind;
	}

	@Override
	public void setKind(Kind kind) {
		this.kind = kind;
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
	/**
	 * @return the submodelElements
	 */
	public List<ISubmodelElement> getSubmodelElements() {
		if ( getChildElements().size()>0) {
			return getChildElements().stream()
					.filter(new Predicate<ReferableElement>() {
	
						@Override
						public boolean test(ReferableElement t) {
							return t instanceof ISubmodelElement;
						}
					})
					.map(new Function<ReferableElement, ISubmodelElement>() {
	
						@Override
						public ISubmodelElement apply(ReferableElement t) {
							return ISubmodelElement.class.cast(t);
						}
						
					})
					.collect(Collectors.toList());
		}
		return new ArrayList<ISubmodelElement>();
	}
//	public void setSubmodelElements(Collection<SubmodelElement> elements) {
//		this.getChildren().addAll(elements);
//	}
	@Override
	public void addSubmodelElement(ISubmodelElement element) {
		SubmodelElement submodelElement = SubmodelElement.class.cast(element);
		// the submodel is the parent element
		submodelElement.setParent(this);
		// the submodel element belongs to this submodel
		submodelElement.setSubmodel(this);
		// keep the element in the map
		this.addChild(submodelElement);
	}
	
	/**
	 * Add a new child element to the shell. the child must be a {@link SubmodelElement}
	 */
	@Override
	public void addChildElement(Referable submodel) {
		
		if (SubmodelElement.class.isInstance(submodel)) {
			// 
			addSubmodelElement(SubmodelElement.class.cast(submodel));
		}
		else {
			throw new IllegalArgumentException("Only submodel elements allowed as children");
		}
	}
	/** 
	 * Remove a child element from the shell
	 */
	public boolean removeChildElement(Referable referable) {
		if ( ReferableElement.class.isInstance(referable)) {
			return removeChild(ReferableElement.class.cast(referable));
		}
		return false;
	}
	@Override
	public boolean removeSubmodelElement(ISubmodelElement element) {
		return removeChildElement(element);
	}
	/**
	 * Required for {@link SubmodelElementContainer}
	 */
	@JsonIgnore
	public ISubmodel getSubmodel() {
		return this;
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Submodel)) {
			return false;
		}
		return super.equals(other);
		
	}

	@Override
	public void setSubmodelElements(List<ISubmodelElement> elements) {
		// remove the existing elements first
		getChildElements().clear();
		// set the list of submodel elements
		for (ISubmodelElement element : elements) {
			addSubmodelElement(element);
		}
	}

	@Override
	public Optional<Referable> asInstance(Referable parent) {
		// TODO Auto-generated method stub
		if (isInstance()) {
			return Optional.empty();
		}
		if (AssetAdministrationShell.class.isInstance(parent)) {
			AssetAdministrationShell s = AssetAdministrationShell.class.cast(parent);
			Submodel model = new Submodel(getIdShort(), s);
			model.setKind(Kind.Instance);
			model.setIdShort(getIdShort());
			model.setCategory(getCategory());
			model.setDescription(parent.getDescription());
			model.setSemanticElement(this);
			for (HasKind hasKind : getChildElements(HasKind.class)) {
				// clone the 
				hasKind.asInstance(model);
			}
			return Optional.of(model);
		}
		throw new IllegalStateException("Wrong usage");
	}
}
