package at.srfg.iot.common.registryconnector.impl;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import at.srfg.iot.common.datamodel.asset.aas.basic.Asset;
import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Endpoint;
import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.AssetAdministrationShellDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.SubmodelDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.common.DirectoryEntry;
import at.srfg.iot.common.datamodel.asset.aas.common.HasKind;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Key;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.KeyElementsEnum;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.DataElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Operation;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;
import at.srfg.iot.common.datamodel.asset.connectivity.IAssetConnection;
import at.srfg.iot.common.datamodel.asset.connectivity.move.IAssetDirectory;
import at.srfg.iot.common.datamodel.asset.connectivity.rest.ConsumerFactory;
import at.srfg.iot.common.datamodel.asset.provider.IAssetModelListener;
import at.srfg.iot.common.datamodel.asset.provider.IAssetProvider;
import at.srfg.iot.common.datamodel.asset.provider.impl.AssetModel;
import at.srfg.iot.common.datamodel.asset.provider.impl.ConnectedAssetModel;
import at.srfg.iot.common.registryconnector.AssetComponent;
import at.srfg.iot.common.registryconnector.IAssetRegistry;
/**
 * Component for the interaction with the registry
 * @author dglachs
 *
 */
public class AssetRegistry implements IAssetRegistry, AssetComponent {
	/**
	 * Service Proxy to the i-Asset Directory Service for registration and lookup 
	 * 
	 */
	final IAssetDirectory directory;
	final IAssetConnection repository;
	
	private Map<String, IAssetProvider> modelProvider = new HashMap<String, IAssetProvider>();
	
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
			// obtain the message-broker element
			Reference refToBroker = element.getMessageBroker();
			// the reference must have the identifier of the aas or submodel points to 
			Key keyToAAS = refToBroker.getFirstKey();
			if ( keyToAAS != null) {
				// obtain the element from referenced AAS
				Optional<Referable> messageBroker = repository.getModelElement(keyToAAS.getValue(), element.getMessageBroker());
				
				if (messageBroker.isPresent()) {
					// need to obtain the child element with a given semantic identifier
					//    or
					// use 
					messageBroker.get().getChildElement(assetIdentifier);
				}
				//
				//
				
			}
			
			
			// 
			
		}
	}; 
	/**
	 * URI of the iAsset Directory
	 */
	final String directoryUrl;
