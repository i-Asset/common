package at.srfg.iot.common.registryconnector;


import java.util.Collection;
import java.util.Optional;

import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.AssetAdministrationShellDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.SubmodelDescriptor;
import at.srfg.iot.common.registryconnector.impl.AssetRegistry;
import at.srfg.iot.common.datamodel.asset.provider.IAssetProvider;
public interface IAssetRegistry extends AssetComponent {
	static IAssetRegistry connectWithRegistry(String url) {
		return new AssetRegistry(url);
	}
	/**
	 * Connect with a remote {@link IAssetComponent}.
	 * @param identifier
	 * @return
	 */
	public IAssetProvider connect(Identifier identifier);
	/**
	 * Create a new local model based on a given type.
	 * The type must be present in the registry 
	 * @param identifier
	 * @return
	 */
	public IAssetProvider fromType(Identifier instanceIdentifier, Identifier typeIdentifier);
	/**
	 * Register a new Asset with the directory service. 
	 * @param descriptor
	 */
	public void register(AssetAdministrationShellDescriptor descriptor);
	public void register(IAssetProvider assetProvider);
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
	
}
