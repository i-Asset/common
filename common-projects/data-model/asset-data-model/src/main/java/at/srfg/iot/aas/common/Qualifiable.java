package at.srfg.iot.aas.common;

import java.util.ArrayList;
import java.util.List;

import at.srfg.iot.aas.modeling.Constraint;
import at.srfg.iot.aas.modeling.Formula;
import at.srfg.iot.aas.modeling.Qualifier;

/**
 * Interface for the {@link Qualifiable} stereotype.
 * Each qualifiable elementmay be further described with
 * {@link Constraint} elements. Eligible {@link Constraint}s
 * are 
 * <ul>
 * <li>{@link Formula}
 * <li>{@link Qualifier}
 * </ul>
 * @author dglachs
 *
 */
public interface Qualifiable {
	/**
	 * Get the list of qualifying constraints
	 * @return
	 */
	List<Constraint> getQualifier();
	/**
	 * Set the list of qualifying constraints
	 * @param qualifier
	 */
	void setQualifier(List<Constraint> qualifier);
	default void addQualifier(Constraint constraint) {
		if (getQualifier() == null) {
			setQualifier(new ArrayList<Constraint>());
		}
		if ( ! getQualifier().contains(constraint)) {
			getQualifier().add(constraint);
		}
	}

}
