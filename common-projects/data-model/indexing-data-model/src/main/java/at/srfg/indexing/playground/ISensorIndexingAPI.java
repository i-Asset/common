package at.srfg.indexing.playground;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.srfg.iot.common.solr.model.model.common.CodedType;
import at.srfg.iot.common.solr.model.model.common.PropertyType;
import at.srfg.iot.common.solr.model.model.playground.Sensor;
import at.srfg.iot.common.solr.model.model.solr.FacetResult;
import at.srfg.iot.common.solr.model.model.solr.IndexField;
import at.srfg.iot.common.solr.model.model.solr.Search;
import at.srfg.iot.common.solr.model.model.solr.SearchResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Annotated interface for the main cores, e.g. {@link Sensor},
 * {@link PropertyType} and {@link CodedType} instances.
 * @author dglachs
 *
 */
@Api(value = "Sensor API Controller",
description = "Search API to perform Solr operations on indexed sensors ")

public interface ISensorIndexingAPI {
	/**
	 * Read a single concept class, e.g.  
	 * @param uri The id/uri of the class
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(
			method = RequestMethod.GET,
			path="/sensor")
	@ApiOperation("Obtain a Concept Class (Sensor) from the class collection")
	Optional<Sensor> getSensor(
//			@RequestHeader(value = "Authorization") 
//			String bearerToken,
			@ApiParam("Identifier of the requested Sensor")
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
			path="/sensor/fields")
	@ApiOperation(
			value = "Get field definitions for Sensor index", 
			notes = "Obtain the field descriptors (IndexField) from the asset collection")
	Collection<IndexField> fieldsForSensor(
			@ApiParam("Optional list of field names to search for, when empty, all fields are returned")
			@RequestParam(name="fieldName", required = false)
			Set<String> fieldNames) throws Exception;
	

	/**
	 * Perform a search in the {@link Sensor} collection
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
			path="/sensor/select")
	@ApiOperation(
			value = "Search in the collection for Sensor objects", 
			notes = "Perform a search by providing the distinct parameters")
	SearchResult<Sensor> searchForSensor(
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
	 * Perform a {@link Search} in the {@link Sensor} collection
	 * @param search The search object defining query parameters
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(
			value = "Search in the collection for Sensor objects", 
			notes = "Perform a search by providing search parameters as Search object")
	@RequestMapping(
			method = RequestMethod.POST,
			path="/sensor/select")
	public SearchResult<Sensor> searchForSensor(
			@ApiParam("Search definition")
			@RequestBody Search search
			) throws Exception;
	
	/**
	 * Search for suggestions in the class type collection
	 * @param query
	 * @param fieldName
	 * @param limit
	 * @param minCount
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(
			value = "Suggest Asset Type objects", 
			notes = "Get suggestions for a class (category)", response = FacetResult.class)
	@RequestMapping(
			method = RequestMethod.GET,
			path="/sensor/suggest")
	FacetResult suggestForSensor(
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
	 * Store a new or update an existing {@link Sensor} object
	 * @param prop
	 * @return The stored version of the sensor
	 * @throws Exception
	 */
	@ApiOperation(
			value = "Create or update a Sensor", 
			notes = "Store the provided Sensor object in the index, will also "
					+ "create/update Properties including the links between Sensor and PropertyType ...")
	@RequestMapping(
			method = RequestMethod.POST,
			path="/sensor")
	public Sensor setSensor(
//			@RequestHeader(value = "Authorization") 
//			String bearerToken,
			@RequestBody Sensor prop) throws Exception;
	/**
	 * Remove the provided class from the index
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(
			method = RequestMethod.DELETE,
			path="/sensor")
	@ApiOperation(
			value = "Delete a Sensor element from the index", 
			notes = "The entry is removed, corresponding links from PropertyType are also removed!")
	public boolean deleteSensor(
			@ApiParam("The id of the concept class to remove")
			@RequestParam(name = "id") String uri) throws Exception ;

}