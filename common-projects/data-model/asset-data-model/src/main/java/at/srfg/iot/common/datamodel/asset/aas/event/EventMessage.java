package at.srfg.iot.common.datamodel.asset.aas.event;

import java.time.Instant;

import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;

public class EventMessage {
	
	private Reference source;
	
	private Reference sourceSemanticId;
	
	private Reference observableReference;
	
	private Reference observableSemanticId;
	
	private String topic;
	
	private String subject;
	
	private Instant timestamp;
	
	private Object payload;
	
}
