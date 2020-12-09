package at.srfg.indexing;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.srfg.indexing.model.common.ClassType;
import at.srfg.indexing.model.common.CodedType;
import at.srfg.indexing.model.solr.FacetResult;
import at.srfg.indexing.model.solr.IndexField;
import at.srfg.indexing.model.solr.Search;
import at.srfg.indexing.model.solr.SearchResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Annotated interface for the main cores: {@link CodedType},
 * @author dglachs
 *
 */

@Api(value = "CodedType Indexing",
description = "Search API to perform Solr operations on indexed parties (organizations), items, item-properties, "
		+ "property-codes and classes (item categories)")

public interface CodedTypeIndexing {

	/**
	 * Read a single coded type
	 * @param uri The id/uri of the coded type
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(
			method = RequestMethod.GET,
			path="/codedType")
	@ApiOperation("Obtain the definition (CodedType) from the coded type  collection")
	Optional<CodedType> getCodedType(
//			@RequestHeader(value = "Authorization") 
//			String bearerToken,
			@ApiParam("Identifier of the requested CodedType")
			@RequestParam(name = "id") 				
			String uri) throws Exception;
	/**
	 * Obtain the field description (see {@link IndexField}) 
	 * @param fieldNames A set of field names to include in the result.
	 * 		  When empty or not provided, all fields are returned
	 * @return A collection of {@link IndexField}s.
	 * @throws Exception
	 */	
	@RequestMapping(
			method = RequestMethod.GET,
			path="/codedType/fields")
	@ApiOperation(
			value = "Get field definitions for CodedType index", 
			notes = "Obtain the field descriptors (IndexField) from the class collection")
	Collection<IndexField> fieldsForCodedType(
			@ApiParam("Optional list of field names to search for, when empty, all fields are returned")
			@RequestParam(name="fieldName", required = false)
			Set<String> fieldNames) throws Exception;
	

	/**
	 * Perform a search in the {@link ClassType} collection
	 * @param query
	 * @param filterQuery
	 * @param facetFields
	 * @param facetLimit
	 * @param facetMinCount
	 * @param start
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(
			method = RequestMethod.GET,
			path="/codedType/select")
	@ApiOperation(
			value = "Search in the collection for CodedType objects", 
			notes = "Perform a search by providing the distinct parameters")
	SearchResult<CodedType> searchForCodedType(
			@ApiParam("SOLR query string")
			@RequestParam(name = "q", required = false, defaultValue = "*:*") 
			String query,
			@ApiParam("SOLR field query (fq) statements")
			@RequestParam(name = "fq", required = false) 
			List<String> filterQuery,
			@ApiParam("SOLR facet field list")
			@RequestParam(name = "facet.field", required = false) 
			List<String> facetFields,
			@ApiParam("SOLR facet limit, defaults to 15")
			@RequestParam(name = "facet.limit", required = false, defaultValue = "15") 
			int facetLimit,
			@ApiParam("SOLR facet mincount, defaults to 1")
			@RequestParam(name = "facet.mincount", required = false, defaultValue = "1") 
			int facetMinCount,
			@ApiParam("SOLR start page, defaults to 0 (first page)")
			@RequestParam(name = "start", required = false, defaultValue = "0")
			int start,
			@ApiParam("SOLR page size, defaults to 10 rows per page")
			@RequestParam(name = "rows", required = false, defaultValue = "10") 
			int rows) throws Exception;
	/**
	 * Perform a {@link Search} in the {@link CodedType} collection
	 * @param search The search object defining query parameters
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(
			value = "Search in the collection for CodedType objects", 
			notes = "Perform a search by providing search parameters as Search object")
	@RequestMapping(
			method = RequestMethod.POST,
			path="/codedType/select")
	public SearchResult<CodedType> searchForCodedType(
			@ApiParam("Search definition")
			@RequestBody Search search
			) throws Exception;
	
	/**
	 * Search for suggestions in the CodedType collection
	 * @param query
	 * @param fieldName
	 * @param limit
	 * @param minCount
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(
			value = "Suggest CodedType objects", 
			notes = "Get suggestions for a coded type", response = FacetResult.class)
	@RequestMapping(
			method = RequestMethod.GET,
			path="/codedType/suggest")
	FacetResult suggestForCodedType(
//			@RequestHeader(value = "Authorization") 
//			String bearerToken,
			@ApiParam("The text fragment to search for")
			@RequestParam(name = "q") 
			String query,
			@ApiParam("The index field for searching the text fragment")
			@RequestParam(name = "field") 
			String fieldName,
			@ApiParam("Upper Limit for suggestions, defaults to 10")
			@RequestParam(name = "limit", required = false, defaultValue = "10") 
			int limit,
			@ApiParam("Lower limit for suggestions, defaults to 1)")
			@RequestParam(name = "minCount", required = false, defaultValue = "1") 
			int minCount
			) throws Exception;

	/**
	 * Store a new or update an existing {@link CodedType} object
	 * @param prop
	 * @return The stored version of the CodedType
	 * @throws Exception
	 */
	@ApiOperation(
			value = "Create or update a CodedType", 
			notes = "Store the provided CodedType object in the index")
	@RequestMapping(
			method = RequestMethod.POST,
			path="/codedType")
	public CodedType setCodedType(
//			@RequestHeader(value = "Authorization") 
//			String bearerToken,
			@RequestBody CodedType prop) throws Exception;
	/**
	 * Remove the provided coded type from the index
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(
			method = RequestMethod.DELETE,
			path="/codedType")
	@ApiOperation(
			value = "Delete a CodedType element from the index", 
			notes = "The entry is removed, corresponding links from ClassType are NOT removed!")
	public boolean deleteCodedType(
			@ApiParam("The id of the CodedType to remove")
			@RequestParam(name = "id") List<String> uri) throws Exception ;

}
