package at.srfg.iot.common.datamodel.asset.api;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.AssetAdministrationShellDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.SubmodelDescriptor;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

public interface AssetDirectoryAPI {
	@ApiOperation(value = "Obtain the descriptor of an Asset based on it's identifier")
	@RequestMapping(
			method = RequestMethod.GET,
			path="/directory/aas")
	
	Optional<AssetAdministrationShellDescriptor> lookup(
			@ApiParam("The identifier of the asset to lookup, either IRI or IRDI")
			@RequestParam("identifier")
			String aasIdentifier);
	@ApiOperation(value = "Obtain the descriptor of an Asset based on it's identifier")
	@RequestMapping(
			method = RequestMethod.GET,
			path="/directory/submodel")
	Optional<SubmodelDescriptor> lookup(
			@ApiParam("The  identifier of the asset to lookup, IdType must resolve to either IRI or IRDI")
			@RequestParam("identifier")
			String aasIdentifier, 
			@ApiParam("The  identifier of the submodel to lookup, IdType must resolve to either IRI or IRDI or idShort")
			@RequestParam("submodelIdentifier")
			String submodelIdentifier);
	/**
	 * register new AAS with the directory
	 * @param shell
	 */
	@RequestMapping(
			method = RequestMethod.POST,
			path="/directory/aas")
	Optional<AssetAdministrationShell> register(@RequestBody AssetAdministrationShellDescriptor shell);
	@RequestMapping(
			method = RequestMethod.POST,
			path="/directory/submodel")
	Optional<Submodel> register(
			@ApiParam("The  identifier of the AAS receiving the Submodel, IdType must resolve to either IRI or IRDI")
			@RequestParam("identifier")
			String aasIdentifier,
			@ApiParam("The full submodel descriptor, including endpoints")
			@RequestBody
			SubmodelDescriptor model);
	/**
	 * Remove the registration for the AAS
	 * @param aasIdentifier
	 */
	@RequestMapping(
			method = RequestMethod.DELETE,
			path="/directory/aas")
	void unregister(
			@RequestParam("identifier") 
			String aasIdentifier);
	/**
	 * Remove all provided submodel's from the {@link AssetAdministrationShell}
	 * @param aasIdentifier
	 * @param submodelIdentifier
	 */
	@RequestMapping(
			method = RequestMethod.DELETE,
			path="/directory/submodel")
	void unregister(
			@RequestParam("identifier")
			String aasIdentifier, 
			@RequestParam("submodelIdentifier")
			List<String> submodelIdentifier);

}
