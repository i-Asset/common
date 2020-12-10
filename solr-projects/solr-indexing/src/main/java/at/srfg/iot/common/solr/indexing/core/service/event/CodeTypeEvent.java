package at.srfg.iot.common.solr.indexing.core.service.event;

import org.springframework.context.ApplicationEvent;

import at.srfg.iot.common.solr.model.model.common.CodedType;

public class CodeTypeEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	
	public CodeTypeEvent(Object source, CodedType coded) {
		super(source);
		this.codedType = coded;
	}
	private final CodedType codedType;
	
	public CodedType getCodedType() {
		return codedType;
	}


}
