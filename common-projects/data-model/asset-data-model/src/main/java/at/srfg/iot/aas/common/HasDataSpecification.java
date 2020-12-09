package at.srfg.iot.aas.common;

import java.util.List;

import at.srfg.iot.aas.common.referencing.ReferableElement;
/**
 * Element that can have multiple data specification templates. A template defines the
 * additional attributes an element may or shall have  
 *
 */
public interface HasDataSpecification {
	List<ReferableElement> getDataSpecification();
	void setDataSpecification(List<ReferableElement> dataSpecification);

}
