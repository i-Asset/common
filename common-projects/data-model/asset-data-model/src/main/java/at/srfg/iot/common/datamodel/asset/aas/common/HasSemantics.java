package at.srfg.iot.common.datamodel.asset.aas.common;

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
	Referable getSemanticElement();
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
		if ( getSemanticElement()== null) {
			return null;
		}
		if ( getSemanticElement() instanceof Reference) {
			return (Reference) getSemanticElement();
		}
		else {
			Referable semantic = getSemanticElement();
			return semantic.asReference();
		}
		//return new Reference(getSemanticElement());
	}
	/**
	 * Setter for the reference
	 * @param reference
	 */
	default void setSemanticId(Reference reference) {
		setSemanticElement(reference);
	}
	/**
	 * Convenience method to check whether a provided {@link Reference}
	 * must be resolved and stored in the current {@link HasSemantics} with
	 * {@link #setSemanticElement(ReferableElement)}
	 * 
	 * @param reference The reference pointing to a {@link ReferableElement}
	 * 
	 * @return <code>true</code> when the current {@link #getSemanticElement()} is referenced 
	 * with the provided {@link Reference}, <code>false</code> to indicate that
	 * the reference needs to be resolved!
	 * 
	 */
	default boolean isSemanticReferenceResolved(Reference reference) {
		if ( reference != null ) {
			if ( getSemanticElement() != null  ) {
				return getSemanticElement().asReference().equals(reference);		
			}
			return false;
		}
		return true;
		// the current semantic element is null, the provided reference must not be null  
	}

}