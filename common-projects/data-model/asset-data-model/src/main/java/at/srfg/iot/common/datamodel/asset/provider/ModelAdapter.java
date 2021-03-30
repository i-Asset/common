package at.srfg.iot.common.datamodel.asset.provider;

import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.DataElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Operation;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;

@Deprecated
public class ModelAdapter implements IAssetModelListener {


	@Override
	public void onValueChange(DataElement<?> element, Object oldValue, Object newValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventElementCreate(String path, EventElement element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventElementRemove(String path, EventElement element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOperationCreate(String path, Operation element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOperationRemove(String path, Operation element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPropertyCreate(String path, Property property) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPropertyRemove(String path, Property property) {
		// TODO Auto-generated method stub
		
	}


}
