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

	public Reference getSource() {
		return source;
	}

	public void setSource(Reference source) {
		this.source = source;
	}

	public Reference getSourceSemanticId() {
		return sourceSemanticId;
	}

	public void setSourceSemanticId(Reference sourceSemanticId) {
		this.sourceSemanticId = sourceSemanticId;
	}

	public Reference getObservableReference() {
		return observableReference;
	}

	public void setObservableReference(Reference observableReference) {
		this.observableReference = observableReference;
	}

	public Reference getObservableSemanticId() {
		return observableSemanticId;
	}

	public void setObservableSemanticId(Reference observableSemanticId) {
		this.observableSemanticId = observableSemanticId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}
	
}
