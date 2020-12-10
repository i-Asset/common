package at.srfg.iot.common.datamodel.asset.aas.common;

import java.util.List;

import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;
/**
 * Element that can have multiple data specification templates. A template defines the
 * additional attributes an element may or shall have  
 *
 */
public interface HasDataSpecification {
	List<ReferableElement> getDataSpecification();
	void setDataSpecification(List<ReferableElement> dataSpecification);

}
