package at.srfg.iot.common.solr.model.model.asset;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import at.srfg.iot.common.solr.model.model.common.Concept;
import at.srfg.iot.common.solr.model.model.common.IConcept;
import at.srfg.iot.common.solr.model.model.common.ICustomPropertyAware;
import at.srfg.iot.common.solr.model.model.common.PropertyType;
import at.srfg.iot.common.solr.model.model.party.PartyType;
import at.srfg.iot.common.solr.model.model.solr.annotation.SolrJoin;

@SolrDocument(collection = IAssetType.COLLECTION)
public class AssetType extends Concept implements IAssetType, IConcept, ICustomPropertyAware, ISubmodelAware {
	@SolrJoin(joinedType = PartyType.class, joinName = {"operator"})
	@Indexed(name = OPERATOR_ID)
	private String operatorId;
	
	@SolrJoin(joinedType = PartyType.class, joinName = {"manufacturer"})
	@Indexed(name = MAINTAINER_ID)
	private String manufacturerId;
	
	@SolrJoin(joinedType = SubmodelType.class, joinName = {"submodel"})
	@Indexed(name=SUBMODEL_FIELD, type=SOLR_STRING)
	private Collection<String> subModels;
	
	@Dynamic
	@Indexed(name=CUSTOM_INTEGER_PROPERTY, type=SOLR_INT)
	private Map<String, Collection<Integer>> customIntValues;
	@Dynamic
	@Indexed(name=CUSTOM_DOUBLE_PROPERTY, type=SOLR_NUMBER)
	private Map<String, Collection<Double>> customDoubleValues;
	@Dynamic
	@Indexed(name=CUSTOM_STRING_PROPERTY, type=SOLR_STRING, copyTo = TEXT_FIELD)
	private Map<String, Collection<String>> customStringValues;
	@Dynamic
	@Indexed(name=CUSTOM_BOOLEAN_PROPERTY, type=SOLR_BOOLEAN)
	private Map<String, Boolean> customBooleanValue;
	/**
	 * Stores keys and labels for indexing of custom properties
	 */
	@Dynamic
	@Indexed(name=CUSTOM_KEY_FIELD, type=SOLR_STRING)
	private Map<String, String> customPropertyKeys;
	@ReadOnlyProperty
	private Map<String, SubmodelType> submodelMap;
	
	/**
	 * Stores custom property metadata
	 */
	@ReadOnlyProperty
	private Map<String, PropertyType> customProperties;
	/**
	 * @return the customIntValues
	 */
	public Map<String, Collection<Integer>> getCustomIntValues() {
		if ( customIntValues == null) {
			customIntValues = new HashMap<String, Collection<Integer>>();
		}
		return customIntValues;
	}
	/**
	 * @param customIntValues the customIntValues to set
	 */
	public void setCustomIntValues(Map<String, Collection<Integer>> customIntValues) {
		this.customIntValues = customIntValues;
	}
	/**
	 * @return the customDoubleValues
	 */
	public Map<String, Collection<Double>> getCustomDoubleValues() {
		if ( customDoubleValues == null) {
			customDoubleValues = new HashMap<String, Collection<Double>>();
		}
		return customDoubleValues;
	}
	/**
	 * @param customDoubleValues the customDoubleValues to set
	 */
	public void setCustomDoubleValues(Map<String, Collection<Double>> customDoubleValues) {
		this.customDoubleValues = customDoubleValues;
	}
	/**
	 * @return the customStringValues
	 */
	public Map<String, Collection<String>> getCustomStringValues() {
		if ( customStringValues == null) {
			customStringValues = new HashMap<String, Collection<String>>();
		}
		return customStringValues;
	}
	/**
	 * @param customStringValues the customStringValues to set
	 */
	public void setCustomStringValues(Map<String, Collection<String>> customStringValues) {
		this.customStringValues = customStringValues;
	}
	/**
	 * @return the customBooleanValue
	 */
	public Map<String, Boolean> getCustomBooleanValue() {
		if ( customBooleanValue == null) {
			customBooleanValue = new HashMap<String, Boolean>();
		}
		return customBooleanValue;
	}
	/**
	 * @param customBooleanValue the customBooleanValue to set
	 */
	public void setCustomBooleanValue(Map<String, Boolean> customBooleanValue) {
		this.customBooleanValue = customBooleanValue;
	}
	/**
	 * @return the customPropertyKeys
	 */
	public Map<String, String> getCustomPropertyKeys() {
		if ( customPropertyKeys == null) {
			customPropertyKeys = new HashMap<String, String>();
		}
		return customPropertyKeys;
	}
	/**
	 * @param customPropertyKeys the customPropertyKeys to set
	 */
	public void setCustomPropertyKeys(Map<String, String> customPropertyKeys) {
		this.customPropertyKeys = customPropertyKeys;
	}
	/**
	 * @return the customProperties
	 */
	public Map<String, PropertyType> getCustomProperties() {
		if ( customProperties == null) {
			customProperties = new HashMap<String, PropertyType>();
		}
		return customProperties;
	}
	/**
	 * @param customProperties the customProperties to set
	 */
	public void setCustomProperties(Map<String, PropertyType> customProperties) {
		this.customProperties = customProperties;
	}
	/**
	 * @return the manufacturerId
	 */
	public String getManufacturerId() {
		return manufacturerId;
	}
	/**
	 * @param manufacturerId the manufacturerId to set
	 */
	public void setManufacturerId(String manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
	@Override
	public Map<String, SubmodelType> getSubmodelMap() {
		return submodelMap;
	}
	@Override
	public void setSubmodelMap(Map<String, SubmodelType> customProperties) {
		this.submodelMap = customProperties;
		
	}
	@Override
	public Collection<String> getSubmodels() {
		return subModels;
	}
	@Override
	public void setSubmodels(Collection<String> submodel) {
		if ( submodel != null && !submodel.isEmpty()) {
			this.subModels = submodel.stream().collect(Collectors.toSet());
		}
		else {
			this.subModels = new HashSet<String>();
		}
		
	}


}
