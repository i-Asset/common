package at.srfg.iot.common.kafka.api;

import at.srfg.iot.common.datamodel.asset.aas.event.EventMessage;

public interface EventMessageHandler<T> {
	
	public Class<T> getPayloadType();
	
	public void onEventMessage(EventMessage event, T payload);

}
