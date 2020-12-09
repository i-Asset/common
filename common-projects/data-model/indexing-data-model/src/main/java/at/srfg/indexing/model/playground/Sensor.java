package at.srfg.indexing.model.playground;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import at.srfg.indexing.model.common.ClassType;
import at.srfg.indexing.model.common.Concept;
import at.srfg.indexing.model.common.ICustomPropertyAware;
import at.srfg.indexing.model.common.PropertyType;
import at.srfg.indexing.model.party.IPartyTypeAware;
import at.srfg.indexing.model.party.PartyType;
import at.srfg.indexing.model.solr.annotation.SolrJoin;
/**
 * Java Class representing a distinct sensor places on a sensor plattform or wearable device
 * @author dglachs
 *
 */
@SolrDocument(collection = ISensor.COLLECTION)
public class Sensor extends Concept implements ISensor, IPartyTypeAware, ICustomPropertyAware {
	@SolrJoin(joinedType = PartyType.class, joinName = {"manufacturer"})
	@Indexed(name = MANUFACTURER_ID_FIELD)
	private Collection<String> manufacturer;

	@SolrJoin(joinedType = SensorPlatform.class, joinName = {"platform"})
	@Indexed(name = PLATTFORM_FIELD, type=SOLR_STRING)
	private Collection<String> sensorAware;

	@Indexed(name = MANUFACTURER_NAME_FIELD)
	private Collection<String> manufacturerName;

	@SolrJoin(joinedType = ClassType.class, joinName = {"measures"})
	@Indexed(name = MEASURE_ID_FIELD, type=SOLR_STRING)
	private Collection<String> measureId;
	@Indexed(name = MEASURE_FIELD, type=SOLR_STRING)
	private Collection<String> measures;
	
	@SolrJoin(joinedType = ClassType.class, joinName = {"category"})
	@Indexed(name = CATEGORY_FIELD)
	private Collection<String> category;
	
	@Indexed(name = TAG_FIELD)
	private Collection<String> tag;

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
	/**
	 * Stores custom property metadata
	 */
	@ReadOnlyProperty
	private Map<String, PropertyType> customProperties;
	@ReadOnlyProperty
	private Map<String, PartyType> manufacturerMap;

	/**
	 * @return the manufacturerMap
	 */
	public Map<String, PartyType> getManufacturerMap() {
		return manufacturerMap;
	}
	/**
	 * @param manufacturerMap the manufacturerMap to set
	 */
	public void setManufacturerMap(Map<String, PartyType> manufacturerMap) {
		this.manufacturerMap = manufacturerMap;
	}
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
	 * @return the manufacturer
	 */
	public Collection<String> getManufacturer() {
		return manufacturer;
	}
	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(Collection<String> manufacturer) {
		this.manufacturer = manufacturer;
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
	 * @return the measureId
	 */
	public Collection<String> getMeasureId() {
		return measureId;
	}
	/**
	 * @param measureId the measureId to set
	 */
	public void setMeasureId(Collection<String> measureId) {
		this.measureId = measureId;
	}
	/**
	 * @return the measures
	 */
	public Collection<String> getMeasures() {
		return measures;
	}
	/**
	 * @param measures the measures to set
	 */
	public void setMeasures(Collection<String> measures) {
		this.measures = measures;
	}
	
	/**
	 * @return the category
	 */
	public Collection<String> getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(Collection<String> category) {
		this.category = category;
	}
	/**
	 * Convenience method for adding a category to the list
	 * @param tag
	 */
	public void addCategory(String category) {
		if ( getCategory() == null) {
			setCategory(new HashSet<String>());
		}
		getCategory().add(category);
	}
	/**
	 * @return the tag
	 */
	public Collection<String> getTag() {
		return tag;
	}
	/**
	 * @param tag the tag to set
	 */
	public void setTag(Collection<String> tag) {
		this.tag = tag;
	}
	/**
	 * Convenience method for adding a tag to the list
	 * @param tag
	 */
	public void addTag(String tag) {
		if ( getTag() == null) {
			setTag(new HashSet<String>());
		}
		getTag().add(tag);
	}
	/**
	 * @return the platform
	 */
	public Collection<String> getSensorAware() {
		return sensorAware;
	}
	/**
	 * @param platform the platform to set
	 */
	public void setSensorAware(Collection<String> platform) {
		this.sensorAware = platform;
	}
}
