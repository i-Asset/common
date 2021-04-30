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

import at.srfg.iot.common.aas.IAssetModel;
import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.AssetAdministrationShellDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.DataElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Operation;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;
import at.srfg.iot.common.datamodel.asset.api.ISubmodelElement;
import at.srfg.iot.common.datamodel.asset.connectivity.IAssetConnection;

@Path("")
public class AssetRestController implements IAssetConnection {
	@Context
	private SecurityContext securityContext;
	/**
	 * Injected IAssetProvider
	 */
	@Inject
	@org.jvnet.hk2.annotations.Optional
	private IAssetModel shell;
	
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
			// TODO: check for allowDuplicates
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
	public Map<String,Object> invokeOperation(String identifier, String path, Map<String, Object> parameterMap) {
		Optional<Operation> operation = shell.getElement(path,  Operation.class);
		if ( operation.isPresent()) {
			return operation.get().invoke(parameterMap);
		}
		return null;
	}
	@Override
	public Object getValue(String identifier, String path) {
		Optional<Referable> referable = shell.getElement(path);
		if ( referable.isPresent()) {
			Referable ref = referable.get();
			if ( SubmodelElementContainer.class.isInstance(ref)) {
				return SubmodelElementContainer.class.cast(ref).getValue();
			}
			if ( DataElement.class.isInstance(ref)) {
				return DataElement.class.cast(ref).getValue();
			}
		}
		return null;
	}
	@Override
	public void setValue(String identifier, String path, Object value) {
		Optional<Property> opt = shell.getElement(path, Property.class);
		if (opt.isPresent()) {
			
			shell.setElementValue(opt.get().asReference(), value);
			opt.get().setValue(value.toString());
		}
		
	}
	@Override
	public Optional<Referable> getModelElement(String identifier, Reference element) {
		return shell.getElement(element);
	}
	@Override
	public Optional<Referable> getModelInstance(String identifier,Reference element) {
		return shell.getElement(element);
	}


}
