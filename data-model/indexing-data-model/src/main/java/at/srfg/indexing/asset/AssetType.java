package at.srfg.indexing.asset;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import at.srfg.indexing.model.common.Concept;
import at.srfg.indexing.model.common.IConcept;
import at.srfg.indexing.model.common.ICustomPropertyAware;
import at.srfg.indexing.model.common.PropertyType;
import at.srfg.indexing.model.solr.annotation.SolrJoin;
import at.srfg.indexing.party.PartyType;

@SolrDocument(collection = IAssetType.COLLECTION)
public class AssetType extends Concept implements IAssetType, IConcept, ICustomPropertyAware {
	@Indexed(name = OPERATOR_ID)
	@SolrJoin(joinedType = PartyType.class, joinName = {"operator"})
	private String operatorId;
	@Indexed(name = MAINTAINER_ID)
	@SolrJoin(joinedType = PartyType.class, joinName = {"manufacturer"})
	private String manufacturerId;
	@Indexed(name=CUSTOM_INTEGER_PROPERTY, type=SOLR_INT)
	@Dynamic
	private Map<String, Collection<Integer>> customIntValues;
	@Indexed(name=CUSTOM_DOUBLE_PROPERTY, type=SOLR_NUMBER)
	@Dynamic
	private Map<String, Collection<Double>> customDoubleValues;
	@Indexed(name=CUSTOM_STRING_PROPERTY, type=SOLR_STRING, copyTo = TEXT_FIELD)
	@Dynamic
	private Map<String, Collection<String>> customStringValues;
	@Indexed(name=CUSTOM_BOOLEAN_PROPERTY, type=SOLR_BOOLEAN)
	@Dynamic
	private Map<String, Boolean> customBooleanValue;
	@Indexed(name=CUSTOM_KEY_FIELD, type=SOLR_STRING)
	/**
	 * Stores keys and labels for indexing of custom properties
	 */
	@Dynamic
	private Map<String, String> customPropertyKeys;
	/**
	 * Stores custom property metadata
	 */
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


}
