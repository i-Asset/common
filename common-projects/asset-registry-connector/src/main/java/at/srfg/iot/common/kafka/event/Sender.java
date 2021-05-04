package at.srfg.iot.common.kafka.event;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;

import at.srfg.iot.common.datamodel.asset.aas.event.EventMessage;
import at.srfg.iot.common.kafka.api.Producer;
import at.srfg.iot.common.kafka.event.util.ObjectMapperFactory;

/**
 * Helper class providing the "sendMessage" method for Kafka messaging
 * @author dglachs
 *
 */
public final class Sender {
    Logger logger = LoggerFactory.getLogger(Producer.class);
    private final String topic;
    private final String key;
    private final Properties properties;
    private KafkaProducer<String, String> producer;
    private boolean closed = true;
    public Sender(String name, String topic, String key, String hosts) {
        // create the id based on name and the data-stream-id
        // 
        this.topic = topic;
        this.key = key;
        
        // create properties
        properties = new Properties();
        properties.put("client.id", name);
        properties.put("producer.type", "async");
        properties.put("bootstrap.servers", String.join(",", hosts));
        
        properties.put("acks", "all");

        // If the request fails, the producer can automatically retry,
        properties.put("retries", 0);

        // Specify buffer size in config
        properties.put("batch.size", 16384);

        // Reduce the no of requests less than 0
        properties.put("linger.ms", 1);

        // The buffer.memory controls the total amount of memory available to the
        // producer for buffering.
        properties.put("buffer.memory", 33554432);

        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", StringDeserializer.class.getName());
        
        producer = new KafkaProducer<>(properties);
        closed = false;
    }
    
    
    public boolean sendMessage(EventMessage observation) {
    	return sendMessage(observation, null);
    }
    
    public boolean sendMessage(EventMessage observation, Callback callback) {
        if ( producer == null || closed ) {
            producer = new KafkaProducer<String, String>(properties);
            closed = false;
        }
        try {
            String json = ObjectMapperFactory.get().writeValueAsString(observation);
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, json);
            if ( callback != null ) {
                producer.send(record, callback);
            }
            else {
                producer.send(record);
            }
            return true;
        } catch (JsonProcessingException e) {
            logger.error(e.getLocalizedMessage());
        }
        return false;
        
    }
    public void close() {
        try {
            producer.close();
            closed = true;
        } finally {
            producer = null;
        }
    }


    public boolean isClosed() {
        return closed;
    }


}
