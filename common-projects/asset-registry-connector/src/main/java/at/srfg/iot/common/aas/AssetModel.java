package at.srfg.iot.common.aas;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Key;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Path;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.DataElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Operation;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.OperationVariable;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;
import at.srfg.iot.common.datamodel.asset.api.IAssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.api.ISubmodel;
import at.srfg.iot.common.datamodel.asset.api.ISubmodelElement;
import at.srfg.iot.common.kafka.event.EventProcessor;
import at.srfg.iot.common.registryconnector.impl.AssetRegistry;
/**
 * In Memory Model representing either an {@link AssetAdministrationShell} or a {@link Submodel}.
 * 
 * @author dglachs
 *
 */
public class AssetModel implements IAssetModel {
	
	private final Identifiable root;
	private final AssetRegistry registry;
	private boolean eventProcessingActive = false;
	
	private Map<EventElement, EventProcessor> eventProcessor = new HashMap<EventElement, EventProcessor>();

	
	public AssetModel(AssetRegistry registry, Identifiable root) {
		this.root = root;
		this.registry = registry;
		// 
		handleCreation(root);
	}
	public Identifiable getRoot() {
		return this.root;
	}
	public IAssetAdministrationShell getShell() {
		if ( root instanceof IAssetAdministrationShell ) {
			return (IAssetAdministrationShell)root;
		}
		throw new IllegalStateException("Current model does not have an AAS");
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
		// TODO: 
		String[] parts = checkPath(path);
		Referable current = root;
		Iterator<String> pathIterator = Arrays.asList(parts).iterator();
		while (pathIterator.hasNext()) {
			String idShort = pathIterator.next();
			// 
			Optional<Referable> child = current.getChildElement(idShort);
			if ( child.isPresent()) {
				current = child.get();
				// check for reference ...
				if ( current instanceof Reference) {
					// resolve the reference
					
				}
			}
			else {
				return Optional.empty();
			}
		}
		return Optional.of(current);
	}
	private  <T extends ReferableElement> Optional<T> resolveReference(String path, Class<T> clazz) {
		Optional<Referable> referable = resolveReference(path);
		if ( referable.isPresent() && clazz.isInstance(referable.get())) {
			return Optional.of(clazz.cast(referable.get()));
		}
		return Optional.empty();
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
		Optional<Referable> parentReferable = resolveReference(parent);
		if ( parentReferable.isPresent()) {
			return setElement(parentReferable.get(), element);
//			Referable myParent = elem.get();
//			Optional<Referable> elemExist = myParent.getChildElement(element.getIdShort());
//			if ( elemExist.isPresent()) {
//				handleDeletion(elemExist.get());
//				myParent.removeChildElement(elemExist.get());
//			}
//			myParent.addChildElement(element);
//			handleCreation(element);
//			// 
//			return myParent;
		}
		return null;
	}
	/**
	 * Add the provided element as a child element
	 * @param parent
	 * @param element
	 * @return
	 */
	private Referable setElement(Referable parent, Referable element) {
		Optional<Referable> elemExist = parent.getChildElement(element.getIdShort());
		if ( elemExist.isPresent()) {
			if ( ! Reference.class.isInstance(elemExist.get())) {
				handleDeletion(elemExist.get());
			}
			parent.removeChildElement(elemExist.get());
		}
		parent.addChildElement(element);
		//
		handleCreation(element);
		// 
		return element;

	}
	public boolean deleteElement(Referable referable) {
		
		if ( referable instanceof Reference ) {
			Optional<Referable> toDelete = resolveReference(Reference.class.cast(referable));
			if (toDelete.isPresent()) {
				// notify listeners, that the element will be deleted!
				handleDeletion(toDelete.get());
				// remove the element
				return toDelete.get().getParentElement().removeChildElement(toDelete.get());
			}
			return false;
		}
		else {
			Optional<Referable> parent = resolveReference(referable.getParent());
			if ( parent.isPresent()) {
				Optional<Referable> toDelete = parent.get().getChildElement(referable.getIdShort());
				if ( toDelete.isPresent()) {
					// notify listeners, that the element will be deleted!
					handleDeletion(toDelete.get());
					// remove the element
					return parent.get().removeChildElement(toDelete.get());
				}
				return false;
			}
		}
		return deleteElement(referable.asReference());
	}
	@Override
	public Map<String,Object> execute(String path, Map<String,Object> parameter) {
		Optional<Operation> optOperation = resolveReference(path, Operation.class);
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
			return operation.invoke(parameter);
		}
		// TODO Auto-generated method stub
		return null;
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
			Object result = operation.invoke(parameter);
			return null;
		}
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getElementValue(String path) {
		Optional<Referable> referable = resolveReference(path);
		if ( referable.isPresent()) {
			Referable ref = referable.get();
			if ( SubmodelElementContainer.class.isInstance(ref)) {
				return SubmodelElementContainer.class.cast(ref).getValue();
			}
			if ( DataElement.class.isInstance(ref)) {
				return DataElement.class.cast(ref).getValue();
			}
		}
//		Optional<DataElement> dataElem = resolveReference(reference, DataElement.class);
//		if ( dataElem.isPresent()) {
//			return dataElem.get().getValue();
//		}
		return null;

	}
	@Override
	public Object getElementValue(Reference reference) {
		Optional<Referable> referable = resolveReference(reference);
		if ( referable.isPresent()) {
			Referable ref = referable.get();
			if ( SubmodelElementContainer.class.isInstance(ref)) {
				return SubmodelElementContainer.class.cast(ref).getValue();
			}
			if ( DataElement.class.isInstance(ref)) {
				return DataElement.class.cast(ref).getValue();
			}
		}
//		Optional<DataElement> dataElem = resolveReference(reference, DataElement.class);
//		if ( dataElem.isPresent()) {
//			return dataElem.get().getValue();
//		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Referable setElementValue(Reference element, Object value) {
		@SuppressWarnings("rawtypes")
		Optional<DataElement> dataElem = resolveReference(element, DataElement.class);
		if ( dataElem.isPresent()) {
			// notify listeners that the value is about to cg
			handleValueChange(dataElem.get(), value);
			dataElem.get().setValue(value);
			return dataElem.get();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Referable setElementValue(String path, Object value) {
		@SuppressWarnings("rawtypes")
		Optional<DataElement> dataElem = resolveReference(path, DataElement.class);
		if ( dataElem.isPresent()) {
			// notify listeners that the value is about to cg
			handleValueChange(dataElem.get(), value);
			dataElem.get().setValue(value);
			return dataElem.get();

		}
		return null;
	}


	@Override
	public Referable setElement(String path, Referable element) {
		Path thePath = new Path(path);
		Optional<ISubmodel> model = root.getChildElement(thePath.getFirst(), ISubmodel.class);
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
			property.get().setValueConsumer(consumer);
		}
		
	}
	@Override
	public void setValueSupplier(String pathToProperty, Supplier<String> supplier) {
		Optional<Property>  property = getElement(pathToProperty, Property.class);
		if ( property.isPresent()) {
			property.get().setValueSupplier(supplier);
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
	
	/**
	 * Helper method for traversing the tree hierarchy, searching for {@link Property}, {@link Operation}
	 * and {@link EventElement} elements and notifies the registered {@link IAssetModelListener}s
	 * of the respective deletion
	 * @param element
	 */
	private void handleDeletion(Referable element) {
		switch(element.getModelType()) {
		case Property:
			registry.accept(new Consumer<IAssetModelListener>() {

				@Override
				public void accept(IAssetModelListener t) {
					t.onPropertyRemove(element.asReference().getPath(),Property.class.cast(element));
				}
			});
			break;
		case Operation:
			registry.accept(new Consumer<IAssetModelListener>() {

				@Override
				public void accept(IAssetModelListener t) {
					t.onOperationRemove(element.asReference().getPath(),Operation.class.cast(element));
				}
			});
			break;
		case EventElement:
			// notify listeners
			registry.accept(new Consumer<IAssetModelListener>() {

				@Override
				public void accept(IAssetModelListener t) {
					t.onEventElementRemove(element.asReference().getPath(),EventElement.class.cast(element));
				}
			});
			// 
			removeEventElement(EventElement.class.cast(element));
			break;
		case AssetAdministrationShell:
		case Submodel:
		case SubmodelElementCollection:
			element.getChildren().forEach(new Consumer<Referable>() {
				// recursively process all child elements, thus search for
				// Property, EventElement and Operation
				@Override
				public void accept(Referable t) {
					handleDeletion(t);
					
				}
			});
		default:
			break;
		}
	}
	private void handleCreation(Referable element) {
		switch(element.getModelType()) {
//		case Reference:
//			Optional<Referable> ref = registry.resolveInstance(root.getId(), Reference.class.cast(element));
//			if ( ref.isPresent() ) {
//				
//			}
//			break;
		case Property:
			registry.accept(new Consumer<IAssetModelListener>() {

				@Override
				public void accept(IAssetModelListener t) {
					t.onPropertyCreate(element.asReference().getPath(),Property.class.cast(element));
				}
			});
			break;
		case Operation:
			registry.accept(new Consumer<IAssetModelListener>() {

				@Override
				public void accept(IAssetModelListener t) {
					t.onOperationCreate(element.asReference().getPath(),Operation.class.cast(element));
				}
			});
			break;
		case EventElement:
			// notify listeners that a new evetn element is to be created
			registry.accept(new Consumer<IAssetModelListener>() {

				@Override
				public void accept(IAssetModelListener t) {
					// 
					t.onEventElementCreate(element.asReference().getPath(),EventElement.class.cast(element));
				}
			});
			// register the event element with the current registry
			registerEventElement(EventElement.class.cast(element));
			break;
		case AssetAdministrationShell:
		case Submodel:
		case SubmodelElementCollection:
			element.getChildren().forEach(new Consumer<Referable>() {
				// recursively process all child elements, thus search for
				// Property, EventElement and Operation
				@Override
				public void accept(Referable t) {
					handleCreation(t);
					
				}
			});
		default:
			break;
		}
	}
	private void handleValueChange(DataElement<?> element, Object value) {
		registry.accept(new Consumer<IAssetModelListener>() {

			@Override
			public void accept(IAssetModelListener t) {
				t.onValueChange(element,element.getValue().toString(), value);
			}
		});
		
	}
	private void removeEventElement(EventElement eventElement) {
		if ( eventProcessor.containsKey(eventElement)) {
			EventProcessor proc = eventProcessor.get(eventElement);
			// stop processing
			proc.stop();
		}
	}
	private void registerEventElement(EventElement eventElement) {
		// if element already present
		removeEventElement(eventElement);
		// create a new processor
		EventProcessor processor = new EventProcessor(eventElement, registry);
		if ( processor.isInitialized()) {
			eventProcessor.put(eventElement, processor);
			if ( eventProcessingActive ) {
				// 
				processor.start();
			}
		}
	}
	@Override
	public void startEventProcessing() {
		eventProcessingActive = true;
		for (EventProcessor proc : eventProcessor.values()) {
			proc.start();
		}
		
	}
	@Override
	public void stopEventProcessing() {
		eventProcessingActive = false;
		for (EventProcessor proc : eventProcessor.values()) {
			proc.stop();
		}
		
	}
	public boolean isEventProcessingActive() {
		return eventProcessingActive;
	}


}
