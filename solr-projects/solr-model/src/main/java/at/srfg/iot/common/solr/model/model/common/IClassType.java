package at.srfg.iot.common.solr.model.model.common;

/**
 * 
 * @author dglachs
 *
 */
public interface IClassType extends IConcept {
	/**
	 * Name of the index collection
	 */
	String COLLECTION = "concept_class";
	String TYPE_FIELD = "doctype";
	String TYPE_VALUE = "class";
	String PROPERTIES_FIELD = "properties";
	/**
	 * Holds the identifier of all direct
	 * parents. E.g. reflects an "is a"
	 * relationship. 
	 */
	String PARENTS_FIELD = "parents";
	/**
	 * Holds the identifier of all 
	 * parents at any upper level.
	 */
	String ALL_PARENTS_FIELD = "allParents";
	/**
	 * Holds the identifier of all direct
	 * children (only one level down)
	 */
	String CHILDREN_FIELD ="children";
	/**
	 * Holds the identifier of all children
	 * at any level below the current.
	 */
	String ALL_CHILDREN_FIELD ="allChildren";
	/**
	 * Hierarchy level, zero or one 
	 * is the root level.
	 */
	String LEVEL_FIELD = "level";
	
}
