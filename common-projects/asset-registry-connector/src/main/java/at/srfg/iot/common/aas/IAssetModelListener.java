package at.srfg.iot.common.aas;

import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.DataElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Operation;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;

public interface IAssetModelListener {
	/**
	 * Callback method indicating that a new EventElement has been added
	 * @param identifier
	 * @param element
	 */
	public void onEventElementCreate(String identifier, EventElement element);
	/**
	 * Callback method indicating that an EventElement has been removed 
	 * @param path
	 * @param element
	 */
	public void onEventElementRemove(String path, EventElement element);
	/**
	 * Callback method indicating that a new Operation has been added
	 * @param path
	 * @param element
	 */
	public void onOperationCreate(String path, Operation element);
	/**
	 * Callback method indicating that an Operation element has been removed
	 * @param path
	 * @param element
	 */
	public void onOperationRemove(String path, Operation element);
	/**
	 * Callback method indicating that a new Property (DataElement) has been created
	 * @param path
	 * @param property
	 */
	public void onPropertyCreate(String path, Property property);
	/**
	 * Callback method indicating that a Property (DataElement) has been removed
	 * @param path
	 * @param property
	 */
	public void onPropertyRemove(String path, Property property);
	/**
	 * Callback method allowing to react on a value change
	 * @param element
	 * @param oldValue
	 * @param newValue
	 */
	public void onValueChange(DataElement<?> element, Object oldValue, Object newValue);
}
