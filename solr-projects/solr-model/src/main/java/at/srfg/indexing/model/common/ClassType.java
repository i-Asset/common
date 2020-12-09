package at.srfg.indexing.model.common;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import at.srfg.indexing.model.solr.annotation.SolrJoin;
import io.swagger.annotations.ApiModel;


/**
 * SOLR Document Class mapping a ClassType Concept.
 * <p>
 * The ClassType class covers owl:Classes and also IEC 61360 or eCl@ss Concepts.
 * It features
 * <ul> 
 * <li>multilingual labeling (preferred, alternate and hidden).
 * <li>hierarchical structuring (level, direct parent/child, all parents/children) 
 * <li>link to all assigned property uri's
 * </ul>
 * </p>
 * @author dglachs
 *
 */
@ApiModel(description = "Index Collection for managing arbitrary class types or categories")
@SolrDocument(collection=IClassType.COLLECTION)
public class ClassType extends Concept implements IClassType, IPropertyAware {
	@Indexed(defaultValue=TYPE_VALUE, name=TYPE_FIELD)
	private String type = TYPE_VALUE;
	
	@Indexed(required=false, name=PROPERTIES_FIELD)
	@SolrJoin(joinedType = PropertyType.class)
	private Collection<String> properties;

	@Indexed(required=false, name=PARENTS_FIELD)
	private Collection<String> parents;
	@Indexed(required=false, name=CHILDREN_FIELD)
	private Collection<String> children;
	
	@Indexed(required=false, name=ALL_PARENTS_FIELD)
	private Collection<String> allParents;
	@Indexed(required=false, name=ALL_CHILDREN_FIELD)
	private Collection<String> allChildren;

	@Indexed(required=false, name=LEVEL_FIELD, type="pint")
	private Integer level;
	@ReadOnlyProperty
	private Map<String, PropertyType> propertyMap;
	/**
	 * Getter for the document type - <code>class</code>
	 * @return
	 */
	public String getType() {
		return type;
	}
	/**
	 * Setter for the document type - do not use
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Getter for the collection of related propertyIds. 
	 * @return The list of URI's pointing to related propertyIds
	 */
	public Collection<String> getProperties() {
//		if (this.properties == null ) {
//			this.properties = new HashSet<>();
//		}
		return properties;
	}
	/**
	 * Setter for the propertyIds
	 * @param propertyIds
	 */
	public void setProperties(Collection<String> properties) {
		// ensure having a Set to avoid duplicate checking
		if ( properties != null && !properties.isEmpty()) {
			this.properties = properties.stream().collect(Collectors.toSet());
		}
		else {
			this.properties = new HashSet<String>();
		}
	}
	public void addParent(String superClass) {
		if (this.parents == null ) {
			this.parents = new HashSet<>();
		}
		this.parents.add(superClass);
	}
	/**
	 * Getter for the URI pointing to direct super classes
	 * @return
	 */
	public Collection<String> getParents() {
		return parents;
	}
	/**
	 * Setter for the direct parents
	 * @param parent A list of URI'S pointing to the direct parents
	 */
	public void setParents(Collection<String> parent) {
		// ensure having a set to avoid duplicates
		if ( parent != null && ! parent.isEmpty()) {
			this.parents = parent.stream().collect(Collectors.toSet());
		} 
		else {
			this.parents = new HashSet<String>();
		}
	}
	/**
	 * Helper method for adding a single child (or direct sub-class)
	 * @param childClass
	 */
	public void addChild(String childClass) {
		if (this.children == null ) {
			this.children = new HashSet<>();
		}
		this.children.add(childClass);
	}
	public void removeChild(String childClass) {
		if (this.children == null ) {
			this.children = new HashSet<>();
		}
		this.children.remove(childClass);
	}
	public void removeAllChild(String childClass) {
		if (this.allChildren == null ) {
			this.allChildren = new HashSet<>();
		}
		this.allChildren.remove(childClass);
	}
	public void removeParent(String parentClass) {
		if (this.parents == null ) {
			this.parents = new HashSet<>();
		}
		this.parents.remove(parentClass);
	}
	public void removeAllParent(String childClass) {
		if (this.allParents == null ) {
			this.allParents = new HashSet<>();
		}
		this.allParents.remove(childClass);
	}

	/**
	 * Retrieve the list of all direct sub-classes
	 * @return
	 */
	public Collection<String> getChildren() {
		return children;
	}
	/**
	 * Set the list of all direct subclasses
	 * @param child
	 */
	public void setChildren(Collection<String> child) {
		if ( child != null && ! child.isEmpty()) {
			this.children = child.stream().collect(Collectors.toSet());
		}
		else {
			this.children = new HashSet<String>();
		}
	}
	/** 
	 * Helper method for adding a single parent (all upper levels)
	 * @param superClass
	 */
	public void addAllParent(String superClass) {
		if (this.allParents == null ) {
			this.allParents = new HashSet<>();
		}
		this.allParents.add(superClass);
	}
	/**
	 * Retrieve the list of all parents, this list must include 
	 * all elements of {@link #getParents()}
	 * @return
	 */
	public Collection<String> getAllParents() {
		if (this.allParents == null ) {
			this.allParents = new HashSet<>();
		}
		return allParents;
	}
	/**
	 * Setter for the list of all parents
	 * @param parent
	 */
	public void setAllParents(Collection<String> parent) {
		if ( parent != null && ! parent.isEmpty()) {
			this.allParents = parent.stream().collect(Collectors.toSet());
		}
		else {
			this.allParents = new HashSet<String>();
		}
	}
	/**
	 * Helper class to add a single element to the {@link #allChildren} list. 
	 * 
	 * @param childClass
	 */
	public void addAllChild(String childClass) {
		if (this.allChildren == null ) {
			this.allChildren = new HashSet<>();
		}
		this.allChildren.add(childClass);
	}
	/**
	 * Retrieve a list of all sub classes, this list must include
	 * all elements of {@link #getChildren()}
	 * @param childClass
	 */	
	public Collection<String> getAllChildren() {
		return allChildren;
	}
	/**
	 * Setter for {@link #allChildren}
	 * @param child
	 */
	public void setAllChildren(Collection<String> child) {
		if ( child != null && ! child.isEmpty()) {
			this.allChildren = child.stream().collect(Collectors.toSet());
		}
		else {
			this.allChildren = new HashSet<String>();
		}
	}
	/**
	 * Retrieve the hierarchy level (if set)
	 * @return
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * Set the hierarchy level
	 * @param level
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	@Override
	public Map<String, PropertyType> getPropertyMap() {
		return propertyMap;
	}
	@Override
	public void setPropertyMap(Map<String, PropertyType> customProperties) {
		this.propertyMap = customProperties;
		
	}

}
