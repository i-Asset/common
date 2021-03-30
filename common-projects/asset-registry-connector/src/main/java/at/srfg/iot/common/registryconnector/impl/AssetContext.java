package at.srfg.iot.common.registryconnector.impl;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import at.srfg.iot.common.aas.IAssetModel;


/**
 * Represents the REST handler bound to a specific AAS
 * @author dglachs
 *
 */
public class AssetContext extends ResourceConfig {
	
	private final IAssetModel shell;
	private final String pathSpec;
	
	public AssetContext(IAssetModel shell, String pathSpec) {
		this.shell = shell;
		this.pathSpec = pathSpec;
		// register the provided shell for dependency injection
		register(new AbstractBinder() {
			
			@Override
			protected void configure() {
				bind(shell).to(IAssetModel.class);
				
			}
		});
		// name the packages where the service implementation can be found
		packages(getClass().getPackage().getName());
		packages("at.srfg.iot.common.registryconnector.impl.rest");
		
	}

	public IAssetModel getShell() {
		return shell;
	}

	public String getPathSpec() {
		return pathSpec;
	}

}
