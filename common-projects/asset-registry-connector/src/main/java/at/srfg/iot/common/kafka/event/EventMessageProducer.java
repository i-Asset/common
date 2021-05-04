package at.srfg.iot.common.kafka.event;

import java.time.Duration;
import java.time.Instant;

import org.apache.kafka.clients.producer.Callback;

import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;
import at.srfg.iot.common.datamodel.asset.aas.event.EventMessage;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;
import at.srfg.iot.common.kafka.api.Producer;

public class EventMessageProducer implements Producer<Object> {
	final Reference source;
	final Reference sourceSemanticId;
	final Reference observableReference;
	final Reference observableReferenceSemanticId;
	final String topic;
	
	final Sender sender;
	
	public EventMessageProducer(EventElement element) {
		source = element.asReference();
		sourceSemanticId = element.getSemanticId();
		observableReference = element.getObservableReference();
		observableReferenceSemanticId = element.getSemanticId();
		topic = element.getMessageTopic();
		//  todo
		sender = new Sender(String.format("%s - %s", element.getIdShort(), observableReference.getPath()), topic, null, "iasset.salzburgresearch.at:9092");
	}

	@Override
	public boolean send(Object value) {
        return send(value, (Callback)null);
	}

	@Override
	public boolean send(Object value, Instant when) {
        return send(value, when, (Callback)null);
	}

	@Override
	public boolean send(Object value, Instant when, Duration duration) {
        return send(value, when, duration, (Callback)null);
	}

	@Override
	public boolean send(Object value, Callback callback) {
		EventMessage message = new EventMessage();
		message.setSource(source);
		message.setSourceSemanticId(source);
		message.setObservableReference(source);
		message.setObservableSemanticId(observableReferenceSemanticId);
		message.setPayload(value);
		message.setTimestamp(Instant.now());
		message.setTopic(topic);
		return sender.sendMessage(message, callback);
	}

	@Override
	public boolean send(Object value, Instant when, Callback callback) {
		EventMessage message = new EventMessage();
		message.setSource(source);
		message.setSourceSemanticId(source);
		message.setObservableReference(source);
		message.setObservableSemanticId(observableReferenceSemanticId);
		message.setPayload(value);
		message.setTimestamp(Instant.now());
		message.setTopic(topic);
		return sender.sendMessage(message, callback);
	}

	@Override
	public boolean send(Object value, Instant when, Duration duration, Callback callback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
