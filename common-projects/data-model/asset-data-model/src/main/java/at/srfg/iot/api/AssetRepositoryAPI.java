package at.srfg.iot.api;

import java.util.Optional;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.srfg.iot.aas.basic.Asset;
import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.common.referencing.IdType;
import at.srfg.iot.aas.common.referencing.ReferableElement;
import at.srfg.iot.aas.common.referencing.Reference;
import at.srfg.iot.aas.dictionary.ConceptDescription;
import at.srfg.iot.aas.modeling.SubmodelElement;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Annotated interface for the main CRUD operations for
 * {@link AssetAdministrationShell}, {@link Asset}, {@link Submodel}, {@link SubmodelElement}
 * and {@link ConceptDescription}. 
 * @author dglachs
 *
 */
public interface AssetRepositoryAPI {
	
	/**
	 * Read a full AAS from the Registry
	 * @param uri The identifier of the AAS
	 * @return 
	 * @throws Exception
	 */
	@ApiOperation(value = "Retrieve a full AAS based on it's identifier")
	@RequestMapping(
			method = RequestMethod.GET,
			path="/aas")
	Optional<AssetAdministrationShell> getAssetAdministrationShell(
			@ApiParam("The identifier of the AAS, either a IRI, IRDI or UUID")
			@RequestParam(name = "id") 				
			String uri,
			@ApiParam("Retrieve the head element only or the complete AAS")
			@RequestParam(name = "complete", required = false, defaultValue = "true")
			boolean complete) throws Exception;
	/**
	 * Set a full AAS from the Registry
	 * @param uri The identifier of the AAS
	 * @return 
	 * @throws Exception
	 */
	@ApiOperation(value = "Store/Update a full AAS")
	@RequestMapping(
			method = RequestMethod.POST,
			path="/aas")
	Optional<AssetAdministrationShell> setAssetAdministrationShell(
			@ApiParam("The asset administration shell")
			@RequestBody
			AssetAdministrationShell dto) throws Exception;

	/**
	 * Delete a complete AAS from the Registry, will delete all contained {@link Submodel} and {@link SubmodelElement}
	 * @param uri The identifier of the AAS
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "Delete a full AAS based on it's identifier")
	@RequestMapping(
			method = RequestMethod.DELETE,
			path = "/aas")
	boolean deleteAssetAdministrationShell(			
			@ApiParam("The identifier of the AAS, either a IRI, IRDI or UUID")
			@RequestParam(name = "id") 				
			String uri) throws Exception;

	/**
	 * Read a full AAS from the Registry
	 * @param uri The identifier of the AAS
	 * @return 
	 * @throws Exception
	 */
	@ApiOperation(value = "Retrieve a full asset based on it's identifier")
	@RequestMapping(
			method = RequestMethod.GET,
			path="/asset")
	Optional<Asset> getAsset(
			@ApiParam("The identifier of the Asset, either a IRI, IRDI or UUID")
			@RequestParam(name = "id") 				
			String uri) throws Exception;
	
	
//	/**
//	 * Retrieve a single {@link Submodel} from the registry. The model must not have 
//	 * and identifier with {@link IdType#IdShort}.
//	 * 
//	 * @param uri The identifier of the {@link Submodel}
//	 * @return
//	 * @throws Exception
//	 */
//	@ApiOperation(value = "Retrieve a full Submodel based on it's identifier")
//	@RequestMapping(
//			method = RequestMethod.GET,
//			path="/submodel")
//	Optional<Submodel> getSubmodel(			
//			@ApiParam("The identifier of the Submodel, either a IRI, IRDI or UUID")
//			@RequestParam(name = "id") 				
//			String uri) throws Exception;
	
	/**
	 * Add a new Submodel to the registry
	 * @param uri The identifier of the {@link AssetAdministrationShell} the new model belongs to
	 * @param submodel The {@link Submodel} to add
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "Add a new Submodel to an existing AAS" )
	@RequestMapping(
			method = RequestMethod.PUT,
			path="/submodel")
	Optional<Submodel> addSubmodel(
			@ApiParam("The identifier of the AAS the submodel belongs to, either a IRI, IRDI or UUID")
			@RequestParam(name = "aas", required = false) 				
			String uri, 
			@ApiParam("The full Submodel")
			@RequestBody Submodel submodel) throws Exception;
	/**
	 * Update an existing {@link Submodel}. The data provided must be complete. 
	 * @param submodel The model to update
	 * @return 
	 * @throws Exception
	 */
	@ApiOperation(value = "Update an existing Submodel")
	@RequestMapping(
			method = RequestMethod.POST,
			path="/submodel")
	Optional<Submodel> setSubmodel(
			@ApiParam("The identifier of the AAS the submodel belongs to, either a IRI, IRDI or UUID")
			@RequestParam(name = "aas", required = false) 				
			String uri, 
			@ApiParam("The full Submodel")
			@RequestBody Submodel submodel) throws Exception;
	
