package at.srfg.iot.common.datamodel.asset.connectivity.move;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.AssetAdministrationShellDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.SubmodelDescriptor;
import at.srfg.iot.common.datamodel.asset.api.AssetDirectoryAPI;

@Path("")
public interface IAssetDirectory extends AssetDirectoryAPI {

	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/directory/aas")
	Optional<AssetAdministrationShellDescriptor> lookup(@QueryParam("identifier") String aasIdentifier);
	
	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/directory/aas")
		Optional<SubmodelDescriptor> lookup(String aasIdentifier, String submodelIdentifier);

	@Override
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/directory/aas")
	Optional<AssetAdministrationShell> register(AssetAdministrationShellDescriptor shell);
	@Override
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/directory/submodel")
	Optional<Submodel> register(@QueryParam("identifier") String aasIdentifier, SubmodelDescriptor model);

	@Override
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/directory/aas")
	void unregister(@QueryParam("identifier") String aasIdentifier);


	@Override
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/directory/submodel")
	void unregister(String aasIdentifier, List<String> submodelIdentifier);

}
