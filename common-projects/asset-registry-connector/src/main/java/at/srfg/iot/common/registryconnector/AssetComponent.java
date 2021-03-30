package at.srfg.iot.common.registryconnector;

import at.srfg.iot.common.aas.IAssetModel;

public interface AssetComponent {
//	default AssetComponent create(int port) {
//		return new I40Component(port, "/");
//	}
	public void serve(IAssetModel provider, String alias);
	/**
	 * Start a I40 Component/Device, eg. start the REST service providing
	 * external access to this device
	 */
	public void start();
	/**
	 * Stop servicing the component
	 */
	public void stop();
	
	public boolean isStarted();
}
