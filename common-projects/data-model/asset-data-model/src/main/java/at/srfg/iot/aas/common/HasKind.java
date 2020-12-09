package at.srfg.iot.aas.common;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.aas.common.referencing.Kind;
/**
 * Defines the {@link Kind} of the 
 * element, one of {@link Kind#Type} or {@link Kind#Instance}
 * @author dglachs
 *
 */
public interface HasKind extends Referable {
	/**
	 * Get the {@link Kind} of the element
	 * @return
	 */
	Kind getKind();
	/**
	 * Set the {@link Kind} of the element, either {@link Kind#Type}
	 * or {@link Kind#Instance}
	 * 
	 * @param kind The kind
	 */
	void setKind(Kind kind);
	Optional<Referable> asInstance(Referable parent);
	/**
	 * Check whether the current element is an instance
	 * @return
	 */
	@JsonIgnore
	default boolean isInstance() {
		return Kind.Instance.equals(getKind());
	}
	
}
