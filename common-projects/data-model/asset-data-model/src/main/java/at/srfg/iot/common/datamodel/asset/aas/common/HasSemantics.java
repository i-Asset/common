package at.srfg.iot.common.datamodel.asset.aas.common;

import java.util.Optional;

import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;

/**
 * Stereotype for an element, that can have
 * a semantic definition.
 * 
 * The semantic definition is either stored
 * <ul>
 * <li>in the {@link Identifier} obtained via {@link #getSemanticIdentifier()} 
 * pointing to an external global element such as eCl@ss, or
 * <li>in the {@link ReferableElement} obtained via {@link #getSemanticElement()}
 * pointing to a local element of {@link Kind#Type} that defines the semantics
 * </ul>
 * @author dglachs
 *
 */
public interface HasSemantics {
	/** 
	 * Obtain the (local) semantic definition of
	 * the current element.
	 * 
	 * @return
	 */
	Optional<Referable> getSemanticElement();
	/**
	 * Set the semantic element
	 * @param semanticId
	 */
	void setSemanticElement(Referable semanticId);

	/**
	 * provide a {@link Reference} to the (local) semantic definition
	 * @return
	 */
	default Reference getSemanticId() {
		if ( getSemanticElement().isPresent()) {
			Referable semRef = getSemanticElement().get();
			if ( Reference.class.isInstance(semRef)) {
				return Reference.class.cast(semRef);
			}
			else {
				return semRef.asReference();
			}
		}
		else {
			return null;
		}

	}
	/**
	 * Setter for the reference
	 * @param reference
	 */
	default void setSemanticId(Reference reference) {
		setSemanticElement(reference);
	}
}