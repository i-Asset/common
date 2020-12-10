package at.srfg.iot.common.solr.model.model.party;

import at.srfg.iot.common.solr.model.model.common.IConcept;

/**
 * Interface specifying the field names for the manufacturer party
 * @author dglachs
 *
 */
public interface IParty extends IConcept {
	/**
	 * Name of the SOLR collection
	 */
	public String COLLECTION = "party";
	/**
	 * Unique ID field (mandatory)
	 */
	public String ID_FIELD = "id";
	public String LEGAL_ENTITY_IDENTIFIER_FIELD = "legalEntityIdentifier";
	public String LEGAL_NAME_FIELD = "legalName";
	public String LOGO_ID_FIELD = "logoId";
	public String BUSINESS_TYPE_FIELD = "businessType";
	public String ACTIVITY_SECTORS_FIELD = "*_activitySectors";
	public String BUSINESS_KEYWORDS_FIELD = "*_businessKeywords";
}
