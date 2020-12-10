package at.srfg.iot.common.datamodel.asset.connectivity.move;

import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.api.IAssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.api.ISubmodel;

public interface IAssetManager {
	/**
	 * Get an {@link IAssetAdministrationShell} by it's identifier
	 * @param identifier
	 * @return
	 */
	public IAssetAdministrationShell getAssetAdministrationShell(Identifier identifier);
	public ISubmodel getSubmodel(Identifier assetIdentifier, Identifier submodelIdentifier);
	
	public void create(Identifier assetIdentifier, Identifiable identifiable, String endpoint);
	public void create(Identifier assetIdentifier, Identifier submodelIdentifier, Identifiable element, String endpoint );
	public void create(Identifier assetIdentifier, Identifiable element);
	public void create(Identifier assetIdentifier, Identifier submodelIdentifier, Identifiable element );
	
	public void register(Identifier assetIdentifier, Identifiable identifiable, String endpoint);
	public void register(Identifier assetIdentifier, Identifier submodelIdentifier, Identifiable element, String endpoint );
	
}
