package at.srfg.indexing.model.party;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.data.annotation.ReadOnlyProperty;

import com.google.common.base.Strings;

/**
 * Mixin behaviour for classes with references to manufacturer (PartyType) 
 * 
 * @author dglachs
 *
 */
public interface IPartyTypeAware {
	
	public String getUri();
	@ReadOnlyProperty
	public Map<String, PartyType> getManufacturerMap();
	public void setManufacturerMap(Map<String, PartyType> customProperties);
	public Collection<String> getManufacturer();
	/**
	 * Store the of string properties - implementors
	 * must take care of duplicates
	 * @param manufacturer
	 */
	public void setManufacturer(Collection<String> manufacturer);
	/**
	 * Maintain the list of uri's pointing to {@link PartyType}
	 * elements.
	 * 
	 * @param manufacturer The uri of the manufacturer
	 */
	default void addManufacturer(String manufacturer) {
		if ( getManufacturer() == null ) {
			HashSet<String> propSet = new HashSet<String>();
			propSet.add(manufacturer);
			setManufacturer(propSet);
		}
		else {
			getManufacturer().add(manufacturer);
		}
	}
	/**
	 * Convenience method to add a {@link SubmodelType} object
	 * holding complete metadata to an {@link IPartyTypeAware} 
	 * object. The provided metadata must provide a {@link SubmodelType#getUri()} 
	 * to be added via {@link IPartyTypeAware#addManufacturer(String)}.
	 * @param property The property metadata
	 */
	default void addManufacturer(PartyType property) {
		
		if ( getManufacturerMap() == null) {
			setManufacturerMap(new HashMap<String, PartyType>());
		}
		// 
		if (!Strings.isNullOrEmpty(property.getUri())) {
			// keep the full description to be stored in the index
			getManufacturerMap().put(property.getUri(), property);
			// maintain the property uri (the link)
			addManufacturer(property.getUri());
		}
	}
}
