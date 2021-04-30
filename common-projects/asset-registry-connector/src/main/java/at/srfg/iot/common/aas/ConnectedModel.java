package at.srfg.iot.common.aas;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import at.srfg.iot.common.datamodel.asset.aas.basic.Endpoint;
import at.srfg.iot.common.datamodel.asset.aas.common.DirectoryEntry;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;
import at.srfg.iot.common.datamodel.asset.api.IAssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.api.ISubmodel;
import at.srfg.iot.common.datamodel.asset.connectivity.IAssetConnection;
import at.srfg.iot.common.datamodel.asset.connectivity.rest.ConsumerFactory;

public class ConnectedModel implements IAssetModel {
	private final IAssetConnection connection;
	private final Identifiable root;
	public ConnectedModel(DirectoryEntry entry) {
		String endpoint = getEndpoint(entry);
		connection =ConsumerFactory.createConsumer(endpoint, IAssetConnection.class);
		Optional<Identifiable> aasRoot = connection.getRoot(entry.getId());
		if ( aasRoot.isPresent() ) {
			root = aasRoot.get();
		}
		else {
			throw new RuntimeException("Endpoint currently unavailable");
		}

	}
	private String getEndpoint(DirectoryEntry directory) {
		Optional<Endpoint> ep = directory.getFirstEndpoint();
		if ( ep.isPresent()) {
			return ep.get().getAddress();
		}
		//
		throw new UnsupportedOperationException("No endpoint specified");
	}

	@Override
	public IAssetAdministrationShell getShell() {
		if ( IAssetAdministrationShell.class.isInstance(root)) {
			return IAssetAdministrationShell.class.cast(root);
		}
		return null;
	}

	@Override
	public Identifiable getRoot() {
		return root;
	}

	@Override
	public Optional<ISubmodel> getSubmodel(String idShort) {
		return getElement(idShort, ISubmodel.class);
	}

	@Override
	public void setSubmodel(ISubmodel submodel) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public Optional<Referable> getElement(Reference reference) {
		return connection.getModelElement(root.getId(), reference);
	}

	@Override
	public Object getElementValue(Reference reference) {
		return connection.getValue(root.getId(), reference.getPath());
	}

	@Override
	public Referable setElementValue(Reference element, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Referable setElement(Reference parent, Referable element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Referable setElement(String submodelIdentifier, String path, Referable element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean deleteElement(Referable referable) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> execute(Reference reference, Map<String, Object> parameter) {
		return connection.invokeOperation(root.getId(), reference.getPath(), parameter);
	}
	@Override
	public Map<String, Object> execute(String path, Map<String, Object> parameter) {
		return connection.invokeOperation(root.getId(), path, parameter);
	}

	@Override
	public Optional<Referable> getElement(String path) {
		return connection.getElement(root.getId(), path);
	}

	@Override
	public <T extends Referable> Optional<T> getElement(String path, Class<T> clazz) {
		// TODO Auto-generated method stub
		Optional<Referable> elem = connection.getElement(root.getId(), path);
		if ( elem.isPresent() &&  clazz.isInstance(elem.get())) {
			return Optional.of(clazz.cast(elem.get()));
		}
		return Optional.empty();
	}


	@Override
	public void setValueConsumer(String pathToProperty, Consumer<String> consumer) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void setValueSupplier(String pathToProperty, Supplier<String> supplier) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void setFunction(String pathToOperation, Function<Map<String, Object>, Object> function) {
		throw new UnsupportedOperationException();		
	}
	@Override
	public Object getElementValue(String path) {
		return connection.getValue(root.getId(), path);
	}

}
