package at.srfg.iot.common.solr.model.model.common;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
/**
 * Interface specifying index field names as constants for all collections.
 * 
 * <p>
 * Each entry in any collection may have
 * <ul>
 * <li><code>id</code> - the unique identifier within the collection (mandatory field)
 * <li><code>code</code> - an identifying code, may be used as alternate identifier
 * <li><code>languages | locale</code> - the list of languages in use
 * <li><code>label</code> - the preferred label (multilingual) of the entry
 * </p>
 * @author dglachs
 *
 */
public interface IConcept {
	String SOLR_STRING = "string";
	String SOLR_NUMBER = "pdouble";
	String SOLR_INT    = "pint";
	String SOLR_BOOLEAN= "boolean";
	/**
	 * The unique id field
	 */
	String ID_FIELD = "id";
	/**
	 * Code field (alternate identifier)
	 */
	String CODE_FIELD = "code";
	/**
	 * Collection of languages
	 */
	String LANGUAGES_FIELD = "languages";
	/**
	 * Collection of locale's in use
	 */
	String LOCALE_FIELD = "locale";
	/**
	 * Multilingual label, e.g. <code><i>en</i>_label</code> for english label. There 
	 * is <b>only one</b> (preferred) label per language
	 */
	String LABEL_FIELD = "*_label";
	/**
	 * Copy Field. All labels, hidden labels and alternate labels of any language are copied to this field
	 */
	String ALL_LABELS_FIELD = "labels";
	/**
	 * Copy Field. All labels, hidden labels and alternate labels (language dependent) are copied to this field
	 */
	String LANGUAGE_ALL_LABELS_FIELD = "*_labels";
	
	/**
	 * The language based alternate labels, e.g. <code><i>en</i>_alternate</code> for english alternate label.
	 * There might be multiple alternate labels per language.
	 */
	String ALTERNATE_LABEL_FIELD = "*_alternate";
	/**
	 * The language based hidden labels, e.g. <code><i>en</i>_label</code> for english label. There might
	 * be multiple hidden labels per language.
	 */
	String HIDDEN_LABEL_FIELD = "*_hidden";
	/**
	 * Copy Field, language based. The language based label and description are stored in this field
	 * Final used index name is <code><i>en</i>_text</code> for english text (label, description, 
	 * comment, alternate, hidden)
	 */
	String LANGUAGE_TXT_FIELD = "*_text";
	/**
	 * Copy Field. All labels, descriptions are stored in this field
	 */
	String TEXT_FIELD = "_text_";
	/**
	 * The language based comment field, e.g.  <code><i>en</i>_comment</code> for english comments. there is 
	 * only one comment per language allowed
	 */
	String COMMENT_FIELD = "*_comment";
	/**
	 * The namespace of the current concept, the {@link IConcept#ID_FIELD} is (usually)
	 * constructed with {@link IConcept#NAME_SPACE_FIELD} + {@link IConcept#LOCAL_NAME_FIELD}
	 */
	String NAME_SPACE_FIELD = "nameSpace";
	/**
	 * The local name of the current concept, the {@link IConcept#ID_FIELD} is (usually)
	 * constructed with {@link IConcept#NAME_SPACE_FIELD} + {@link IConcept#LOCAL_NAME_FIELD}
	 */
	String LOCAL_NAME_FIELD = "localName"; 
	/**
	 * A language based description, e.g. <code><i>en</i>_description</code> for english comments. There is 
	 * only one description per language.
	 */
	String DESCRIPTION_FIELD = "*_description";
	
	/**
	 * Retrieve the collection of stored/used languages
	 * @return
	 */
	public Collection<String> getLanguages();
	/**
	 * Retrieve all (preferred) labels. 
	 * @return
	 */
	public Map<String, String> getLabel();
	public void setLabel(Map<String,String> label);
	/**
	 * 	 * Retrieve the preferred label of the given locale
	 * @param locale
	 * @return The label of the requested locale, <code>null</code> if not present
	 */
	public String getLabel(Locale locale);
	/**
	 * Retrieve all comments
	 * @return
	 */
	public Map<String, String> getComment();
	/**
	 * Retrieve all descriptions
	 * @return
	 */
	public Map<String, String> getDescription();
	/**
	 * Get the identifing URI or IRDI
	 * @return
	 */
	public String getUri();
	/**
	 * Get the identifying code
	 * @return
	 */
	public String getCode();
	/**
	 * The namespace the currenct concept belongs to
	 * @return
	 */
	public String getNameSpace();
	/**
	 * The local name of the concept, must be unique within the {@link #getNameSpace()}
	 * @return
	 */
	public String getLocalName();
	
}
