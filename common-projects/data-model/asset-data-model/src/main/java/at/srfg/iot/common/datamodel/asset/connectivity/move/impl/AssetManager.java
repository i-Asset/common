package at.srfg.iot.common.datamodel.asset.connectivity.move.impl;

import java.util.Optional;

import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.AssetAdministrationShellDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.api.IAssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.api.ISubmodel;
import at.srfg.iot.common.datamodel.asset.connectivity.ConnectionProvider;
import at.srfg.iot.common.datamodel.asset.connectivity.move.IAssetDirectory;
import at.srfg.iot.common.datamodel.asset.connectivity.move.IAssetManager;
import at.srfg.iot.common.datamodel.asset.connectivity.rest.ConsumerFactory;
import at.srfg.iot.common.datamodel.asset.connectivity.rest.JerseyConnectionProvider;

public class AssetManager implements IAssetManager {
	private final String baseUrl;
	
	private IAssetDirectory directory;
	
	private ConnectionProvider cProvider;
	
	public AssetManager(String baseUrl) {
		this.cProvider = new JerseyConnectionProvider();
		this.baseUrl = baseUrl;
		this.directory = ConsumerFactory.createConsumer(baseUrl, IAssetDirectory.class);
	}
	

	@Override
	public IAssetAdministrationShell getAssetAdministrationShell(Identifier identifier) {
		Optional<AssetAdministrationShellDescriptor> aasDescriptor = directory.lookup(identifier.getId());
		return null ;
	}

	@Override
	public ISubmodel getSubmodel(Identifier assetIdentifier, Identifier submodelIdentifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Identifier assetIdentifier, Identifiable identifiable, String endpoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(Identifier assetIdentifier, Identifier submodelIdentifier, Identifiable element,
			String endpoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(Identifier assetIdentifier, Identifiable element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(Identifier assetIdentifier, Identifier submodelIdentifier, Identifiable element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void register(Identifier assetIdentifier, Identifiable identifiable, String endpoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void register(Identifier assetIdentifier, Identifier submodelIdentifier, Identifiable element,
			String endpoint) {
		// TODO Auto-generated method stub
		
	}

}
