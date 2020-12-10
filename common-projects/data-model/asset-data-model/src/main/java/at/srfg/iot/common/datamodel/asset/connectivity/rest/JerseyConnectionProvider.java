package at.srfg.iot.common.datamodel.asset.connectivity.rest;

import at.srfg.iot.common.datamodel.asset.connectivity.AssetConnectionProvider;
import at.srfg.iot.common.datamodel.asset.connectivity.IAssetConnection;

public class JerseyConnectionProvider extends AssetConnectionProvider {

	@Override
	public IAssetConnection create(String endpoint) {
		return ConsumerFactory.createConsumer(endpoint, IAssetConnection.class);
	}

}
