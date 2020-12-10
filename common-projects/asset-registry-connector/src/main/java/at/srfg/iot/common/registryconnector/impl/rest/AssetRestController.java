package at.srfg.iot.common.registryconnector.impl.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.AssetAdministrationShellDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Operation;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;
import at.srfg.iot.common.datamodel.asset.api.ISubmodelElement;
import at.srfg.iot.common.datamodel.asset.connectivity.IAssetConnection;
import at.srfg.iot.common.datamodel.asset.provider.IAssetProvider;

@Path("")
public class AssetRestController implements IAssetConnection {
	@Context
	private SecurityContext securityContext;
	/**
	 * Injected IAssetProvider
	 */
	@Inject
	@org.jvnet.hk2.annotations.Optional
	private IAssetProvider shell;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/descriptor")
	public AssetAdministrationShellDescriptor getDescriptor() {
		Identifiable root = shell.getRoot();
		if ( root instanceof AssetAdministrationShell) {
			return new AssetAdministrationShellDescriptor((AssetAdministrationShell)root);
		}
		return null;
	}
	@Override
	public Optional<Identifiable> getRoot(String identifier) {
		return Optional.ofNullable(shell.getRoot());
	}
	@Override
	public List<Referable> getChildren(String identifier) {
		return shell.getRoot().getChildren();
	}
	@Override
	public void setModelElement(String identifier, String path, Referable element) {
		Optional<SubmodelElementContainer> container = shell.getElement(path, SubmodelElementContainer.class);
		if ( container.isPresent() && ISubmodelElement.class.isInstance(element)) {
			container.get().addSubmodelElement(ISubmodelElement.class.cast(element));
		}
	}
	@Override
	public void setModelElement(String identifier, Referable element) {
		Reference parent = element.getParent();
		shell.setElement(parent, element);
	}
	@Override
	public boolean removeModelElement(String identifier, Referable element) {
		return shell.deleteElement(element);
	}
	@Override
	public Optional<Referable> getElement(String identifier, String path) {
		return shell.getElement(path);
	}
	@Override
	public List<Referable> getChildren(String identifier, String pathToContainer) {
		Optional<Referable> element = shell.getElement(pathToContainer);
		if (element.isPresent() ) {
			List<Referable> result = element.get().getChildren();
			return result;
		}
		return new ArrayList<Referable>();
	}
	@Override
	public Object invokeOperation(String identifier, String path, Map<String, Object> parameterMap) {
		Optional<Operation> operation = shell.getElement(path,  Operation.class);
		if ( operation.isPresent()) {
			return operation.get().invoke(parameterMap);
		}
		return null;
	}
	@Override
	public String getValue(String identifier, String path) {
		Optional<Property> opt = shell.getElement(path, Property.class);
		if (opt.isPresent()) {
			return opt.get().getValue();
		}
		return null;
	}
	@Override
	public void setValue(String identifier, String path, String value) {
		Optional<Property> opt = shell.getElement(path, Property.class);
		if (opt.isPresent()) {
			opt.get().setValue(value);
		}
		
	}
	@Override
	public Optional<Referable> getModelElement(String identifier, Reference element) {
		return shell.getElement(element);
	}


}
