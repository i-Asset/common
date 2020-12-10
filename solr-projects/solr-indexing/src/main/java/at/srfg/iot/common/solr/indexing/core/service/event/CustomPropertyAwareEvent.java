package at.srfg.iot.common.solr.indexing.core.service.event;

import org.springframework.context.ApplicationEvent;

import at.srfg.iot.common.solr.model.model.common.ICustomPropertyAware;

public class CustomPropertyAwareEvent extends ApplicationEvent {

	public CustomPropertyAwareEvent(Object source, ICustomPropertyAware custom) {
		super(source);

		this.item = custom;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ICustomPropertyAware item;
	
	public ICustomPropertyAware getItem() {
		return item;
	}
	
	

}
