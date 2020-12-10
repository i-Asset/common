package at.srfg.iot.common.solr.model.model.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.common.solr.model.model.solr.annotation.SolrJoin;
import io.swagger.annotations.ApiModelProperty;
/**
 * SOLR Document holding the properties out of 
 * any ontologies and or IEC compliant taxonomy including label, range and 
 * the usage in distinct classes
 * 
 * @author dglachs
 *
 */
@SolrDocument(collection=IPropertyType.COLLECTION)
public class PropertyType extends Concept implements IPropertyType {
	/**
	 * The uri of the property including namespace
	 */
	@Indexed(defaultValue=TYPE_VALUE, name=TYPE_FIELD)
	private String type = TYPE_VALUE;
	@ApiModelProperty("The range outlining the type of the property. Use in combination with valueQualifier")
	@Indexed(required=false, name=RANGE_FIELD) 
	private String range;
	
	@Indexed(required=false, type="string", name=VALUE_QUALIFIER_FIELD)
	private ValueQualifier valueQualifier;
	
	@Indexed(required=false, name=CLASSIFICATION_CLASS_FIELD)
	@SolrJoin(joinedType = ClassType.class)
	private Collection<String> conceptClass = new HashSet<String>();
	
	@Indexed(required=false, type=SOLR_STRING, name=PROPERTY_USAGE_FIELD)
	@Dynamic
	private Map<String, Collection<String>> propertyUsage;

	@Indexed(required=false, name=IDX_FIELD_NAME_FIELD)
	private Collection<String> itemFieldNames = new HashSet<String>();
	
	@Indexed(required=false, name=IS_FACET_FIELD)
	private boolean facet = true;

	@Indexed(required=false, name=IS_VISIBLE_FIELD)
	private boolean visible = true;
	
	@Indexed(required=false, name=BOOST_FIELD, type="pdouble")
	private Double boost;
	
	@Indexed(required=false, name=PROPERTY_TYPE_FIELD)
	private String propertyType;

	@Indexed(required=false, name= CODE_LIST_FIELD)
	private Collection<String> codeList;

	@Indexed(required=false, name=CODE_LIST_ID_FIELD)
	private String codeListId;

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	@JsonIgnore
	public Collection<String> getUnits() {
		return codeList;
	}

	public void setUnits(Collection<String> unitsTypeList) {
		this.codeList = unitsTypeList;
	}

    public Collection<String> getCodeList() {
    	if ( codeList == null) {
    		codeList = new HashSet<String>();
    	}
		return codeList;
	}
	public void setCodeList(Collection<String> valueCodesList) {
		this.codeList = valueCodesList;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public Collection<String> getConceptClass() {
		if (conceptClass == null ) {
			conceptClass = new HashSet<String>(); 
		}
		return conceptClass;
	}
	public void addConceptClass(String conceptClassUri) {
		if ( this.conceptClass == null ) {
			this.conceptClass = new HashSet<>();
		}
		this.conceptClass.add(conceptClassUri);
	}
	public void setConceptClass(Collection<String> conceptClassUris) {
		this.conceptClass = conceptClassUris;
	}
	public void removeConceptClass(String conceptClassUri) {
		if ( this.conceptClass == null ) {
			this.conceptClass = new HashSet<>();
		}
		this.conceptClass.remove(conceptClassUri);
	}
//
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Collection<String> getItemFieldNames() {
		return itemFieldNames;
	}

	public void setItemFieldNames(Collection<String> idxFieldNames) {
		this.itemFieldNames = idxFieldNames;
	}
	public void addItemFieldName(String idxField) {
		if (Strings.isNotBlank(idxField)) {
			if (itemFieldNames==null) {
				itemFieldNames=new HashSet<>();
			}
			else if (! (itemFieldNames instanceof Set)) {
				// ensure to have a new set (to avoid duplicates)
				itemFieldNames = itemFieldNames.stream().collect(Collectors.toSet());
			}
			this.itemFieldNames.add(idxField);
		}
	}
	/**
	 * Specifies the basic type of the property
	 * @return
	 */
	@ApiModelProperty(value="Basic type of the property", example = "STRING or QUANTITY")
	public ValueQualifier getValueQualifier() {
		return valueQualifier;
	}

	public void setValueQualifier(ValueQualifier valueQualifier) {
		this.valueQualifier = valueQualifier;
	}
	@JsonIgnore
	public boolean isFacet() {
		return facet;
	}

	public void setFacet(boolean facet) {
		this.facet = facet;
	}
	@JsonIgnore
	public Double getBoost() {
		return boost;
	}

	public void setBoost(Double boost) {
		this.boost = boost;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getCodeListId() {
		return codeListId;
	}

	public void setCodeListId(String codeListUri) {
		this.codeListId = codeListUri;
	}
	public Map<String, Collection<String>> getPropertyUsage() {
		return propertyUsage;
	}
	public void setPropertyUsage(Map<String, Collection<String>> propertyUsage) {
		this.propertyUsage = propertyUsage;
	}
	public Collection<String> getPropertyUsage(String collection) {
		if ( propertyUsage == null) {
			propertyUsage = new HashMap<>();
		}
		if ( ! propertyUsage.containsKey(collection)) {
			propertyUsage.put(collection, new HashSet<String>());
		}
		// 
		return propertyUsage.get(collection);
	}
	public boolean removePropertyUsage(String collection, String uri) {
		if ( propertyUsage == null) {
			propertyUsage = new HashMap<>();
		}
		if ( ! propertyUsage.containsKey(collection)) {
			propertyUsage.put(collection, new HashSet<String>());
		}
		// 
		return propertyUsage.get(collection).remove(uri);
	}
	public boolean addPropertyUsage(String collection, String uri) {
		if ( propertyUsage == null) {
			propertyUsage = new HashMap<>();
		}
		if ( ! propertyUsage.containsKey(collection)) {
			propertyUsage.put(collection, new HashSet<String>());
		}
		// 
		return propertyUsage.get(collection).add(uri);
	}
}
