package at.srfg.indexing.model.asset;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.data.annotation.ReadOnlyProperty;

import com.google.common.base.Strings;

/**
 * Mixin behaviour for classes wwith references to 
 * properties
 * 
 * @author dglachs
 *
 */
public interface ISubmodelAware {
	
	public String getUri();
	@ReadOnlyProperty
	public Map<String, SubmodelType> getSubmodelMap();
	public void setSubmodelMap(Map<String, SubmodelType> customProperties);
	public Collection<String> getSubmodels();
	/**
	 * Store the of string properties - implementors
	 * must take care of duplicates
	 * @param properties
	 */
	public void setSubmodels(Collection<String> properties);
	/**
	 * Maintain the list of uri's pointing to {@link SubmodelType}
	 * elements.
	 * 
	 * @param property The uri of the property
	 */
	default void addSubmodel(String property) {
		if ( getSubmodels() == null ) {
			HashSet<String> propSet = new HashSet<String>();
			propSet.add(property);
			setSubmodels(propSet);
		}
		else {
			getSubmodels().add(property);
		}
	}
	/**
	 * Convenience method to add a {@link SubmodelType} object
	 * holding complete metadata to an {@link ISubmodelAware} 
	 * object. The provided metadata must provide a {@link SubmodelType#getUri()} 
	 * to be added via {@link ISubmodelAware#addSubmodel(String)}.
	 * @param property The property metadata
	 */
	default void addSubmodel(SubmodelType property) {
		
		if ( getSubmodelMap() == null) {
			setSubmodelMap(new HashMap<String, SubmodelType>());
		}
		// 
		if (!Strings.isNullOrEmpty(property.getUri())) {
			// keep the full description to be stored in the index
			getSubmodelMap().put(property.getUri(), property);
			// maintain the property uri (the link)
			addSubmodel(property.getUri());
		}
	}

}
