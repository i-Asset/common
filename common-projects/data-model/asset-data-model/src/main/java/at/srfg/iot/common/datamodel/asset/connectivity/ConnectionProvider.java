package at.srfg.iot.common.datamodel.asset.connectivity;

public interface ConnectionProvider {
	
	IAssetConnection getConnection(String endpoint);

}
