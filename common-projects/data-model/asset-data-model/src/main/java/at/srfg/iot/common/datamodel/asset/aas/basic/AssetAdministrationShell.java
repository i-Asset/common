package at.srfg.iot.common.datamodel.asset.aas.basic;

import java.util.*;
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

import at.srfg.iot.common.datamodel.asset.aas.common.referencing.*;
import at.srfg.iot.common.datamodel.asset.aas.modeling.SubmodelElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.OperationVariable;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.ReferenceElement;
import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.common.datamodel.asset.aas.common.DirectoryEntry;
import at.srfg.iot.common.datamodel.asset.aas.common.HasDataSpecification;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.dictionary.ConceptDictionary;
import at.srfg.iot.common.datamodel.asset.api.IAssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.api.ISubmodel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

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
	
	/**
	 * Map of {@link Endpoint} elements, at least one entry
	 */
	@OneToMany(cascade = {CascadeType.MERGE}, mappedBy = "id.modelElement", fetch=FetchType.LAZY)
	@MapKey(name="id.index")
	private Map<Integer, Endpoint> endpointMap = new HashMap<Integer, Endpoint>(1);
	
	@JsonIgnore
	public Map<Integer,Endpoint> getEndpoints() {
		return endpointMap;
	}

	/**
	 * Default constructor
	 */
	public AssetAdministrationShell() {}
	public AssetAdministrationShell(String identifier) {
		this(new Identifier(identifier));
	}
	public AssetAdministrationShell(Identifier identifier) {
		this.setIdentification(identifier);
		this.setIdShort("");
	}
	public AssetAdministrationShell(JSONObject obj)
	{
		initWithJSONData(obj);
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
		if ( Reference.class.isInstance(submodel)) {
			return getChildElements().remove(submodel);
		}
		return false;
	}

	/**
	 * initialize this class with data from json object
	 */
	private void initWithJSONData(JSONObject obj)
	{
		Iterator<String> keys = obj.keys();
		while(keys.hasNext()) {
			String key = keys.next();
			if (obj.get(key) instanceof JSONObject) // add entries for AAS
			{
				parseJSONEntry(obj, key, null);
			}
			else if(obj.get(key) instanceof JSONArray) // add submodels
			{
				if(key.equals("submodels"))
				{
					JSONArray arr = obj.getJSONArray("submodels");
					for (int i = 0; i < arr.length(); i++) {

						Submodel sub = new Submodel();
						parseJSONEntry(arr.getJSONObject(i), key, sub);
						addAllSubmodelElements(sub, arr.getJSONObject(i).getJSONArray("submodelElements"));
						this.addSubmodel(sub);
					}
				}
			}
		}
	}

	/**
	 * parse one entry, may be added to submodel or to this asset administration shell
	 * if model is not null, function will add entry to submodel
	 * if model is null, function will add entry to aas instead
	 * @param obj : entity to be parsed
	 * @param key : string value to find current object
	 * @param model : optional parameter to reuse this helper function for submodel entries
	 */
	private void parseJSONEntry(JSONObject obj, String key, Submodel model)
	{
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			switch (key)
			{
				case "category":
					if ((model == null)) this.setCategory((String) obj.get("category"));
					else model.setCategory((String) obj.get("category"));
					break;

				case "idShort":
					if ((model == null)) this.setIdShort((String) obj.get("idShort"));
					else model.setIdShort((String) obj.get("idShort"));
					break;

				case "description":
					if ((model == null)) this.setDescription("de", obj.get("description").toString());
					else model.setDescription("de", obj.get("description").toString());
					break;

				case "administration":
					AdministrativeInformation info = objectMapper.readValue(obj.get("administration").toString(), AdministrativeInformation.class);
					if ((model == null)) this.setAdministration(info);
					else model.setAdministration(info);
					break;

				case "identification":
					Identifier id = objectMapper.readValue(obj.get("identification").toString(), Identifier.class);
					if ((model == null)) this.setIdentification(id);
					else model.setIdentification(id);
					break;

				case "asset":
					JSONObject assetObj = (JSONObject)obj.get("asset");
					Identifier assetID = objectMapper.readValue(assetObj.get("identification").toString(), Identifier.class);
					Asset asset = new Asset();
					asset.setIdentification(assetID);
					asset.setDescription("de", assetObj.get("description").toString());
					asset.setKind(((String) assetObj.get("kind")).equals("Type") ? Kind.Type : Kind.Instance);

					if ((model == null)) this.setAsset(asset);
					else throw new Exception("Format not allowed: Submodels may never have Asset entries");
					break;

				default:
					break;
			}
		}
		catch (JsonProcessingException e) {e.printStackTrace();}
		catch (Exception e) {e.printStackTrace();}
	}

	/**
	 * add submodelElements to submodel
	 * @param model : submodel will be filled with submodelelements
	 * @param arr : JSONArray contains all submodelElements
	 */
	private void addAllSubmodelElements(Submodel model, JSONArray arr)
	{
		if(model != null)
		{
			for (int i = 0; i < arr.length(); i++) {

				SubmodelElement elem = null; // e.g. DataElement/EventElement/OperationElement
				String str = arr.getJSONObject(i).getString("modelType");

				if(str.equals("OperationVariable")) // OperationVariable
				{
					elem = new OperationVariable();
				}
				else if(str.equals("EventElement")) // EventElement
				{
					elem = new EventElement();
				}
				else if(str.equals("Reference")) // DataElement?
				{
					elem = new ReferenceElement();
				}
				else // TODO: rest not yet implemented
				{
					continue;
				}

				elem.setCategory(arr.getJSONObject(i).getString("category"));
				elem.setIdShort(arr.getJSONObject(i).getString("idShort"));

				if (arr.getJSONObject(i).getString("kind").equals("Type")) elem.setKind(Kind.Type);
				else elem.setKind(Kind.Instance);

				// add more props if needed...
				model.addSubmodelElement(elem);
			}
		}
	}
}
