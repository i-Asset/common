package at.srfg.iot.common.registryconnector;


import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import at.srfg.iot.common.aas.IAssetModel;
import at.srfg.iot.common.aas.IAssetModelListener;
import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.AssetAdministrationShellDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.SubmodelDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.registryconnector.impl.AssetRegistry;
public interface IAssetRegistry {
	
	static IAssetRegistry componentWithRegistry(String url) {
		return new AssetRegistry(url);
	}
	public AssetComponent getComponent();
	public AssetComponent getComponent(int port);
	public AssetComponent getComponent(int port, String context);
	/**
	 * Directly connect with a edge device by asking the registry for the edge's endpoint
	 * and establish a connection
	 * @param identifier
	 * @return
	 */
	public IAssetModel connect(String identifier);
	/**
	 * Directly connect with a edge device by asking the registry for the edge's endpoint
	 * and establish a connection
	 * @param identifier
	 * @return
	 */
	public IAssetModel connect(Identifier identifier);
	/**
	 * Create a local Model based on a stored {@link AssetAdministrationShell} or 
	 * {@link Submodel}. Only elements of {@link Kind#Instance} are created.
	 * @param identifier Either IRI or IRDI.
	 * @return
	 */
	public IAssetModel create(String alias, Identifier identifier);
	/**
	 * Create a local Model based on the provided {@link Identifiable}. 
	 * 
	 * @param identifiable The full identifiable.
	 * @return
	 */
	public IAssetModel create(String alias, Identifiable root); 
	/**
	 * Connect with a remote {@link AssetAdministrationShell}.  
	 * @param identifier
	 * @param type
	 * @return
	 */
	public IAssetModel create(String alias, Identifier identifier, Identifier type);
	/**
	 * Delete an model from the remote service, e.g. all instance data is
	 * removed
	 * @param provider
	 */
	public void delete(IAssetModel provider);
	public void delete(String alias);
//	/**
//	 * Register a new Asset with the directory service. This will
//	 * <ul>
//	 * <li>create/update the root element {@link AssetAdministrationShell} or {@link Submodel} to the registry
//	 * <li>store the endpoint with the root element, e.g. tell the registry the model's networking endpoint
//	 * </ul>
//	 * For updating the entire model, use {@link #save(IAssetModel)}
//	 * @param descriptor
//	 */
//	public void register(IAssetModel assetProvider);
	/**
	 * Updates all elements of the model in the registry
	 * @param assetModel
	 */
	public void save(IAssetModel assetModel);
	/**
	 * Register a new Submodel with the directory service
	 * @param aasIdentifier
	 * @param descriptor
	 */
	public void register(Identifier aasIdentifier, SubmodelDescriptor descriptor);
	/**
	 * Delete the {@link AssetAdministrationShell} referenced wit the identifier
	 * @param aasIdentifier
	 */
	public void delete(Identifier aasIdentifier);
	/**
	 * Delete the {@link Submodel} 
	 * @param aasIdentifier The identifier of the {@link AssetAdministrationShell} containing the {@link Submodel}
	 * @param submodelIdentifier
	 */
	public void delete(Identifier aasIdentifier, Identifier submodelIdentifier);
	/**
	 * Retrieve all {@link AssetAdministrationShellDescriptor}s for this particular client
	 * @return
	 */
	public Collection<AssetAdministrationShellDescriptor> lookup();
	/**
	 * Retrieve a single {@link AssetAdministrationShell} 
	 * @param aasIdentifier
	 * @return
	 */
	public Optional<AssetAdministrationShellDescriptor> lookup(Identifier aasIdentifier);
	/**
	 * Retrieve all {@link SubmodelDescriptor}s for the {@link AssetAdministrationShell}
	 * @param aasIdentifier
	 * @return
	 */
	public Collection<SubmodelDescriptor> lookupSubmodels(Identifier aasIdentifier);
	/**
	 * Retrieve a single submodel descriptor
	 * @param aasIdentifier
	 * @param submodelIdentifier
	 * @return
	 */
	public Optional<SubmodelDescriptor> lookupSubmodel(Identifier aasIdentifier, Identifier submodelIdentifier);
	/**
	 * Invoke an operation at the (device) asset with the given Identifier - the repository must resolve the correct endpoint 
	 * when the model is not from found in the local registry.
	 * @param aasIdentifier
	 * @param path
	 * @param parameters
	 * @return
	 */
	public Map<String,Object> invokeOperation(Identifier aasIdentifier, String path, Map<String, Object> parameters);
	
	public void addModelListener(IAssetModelListener listener);
//	public void accept(Consumer<IAssetModelListener> method);
//	void start(int port);
//	public void stop();
	IAssetMessaging getMessaging();
	
}
