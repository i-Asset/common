package at.srfg.indexing;

import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

public interface ConceptIndexing {
	/**
	 * Remove the provided class from the index
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(
			method = RequestMethod.DELETE,
			path="/namespaces")
	@ApiOperation(
			value = "Delete all concepts of the provided namespaces. Deletes from multiple collections", 
			notes = "All concepts are removed!")
	public long deleteConcepts(
			@ApiParam("The nameSpaces to remove")
			@RequestParam(name = "nameSpace") Set<String> uri) throws Exception ;
	/**
	 * Remove the provided class from the index
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(
			method = RequestMethod.DELETE,
			path="/namespace")
	@ApiOperation(
			value = "Delete all concepts of the provided namespaces. Deletes from multiple collections", 
			notes = "All concepts are removed!")
	public long deleteConcepts(
			@ApiParam("The nameSpace to remove")
			@RequestParam(name = "nameSpace") String uri) throws Exception ;

}
