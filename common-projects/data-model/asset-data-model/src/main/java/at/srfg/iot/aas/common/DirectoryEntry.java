package at.srfg.iot.aas.common;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.aas.basic.Endpoint;
import at.srfg.iot.aas.common.referencing.IdentifiableElement;

public interface DirectoryEntry extends Identifiable {
	Map<Integer, Endpoint> getEndpoints();
	/**
	 * Helper method storing a http endpoint for the element
	 * @param index the index value
	 * @param description
	 */
	default void setEndpoint(Integer index, String address, String type) {
		Endpoint desc = getEndpoints().get(index);
		if ( desc != null ) {
			desc.setAddress(address);
			desc.setType(type);
		}
		else {
			if ( this instanceof IdentifiableElement) {
				getEndpoints().put(index,  Endpoint.create((IdentifiableElement)this, index,address, type));
			}
		}
	}
	/**
	 * Helper method providing access to the element's {@link Endpoint} by index (0)
	 * @param index the (ordered) index number  
	 * @return the endpont
	 */
	default Endpoint getEndpoint(Integer index) {
		return this.getEndpoints().get(index);
	}
	@JsonIgnore
	default Optional<Endpoint> getFirstEndpoint() {
		if ( this.getEndpoints() != null && ! this.getEndpoints().isEmpty()) {
			return Optional.of(this.getEndpoints().get(0));
		}
		return Optional.empty();
	}
	default void withDefaultEndpoint(String address) {
		Optional<Endpoint> ep = getFirstEndpoint();
		if (! ep.isPresent() ) {
			Endpoint def = new Endpoint();
			def.setAddress(address);
			def.setType("http");
			this.getEndpoints().put(0,def);
		}
	}
	/**
	 * Getter for the description
	 * @return
	 */
	default Collection<Endpoint> getEndpoint() {
		if ( this.getEndpoints() == null && ! this.getEndpoints().isEmpty()) {
			return this.getEndpoints().values();
		}
		return Collections.emptyList();
	}

}
