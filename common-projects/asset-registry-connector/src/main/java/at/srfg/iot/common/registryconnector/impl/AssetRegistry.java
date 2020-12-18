package at.srfg.iot.common.registryconnector.impl;


import java.util.Collection;
import java.util.Collections;
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
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;
import at.srfg.iot.common.registryconnector.AssetComponent;
import at.srfg.iot.common.datamodel.asset.connectivity.IAssetConnection;
import at.srfg.iot.common.registryconnector.IAssetRegistry;
import at.srfg.iot.common.datamodel.asset.connectivity.move.IAssetDirectory;
import at.srfg.iot.common.datamodel.asset.connectivity.rest.ConsumerFactory;
import at.srfg.iot.common.datamodel.asset.provider.IAssetProvider;
import at.srfg.iot.common.datamodel.asset.provider.impl.AssetModel;
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
	
	/**
	 * URI of the iAsset Directory
	 */
	final String directoryUrl;
	private I40Component server;
	
	public void start() {
		if ( server == null ) {
			server = new I40Component(5000, "/");
		}
		server.start();
		
		
	}
	public void stop() {
		if ( server != null ) {
			server.stop();
		}
	}
	
	public void serve(IAssetProvider provider, String alias) {
		if (server == null) {
			server = new I40Component(5000, "/");
		}
		server.serve(provider, alias);
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
			
			
			
		}
		return null;
	}
	@Override
	public Object invokeOperation(Identifier aasIdentifier, String path, Map<String, Object> parameters) {
		return repository.invokeOperation(aasIdentifier.getId(), path, parameters);
	}

}
