package at.srfg.iot.common.registryconnector.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import at.srfg.iot.common.aas.AssetModel;
import at.srfg.iot.common.aas.ConnectedModel;
import at.srfg.iot.common.aas.IAssetModel;
import at.srfg.iot.common.aas.IAssetModelListener;
import at.srfg.iot.common.datamodel.asset.aas.basic.Asset;
import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.AssetAdministrationShellDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.SubmodelDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.common.HasKind;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.DataElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Operation;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;
import at.srfg.iot.common.datamodel.asset.connectivity.IAssetConnection;
import at.srfg.iot.common.datamodel.asset.connectivity.move.IAssetDirectory;
import at.srfg.iot.common.datamodel.asset.connectivity.rest.ConsumerFactory;
import at.srfg.iot.common.registryconnector.AssetComponent;
import at.srfg.iot.common.registryconnector.IAssetMessaging;
import at.srfg.iot.common.registryconnector.IAssetRegistry;

/**
 * Component for the interaction with the registry
 * 
 * @author dglachs
 *
 */
public class AssetRegistry implements IAssetRegistry {
	/**
	 * Service Proxy to the i-Asset Directory Service for registration and lookup
	 * 
	 */
	final IAssetDirectory directory;
	/**
	 * Service Proxy to the i-Asset Repository Service interacting with AAS
	 */
	final IAssetConnection repository;
	/**
	 * Service Proxy for the distribution network
	 */
	 IAssetMessaging distribution;
	/**
	 * Map holding the
	 */
	private Map<Identifier, IAssetModel> modelProvider = new HashMap<Identifier, IAssetModel>();
	
	/**
	 * Registry ModelListener
	 */
	final List<IAssetModelListener> modelListener = new ArrayList<IAssetModelListener>();

	private IAssetModelListener registryListener = new IAssetModelListener() {

		@Override
		public void onValueChange(DataElement<?> element, Object oldValue, Object newValue) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPropertyRemove(String path, Property property) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPropertyCreate(String path, Property property) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onOperationRemove(String path, Operation element) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onOperationCreate(String path, Operation element) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEventElementRemove(String path, EventElement element) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEventElementCreate(String assetIdentifier, EventElement element) {
			//

		}
	};
	/**
	 * URI of the iAsset Directory
	 */
	final String directoryUrl;
	
	public IAssetConnection getRepoConn() {
		return repository;
	}
//	private I40Component server;

//	private AssetComponent component;

//	@Override
//	public void start(int port) {
//
//		if (component == null) {
//			component = new I40Component(port, "/");
//		}
//		for (String alias : modelProvider.keySet()) {
//			serve(modelProvider.get(alias), alias);
//			// the modelprovider now has a 
//			register(modelProvider.get(alias));
//		}
//		component.start();
//
//	}
//
//	public void stop() {
//		if (component != null) {
//			component.stop();
//		}
//	}

//	public void addModel(String alias, IAssetModel model) {
//		modelProvider.put(alias, model);
//	}

//	public void serve(IAssetModel provider, String alias) {
//		if (component != null && component.isStarted()) {
//			//
//			component.stop();
//			component = null;
//			//
//
//		}
//		if (component == null) {
//			component = new I40Component(5000, "/");
//		}
//		component.serve(provider, alias);
//	}

	public AssetRegistry(String directoryUrl) {
		// create the proxy with the provided url
		this.directoryUrl = directoryUrl;
		// obtain the directory service to work with the directory
		directory = ConsumerFactory.createConsumer(directoryUrl, IAssetDirectory.class);
		repository = ConsumerFactory.createConsumer(directoryUrl + "/repository", IAssetConnection.class);
		// register the "internal" model-listener (maybe obsolete!!)
		modelListener.add(registryListener);
	}

	@Override
	public IAssetModel create(String alias, Identifier identifier) {
		Optional<Identifiable> root = repository.getRoot(identifier.getId());
		if (root.isPresent()) {
			Identifiable rootElement = root.get();
			return create(alias, rootElement);
		}
		throw new RuntimeException("Model creation failed!");

	}

