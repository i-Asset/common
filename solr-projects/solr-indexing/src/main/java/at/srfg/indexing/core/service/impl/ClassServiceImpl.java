package at.srfg.indexing.core.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.srfg.indexing.core.service.ClassService;
import at.srfg.indexing.core.service.event.ParentChildAwareEvent;
import at.srfg.indexing.core.service.event.RemoveParentChildAwareEvent;
import at.srfg.indexing.core.service.repository.ClassRepository;
import at.srfg.indexing.model.common.ClassType;
import at.srfg.indexing.model.solr.SearchResult;

@Service
public class ClassServiceImpl extends SolrServiceImpl<ClassType> implements ClassService {
	@Autowired
	private ClassRepository classRepo;
	@Override
	public String getCollection() {
		return ClassType.COLLECTION;
	}

	@Override
	public Class<ClassType> getSolrClass() {
		return ClassType.class;
	}

	@Override
	public SearchResult<ClassType> findByProperty(String property) {
		List<ClassType> result = classRepo.findByProperties(property);
		return new SearchResult<ClassType>(result);
	}

	@Override
	public SearchResult<ClassType> findByUris(Set<String> uriSet) {
		List<ClassType> result = classRepo.findByUriIn(uriSet);
		return new SearchResult<ClassType>(result);
	}

	@Override
	public SearchResult<ClassType> findForNamespaceAndLocalNames(String nameSpace, Set<String> localNames) {
		List<ClassType> result = classRepo.findByNameSpaceAndLocalNameIn(nameSpace, localNames);
		return new SearchResult<ClassType>(result);
	}

	@Override
	protected void prePersist(ClassType t) {
		getEventPublisher().publishEvent(new ParentChildAwareEvent(this, t));
	}

	@Override
	protected void postRemove(ClassType t) {
		getEventPublisher().publishEvent(new RemoveParentChildAwareEvent(this, t));
	}

	@Override
	public long deleteNameSpace(String nameSpace) {
		return classRepo.deleteByNameSpace(nameSpace);
		
	}
	@Override
	public long deleteNameSpaces(Set<String> nameSpace) {
		return classRepo.deleteByNameSpaceIn(nameSpace);
		
	}
	
}