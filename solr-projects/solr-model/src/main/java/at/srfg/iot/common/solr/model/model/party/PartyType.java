package at.srfg.iot.common.solr.model.model.party;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import at.srfg.iot.common.solr.model.model.common.Concept;
import at.srfg.iot.common.solr.model.model.common.ICustomPropertyAware;
import at.srfg.iot.common.solr.model.model.common.PropertyType;
/**
 * Class representing a manufacturer or other party in the SOLR index
 * @author dglachs
 *
 */
@SolrDocument(collection=IParty.COLLECTION)
public class PartyType extends Concept implements IParty, ICustomPropertyAware {

//	@Indexed(name=NAME_FIELD)
//	private String name;
	@Indexed(name=LEGAL_NAME_FIELD)
	private String legalName;
	@Indexed(name= LOGO_ID_FIELD)
	private String logoId;
	@Indexed(name=BUSINESS_TYPE_FIELD)
	private String businessType;
	@Indexed(name=LEGAL_ENTITY_IDENTIFIER_FIELD)
	private String legalEntityIdentifier;

	@Indexed(name=BRAND_NAME_FIELD) @Dynamic
	private Map<String, String> brandName;
	@Indexed(name=ORIGIN_FIELD) @Dynamic
	private Map<String,String> origin;
	@Indexed(name=CERTIFICATE_TYPE_FIELD, type="string") @Dynamic
	private Map<String,Collection<String>> certificateType;
	@Indexed(name=PPAP_COMPLIANCE_LEVEL_FIELD, type="pint")
	private Integer ppapComplianceLevel;
	@Indexed(name=PPAP_DOCUMENT_TYPE_FIELD) @Dynamic
	private Map<String,String> ppapDocumentType;
	@Indexed(name=TRUST_SCORE_FIELD, type="pdouble")
	private Double trustScore;
	@Indexed(name=TRUST_RATING_FIELD, type="pdouble")
	private Double trustRating;
	@Indexed(name=TRUST_TRADING_VOLUME_FIELD, type="pdouble")
	private Double trustTradingVolume;
	@Indexed(name=TRUST_SELLLER_COMMUNICATION_FIELD, type="pdouble")
	private Double trustSellerCommunication;
	@Indexed(name=TRUST_FULFILLMENT_OF_TERMS_FIELD, type="pdouble")
	private Double trustFullfillmentOfTerms;
	@Indexed(name=TRUST_DELIVERY_PACKAGING_FIELD, type="pdouble")
	private Double trustDeliveryPackaging;
	@Indexed(name=TRUST_NUMBER_OF_TRANSACTIONS_FIELD, type="pdouble")
	private Double trustNumberOfTransactions;
	@Indexed(name=TRUST_NUMBER_OF_EVALUATIONS_FIELD, type="pdouble",required=false)
	private Double trustNumberOfEvaluations;
	@Indexed(name= ACTIVITY_SECTORS_FIELD, type="string")
	@Dynamic
	private Map<String, Collection<String>> activitySectors;
	@Indexed(name=BUSINESS_KEYWORDS_FIELD, type="string")
	@Dynamic
	protected Map<String, Collection<String>> businessKeywords;

	@Indexed(name=VERIFIED_FIELD,type="boolean")
	private Boolean isVerified = false;

	@Indexed(name=WEBSITE_FIELD, type="string")
	private String website;
//	@Indexed(name= ACTIVITY_SECTORS_FIELD, type=SOLR_STRING)
//	@Dynamic
//	private Map<String, Collection<String>> activitySectors;
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
	@Dynamic
	private Map<String, String> customPropertyKeys;
	/**
	 * 
	 */
	private Map<String, PropertyType> customProperties;

	public String getId() {
		return getUri();
	}
	public void setId(String id) {
		setUri(id);
	}