	public Optional<Referable> resolve(String identifier, Reference reference) {
		return repository.getModelElement(identifier, reference);
	}
	public IAssetModel connect(String id) {
		return connect(new Identifier(id));
	}
	public IAssetModel connect(Identifier identifier) {
		Optional<AssetAdministrationShellDescriptor> descriptor = directory.lookup(identifier.getId());
		if ( descriptor.isPresent() ) {
			// create the (Connected)AssetModel
			return new ConnectedModel(descriptor.get());
			
		}
		return null;
	}

	@Override
	public IAssetModel create(String alias, Identifier identifier, Identifier type) {
		Optional<Identifiable> root = repository.getRoot(identifier.getId());
		if (root.isPresent()) {
			Identifiable rootElement = root.get();
			return create(alias, rootElement);
		} else {
			Optional<Identifiable> typeRoot = repository.getRoot(type.getId());
			if (typeRoot.isPresent()) {
				Identifiable typeElement = typeRoot.get();
				AssetAdministrationShell shell = new AssetAdministrationShell(identifier);
				shell.setDerivedFrom(typeElement.asReference());
				shell.setAsset(new Asset());
				shell.getAsset().setKind(Kind.Instance);
				shell.setDescription(typeElement.getDescription());
				AssetModel instanceModel = new AssetModel(this, shell);
				for (Referable child : typeElement.getChildren()) {
					// need to instantiate each element
					if (Reference.class.isInstance(child)) {

						Optional<Referable> full = repository.getModelElement(type.getId(),
																Reference.class.cast(child));

						if (full.isPresent()) {
							child = full.get();
							if (HasKind.class.isInstance(full.get())) {
								HasKind kind = HasKind.class.cast(full.get());
								//
								full = kind.asInstance(shell);
								if (full.isPresent()) {
									// add the instantiated element
									instanceModel.setElement(shell.asReference(), full.get());
								}
							}
						}
					} else {
						// add the element to the model
						instanceModel.setElement(shell.asReference(), child);
					}

				}
				// keep the model
				modelProvider.put(identifier, instanceModel);
				//
				return instanceModel;
			}

		}
		return null;
	}

	@Override
	public IAssetModel create(String alias, Identifiable rootElement) {
		Optional<Identifiable> root = repository.getRoot(rootElement.getIdentification().getId());
		// the element is not yet in the registry
		if (!root.isPresent()) {
			//
			//
		}
		AssetModel rootModel = new AssetModel(this, rootElement);
		// process Submodel References and resolve them from the server
		for (Reference childRef : rootElement.getChildElements(Reference.class)) {
			Optional<Referable> child = repository.getModelElement(rootElement.getIdentification().getId(), childRef);
			if (child.isPresent()) {
				// add the model
				rootModel.setElement(rootElement.asReference(), child.get());
			}
		}
		// 
		// store the model's endpoint with the registry 
		// handle the activation of the model
		modelProvider.put(rootElement.getIdentification(), rootModel);
//		// when I4.0 component has been started already, then add the model
//		if (component != null && component.isStarted()) {
//			component.serve(rootModel, alias);
//		}
//		register(rootModel);
		return rootModel;
	}

	@Override
	public void delete(IAssetModel provider) {
		// TODO Auto-generated method stub

	}

