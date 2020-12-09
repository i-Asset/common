package at.srfg.iot.connectivity.rest;

import at.srfg.iot.connectivity.AssetConnectionProvider;
import at.srfg.iot.connectivity.IAssetConnection;

public class JerseyConnectionProvider extends AssetConnectionProvider {

	@Override
	public IAssetConnection create(String endpoint) {
		return ConsumerFactory.createConsumer(endpoint, IAssetConnection.class);
	}

}
