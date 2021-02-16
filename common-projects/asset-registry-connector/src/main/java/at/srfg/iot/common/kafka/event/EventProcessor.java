package at.srfg.iot.common.kafka.event;

import at.srfg.iot.common.datamodel.asset.aas.event.EventMessage;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;
import at.srfg.iot.common.kafka.event.util.JSON;

public class EventProcessor {
	
	private final EventElement eventElement;
	
	public EventProcessor(EventElement eventElement) {
		this.eventElement = eventElement;
	}
	public void processKafkaMessage(String topic, String key, String value) throws Exception {
		EventMessage message = JSON.deserializeFromString(value, EventMessage.class);
		
		
		
	}
	
	

}