	/**
	 * Obtain a new AssetInstance from a provided type
	 * 
	 * @param identifier
	 * @return
	 */
	public IAssetModel fromType(Identifier instanceId, Identifier identifier) {
		//
		Optional<Identifiable> root = repository.getRoot(identifier.getId());
		if (root.isPresent()) {
			Identifiable type = root.get();
			switch (root.get().getModelType()) {
			case AssetAdministrationShell:
				AssetAdministrationShell shell = new AssetAdministrationShell(instanceId);
				shell.setDerivedFrom(type.asReference());
				shell.setAsset(new Asset());
				shell.getAsset().setKind(Kind.Instance);
				shell.setDescription(type.getDescription());
				AssetModel instanceModel = new AssetModel(this, shell);

				for (Referable ref : type.getChildren()) {
					// ref might be a reference
					switch (ref.getModelType()) {
					case Reference:
						Reference refToSub = Reference.class.cast(ref);
						if (refToSub.hasRoot(type)) {
							//
							Optional<Referable> full = repository.getModelElement(identifier.getId(),
									Reference.class.cast(ref));
							if (full.isPresent()) {

								if (HasKind.class.isInstance(full.get())) {
									HasKind kind = HasKind.class.cast(full.get());
									//
//									kind.accept(new HasKindVisitorImpl(instanceModel));
									//
									full = kind.asInstance(shell);
									if (full.isPresent()) {
										//
										instanceModel.setElement(shell.asReference(), full.get());
										// sh
									}
								} else {
									shell.addChildElement(full.get());
								}
							}
						} else {
							Optional<Referable> full = repository.getModelElement(refToSub.getFirstIdentifier().getId(),
									refToSub);
							if (full.isPresent()) {
								Referable fullType = full.get();
								if (HasKind.class.isInstance(fullType)) {
									HasKind kind = HasKind.class.cast(fullType);
									full = kind.asInstance(shell);
									if (full.isPresent()) {
										// sh
									}
								} else {
									shell.addChildElement(full.get());
								}
							}

						}
						break;
					case Submodel:
						//
						instanceModel.setElement(shell.asReference(), Submodel.class.cast(ref));
//						shell.addSubmodel(Submodel.class.cast(ref));
						break;
					}
				}
//				for (Submodel sub : type.getChildElements(Submodel.class)) {
//					//
//					repository.getModelElement(identifier.getId(), element)
//				}
				return instanceModel;
			case Submodel:
				Submodel submodel = new Submodel();
				submodel.setSemanticId(type.asReference());
				submodel.setDescription(type.getDescription());

			default:
			}

		}

		Optional<AssetAdministrationShellDescriptor> aasDescriptor = directory.lookup(identifier.getId());
		if (aasDescriptor.isPresent()) {
			AssetAdministrationShellDescriptor descriptor = aasDescriptor.get();
			IAssetConnection repoConn = ConsumerFactory.createConsumer(directoryUrl + "/repository",
					IAssetConnection.class);
			AssetAdministrationShell shell = new AssetAdministrationShell();
			// copy from Descriptor
			shell.setDerivedFrom(descriptor.asReference());
			shell.setAsset(descriptor.getAsset());
			shell.setDescription(descriptor.getDescription());

			//

			for (SubmodelDescriptor desc : descriptor.getSubmodels()) {
				// deal with endpoints
			}
			AssetModel model = new AssetModel(this, shell);

		}
		return null;
	}
	public void register(IAssetModel provider, boolean complete) {
		Identifiable root = provider.getRoot();
		if (AssetAdministrationShell.class.isInstance(root)) {
			//
			AssetAdministrationShell shell = AssetAdministrationShell.class.cast(root);
			// create the AAS Descriptor (including all SubmodelDescriptors)
			AssetAdministrationShellDescriptor descriptor = new AssetAdministrationShellDescriptor(shell);
			if (descriptor.getEndpoints().isEmpty()) {

			}
			// create the descriptor entries for shell and contained submodels
			Optional<AssetAdministrationShell> registered = directory.register(descriptor);
			// create / update all elements
			if (registered.isPresent() && complete) {
				for (Referable referable : root.getChildren()) {
					repository.setModelElement(root.getIdentification().getId(), referable);
				}
			}

		}
	}
	/**
	 * Register a new Asset with the directory service. This will
	 * <ul>
	 * <li>create/update the root element {@link AssetAdministrationShell} or {@link Submodel} to the registry
	 * <li>store the endpoint with the root element, e.g. tell the registry the model's networking endpoint
	 * </ul>
	 * For updating the entire model, use {@link #save(IAssetModel)}
	 * @param descriptor
	 */	
	public void register(IAssetModel provider) {
		register(provider, true);
	}
	/**
	 * Store all child elements with the registry
	 */
	public void save(IAssetModel provider) {
		Identifiable root = provider.getRoot();
		Optional<Identifiable> storedRoot = repository.getRoot(root.getId());
		if ( storedRoot.isPresent()) {
			for (Referable referable : root.getChildren()) {
				repository.setModelElement(root.getIdentification().getId(), referable);
			}
		}
	}

