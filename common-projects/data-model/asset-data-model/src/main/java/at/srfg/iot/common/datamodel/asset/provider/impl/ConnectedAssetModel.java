package at.srfg.iot.common.datamodel.asset.provider.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Key;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.DataElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Operation;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.OperationVariable;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;
import at.srfg.iot.common.datamodel.asset.api.IAssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.connectivity.IAssetConnection;
import at.srfg.iot.common.datamodel.asset.connectivity.rest.ConsumerFactory;
import at.srfg.iot.common.datamodel.asset.api.ISubmodel;
import at.srfg.iot.common.datamodel.asset.api.ISubmodelElement;
import at.srfg.iot.common.datamodel.asset.provider.IAssetModelListener;
import at.srfg.iot.common.datamodel.asset.provider.IAssetProvider;
@Deprecated
public class ConnectedAssetModel implements IAssetProvider {
	
	private final Identifiable root;
	
	private final String endpoint;
	
	public ConnectedAssetModel(Identifiable root, String endpoint) {
		this.root = root;
		this.endpoint = endpoint;
		
		
	}
	private IAssetConnection connection;
	private IAssetConnection getConnection() {
		if ( connection == null ) {
			connection = ConsumerFactory.createConsumer(endpoint, IAssetConnection.class);
		}
		return connection;
	}
	@Override
	public IAssetAdministrationShell getShell() {
		if ( root instanceof IAssetAdministrationShell ) {
			return (IAssetAdministrationShell)root;
		}
		throw new IllegalStateException("Current model does not have an AAS");
	}

	@Override
	public Identifiable getRoot() {
		return this.root;
	}
	private Optional<SubmodelElementContainer> resolveContainer(SubmodelElementContainer elementContainer, String path) {
		SubmodelElementContainer current = elementContainer;
		Iterator<String> pathIterator = resolvePathIterator(path);
		while (pathIterator.hasNext()) {
			String idShort = pathIterator.next();
			// 
			Optional<SubmodelElementContainer> child = current.getChildElement(idShort, SubmodelElementContainer.class);
			if ( child.isPresent()) {
				current = child.get();
				// do we have the final element
			}
			else {
				return Optional.empty();
			}
		}
		return Optional.ofNullable(current);
	}
	/**
	 * Resolve a {@link ISubmodelElement}
	 * @param sub The container of the requested {@link ISubmodelElement}
	 * @param path The path to the {@link ISubmodelElement}, starting with 
	 * @return
	 */
	private Optional<ISubmodelElement> resolvePath(SubmodelElementContainer sub, String path) {
		if ( path.startsWith("/")) {
			// remove a beginnig slash
			path = path.substring(1);
		}
		if ( path.endsWith("/")) {
			// remove a trailing slash
			path = path.substring(0, path.length());
		}
		String[] parts = path.split("/");
		Iterator<String> pathIterator = Arrays.asList(parts).iterator();
		SubmodelElementContainer current = sub;
		ISubmodelElement submodelElement = null;
		while (pathIterator.hasNext()) {
			String idShort = pathIterator.next();
			// 
			Optional<ISubmodelElement> child = current.getChildElement(idShort, ISubmodelElement.class);
			if ( child.isPresent()) {
				submodelElement = child.get();
				// do we have the final element
				if ( pathIterator.hasNext()) {
					ISubmodelElement cont = child.get();
					if ( SubmodelElementContainer.class.isInstance(cont)) {
						current = SubmodelElementContainer.class.cast(cont);
					}
				}
			}
			else {
				return Optional.empty();
			}
			
		}
		return Optional.ofNullable(submodelElement);
		
	}
	/**
	 * Resolve the element referenced with the given path. 
	 * 
	 * @param path the list of idShort identifiers concatenated with slash
	 * @return
	 */
	private Optional<Referable> resolveReference(String path) {
		if ( path == null) {
			path = "";
		}
		// resolve the path ...
		String[] parts = checkPath(path);
		Referable current = root;
		Iterator<String> pathIterator = Arrays.asList(parts).iterator();
		while (pathIterator.hasNext()) {
			String idShort = pathIterator.next();
			// 
			Optional<Referable> child = current.getChildElement(idShort);
			if ( child.isPresent()) {
				// 
				// check for reference ...
				if ( child.get() instanceof Reference) {
					// resolve the reference
					Reference refToChild = (Reference) child.get();
					
					child = getConnection().getModelElement(getRoot().getId(), refToChild);
					if ( child.isPresent()) {
						// need to replace the resolved Reference in the current model
						current.removeChildElement(refToChild);
						// add the resolved element
						current.addChildElement(child.get());
					}
				}
				current = child.get();
			}
			else {
				return Optional.empty();
			}
			
		}
		
		return Optional.of(current);
	}
	private String[] checkPath(String path) {
		// remove leading and trailing slashes
		try {
			path = URLDecoder.decode(path,"UTF-8");
			while( path.startsWith("/")) {
				path = path.substring(1);
			}
			while (path.endsWith("/")) {
				path = path.substring(0, path.length()-1);
			}
			while (path.contains("//") ) {
				path = path.replaceAll("//", "/");
			}
			return path.split("/");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return path.split("/");
		}
	}

