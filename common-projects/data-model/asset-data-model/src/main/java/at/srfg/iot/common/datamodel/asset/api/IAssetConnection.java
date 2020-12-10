package at.srfg.iot.common.datamodel.asset.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * REST-API for accessing a single {@link AssetAdministrationShell}.
 * 
 * 
 * 
 * The Device Endpoint already identifies the provided AssetAdministrationShell or Submodel. 
 * The defined API methods therefore are relative to the {@link Identifiable} element.
 * @author dglachs
 *
 */
public interface IAssetConnection {
	public String ASSET_ID_HEADER = "iAsset-Identifier";
	
	/**
	 * Obtain the {@link Identifiable} representing this I40Component
	 * @return
	 */
	@ApiOperation(value = "Obtain the identifiable element")
	@RequestMapping(
			method = RequestMethod.GET,
			path="")
	public Optional<Identifiable> getRoot(
			@ApiParam("The identifier of the Asset Administration Shell or Submodel!")
			@RequestHeader(ASSET_ID_HEADER)
			String identifier);
	/**
	 * Obtain the {@link Identifiable} representing this I40Component
	 * @return
	 */
	@ApiOperation(value = "Obtain the identifiable element")
	@RequestMapping(
			method = RequestMethod.GET,
			path="/children")
	public List<Referable> getChildren(
			@ApiParam("The identifier of the Asset Administration Shell or Submodel!")
			@RequestHeader(ASSET_ID_HEADER)
			String identifier);
	/**
	 * Obtain an element based on the reference
	 * @param identifier
	 * @param element
	 * @return
	 */
	@ApiOperation(value = "Obtain the identifiable element by it's reference")
	@RequestMapping(
			method = RequestMethod.POST,
			path="/reference")
	public Optional<Referable> getModelElement(
			@ApiParam("The identifier of the Asset Administration Shell or Submodel!")
			@RequestHeader(name=ASSET_ID_HEADER)
			String identifier,
			@RequestBody
			Reference element);
	/**
	 * Add a new model element to the {@link IAssetConnection}. The element must
	 * contain a proper parent element (see {@link Reference}) pointing to it's root container!
	 * @param element The element to add
	 * @return
	 */
	@ApiOperation(value = "Add/Update a referable element")
	@RequestMapping(
			method = RequestMethod.POST,
			path="/element")
	public void setModelElement(
			@ApiParam("The identifier of the Asset Administration Shell or Submodel!")
			@RequestHeader(name=ASSET_ID_HEADER)
			String identifier,
			@RequestBody
			Referable element);
	/**
	 * Add a new model element to the {@link IAssetConnection}. The element must
	 * contain a proper parent element (see {@link Reference}) pointing to it's root container!
	 * @param element The element to add
	 * @return
	 */
	@ApiOperation(value = "Obtain the identifiable element")
	@RequestMapping(
			method = RequestMethod.POST,
			path="/element/{path}")
	public void setModelElement(
			@ApiParam("The identifier of the Asset Administration Shell or Submodel!")
			@RequestHeader(name=ASSET_ID_HEADER)
			String identifier,
			@ApiParam("The path to the container")
			@PathVariable("path") 
			String path,
			@RequestBody
			Referable element);
	/**
	 * Remove an element from the {@link IAssetConnection}. The element must 
	 * either hold the reference to it's parent container or 
	 * be a reference element directly!
	 * @param element 
	 * @return <code>true</code> when deletion successful, false otherwise
	 */
	@ApiOperation(value = "Obtain the identifiable element")
	@RequestMapping(
			method = RequestMethod.DELETE,
			path="/element")
	public boolean removeModelElement(
			@ApiParam("The identifier of the Asset Administration Shell or Submodel!")
			@RequestHeader(name=ASSET_ID_HEADER)
			String identifier,
			@RequestBody
			Referable element);
	/**
	 * Retrieve all {@link Referable} children of the current root component
	 * 
	 * @return
	 */
	@ApiOperation(value = "Obtain the identifiable element")
	@RequestMapping(
			method = RequestMethod.GET,
			path="/element/{path}")
	public Optional<Referable> getElement(
			@ApiParam("The identifier of the Asset Administration Shell or Submodel!")
			@RequestHeader(name=ASSET_ID_HEADER)
			String identifier,
			@ApiParam("The path to the container")
			@PathVariable("path") 
			String path);
	/**
	 * Obtain the children of the element
	 * @param identifier
	 * @param path
	 * @return
	 */
	@ApiOperation(value = "Obtain the identifiable element")
	@RequestMapping(
			method = RequestMethod.GET,
			path="/children/{path}")
	public List<Referable> getChildren(
			@ApiParam("The identifier of the Asset Administration Shell or Submodel!")
			@RequestHeader(name=ASSET_ID_HEADER)
			String identifier,
			@ApiParam("The path to the container")
			@PathVariable("path") 
			String path);
	/**
	 * Obtain the children of the element
	 * @param identifier
	 * @param path
	 * @return
	 */
	@ApiOperation(value = "Obtain the identifiable element")
	@RequestMapping(
			method = RequestMethod.GET,
			produces = MediaType.TEXT_PLAIN_VALUE,
			path="/value/{path}")
	public String getValue(	
			@ApiParam("The identifier of the Asset Administration Shell or Submodel!")
			@RequestHeader(name=ASSET_ID_HEADER)
			String identifier,
			@ApiParam("The path to the container")
			@PathVariable("path") 
			String path);
	
	
	@ApiOperation(value = "Obtain the identifiable element")
	@RequestMapping(
			method = RequestMethod.POST,
			path="/value/{path}")
	public void setValue(
			@ApiParam("The identifier of the Asset Administration Shell or Submodel!")
			@RequestHeader(name=ASSET_ID_HEADER)
			String identifier,
			@ApiParam("The path to the container")
			@PathVariable("path") 
			String path,
			@RequestBody
			String value);
	/**
	 * Invoke the operation named with the path
	 * @param path
	 * @param parameterMap
	 * @return
	 */
	@ApiOperation(value = "Obtain the identifiable element")
	@RequestMapping(
			method = RequestMethod.POST,
			path="/invoke/{path}")	
	public Object invokeOperation(
			@ApiParam("The identifier of the Asset Administration Shell or Submodel!")
			@RequestHeader(name=ASSET_ID_HEADER)
			String identifier,
			@ApiParam("The path to the container")
			@PathVariable("path") 
			String path,
			@RequestBody
			Map<String, Object> parameterMap);
	
	
}