	@Override
	public void register(Identifier aasIdentifier, SubmodelDescriptor descriptor) {
		directory.register(aasIdentifier.getId(), descriptor);

	}
	public void unregister(IAssetModel model) {
		directory.unregister(model.getRoot().getId());
	}

	@Override
	public void delete(Identifier aasIdentifier) {
		// pass the id for unregistering
		directory.unregister(aasIdentifier.getId());
	}

	public void delete(String alias) {
		if (modelProvider.containsKey(alias)) {
			modelProvider.get(alias).getRoot();
		}
	}

	@Override
	public void delete(Identifier aasIdentifier, Identifier submodelIdentifier) {
		//
		directory.unregister(aasIdentifier.getId(), Collections.singletonList(submodelIdentifier.getId()));
	}

	@Override
	public List<AssetAdministrationShellDescriptor> lookup() {
		throw new UnsupportedOperationException("Operation not supported!");
	}

	@Override
	public Optional<AssetAdministrationShellDescriptor> lookup(Identifier aasIdentifier) {
		return directory.lookup(aasIdentifier.getId());
	}

	@Override
	public Collection<SubmodelDescriptor> lookupSubmodels(Identifier aasIdentifier) {
		// obtain the descriptor of the shell first
		Optional<AssetAdministrationShellDescriptor> aasDesc = directory.lookup(aasIdentifier.getId());
		if (aasDesc.isPresent()) {
			// provide the submodel descriptors
			return aasDesc.get().getSubmodels();
		}
		return Collections.emptyList();
	}

	@Override
	public Optional<SubmodelDescriptor> lookupSubmodel(Identifier aasIdentifier, Identifier submodelIdentifier) {
		return directory.lookup(aasIdentifier.getId(), submodelIdentifier.getId());
	}
	public Object getElementValue(Reference reference) {
		modelProvider.keySet().contains(reference.getFirstIdentifier());
		if ( modelProvider.containsKey(reference.getFirstIdentifier())) {
			return modelProvider.get(reference.getFirstIdentifier()).getElementValue(reference);
		}
		// 
		return null;
	}
	@Override
	public Map<String,Object> invokeOperation(Identifier aasIdentifier, String path, Map<String, Object> parameters) {
		// try to find the aas in the local registry
		if ( modelProvider.containsKey(aasIdentifier)) {
			return modelProvider.get(aasIdentifier).execute(path, parameters);
		}
		try {
			path = URLEncoder.encode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return repository.invokeOperation(aasIdentifier.getId(), path, parameters);
	}
	@Override
	public IAssetMessaging getMessaging() {
		return new IAssetMessaging() {
			
			@Override
			public void startup() {
				for (IAssetModel model : modelProvider.values()) {
					model.startEventProcessing();
				}
				
			}
			
			@Override
			public void shutdown() {
				for (IAssetModel model : modelProvider.values()) {
					model.stopEventProcessing();
				}
				
			}
		};
	}
	public AssetComponent getComponent(int port) {
		return new I40Component(port, "/", this);
	}
	public AssetComponent getComponent(int port, String contextPath) {
		if ( ! contextPath.startsWith("/") ) {
			contextPath = "/"+contextPath;
		}
		return new I40Component(port, contextPath, this);
	}
	@Override
	public AssetComponent getComponent() {
		return new I40Component(5000, "/", this);

	}

	@Override
	public void addModelListener(IAssetModelListener listener) {
		this.modelListener.add(listener);

	}

	public void accept(Consumer<IAssetModelListener> method) {
		if (!this.modelListener.isEmpty()) {
			this.modelListener.parallelStream().forEach(method);
		}

	}

}
