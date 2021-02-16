package at.srfg.iot.common.datamodel.asset.provider;

import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.DataElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Operation;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;

public interface IAssetModelListener {
	public void onEventElementCreate(String identifier, EventElement element);
	public void onEventElementRemove(String path, EventElement element);
	
	public void onOperationCreate(String path, Operation element);
	public void onOperationRemove(String path, Operation element);
	
	public void onPropertyCreate(String path, Property property);
	public void onPropertyRemove(String path, Property property);
	
	public void onValueChange(DataElement<?> element, Object oldValue, Object newValue);
}
