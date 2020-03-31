package at.srfg.indexing.party;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import at.srfg.indexing.model.common.Concept;
import at.srfg.indexing.model.common.ICustomPropertyAware;
import at.srfg.indexing.model.common.PropertyType;
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
}
