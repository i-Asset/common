package at.srfg.iot.connectivity;

import java.util.HashMap;
import java.util.Map;

public abstract class AssetConnectionProvider implements ConnectionProvider {
	private Map<String, IAssetConnection> conn = new HashMap<>();
	@Override
	public IAssetConnection getConnection(String endpoint) {
		if (!conn.containsKey(endpoint)) {
			conn.put(endpoint, create(endpoint));
		}
		return conn.get(endpoint);
	}
	public abstract IAssetConnection create(String endpoint);
}
