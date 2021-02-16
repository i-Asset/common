package at.srfg.iot.common.kafka.handler;

import java.lang.reflect.ParameterizedType;

import at.srfg.iot.common.kafka.api.EventMessageHandler;

public abstract class AbstractEventMessageHandler<T> implements EventMessageHandler<T> {
    /**
	* Default implementation extracting the generic type of the {@link EventMessageHandler}
    */
   @SuppressWarnings("unchecked")
   @Override
   public Class<T> getPayloadType() {
       ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
       return (Class<T>) genericSuperclass.getActualTypeArguments()[0];
   }
}
