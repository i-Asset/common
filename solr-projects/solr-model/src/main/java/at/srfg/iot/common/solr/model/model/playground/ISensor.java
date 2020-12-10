package at.srfg.iot.common.solr.model.model.playground;

import at.srfg.iot.common.solr.model.model.common.IConcept;
import at.srfg.iot.common.solr.model.model.common.ICustomPropertyAware;

public interface ISensor extends IConcept, ICustomPropertyAware {
	/**
	 * Name of the SOLR collection
	 */
	public String COLLECTION = "sensor";
	/**
	 * 
	 */
	public String CATEGORY_FIELD = "category";
	public String CATEGORY_ID_FIELD = "category_id";
	public String MANUFACTURER_NAME_FIELD = "manufacturer_name";
	public String MANUFACTURER_ID_FIELD = "manufacturer_id";
	public String MEASURE_ID_FIELD = "measure_id";
	public String MEASURE_FIELD = "measure";
	public String MEASURE_UNIT_FIELD = "measure_unit";
	public String TAG_FIELD = "tag";
	
	public String PLATTFORM_FIELD = "platform";

}
