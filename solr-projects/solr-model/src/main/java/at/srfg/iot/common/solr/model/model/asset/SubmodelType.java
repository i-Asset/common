package at.srfg.iot.common.solr.model.model.asset;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import at.srfg.iot.common.solr.model.model.common.Concept;
import at.srfg.iot.common.solr.model.model.common.IConcept;
import at.srfg.iot.common.solr.model.model.common.ICustomPropertyAware;
import at.srfg.iot.common.solr.model.model.common.PropertyType;
@SolrDocument(collection = ISubmodelType.COLLECTION)
public class SubmodelType extends Concept implements IConcept, ISubmodelType, ICustomPropertyAware {
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
	
	@Indexed(name=ASSET_ID, type=SOLR_STRING)
	private String asset;
	/**
	 * Stores custom property metadata
	 */
	@ReadOnlyProperty
	private Map<String, PropertyType> customProperties;


	@Override
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

	@Override
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

	@Override
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

	@Override
	public Map<String, Boolean> getCustomBooleanValue() {
		return customBooleanValue;
	}
	public void setCustomBooleanValue(Map<String, Boolean> customBooleanValue) {
		this.customBooleanValue = customBooleanValue;
	}

	@Override
	public Map<String, String> getCustomPropertyKeys() {
		if ( customPropertyKeys == null ) {
			customPropertyKeys = new HashMap<String, String>();
		}
		return customPropertyKeys;
	}
	public void setCustomPropertyKeys(Map<String, String> customPropertyKeys) {
		this.customPropertyKeys = customPropertyKeys;
	}
	
	@Override
	public Map<String, PropertyType> getCustomProperties() {
		return customProperties;
	}
	public void setCustomProperties(Map<String, PropertyType> customProperties) {
		this.customProperties = customProperties;
	}
	public String getAsset() {
		return asset;
	}
	public void setAsset(String assetIdentifier) {
		this.asset = assetIdentifier;
	}
}