//	private I40Component server;
	
	private AssetComponent component;
	
	public void start() {
		if ( component == null ) {
			component = new I40Component(5000, "/");
		}
		component.start();
		
		
	}
	public void stop() {
		if ( component != null ) {
			component.stop();
		}
	}
	public void addModel(String alias, IAssetProvider model) {
		modelProvider.put(alias, model);
	}
	
	public void serve(IAssetProvider provider, String alias) {
		if (component == null) {
			component = new I40Component(5000, "/");
		}
		component.serve(provider, alias);
	}
	
	public AssetRegistry(String directoryUrl) {
		// create the proxy with the provided url
		this.directoryUrl = directoryUrl;
		// obtain the directory service to work with the directory
		directory = ConsumerFactory.createConsumer(directoryUrl, IAssetDirectory.class);
		repository = ConsumerFactory.createConsumer(directoryUrl + "/repository", IAssetConnection.class);
		
	}

	@Override
	public void register(AssetAdministrationShellDescriptor descriptor) {
		Optional<AssetAdministrationShell> storedAAS = directory.register(descriptor);
		

	}

	/**
	 * Obtain a new AssetInstance from a provided type
	 * @param identifier
	 * @return
	 */
	public IAssetProvider fromType(Identifier instanceId, Identifier identifier) {
		Optional<Identifiable> instanceRoot = repository.getRoot(instanceId.getId());
		// 
		Optional<Identifiable> root = repository.getRoot(identifier.getId());
		if ( root.isPresent()) {
			Identifiable type = root.get();
			switch(root.get().getModelType()) {
			case AssetAdministrationShell:
				AssetAdministrationShell shell = new AssetAdministrationShell(instanceId);
				shell.setDerivedFrom(type.asReference());
				shell.setAsset(new Asset());
				shell.getAsset().setKind(Kind.Instance);
				shell.setDescription(type.getDescription());
				for (Referable ref : type.getChildren()) {
					// ref might be a reference
					switch(ref.getModelType()) {
					case Reference:
						Reference refToSub = Reference.class.cast(ref);
						if ( refToSub.hasRoot(type) ) {
							// 
							Optional<Referable> full = repository.getModelElement(identifier.getId(), Reference.class.cast(ref));
							if ( full.isPresent() ) {
								if ( HasKind.class.isInstance(full.get())) {
									HasKind kind = HasKind.class.cast(full.get());
									full = kind.asInstance(shell);
									if ( full.isPresent()) {
										//sh
									}
								}
								else {
									shell.addChildElement(full.get());
								}
							}
						}
						else {
							Optional<Referable> full = repository.getModelElement(refToSub.getFirstIdentifier().getId(), refToSub);
							if ( full.isPresent() ) {
								Referable fullType = full.get();
								if ( HasKind.class.isInstance(fullType)) {
									HasKind kind = HasKind.class.cast(fullType);
									full = kind.asInstance(shell);
									if ( full.isPresent()) {
										//sh
									}
								}
								else {
									shell.addChildElement(full.get());
								}
							}
							
						}
						break;
					case Submodel:
						shell.addSubmodel(Submodel.class.cast(ref));
						break;
					}
				}
//				for (Submodel sub : type.getChildElements(Submodel.class)) {
//					//
//					repository.getModelElement(identifier.getId(), element)
//				}
				return new AssetModel(shell);
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
			IAssetConnection repoConn = ConsumerFactory.createConsumer(directoryUrl+"/repository", IAssetConnection.class);
			AssetAdministrationShell shell = new AssetAdministrationShell();
			// copy from Descriptor
			shell.setDerivedFrom(descriptor.asReference());
			shell.setAsset(descriptor.getAsset());
			shell.setDescription(descriptor.getDescription());
			
			// 
			
			for (SubmodelDescriptor desc : descriptor.getSubmodels() ) {
				// deal with endpoints
			}
			AssetModel model = new AssetModel(shell);
			
		}
		return null;
	}
	
	public void register(IAssetProvider provider) {
		Identifiable root = provider.getRoot();
		if ( AssetAdministrationShell.class.isInstance(root)) {
			//
			AssetAdministrationShell shell = AssetAdministrationShell.class.cast(root);
			AssetAdministrationShellDescriptor descriptor = new AssetAdministrationShellDescriptor(shell);
			// create the descriptor entries for shell and contained submodels
			Optional<AssetAdministrationShell> registered = directory.register(descriptor);
			// create / update all elements
			if (registered.isPresent()) {
				for (Referable referable: root.getChildren()) {
					repository.setModelElement(root.getId(), referable);
				}
			}
			
			
			
		}
	}
	

	@Override
	public void register(Identifier aasIdentifier, SubmodelDescriptor descriptor) {
		directory.register(aasIdentifier.getId(), descriptor);

	}

	
	@Override
	public void delete(Identifier aasIdentifier) {
		// pass the id for unregistering
		directory.unregister(aasIdentifier.getId());
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
		if ( aasDesc.isPresent()) {
			// provide the submodel descriptors
			return aasDesc.get().getSubmodels();
		}
		return Collections.emptyList();
	}

	@Override
	public Optional<SubmodelDescriptor> lookupSubmodel(Identifier aasIdentifier, Identifier submodelIdentifier) {
		return directory.lookup(aasIdentifier.getId(), submodelIdentifier.getId());
	}

	private String getEndpoint(DirectoryEntry directory) {
		Optional<Endpoint> ep = directory.getFirstEndpoint();
		if ( ep.isPresent()) {
			return ep.get().getAddress();
		}
		return directoryUrl;
	}
	public void add(IAssetProvider provider) {
		
	}

	@Override
	public IAssetProvider connect(Identifier identifier) {
		Optional<AssetAdministrationShellDescriptor> descriptor = directory.lookup(identifier.getId());
		if ( descriptor.isPresent() ) {
			// create the (Connected)AssetModel
			String endpoint = getEndpoint(descriptor.get());
			IAssetConnection repo =ConsumerFactory.createConsumer(endpoint, IAssetConnection.class);
			// 
			Optional<Identifiable> root = repo.getRoot(identifier.getId());
			
			Identifiable type = root.get();
			
			IAssetProvider model = new ConnectedAssetModel(type, directoryUrl + "/repository");
			return model;
			
		}
		return null;
	}
	@Override
	public Object invokeOperation(Identifier aasIdentifier, String path, Map<String, Object> parameters) {
		try {
			path = URLEncoder.encode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return repository.invokeOperation(aasIdentifier.getId(), path, parameters);
	}
	@Override
	public void setAssetComponent(AssetComponent component) {
		this.component = component;
		
	}
	@Override
	public void addModelListener(IAssetModelListener listener) {
		if ( component == null) {
			throw new IllegalStateException("Component not yet initialized!");
		}
		component.addModelListener(listener);
		// TODO Auto-generated method stub
		
	}

}
