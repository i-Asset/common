package at.srfg.iot.common.solr.indexing.core.service.event;

import org.springframework.context.ApplicationEvent;

import at.srfg.iot.common.solr.model.model.common.IPropertyAware;

public class RemovePropertyAwareEvent extends ApplicationEvent {

	public RemovePropertyAwareEvent(Object source, IPropertyAware custom) {
		super(source);

		this.item = custom;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final IPropertyAware item;
	
	public IPropertyAware getEventObject() {
		return item;
	}
	
	

}
