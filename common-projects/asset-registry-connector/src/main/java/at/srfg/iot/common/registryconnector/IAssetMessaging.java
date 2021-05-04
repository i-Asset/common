package at.srfg.iot.common.registryconnector;

import java.util.List;

import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;

public interface IAssetMessaging {
//	List<EventElement> getInputEventElements();
//	List<EventElement> getOutputEventElements();
	void startup();
	
	void shutdown();
}