	/**
	 * Delete a {@link Submodel} from the registry. The model must not have
	 * an identifier with {@link IdType#IdShort}
	 * @param uri The identifier of the submodel
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "Delete a submodel")
	@RequestMapping(
			method = RequestMethod.DELETE,
			path = "/submodel")
	boolean deleteSubmodel(			
			@ApiParam("The identifier the submodel to delete, either a IRI, IRDI or UUID")
			@RequestParam(name = "id") 				
			String uri) throws Exception;
	/**
	 * Retrieve a single {@link SubmodelElement}
	 * @param uri The 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "Read a submodel element by Submodel-Identifier and path",notes = "The path is the concatenation of all idShort-values, separated by slash")
	@RequestMapping(
			method = RequestMethod.GET,
			path="/submodel/element")
	Optional<SubmodelElement> getSubmodelElement(			
			@RequestParam(name = "id") 				
			String uri,
			@RequestParam(name = "path")
			String path
			) throws Exception;
	/**
	 * Retrieve a single {@link AssetAdministrationShell} based on it's reference
	 * @param Reference  
	 * @return
	 * @see #getAssetAdministrationShell(String)
	 * @throws Exception
	 */
	@ApiOperation(value = "Retrieve an asset adminstration shell based it's reference")
	@RequestMapping(
			method = RequestMethod.POST,
			path="/reference/aas")
	Optional<AssetAdministrationShell> getAssetAdministrationShellByReference(
			@ApiParam("The reference to the requested aas")
			@RequestBody
			Reference reference
			) throws Exception;

	/**
	 * Retrieve a single {@link Asset} based on it's reference
	 * @param Reference  
	 * @return
	 * @see #getAsset(String)
	 * @throws Exception
	 */
	@ApiOperation(value = "Retrieve an asset based it's reference")
	@RequestMapping(
			method = RequestMethod.POST,
			path="/reference/asset")
	Optional<Asset> getAssetByReference(
			@ApiParam("The reference to the requested asset")
			@RequestBody
			Reference reference
			) throws Exception;
	/**
	 * Retrieve a single {@link Submodel} based on it's reference
	 * @param Reference
	 * @return
	 * @see #getSubmodel(String)
	 * 
	 * @throws Exception
	 */
	@ApiOperation(value = "Retrieve Submodel based it's reference")
	@RequestMapping(
			method = RequestMethod.POST,
			path="/reference/submodel")
	Optional<Submodel> getSubmodelByReference(
			@ApiParam("The reference to the requested submodel element")
			@RequestBody
			Reference reference
			) throws Exception;
	
	/**
	 * Retrieve a single {@link SubmodelElement} based on it's reference
	 * @param Reference  
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "Retrieve a SubmodelElement based it's reference", notes="Note: The first key must point to the Submodel containing the element")
	@RequestMapping(
			method = RequestMethod.POST,
			path="/reference/submodel/element")
	Optional<SubmodelElement> getSubmodelElementByReference(
			@ApiParam("The reference to the requested submodel element")
			@RequestBody
			Reference reference
			) throws Exception;
	/**
	 * Retrieve a single {@link ConceptDescription} based on it's reference
	 * @param Reference  
	 * @return
	 * @see #getConceptDescription(String)
	 * @throws Exception
	 */
	@ApiOperation(value = "Retrieve a ConceptDescription based it's reference")
	@RequestMapping(
			method = RequestMethod.POST,
			path="/reference/concept")
	Optional<ConceptDescription> getConceptDescriptionByReference(
			@ApiParam("The reference to the requested concept description")
			@RequestBody
			Reference reference
			) throws Exception;
	
