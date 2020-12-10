package at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import at.srfg.iot.common.datamodel.asset.aas.common.HasKind;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.SubmodelElement;
import at.srfg.iot.common.datamodel.asset.api.ISubmodelElement;

@Entity
@Table(name="aas_submodel_element_collection")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public class SubmodelElementCollection extends SubmodelElement implements SubmodelElementContainer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="ordered")
	private Boolean ordered = false;
	@Column(name="allow_duplicates")
	private Boolean allowDuplicates = false;
	/**
	 * Default constructor
	 */
	public SubmodelElementCollection() {
		
	}
	public SubmodelElementCollection(SubmodelElementContainer container) {
		super(container);
	}
	/**
	 * Create a {@link SubmodelElementCollection} with a {@link SubmodelElementContainer}
	 * as it's parent element.
	 * @param idShort The idShort identifier
	 * @param submodel The parent element
	 */
	public SubmodelElementCollection(String idShort, SubmodelElementContainer submodel) {
		super(idShort, submodel);
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
							return t instanceof SubmodelElement;
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
	public void setSubmodelElements(List<ISubmodelElement> elements) {
		// remove the existing elements first
		getChildElements().clear();
		// set the list of submodel elements
		for (ISubmodelElement element : elements) {
			addSubmodelElement(element);
		}
	}
	@Override
	public void addSubmodelElement(ISubmodelElement element) {
		// need to cast to 
		SubmodelElement submodelElement = SubmodelElement.class.cast((element));
		// the submodel is the parent element
		submodelElement.setParent(this);
		// the submodel element belongs to this submodel
		submodelElement.setSubmodel(getSubmodel());
		// keep the element in the map
		this.addChild(submodelElement);
	}
	
	/**
	 * Add a new child element to the shell. the child must be a {@link SubmodelElement}
	 * <p>
	 * Overrides the basic {@link ReferableElement#addChildElement(ReferableElement)} and
	 * enables the method
	 * </p>
	 * 
	 */
	@Override
	public void addChildElement(Referable submodel) {
		
		if (SubmodelElement.class.isInstance(submodel)) {
			// 
			addSubmodelElement(SubmodelElement.class.cast(submodel));
		}
		else {
			throw new IllegalArgumentException("Only submodel allowed as children");
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
	@Override
	public Optional<Referable> asInstance(Referable parent) {
		if ( isInstance()) {
			return Optional.empty();
		}
		if (SubmodelElementContainer.class.isInstance(parent)) {
			SubmodelElementContainer s = SubmodelElementContainer.class.cast(parent);
			SubmodelElementCollection model = new SubmodelElementCollection(getIdShort(), s);
			model.setKind(Kind.Instance);
			model.setCategory(getCategory());
			model.setDescription(parent.getDescription());
			model.setSemanticElement(this);
			for (HasKind hasKind : getChildElements(HasKind.class)) {
				// clone the 
				hasKind.asInstance(model);
			}
			return Optional.of(model);
		}
		throw new IllegalStateException("Cannot create instance!");
	}

}
