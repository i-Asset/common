package at.srfg.iot.common.kafka.event;

import at.srfg.iot.common.datamodel.asset.aas.event.EventMessage;

public interface EventHandler<T> {
	/**
	 * Method invoked when the {@link EventHandler} receives 
	 * a message from the messaging ecosystem.
	 * @param message
	 * @param payload
	 */
	public void onEventMessage(EventMessage message, T payload);
}