	/**
	 * Add a new {@link SubmodelElement} to the registry. The containing 
	 * @param uri
	 * @param path
	 * @param submodelElement
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value ="Add a new submodel element to the registry", 
			notes = "The parent element must be provided by the Submodel-URI and path (concatenation of idShort, delimited with Slash [/]). "
					+ "When not present, the submodel must have a valid parent-Element pointing to it's parent")
	@RequestMapping(
			method = RequestMethod.PUT,
			path="/submodel/element")
	Optional<SubmodelElement> addSubmodelElement(
			@ApiParam("The identifier of the containing Submodel, either IRI, IRDI or UUID")
			@RequestParam(name = "id", required = false) 				
			String uri,
			@ApiParam(value = "The path pointing to the parent element of the new element - concatenated idShort-paths delimited with slash [/]")
			@RequestParam(name = "path", required = false)
			String path,
			@ApiParam(value = "The full submodel element")
			@RequestBody 
			SubmodelElement submodelElement
			) throws Exception;
	
	/**
	 * Update an existing {@link SubmodelElement} in the registry 
	 * @param uri The path to the containing {@link Submodel}
	 * @param path The concatenated {@link ReferableElement#getIdShort()} of all parent SubmodelElements, concatenated with slash "/" 
	 * @param submodelElement The changed {@link SubmodelElement}
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "Update an existing SubmodelElement in the registry")
	@RequestMapping(
			method = RequestMethod.POST,
			path="/submodel/element")
	Optional<SubmodelElement> setSubmodelElement(			
			@ApiParam("The identifier of the containing Submodel, either IRI, IRDI or UUID")
			@RequestParam(name = "id", required = false) 				
			String uri,
			@ApiParam(value = "The path pointing to the parent element of the new element - concatenated idShort-paths delimited with slash [/]")
			@RequestParam(name = "path", required = false)
			String path,
			@ApiParam(value = "The full submodel element")
			@RequestBody 
			SubmodelElement submodelElement
			) throws Exception;
	
	/**
	 * Delete a {@link SubmodelElement} from the registry
	 * @param uri The path to the containing {@link Submodel}
	 * @param path The concatenated {@link ReferableElement#getIdShort()} of all parent SubmodelElements, concatenated with slash "/" 
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "Delete a SubmodelElement from the registry")
	@RequestMapping(
			method = RequestMethod.DELETE,
			path = "/submodel/element")
	boolean deleteSubmodelElement(			
			@ApiParam("The identifier of the containing Submodel, either IRI, IRDI or UUID")
			@RequestParam(name = "id", required = false) 				
			String uri,
			@ApiParam(value = "The path pointing to the parent element of the new element - concatenated idShort-paths delimited with slash [/]")
			@RequestParam(name = "path", required = false)
			String path
			) throws Exception;
	
	/**
	 * Retrieve a {@link ConceptDescription} from the registry
	 * @param uri The id of the concept description
	 * @return
	 * @see #getConceptDescriptionByReference(Reference)
	 * @throws Exception
	 */
	@ApiOperation("Retrieve a ConceptDescription from the registry")
	@RequestMapping(
			method = RequestMethod.GET,
			path="/concept/description")
	Optional<ConceptDescription> getConceptDescription(			
			@ApiParam("The identifier of the ConceptDescription, either IRI, IRDI or UUID")
			@RequestParam(name = "id") 				
			String uri
			) throws Exception;
	
	/**
	 * Add a new {@link ConceptDescription} to the registry
	 * @param uri The id of the new element
	 * @param submodelElement The full concept element
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("")
	@RequestMapping(
			method = RequestMethod.PUT,
			path="/concept/description")
	Optional<ConceptDescription> addConceptDescription(			
			@RequestParam(name = "id") 				
			String uri,
			@RequestBody 
			ConceptDescription submodelElement
			) throws Exception;
	/**
	 * Update a {@link ConceptDescription} in the registry
	 * @param uri The identifer of the element to update
	 * @param conceptDescription
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(
			method = RequestMethod.POST,
			path="/concept/description")
	Optional<ConceptDescription> setConceptDescription(			
			@RequestParam(name = "id") 				
			String uri,
			@RequestBody 
			ConceptDescription conceptDescription
			) throws Exception;
	
	@RequestMapping(
			method = RequestMethod.DELETE,
			path = "/concept/description")
	boolean deleteConceptDescription(			
			@RequestParam(name = "id") 				
			String uri
			) throws Exception;



}