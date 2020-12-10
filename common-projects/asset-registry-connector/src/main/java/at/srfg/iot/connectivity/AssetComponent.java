package at.srfg.iot.connectivity;

import at.srfg.iot.provider.IAssetProvider;

public interface AssetComponent {
//	default AssetComponent create(int port) {
//		return new I40Component(port, "/");
//	}
	public void serve(IAssetProvider provider, String alias);
	/**
	 * Start a I40 Component/Device, eg. start the REST service providing
	 * external access to this device
	 */
	public void start();
	/**
	 * Stop servicing the component
	 */
	public void stop();
}