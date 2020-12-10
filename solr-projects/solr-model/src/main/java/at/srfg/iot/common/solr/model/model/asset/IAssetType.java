package at.srfg.iot.common.solr.model.model.asset;

import at.srfg.iot.common.solr.model.model.common.IConcept;

/**
 * Interface specifying the field names for the manufacturer party
 * @author dglachs
 *
 */
public interface IAssetType extends IConcept {
	/**
	 * Name of the SOLR collection
	 */
	public String COLLECTION = "asset";
	/**
	 * 
	 */
	public String MANUFACTURER_ID = "manufacturer_id";
	public String MAINTAINER_ID = "maintainer_id";
	public String OPERATOR_ID = "operator_id";
	public String KIND = "kind";
	public String SUBMODEL_FIELD = "submodel";

}
