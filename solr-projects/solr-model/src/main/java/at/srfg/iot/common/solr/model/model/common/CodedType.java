package at.srfg.iot.common.solr.model.model.common;

import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
/**
 * SOLR Collection for indexing valid code lists for
 * property values.
 * 
 * Each entry in the collection is assigned to a 
 * code list {@link ICodedType#LIST_ID_FIELD} and 
 * specifies the {@link IConcept#CODE_FIELD}.  
 * 
 * @author dglachs
 *
 */
@SolrDocument(collection=ICodedType.COLLECTION)
public class CodedType extends Concept implements ICodedType {
	/**
	 * the type is the kind of the index entry, e.g. {@link ICodedType#TYPE_VALUE}
	 */
	@Indexed(defaultValue=TYPE_VALUE, name=TYPE_FIELD)
	private String type = TYPE_VALUE;
	
	@Indexed(name=LIST_ID_FIELD)
	private String listId;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getListId() {
		return listId;
	}

	public void setListId(String codedList) {
		this.listId = codedList;
	}
	

}
