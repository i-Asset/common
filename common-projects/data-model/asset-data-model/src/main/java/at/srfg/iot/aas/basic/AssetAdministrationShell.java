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
import javax.persistence.DiscriminatorValue;
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
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.aas.common.DirectoryEntry;
import at.srfg.iot.aas.common.HasDataSpecification;
import at.srfg.iot.aas.common.Identifiable;
import at.srfg.iot.aas.common.Referable;
import at.srfg.iot.aas.common.referencing.IdPart;
import at.srfg.iot.aas.common.referencing.IdentifiableElement;
import at.srfg.iot.aas.common.referencing.KeyElementsEnum;
import at.srfg.iot.aas.common.referencing.ReferableElement;
import at.srfg.iot.aas.common.referencing.Reference;
import at.srfg.iot.aas.dictionary.ConceptDictionary;
import at.srfg.iot.api.IAssetAdministrationShell;
import at.srfg.iot.api.ISubmodel;

@Entity
@Table(name="aas")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("AssetAdministrationShell")
@PrimaryKeyJoinColumn(name="model_element_id")
public class AssetAdministrationShell extends IdentifiableElement implements Referable, Identifiable, HasDataSpecification, DirectoryEntry, IAssetAdministrationShell {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="derivedFrom")
	private AssetAdministrationShell derivedFromElement;
	
	@Transient
	private Reference derivedFromReference;
	
	@ManyToMany
	@JoinTable(name = "aas_data_spec", joinColumns = {
			@JoinColumn(name = "model_element_id") }, inverseJoinColumns = { @JoinColumn(name = "spec_element_id") })
	private List<ReferableElement> dataSpecification;
	
	@OneToMany (mappedBy = "assetAdministrationShell", cascade = CascadeType.ALL)
	private List<ConceptDictionary> conceptDictionary;
	@OneToOne (mappedBy = "assetAdministrationShell", cascade = CascadeType.ALL, optional = false)
	private Asset asset;
