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
	
	public String BRAND_NAME_FIELD = "*_brandName";
	public String LOWERCASE_LEGAL_NAME_FIELD = "lowercaseLegalName";
	public String NAME_FIELD = "name";
	public String NAME_ML_FIELD = "*_name";
	public String ORIGIN_FIELD = "*_origin";
	public String CERTIFICATE_TYPE_FIELD = "*_certificateType";
	public String PPAP_COMPLIANCE_LEVEL_FIELD = "ppapComplianceLevel";
	public String PPAP_DOCUMENT_TYPE_FIELD = "*_ppapDocumentType";
	public String TRUST_SCORE_FIELD = "trustScore";
	public String TRUST_RATING_FIELD = "trustRating";
	public String TRUST_TRADING_VOLUME_FIELD = "trustTradingVolume";
	public String TRUST_SELLLER_COMMUNICATION_FIELD = "trustSellerCommunication";
	public String TRUST_FULFILLMENT_OF_TERMS_FIELD = "trustFullfillmentOfTerms";
	public String TRUST_DELIVERY_PACKAGING_FIELD = "trustDeliveryPackaging";
	public String TRUST_NUMBER_OF_TRANSACTIONS_FIELD = "trustNumberOfTransactions";
	public String TRUST_NUMBER_OF_EVALUATIONS_FIELD = "trustNumberOfEvaluations";
	public String VERIFIED_FIELD = "verified";
	public String WEBSITE_FIELD = "website";	
}
