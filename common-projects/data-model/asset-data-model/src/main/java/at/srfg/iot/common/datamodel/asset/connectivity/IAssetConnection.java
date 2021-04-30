package at.srfg.iot.common.datamodel.asset.connectivity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;

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
@Path("")
public interface IAssetConnection extends at.srfg.iot.common.datamodel.asset.api.IAssetConnection {
	public String ASSET_ID_HEADER = "iAsset-Identifier";
	
	/**
	 * Obtain the {@link Identifiable} representing this I40Component
	 * @return
	 */
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Optional<Identifiable> getRoot(
			@HeaderParam(ASSET_ID_HEADER)
			String identifier);
	/**
	 * Obtain the {@link Identifiable} representing this I40Component
	 * @return
	 */
	@GET
	@Path("/children")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Referable> getChildren(
			@HeaderParam(ASSET_ID_HEADER)
			String identifier);
	@POST
	@Path("/reference")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Optional<Referable> getModelElement(
			@HeaderParam(ASSET_ID_HEADER)
			String identifier,
			Reference element);
	@POST
	@Path("/instance")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Deprecated
	public Optional<Referable> getModelInstance(
			@HeaderParam(ASSET_ID_HEADER)
			String identifier,
			Reference element);
	/**
	 * Add a new model element to the {@link IAssetConnection}. The element must
	 * contain a proper parent element (see {@link Reference}) pointing to it's root container!
	 * @param element The element to add
	 * @return
	 */
	@POST
	@Path("/element")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void setModelElement(
			@HeaderParam(ASSET_ID_HEADER)
			String identifier,
			Referable element);
	/**
	 * Add a new model element to the {@link IAssetConnection}. The element must
	 * contain a proper parent element (see {@link Reference}) pointing to it's root container!
	 * @param element The element to add
	 * @return
	 */
	@POST
	@Path("/element/{path: .*}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void setModelElement(
			@HeaderParam(ASSET_ID_HEADER)
			String identifier,
			@PathParam("path") String path, Referable element);
	/**
	 * Remove an element from the {@link IAssetConnection}. The element must 
	 * either hold the reference to it's parent container or 
	 * be a reference element directly!
	 * @param element 
	 * @return <code>true</code> when deletion successful, false otherwise
	 */
	@DELETE
	@Path("/element")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean removeModelElement(
			@HeaderParam(ASSET_ID_HEADER)
			String identifier,
			Referable element);
	/**
	 * Retrieve all {@link Referable} children of the current root component
	 * 
	 * @return
	 */
	@GET
	@Path("/element/{path: .*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Optional<Referable> getElement(
			@HeaderParam(ASSET_ID_HEADER)
			String identifier,
			@PathParam("path") String path);
	@GET
	@Path("/children/{path: .*}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Referable> getChildren(
			@HeaderParam(ASSET_ID_HEADER)
			String identifier, 
			@PathParam("path") String path);
	@GET
	@Path("/value/{path: .*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getValue(	
			@HeaderParam(ASSET_ID_HEADER)
			String identifier,
			@PathParam("path") String path);
	@POST
	@Path("/value/{path: .*}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void setValue(
			@HeaderParam(ASSET_ID_HEADER)
			String identifier,
			@PathParam("path") String path, Object value);
	/**
	 * Invoke the operation named with the path
	 * @param path
	 * @param parameterMap
	 * @return
	 */
	@POST
	@Path("/invoke/{path: .*}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Map<String,Object> invokeOperation(
			@HeaderParam(ASSET_ID_HEADER)
			String identifier,
			@PathParam("path") String path, Map<String, Object> parameterMap);
	
	
}
