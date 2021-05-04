package at.srfg.iot.common.kafka.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Key;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.KeyElementsEnum;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;
import at.srfg.iot.common.datamodel.asset.aas.event.EventMessage;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;
import at.srfg.iot.common.datamodel.asset.connectivity.IAssetConnection;
import at.srfg.iot.common.kafka.event.util.JSON;
import at.srfg.iot.common.registryconnector.impl.AssetRegistry;

public class EventProcessor {
	private String hosts;
	private EventElement eventElement;
	private final AssetRegistry registry;
	private IAssetConnection repository;
	private MessageProducer observer;
	private boolean initialized;
	private List<EventHandler<?>> eventHandler;
    private List<Consumer> consumers = new ArrayList<Consumer>();

	public EventProcessor(EventElement eventElement, AssetRegistry registry) {
		this.eventElement = eventElement;
		this.registry = registry;
		this.repository = registry.getRepoConn();
		// initialize processor
		this.initialized = initialize();
	}
	public boolean isInitialized() {
		return initialized;
	}
	public void start() {
		switch(eventElement.getDirection()) {
		case Input:
			startMessageConsumer(hosts);
			break;
		case Output:
			startMessageProducer();
			break;
		}
	}
	
    private void startMessageConsumer(String clientName) {
        int numConsumers = 3;
        final ExecutorService executor = Executors.newFixedThreadPool(numConsumers);
        for (int i = 0; i < numConsumers; i++) {
            // 
            
            Consumer consumer = new Consumer(i, hosts, eventElement.getMessageTopic(), this);
            consumers.add(consumer);
            executor.submit(consumer);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                for (Consumer consumer : consumers) {
                    consumer.shutdown();
                }
                executor.shutdown();
                try {
                    executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

	private void startMessageProducer() {
		observer = new MessageProducer(eventElement);
		// start the observer element and send the messages
		observer.start();
		
	}
	public void stop() {
		switch(eventElement.getDirection()) {
		case Input:
		case Output:
			if ( observer != null ) {
				observer.stop();
			}
			break;
		}
	}
	private boolean initialize() {
		Reference refToBroker = eventElement.getMessageBroker();
		if ( refToBroker != null) {
			// the reference must have the identifier of the aas or submodel points to
			Key keyToAAS = refToBroker.getFirstKey();
			if (keyToAAS != null) {
				// obtain the element from referenced AAS
				Optional<Referable> messageBroker = repository.getModelElement(keyToAAS.getValue(),
						eventElement.getMessageBroker());
				
				if (messageBroker.isPresent()) {
					eventElement.setMessageBrokerElement(ReferableElement.class.cast(messageBroker.get()));
					// need to obtain the child element with a given semantic identifier
					// or
					// use
					Reference hostsRef = new Reference(Key.of("http://iasset.salzburgresearch.at/data/messageBroker/hosts", KeyElementsEnum.GlobalReference));
//					Reference brokerTypeRef = new Reference(Key.of("http://iasset.salzburgresearch.at/data/messageBroker/brokerType", KeyElementsEnum.GlobalReference));
					Optional<Property> hosts = messageBroker.get().getChildElement(hostsRef, Property.class);
					if (hosts.isPresent()) {
						// keep the hosts settings
						this.hosts = hosts.get().getValue();
						return true;
					}
					// brokertType currently not in use
// 					Optional<Property> brokerType = messageBroker.get().getChildElement(brokerTypeRef, Property.class);
				}
				//
				//
				
			}
			
		}
		return false;
		
	}
	public void processKafkaMessage(String topic, String key, String value) throws Exception {
		EventMessage message = JSON.deserializeFromString(value, EventMessage.class);
		for (EventHandler<?> handler : eventHandler) {
			//
			handler.onEventMessage(message, null);
		}
		//
		
	}
	
	private class MessageProducer implements Runnable {
		private long timeOut;
		EventMessageProducer producer;
		private Thread runner;
		boolean alive = true;
		
		MessageProducer(EventElement eventElement) {
			// use 2 seconds by default
			// TODO: use timeout from event element
			this.timeOut = 2000;
			producer = new EventMessageProducer(eventElement);
					
		}
		
		public void start() {
			alive = true;
			runner = new Thread(this);
			runner.start();
		}
		public void stop() {
			alive = false;
			
		}
		
		@Override
		public void run() {
			while (alive) {
				try {
					// obtain the current values
					Object value = registry.getElementValue(eventElement.getObservableReference());
					if ( value != null) {
						producer.send(value);
					}
					Thread.sleep(timeOut);
				} catch (InterruptedException e) {
					
				}
				
			}
		}
		
	}
	
	

}
