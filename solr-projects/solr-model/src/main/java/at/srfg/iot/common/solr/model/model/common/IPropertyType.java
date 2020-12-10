package at.srfg.iot.common.solr.model.model.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.solr.core.query.Field;
import org.springframework.data.solr.core.query.SimpleField;

public interface IPropertyType extends IConcept {
	/**
	 * Name of the properties collection
	 */
	String COLLECTION = "property";
	/**
	 * Field pointing to the contained SOLR documents
	 */
	String TYPE_FIELD = "doctype";
	/**
	 * Value Constant for the contained SOLR documents
	 */
	String TYPE_VALUE = "property";
	/**
	 * 
	 */
	String IS_FACET_FIELD = "isFacet";
	String IS_VISIBLE_FIELD = "isVisible";
	String BOOST_FIELD = "boost";
	String RANGE_FIELD = "range";
	String VALUE_QUALIFIER_FIELD = "valueQualifier";
	String PROPERTY_USAGE_FIELD = "*_usage";
	String CLASSIFICATION_CLASS_FIELD = "classificationClass";
	String CLASSIFICATION_INSTANCE_FIELD = "forAsset";
	/**
	 * collection of (custom) index field names in use for this particular property 
	 */
	String IDX_FIELD_NAME_FIELD = "indexFieldName";
	/**
	 * Type of this property
	 */
	String PROPERTY_TYPE_FIELD = "propertyType";
	String CODE_LIST_FIELD = "codeList";
	String CODE_LIST_ID_FIELD = "codeListId";


	/**
	 * Define the default field list 
	 * 
	 * @return
	 */
	public static String[] defaultFieldNames() {
		return new String[] {
				TYPE_FIELD, 
				IS_FACET_FIELD, 
				IS_VISIBLE_FIELD,
				BOOST_FIELD, 
				IDX_FIELD_NAME_FIELD,
				PROPERTY_TYPE_FIELD, 
				LABEL_FIELD, 
				ALTERNATE_LABEL_FIELD, 
				HIDDEN_LABEL_FIELD, 
				LANGUAGES_FIELD, 
				LANGUAGE_TXT_FIELD,
				LOCAL_NAME_FIELD, 
				NAME_SPACE_FIELD, 
				ID_FIELD, 
				COMMENT_FIELD, 
				DESCRIPTION_FIELD,
				RANGE_FIELD,
				VALUE_QUALIFIER_FIELD,
				CODE_LIST_FIELD,
				CODE_LIST_ID_FIELD
				
		};
		
	}
	/**
	 * The list of default {@link Field}s. 
	 * The 
	 * @return
	 */
	public static List<Field> defaultFields() {
		List<Field> f = new ArrayList<>();
		for ( String s : defaultFieldNames()) {
			f.add(new SimpleField(s));
		}
		return f;
	}
}
