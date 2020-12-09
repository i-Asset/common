package at.srfg.iot.connectivity;

public interface ConnectionProvider {
	
	IAssetConnection getConnection(String endpoint);

}
