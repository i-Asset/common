package at.srfg.indexing.asset;

import at.srfg.indexing.model.common.IConcept;

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
	 * Unique ID field (mandatory)
	 */
	public String ID_FIELD = "id";
	/**
	 * 
	 */
	public String MANUFACTURER_ID = "manufacturer_id";
	public String MAINTAINER_ID = "maintainer_id";
	public String OPERATOR_ID = "operator_id";

}