	/**
	 * Helper method for adding a multilingual origin to the concept. Only one origin per language is stored.
	 * This method maintains the list of languages in use, see {@link #getLanguages()}
	 * @param language The language code such as <i>en</i>, <i>es</i>
	 * @param label The respective label for the origin
	 */
	public void addOrigin(String language, String label) {
		addProperty(label, language, "origin" );
		// 
		addLanguage(language);
	}
	/**
	 * Helper method for adding a multilingual origin to the concept. Only one origin per language is stored.
	 * This method maintains the list of languages in use, see {@link #getLanguages()}
	 * @param language The language code such as <i>en</i>, <i>es</i>
	 * @param label The respective label for the brandName
	 */
	public void addBrandName(String language, String label) {
		addProperty(label, language, "brand" );
		addLanguage(language);
	}

	

	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}


	public String getLogoId() {
		return logoId;
	}

	public void setLogoId(String logoId) {
		this.logoId = logoId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Override
	public Map<String, Collection<Integer>> getCustomIntValues() {
		if ( customIntValues == null ) {
			customIntValues = new HashMap<String, Collection<Integer>>();
		}
		return customIntValues;
	}
	@Override
	public Map<String, Collection<Double>> getCustomDoubleValues() {
		if (customDoubleValues == null) {
			customDoubleValues = new HashMap<String, Collection<Double>>();
		}
		return customDoubleValues;
	}
	@Override
	public Map<String, Collection<String>> getCustomStringValues() {
		if ( customStringValues == null) {
			customStringValues = new HashMap<String, Collection<String>>();
		}
		return customStringValues;
	}
	@Override
	public Map<String, Boolean> getCustomBooleanValue() {
		if ( customBooleanValue == null) {
			customBooleanValue = new HashMap<String, Boolean>();
		}
		return customBooleanValue;
	}
	@Override
	public Map<String, String> getCustomPropertyKeys() {
		if ( customPropertyKeys == null) {
			customPropertyKeys = new HashMap<String, String>();
		}
		return customPropertyKeys;
	}
	@Override
	@ReadOnlyProperty
	public Map<String, PropertyType> getCustomProperties() {
		if ( customProperties == null) {
			customProperties = new HashMap<String, PropertyType>();
		}
		return customProperties;
	}
	/**
	 * @return the legalEntityIdentifier
	 */
	public String getLegalEntityIdentifier() {
		return legalEntityIdentifier;
	}
	/**
	 * @param legalEntityIdentifier the legalEntityIdentifier to set
	 */
	public void setLegalEntityIdentifier(String legalEntityIdentifier) {
		this.legalEntityIdentifier = legalEntityIdentifier;
	}
	/**
	 * @return the brandName
	 */
	public Map<String, String> getBrandName() {
		return brandName;
	}
	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(Map<String, String> brandName) {
		this.brandName = brandName;
	}
	/**
	 * @return the origin
	 */
	public Map<String, String> getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(Map<String, String> origin) {
		this.origin = origin;
	}
	
	public Map<String,Collection<String>> getCertificateType() {
		return certificateType;
	}

	/**
	 * Setter for the certificateType labels
	 * @param certificateTypeMap
	 */
	public void setCertificateType(Map<String, Collection<String>> certificateTypeMap) {
		if ( certificateTypeMap != null ) {
			for ( String lang : certificateTypeMap.keySet() ) {
				for (String label : certificateTypeMap.get(lang)) {
					addCertificateType(lang, label);
					
				}
			}
		}
		else {
			this.certificateType = null;
		}
	}
	/**
	 * Helper method for adding a (multilingual) label to the list of certificat types 
	 * @param language
	 * @param certificatTypeLabel
	 */
	public void addCertificateType(String language, String certificatTypeLabel) {
		if (this.certificateType ==null) {
			this.certificateType = new HashMap<>();
		}
		if ( !this.certificateType.containsKey(language)) {
			this.certificateType.put(language, new HashSet<>());
		}
		this.certificateType.get(language).add(certificatTypeLabel);
		// 
		addLanguage(language);
	}	/**
	 * @return the ppapComplianceLevel
	 */
	public Integer getPpapComplianceLevel() {
		return ppapComplianceLevel;
	}
	/**
	 * @param ppapComplianceLevel the ppapComplianceLevel to set
	 */
	public void setPpapComplianceLevel(Integer ppapComplianceLevel) {
		this.ppapComplianceLevel = ppapComplianceLevel;
	}
	/**
	 * @return the ppapDocumentType
	 */
	public Map<String, String> getPpapDocumentType() {
		return ppapDocumentType;
	}
	/**
	 * @param ppapDocumentType the ppapDocumentType to set
	 */
	public void setPpapDocumentType(Map<String, String> ppapDocumentType) {
		this.ppapDocumentType = ppapDocumentType;
	}
	/**
	 * @return the trustScore
	 */
	public Double getTrustScore() {
		return trustScore;
	}
	/**
	 * @param trustScore the trustScore to set
	 */
	public void setTrustScore(Double trustScore) {
		this.trustScore = trustScore;
	}
	/**
	 * @return the trustRating
	 */
	public Double getTrustRating() {
		return trustRating;
	}
	/**
	 * @param trustRating the trustRating to set
	 */
	public void setTrustRating(Double trustRating) {
		this.trustRating = trustRating;
	}
	/**
	 * @return the trustTradingVolume
	 */
	public Double getTrustTradingVolume() {
		return trustTradingVolume;
	}
	/**
	 * @param trustTradingVolume the trustTradingVolume to set
	 */
	public void setTrustTradingVolume(Double trustTradingVolume) {
		this.trustTradingVolume = trustTradingVolume;
	}
	/**
	 * @return the trustSellerCommunication
	 */
	public Double getTrustSellerCommunication() {
		return trustSellerCommunication;
	}
	/**
	 * @param trustSellerCommunication the trustSellerCommunication to set
	 */
	public void setTrustSellerCommunication(Double trustSellerCommunication) {
		this.trustSellerCommunication = trustSellerCommunication;
	}
	/**
	 * @return the trustFullfillmentOfTerms
	 */
	public Double getTrustFullfillmentOfTerms() {
		return trustFullfillmentOfTerms;
	}
	/**
	 * @param trustFullfillmentOfTerms the trustFullfillmentOfTerms to set
	 */
	public void setTrustFullfillmentOfTerms(Double trustFullfillmentOfTerms) {
		this.trustFullfillmentOfTerms = trustFullfillmentOfTerms;
	}
	/**
	 * @return the trustDeliveryPackaging
	 */
	public Double getTrustDeliveryPackaging() {
		return trustDeliveryPackaging;
	}
	/**
	 * @param trustDeliveryPackaging the trustDeliveryPackaging to set
	 */
	public void setTrustDeliveryPackaging(Double trustDeliveryPackaging) {
		this.trustDeliveryPackaging = trustDeliveryPackaging;
	}
	/**
	 * @return the trustNumberOfTransactions
	 */
	public Double getTrustNumberOfTransactions() {
		return trustNumberOfTransactions;
	}
	/**
	 * @param trustNumberOfTransactions the trustNumberOfTransactions to set
	 */
	public void setTrustNumberOfTransactions(Double trustNumberOfTransactions) {
		this.trustNumberOfTransactions = trustNumberOfTransactions;
	}
	/**
	 * @return the trustNumberOfEvaluations
	 */
	public Double getTrustNumberOfEvaluations() {
		return trustNumberOfEvaluations;
	}
	/**
	 * @param trustNumberOfEvaluations the trustNumberOfEvaluations to set
	 */
	public void setTrustNumberOfEvaluations(Double trustNumberOfEvaluations) {
		this.trustNumberOfEvaluations = trustNumberOfEvaluations;
	}
	/**
	 * @return the activitySectors
	 */
	public Map<String, Collection<String>> getActivitySectors() {
		return activitySectors;
	}
	/**
	 * @param activitySectors the activitySectors to set
	 */
	public void setActivitySectors(Map<String, Collection<String>> activitySectors) {
		this.activitySectors = activitySectors;
	}
	/**
	 * @return the businessKeywords
	 */
	public Map<String, Collection<String>> getBusinessKeywords() {
		return businessKeywords;
	}
	/**
	 * @param businessKeywords the businessKeywords to set
	 */
	public void setBusinessKeywords(Map<String, Collection<String>> businessKeywords) {
		this.businessKeywords = businessKeywords;
	}
	/**
	 * @return the isVerified
	 */
	public Boolean getIsVerified() {
		return isVerified;
	}
	/**
	 * @param isVerified the isVerified to set
	 */
	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}
	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}
	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}
	/**
	 * @param customIntValues the customIntValues to set
	 */
	public void setCustomIntValues(Map<String, Collection<Integer>> customIntValues) {
		this.customIntValues = customIntValues;
	}
	/**
	 * @param customDoubleValues the customDoubleValues to set
	 */
	public void setCustomDoubleValues(Map<String, Collection<Double>> customDoubleValues) {
		this.customDoubleValues = customDoubleValues;
	}
	/**
	 * @param customStringValues the customStringValues to set
	 */
	public void setCustomStringValues(Map<String, Collection<String>> customStringValues) {
		this.customStringValues = customStringValues;
	}
	/**
	 * @param customBooleanValue the customBooleanValue to set
	 */
	public void setCustomBooleanValue(Map<String, Boolean> customBooleanValue) {
		this.customBooleanValue = customBooleanValue;
	}
	/**
	 * @param customPropertyKeys the customPropertyKeys to set
	 */
	public void setCustomPropertyKeys(Map<String, String> customPropertyKeys) {
		this.customPropertyKeys = customPropertyKeys;
	}
	/**
	 * @param customProperties the customProperties to set
	 */
	public void setCustomProperties(Map<String, PropertyType> customProperties) {
		this.customProperties = customProperties;
	}
}
