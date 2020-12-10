package at.srfg.iot.common.datamodel.asset.connectivity.rest;

import javax.ws.rs.client.Client;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
/**
 * Class creating a 
 * @author dglachs
 *
 */
public class ClientFactory {
	private static ClientFactory instance;
	
	
	private final ObjectMapper mapper;
//
//	private String userName;
//	private String password;
//	private boolean autoConnect;
	private ClientFactory() {
		this.mapper = createMapper();
	}
	public static ObjectMapper getObjectMapper() {
		return getInstance().getMapper();

	}

	private ObjectMapper getMapper() {
		return this.mapper;
	}
	private ObjectMapper createMapper() {
		return new ObjectMapper()
	        .enable( DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL )
	        .enable( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES )
	        .disable( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
	        .disable( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES )
	        // enable the use of java.time
	        .registerModule(new JavaTimeModule());
	}
	
	
	public static ClientFactory getInstance() {
		if ( instance == null ) {
			instance = new ClientFactory();
		}
		return instance;
		
	}
	public Client getClient() {
		return getClient(null);
	}
	public Client getClient(HttpAuthenticationFeature auth) { 
//		String host = RESTConnector.getDefault().getHost();
//		
//		KeyStore keyStore = loadKeystore(host);
		// 
		ClientConfig config = new ClientConfig();
		if ( auth != null ) {
			config.register(auth);
		}
//		JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
////		// register the objectmapper
//		provider.setMapper(this.mapper);	
		
		return JerseyClientBuilder.newBuilder()
				.withConfig(config)
				// optionally add truststore 
//				.trustStore(keyStore)
				// register a custom json provider
//				.register(provider)
				.build();
	}}
