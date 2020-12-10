package at.srfg.iot.common.solr.model.model.playground;

import java.util.Collection;
import java.util.HashSet;

import at.srfg.iot.common.solr.model.model.common.IConcept;
import at.srfg.iot.common.solr.model.model.common.ICustomPropertyAware;

public interface ISensorPlatform extends IConcept, ICustomPropertyAware {
	/**
	 * Name of the SOLR collection
	 */
	public String COLLECTION = "sensor_platform";
	/**
	 * Collection of the usage category
	 */
	public String CATEGORY_FIELD = "category";
	public String MANUFACTURER_NAME_FIELD = "manufacturer_name";
	public String MANUFACTURER_ID_FIELD = "manufacturer_id";
	/**
	 * Collection of components placed on this particular platform
	 */
	public String COMPONENT_FIELD = "component";
	public String COMPONENT_ID_FIELD = "component_id";
	public String FORM_FACTOR_FIELD = "form_factor";
	public String LOCATION_FIELD = "location";
	public String MEASURE_FIELD = "measures";
	public String RELEVANT_DEMOGRAPHIC_FIELD = "relevant_demograpic";
	public String POSITION_FIELD = "position";
	public String TAG_FIELD = "tag";
	
	Collection<String> getCategory();
	void setCategory(Collection<String> category);
	Collection<String> getFormFactor();
	void setFormFactor(Collection<String> factor);
	Collection<String> getMeasures();
	void setMeasures(Collection<String> measures);
	Collection<String> getDemographic();
	void setDemographic(Collection<String> measures);
	Collection<String> getTags();
	void setTags(Collection<String> measures);
	Collection<String> getPosition();
	void setPosition(Collection<String> measures);
	
	default void addPosition(String category) {
		if ( getPosition() == null) {
			setPosition(new HashSet<String>());
		}
		getPosition().add(category);
	}
	
	default void addCategory(String category) {
		if ( getCategory() == null) {
			setCategory(new HashSet<String>());
		}
		getCategory().add(category);
	}
	default void addFormFactor(String formFactor) {
		if ( getFormFactor() == null) {
			setFormFactor(new HashSet<String>());
		}
		getFormFactor().add(formFactor);
	}
	default void addMeasure(String measure) {
		if ( getMeasures() == null) {
			setMeasures(new HashSet<String>());
		}
		getMeasures().add(measure);
	}
	default void addDemographic(String measure) {
		if ( getDemographic() == null) {
			setDemographic(new HashSet<String>());
		}
		getDemographic().add(measure);
	}
	default void addTag(String ... tags) {
		if ( tags != null ) {
			for (String tag : tags) {
				if ( getTags() == null) {
					setTags(new HashSet<String>());
				}
				getTags().add(tag);
			}
		}
	}
}
