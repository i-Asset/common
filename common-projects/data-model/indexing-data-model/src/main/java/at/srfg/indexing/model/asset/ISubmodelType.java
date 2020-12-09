package at.srfg.indexing.model.asset;

import at.srfg.indexing.model.common.IConcept;

/**
 * Interface specifying the field names for the manufacturer party
 * @author dglachs
 *
 */
public interface ISubmodelType extends IConcept {
	/**
	 * Name of the SOLR collection
	 */
	public String COLLECTION = "asset_model";
	/**
	 * Unique ID field (mandatory)
	 */
	public String ID_FIELD = "id";
	/**
	 * Reference to the asset, the submodel is assigned to
	 */
	public String ASSET_ID = "asset";
	public String KIND = "kind";
	
	public String SEMANTIC_ID = "semanticId";
	

}