	private Iterator<String> resolvePathIterator(String path) {
		if ( path.startsWith("/")) {
			// remove a beginnig slash
			path = path.substring(1);
		}
		if ( path.endsWith("/")) {
			// remove a trailing slash
			path = path.substring(0, path.length()-1);
		}
		String[] parts = path.split("/");
		return Arrays.asList(parts).iterator();
	}
	/**
	 * Helper function for resolving a provided reference
	 * @param ref
	 * @return
	 */
	private Optional<Referable> resolveReference(Reference ref) {
		// check first key - musst be identical with root
		if (ref.hasRoot(root)) {
			Referable current = root;
			
			Iterator<Key> pathIterator = ref.getPathIterator();
			
			while (pathIterator.hasNext()) {
				Key next = pathIterator.next();
				Optional<Referable> child = current.getChildElement(next.getValue());
				if ( child.isPresent()) {
					current = child.get();
				}
				else {
					// referenced element not present
					return Optional.empty();
				}
			}
			return Optional.of(current);
		}
		throw new IllegalArgumentException("Reference points to a different model!");
	}
	private <T extends ReferableElement> Optional<T> resolveReference(Reference ref, Class<T> elementClass) {
		Optional<? extends Referable> result = resolveReference(ref);
		if ( result.isPresent() && elementClass.isInstance(result.get())) {
			return Optional.of(elementClass.cast(result.get()));
		}
		return Optional.empty();
			
	}
	@Override
	public <T extends Referable> Optional<T> getElement(String path, Class<T> clazz) {
		Optional<Referable> elem = resolveReference(path);
		if (elem.isPresent()) {
			if ( clazz.isInstance(elem.get())) {
				return Optional.of(clazz.cast(elem.get()));
			}
		}
		return Optional.empty();
	}
	@Override
	public Optional<Referable> getElement(String path) {
		return resolveReference(path);
	}
	@Override
	public Optional<Referable> getElement(Reference reference) {
		return resolveReference(reference);
	}

