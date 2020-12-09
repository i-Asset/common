package at.srfg.indexing.model.playground;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import at.srfg.indexing.model.common.ClassType;
import at.srfg.indexing.model.common.Concept;
import at.srfg.indexing.model.common.ICustomPropertyAware;
import at.srfg.indexing.model.common.PropertyType;
import at.srfg.indexing.model.party.PartyType;
import at.srfg.indexing.model.solr.annotation.SolrJoin;
/**
 * SensorPlattform or Wearable Device 
 * @author dglachs
 *
 */
@SolrDocument(collection = ISensorPlatform.COLLECTION)
public class SensorPlatform extends Concept implements ISensorPlatform, ICustomPropertyAware, ISensorAware {

	@SolrJoin(joinedType = ClassType.class, joinName = {"measures"})
	@Indexed(name = MEASURE_FIELD, type=SOLR_STRING)
	private Collection<String> measures;
	
	@SolrJoin(joinedType = ClassType.class, joinName = {"category"})
	@Indexed(name = CATEGORY_FIELD)
	private Collection<String> category;
	
	@SolrJoin(joinedType = Sensor.class, joinName = {"component"})
	@Indexed(name = COMPONENT_FIELD)
	private Collection<String> sensors;
	
	@SolrJoin(joinedType = ClassType.class, joinName = {"formFactor"})
	@Indexed(name = FORM_FACTOR_FIELD)
	private Collection<String> formFactor;
	
	@SolrJoin(joinedType = ClassType.class, joinName = {"demographic"})
	@Indexed(name = RELEVANT_DEMOGRAPHIC_FIELD)
	private Collection<String> demographic;

	@SolrJoin(joinedType = ClassType.class, joinName = {"position"})
	@Indexed(name = POSITION_FIELD)
	private Collection<String> position;
	
	@Indexed(name = TAG_FIELD)
	private Collection<String> tags;

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
	
	@SolrJoin(joinedType = PartyType.class, joinName = {"manufacturer"})
	@Indexed(name = MANUFACTURER_ID_FIELD)
	private Collection<String> manufacturer;
	

	@Indexed(name = MANUFACTURER_NAME_FIELD)
	private Collection<String> manufacturerName;
	@ReadOnlyProperty
	private Map<String, PartyType> manufacturerMap;
	@ReadOnlyProperty
	private Map<String, Sensor> sensorMap;


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
	 * @return the sensors
	 */
	public Collection<String> getSensors() {
		return sensors;
	}
	/**
	 * @param sensors the sensors to set
	 */
	public void setSensors(Collection<String> sensors) {
		this.sensors = sensors;
	}
	/**
	 * @return the sensorMap
	 */
	public Map<String, Sensor> getSensorMap() {
		return sensorMap;
	}
	/**
	 * @param sensorMap the sensorMap to set
	 */
	public void setSensorMap(Map<String, Sensor> sensorMap) {
		this.sensorMap = sensorMap;
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
	 * @return the formFactor
	 */
	public Collection<String> getFormFactor() {
		return formFactor;
	}
	/**
	 * @param formFactor the formFactor to set
	 */
	public void setFormFactor(Collection<String> formFactor) {
		this.formFactor = formFactor;
	}
	/**
	 * @return the demographic
	 */
	public Collection<String> getDemographic() {
		return demographic;
	}
	/**
	 * @param demographic the demographic to set
	 */
	public void setDemographic(Collection<String> demographic) {
		this.demographic = demographic;
	}
	/**
	 * @return the tag
	 */
	public Collection<String> getTags() {
		return tags;
	}
	/**
	 * @param tag the tag to set
	 */
	public void setTags(Collection<String> tag) {
		this.tags = tag;
	}
	/**
	 * @return the manufacturerName
	 */
	public Collection<String> getManufacturerName() {
		return manufacturerName;
	}
	/**
	 * @param manufacturerName the manufacturerName to set
	 */
	public void setManufacturerName(Collection<String> manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	/**
	 * @return the position
	 */
	public Collection<String> getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Collection<String> position) {
		this.position = position;
	}

}
