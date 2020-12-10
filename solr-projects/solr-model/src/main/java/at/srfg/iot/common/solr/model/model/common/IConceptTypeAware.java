package at.srfg.iot.common.solr.model.model.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.data.annotation.ReadOnlyProperty;

import com.google.common.base.Strings;

/**
 * Mixin behaviour for classes with references to 
 * the generic type
 * 
 * @author dglachs
 *
 */
@Deprecated
public interface IConceptTypeAware<T extends IConcept> {
	/**
	 * access the uri
	 * @return
	 */
	public String getUri();
	@ReadOnlyProperty
	public Map<String, T> getAssignedConceptMap();
	public void setAssignedConceptMap(Map<String, T> customConceptMap);
	/**
	 * Retrieve the list of assigned concept uri's
	 * @return
	 */
	public Collection<String> getAssignedConceptUris();
	/**
	 * Store the of string properties - implementors
	 * must take care of duplicates
	 * @param properties
	 */
	public void setAssignedConceptUris(Collection<String> properties);
	/**
	 * Maintain the list of uri's pointing to {@link PropertyType}
	 * elements.
	 * 
	 * @param property The uri of the property
	 */
	default void addAssignedConceptUri(String property) {
		if ( getAssignedConceptUris() == null ) {
			HashSet<String> propSet = new HashSet<String>();
			propSet.add(property);
			setAssignedConceptUris(propSet);
		}
		else {
			getAssignedConceptUris().add(property);
		}
	}
	/**
	 * Convenience method to add the (generic) type object
	 * holding complete metadata to an {@link IConceptTypeAware} 
	 * object. The provided metadata must provide a {@link #getUri()} 
	 * to be added via {@link IConceptTypeAware#addAssignedConceptUri(String)}.
	 * @param conceptType The conceptType metadata
	 */
	default void addAssignedConcept(T conceptType) {
		
		if ( getAssignedConceptMap() == null) {
			setAssignedConceptMap(new HashMap<String, T>());
		}
		// 
		if (!Strings.isNullOrEmpty(conceptType.getUri())) {
			// keep the full description to be stored in the index
			getAssignedConceptMap().put(conceptType.getUri(), conceptType);
			// maintain the property uri (the link)
			addAssignedConceptUri(conceptType.getUri());
		}
	}

}
