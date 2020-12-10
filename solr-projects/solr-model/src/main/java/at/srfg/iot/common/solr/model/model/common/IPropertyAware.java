package at.srfg.iot.common.solr.model.model.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.google.common.base.Strings;

/**
 * Mixin behaviour for classes wwith references to 
 * properties
 * 
 * @author dglachs
 *
 */
public interface IPropertyAware {
	
	public String getUri();
	@ReadOnlyProperty
	public Map<String, PropertyType> getPropertyMap();
	public void setPropertyMap(Map<String, PropertyType> customProperties);
	public Collection<String> getProperties();
	/**
	 * Store the of string properties - implementors
	 * must take care of duplicates
	 * @param properties
	 */
	public void setProperties(Collection<String> properties);
	/**
	 * Maintain the list of uri's pointing to {@link PropertyType}
	 * elements.
	 * 
	 * @param property The uri of the property
	 */
	default void addProperty(String property) {
		if ( getProperties() == null ) {
			HashSet<String> propSet = new HashSet<String>();
			propSet.add(property);
			setProperties(propSet);
		}
		else {
			getProperties().add(property);
		}
	}
	/**
	 * Convenience method to add a {@link PropertyType} object
	 * holding complete metadata to an {@link IPropertyAware} 
	 * object. The provided metadata must provide a {@link PropertyType#getUri()} 
	 * to be added via {@link IPropertyAware#addProperty(String)}.
	 * @param property The property metadata
	 */
	default void addProperty(PropertyType property) {
		
		if ( getPropertyMap() == null) {
			setPropertyMap(new HashMap<String, PropertyType>());
		}
		// 
		if (!Strings.isNullOrEmpty(property.getUri())) {
			// keep the full description to be stored in the index
			getPropertyMap().put(property.getUri(), property);
			// maintain the property uri (the link)
			
			addProperty(property.getUri());
			// provide the backlink to the issuing IPropertyAware
			property.addPropertyUsage(getCollection(), getUri());
		}
	}
	/**
	 * Helper method to extract the collection
	 * @return
	 */
	default String getCollection() {
		SolrDocument solrDocument = getClass().getAnnotation(SolrDocument.class);
		
		if ( solrDocument != null && solrDocument.collection() != null) {
			return solrDocument.collection();
		}
		throw new IllegalStateException("No @SolrDocument annotation found ...");
	}
	
	
}