//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinTable(name = "aas_submodel_assignment", 
//		joinColumns = {	@JoinColumn(name = "model_element_id") }, 
//		inverseJoinColumns = { @JoinColumn(name = "submodel_id") })
//	private List<Submodel> subModel;
	
	/**
	 * Map of {@link Endpoint} elements, at least one entry
	 */
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "id.modelElement", fetch=FetchType.LAZY)
	@MapKey(name="id.index")
	private Map<Integer, Endpoint> endpointMap = new HashMap<Integer, Endpoint>(1);
	
	@JsonIgnore
	public Map<Integer,Endpoint> getEndpoints() {
		return endpointMap;
	}

	/**
	 * Default constructor
	 */
	public AssetAdministrationShell() {
		
	}
	public AssetAdministrationShell(String identifier) {
		this(new Identifier(identifier));
	}
	public AssetAdministrationShell(Identifier identifier) {
		this.setIdentification(identifier);
		this.setIdShort("");
	}
	/**
	 * 
	 * @param asset
	 */
	public AssetAdministrationShell(Asset asset) {
		setAsset(asset);
		asset.setAssetAdministrationShell(this);
		// TODO: handle assetSubmodel
	}
	
	/**
	 * @return the derivedFrom
	 */
	@JsonIgnore
	public AssetAdministrationShell getDerivedFromElement() {
		return derivedFromElement;
	}

	/**
	 * @param derivedFrom the derivedFrom to set
	 */
	@JsonIgnore
	public void setDerivedFromElement(AssetAdministrationShell derivedFrom) {
		this.derivedFromElement = derivedFrom;
		if (derivedFrom != null) {
			this.derivedFromReference = new Reference(derivedFrom);
		}
	}
	/**
	 * Create a 
	 * @return
	 */
	@Override
	public Reference getDerivedFrom() {
		if (derivedFromElement!=null) {
			return new Reference(derivedFromElement);
		}
		return derivedFromReference;
	}
	@Override
	public void setDerivedFrom(Reference reference) {
		// Reference to a parent provided, must be resolved and stored!
		this.derivedFromReference = reference; 
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
	 * @return the conceptDictionary
	 */
	public List<ConceptDictionary> getConceptDictionary() {
		return conceptDictionary;
	}
	/**
	 * Convenience method to obtain a dictionary based on its short id
	 * @param idShort
	 * @return
	 */
	public Optional<ConceptDictionary> getConceptDictionary(String idShort) {
		if ( getConceptDictionary()!= null && ! getConceptDictionary().isEmpty()) {
			return getConceptDictionary().stream()
					.filter(new Predicate<ConceptDictionary>() {

						@Override
						public boolean test(ConceptDictionary t) {
							return idShort.equals(t.getIdShort());
						}
					})
					.findFirst();
		}
		return Optional.empty();
	}
	public void setConceptDictionary(List<ConceptDictionary> conceptDictionary) {
		this.conceptDictionary = conceptDictionary;
	}

	/**
	 * @param conceptDictionary the conceptDictionary to set
	 */
	public void addConceptDictionary(ConceptDictionary toAdd) {
		if ( this.conceptDictionary == null ) {
			this.conceptDictionary = new ArrayList<ConceptDictionary>();
		}
		toAdd.setAssetAdministrationShell(this);
		toAdd.setParentElement(this);
		//
		this.conceptDictionary.add(toAdd);
	}
	/**
	 * @return the asset
	 */
	public Asset getAsset() {
		return asset;
	}
	/**
	 * @param asset the asset to set
	 */
	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	public void setSubmodels(List<Referable> sub) {
		// ignore
		
		for (Referable e : sub) {
			switch(e.getModelType()) {
			case Submodel:
				addSubmodel(Submodel.class.cast(e));
				break;
			case Reference:
				Reference refToSub = Reference.class.cast(e);
				if ( refToSub.hasTargetType(KeyElementsEnum.Submodel)) {
					getChildElements().add(refToSub);
					//addChildElement(refToSub);
				}
				break;
			default:
			}
		}
	}
	/**
	 * Provide a list of references to the submodels
	 * @return
	 */
	public List<ReferableElement> getSubmodels() {
		if (getChildren()!= null && !getChildren().isEmpty()) {
			return getChildren().stream()
				// filter for Submodel
				.filter(new Predicate<Referable>() {

					@Override
					public boolean test(Referable t) {
						return Submodel.class.isInstance(t);
					}
				})
				// cast to Submodel
				.map(new Function<Referable, Reference>() {

					@Override
					public Reference apply(Referable t) {
						return new Reference(t);
						//return Submodel.class.cast(t);
					}
				
				})
				.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}
//	/**
//	 * @return the subModel
//	 */
//	@JsonIgnore
//	public List<Submodel> getSubModel() {
//		if (getChildren()!= null && !getChildren().isEmpty()) {
//			return getChildren().stream()
//				// filter for Submodel
//				.filter(new Predicate<Referable>() {
//
//					@Override
//					public boolean test(Referable t) {
//						return Submodel.class.isInstance(t);
//					}
//				})
//				// cast to Submodel
//				.map(new Function<Referable, Submodel>() {
//
//					@Override
//					public Submodel apply(Referable t) {
//						return Submodel.class.cast(t);
//					}
//				
//				})
//				.collect(Collectors.toList());
//		}
//		return new ArrayList<>();
//	}
	public Optional<ISubmodel> getSubmodel(String idShort) {
		List<ISubmodel> subModel = getChildElements(ISubmodel.class);
		if (! subModel.isEmpty()) {
			return subModel.stream()
				.filter(new Predicate<ISubmodel>() {

					@Override
					public boolean test(ISubmodel t) {
						return t.getIdShort().equals(idShort);
					}
				})
				.findFirst();
		}
		return Optional.empty();
	}
	public Optional<Submodel> getSubmodel(Identifier identifier) {
		List<Submodel> subModel = getChildElements(Submodel.class);
		if (! subModel.isEmpty()) {
			return subModel.stream()
				.filter(new Predicate<Submodel>() {

					@Override
					public boolean test(Submodel t) {
						return t.getIdentification().equals(identifier);
					}
				})
				.findFirst();
		}
		return Optional.empty();
	}
	/**
	 * @param subModel the subModel to set
	 */
	public void setSubModel(List<Submodel> subModel) {
		for (Submodel model : subModel) {
			addChildElement(model);
		}
	}
	/**
	 * Helper method to add a submodel to the asset administration shell
	 * @param submodel
	 */
	public void addSubmodel(ISubmodel submodel) {
		Submodel sub = Submodel.class.cast(submodel);
		sub.setParent(this);
		this.addChild(sub);
	}
	public boolean removeSubmodel(ISubmodel submodel) {
		if ( Submodel.class.isInstance(submodel)) {
			return this.removeChild(Submodel.class.cast(submodel));
		}
		return false;
	}
//
//	@Override
//	public List<Submodel> getChildElements() {
//		if (getChildren()!= null && !getChildren().isEmpty()) {
//			return getChildren().stream()
//				// filter for Submodel
//				.filter(new Predicate<ReferableElement>() {
//
//					@Override
//					public boolean test(ReferableElement t) {
//						return Submodel.class.isInstance(t);
//					}
//				})
//				// cast to Submodel
//				.map(new Function<ReferableElement, Submodel>() {
//
//					@Override
//					public Submodel apply(ReferableElement t) {
//						return Submodel.class.cast(t);
//						//return Submodel.class.cast(t);
//					}
//				
//				})
//				.collect(Collectors.toList());
//		}
//		return new ArrayList<>();
//	}
//
	/**
	 * Add a new child element to the shell. the child must be a {@link Submodel}
	 */
	@Override
	public void addChildElement(Referable submodel) {
		if (Submodel.class.isInstance(submodel)) {
			addSubmodel(Submodel.class.cast(submodel));
		}
		else {
			throw new IllegalArgumentException("Only submodel allowed as children");
		}
	}
	/**
	 * Remove a referable element from the shell
	 */
	public boolean removeChildElement(Referable submodel) {
		if ( Submodel.class.isInstance(submodel) ) {
			return removeSubmodel(Submodel.class.cast(submodel));
		}
		return false;
	}
}
