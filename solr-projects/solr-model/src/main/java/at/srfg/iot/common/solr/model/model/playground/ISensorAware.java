package at.srfg.iot.common.solr.model.model.playground;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.data.annotation.ReadOnlyProperty;

import com.google.common.base.Strings;

import at.srfg.iot.common.solr.model.model.party.PartyType;

/**
 * Mixin behaviour for classes with references to sensors (Sensor) 
 * 
 * @author dglachs
 *
 */
public interface ISensorAware {
	
	public String getUri();
	@ReadOnlyProperty
	public Map<String, Sensor> getSensorMap();
	public void setSensorMap(Map<String, Sensor> customSensor);
	public Collection<String> getSensors();
	/**
	 * Store the of string properties - implementors
	 * must take care of duplicates
	 * @param sensors
	 */
	public void setSensors(Collection<String> sensors);


	/**
	 * Maintain the list of uri's pointing to {@link PartyType}
	 * elements.
	 * 
	 * @param sensor The uri of the Sensor
	 */
	default void addSensor(String sensor) {
		if ( getSensors() == null ) {
			HashSet<String> propSet = new HashSet<String>();
			propSet.add(sensor);
			setSensors(propSet);
		}
		else {
			getSensors().add(sensor);
		}
	}
	/**
	 * Convenience method to add a {@link Sensor} object
	 * holding complete metadata to an {@link ISensorAware} 
	 * object. The provided metadata must provide a {@link Sensor#getUri()} 
	 * to be added via {@link ISensorAware#addSensor(String)}.
	 * @param property The property metadata
	 */
	default void addSensor(Sensor property) {
		
		if ( getSensorMap() == null) {
			setSensorMap(new HashMap<String, Sensor>());
		}
		// 
		if (!Strings.isNullOrEmpty(property.getUri())) {
			// keep the full description to be stored in the index
			getSensorMap().put(property.getUri(), property);
			// maintain the property uri (the link)
			addSensor(property.getUri());
		}
	}

}