	@Override
	public Referable setElement(Reference parent, Referable element) {
		Optional<Referable> elem = resolveReference(parent);
		if ( elem.isPresent()) {
			Referable myParent = elem.get();
			myParent.addChildElement(element);
			return myParent;
		}
		return null;
	}

//	@Override
//	public boolean deleteElement(Reference reference) {
//		Optional<Referable> elem = resolveReference(reference);
//		if ( elem.isPresent()) {
//			
//			Referable referable = elem.get().getParentElement();
//			// remove it from the model
//			return referable.removeChildElement(elem.get());
//			
//		}
//		return false;
//	}
	public boolean deleteElement(Referable referable) {
		
		if ( referable instanceof Reference ) {
			Optional<Referable> toDelete = resolveReference(Reference.class.cast(referable));
			if (toDelete.isPresent()) {
				return toDelete.get().getParentElement().removeChildElement(toDelete.get());
			}
			return false;
		}
		else {
			Optional<Referable> parent = resolveReference(referable.getParent());
			if ( parent.isPresent()) {
				Optional<Referable> toDelete = parent.get().getChildElement(referable.getIdShort());
				if ( toDelete.isPresent()) {
					return parent.get().removeChildElement(toDelete.get());
				}
				return false;
			}
		}
		return deleteElement(referable.asReference());
	}
	@Override
	public Map<String,Object> execute(Reference reference, Map<String, Object> parameter) {
		Optional<Operation> optOperation = resolveReference(reference, Operation.class);
		if ( optOperation.isPresent() ) {
			Operation operation = optOperation.get();
			// check input variable
			for (OperationVariable vIn : operation.getIn() ) {
				if (! parameter.containsKey(vIn.getIdShort())) {
					// throw MissingParameter Exception
				}
			}
			for (OperationVariable vOut : operation.getOut()) {
				// 
			}
			
		}
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getElementValue(Reference reference) {
		@SuppressWarnings("rawtypes")
		Optional<DataElement> dataElem = resolveReference(reference, DataElement.class);
		if ( dataElem.isPresent()) {
			return dataElem.get().getValue();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Referable setElementValue(Reference element, Object value) {
		@SuppressWarnings("rawtypes")
		Optional<DataElement> dataElem = resolveReference(element, DataElement.class);
		if ( dataElem.isPresent()) {
			// TODO: check for type safety
			dataElem.get().setValue(value);
			return dataElem.get();
		}
		return null;
	}

	@Override
	public Optional<ISubmodel> getSubmodel(String idShort) {
		return getShell().getSubmodel(idShort);
	}
	public void setSubmodel(ISubmodel submodel) {
		getShell().addSubmodel(submodel);
	}
	@Override
	public <T extends Referable> Optional<T> getElement(String submodelIdShort, String path, Class<T> clazz) {
		Optional<ISubmodel> sub = getElement(submodelIdShort, ISubmodel.class);
		if ( sub.isPresent()) {
			Optional<ISubmodelElement> elem = resolvePath(sub.get(), path);
			if ( elem.isPresent() && clazz.isInstance(elem.get())) {
				return Optional.of(clazz.cast(elem.get()));
			}
				
		}
		return Optional.empty();
	}
	@Override
	public Referable setElement(String submodelIdentifier, String path, Referable element) {
		Optional<ISubmodel> model = root.getChildElement(submodelIdentifier, ISubmodel.class);
		if ( model.isPresent() ) {
			Optional<SubmodelElementContainer> parent = resolveContainer(model.get(), path);
			if ( parent.isPresent()) {
				parent.get().addChildElement(element);
				return element;
			}
		}
		return null;
	}
	@Override
	public void setValueConsumer(String pathToProperty, Consumer<String> consumer) {
		Optional<Property>  property = getElement(pathToProperty, Property.class);
		if ( property.isPresent()) {
			property.get().setSetter(consumer);
		}
		
	}
	@Override
	public void setValueSupplier(String pathToProperty, Supplier<String> supplier) {
		Optional<Property>  property = getElement(pathToProperty, Property.class);
		if ( property.isPresent()) {
			property.get().setGetter(supplier);
		}
		
	}
	@Override
	public void setFunction(String pathToOperation, Function<Map<String, Object>, Object> function) {
		Optional<Operation> operation = getElement(pathToOperation, Operation.class);
		if ( operation.isPresent()) {
			// assign the function
			operation.get().setFunction(function);
		}
		
	}
	@Override
	public void addModelListener(IAssetModelListener listener) {
		// TODO Auto-generated method stub
		
	}

}
