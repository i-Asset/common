package at.srfg.iot.common.registryconnector.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import at.srfg.iot.common.datamodel.asset.aas.common.DirectoryEntry;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.IdType;
import at.srfg.iot.common.registryconnector.AssetComponent;
import at.srfg.iot.common.datamodel.asset.provider.IAssetProvider;

public class I40Component implements AssetComponent {
	private Server server;
	private int port;
	private String contextPath;
	
	private Map<String, AssetContext> serviceMap = new HashMap<String, AssetContext>();

	public String getHostName() {
		try {
			InetAddress adr = InetAddress.getLocalHost();
			return adr.getHostName();
		} catch (UnknownHostException e) {
			return "localhost";
		}
	}
	
	
	/**
	 * 
	 * @param shell
	 * @param alias
	 */
	public void serve(IAssetProvider shell, String alias) {
		if (!IdType.getType(alias).equals(IdType.IdShort)) {
			throw new IllegalArgumentException("Not a valid alias");
		}
		if (shell.getRoot() instanceof DirectoryEntry) {
			DirectoryEntry e = (DirectoryEntry)shell.getRoot();
			e.setEndpoint(0, String.format("http://%s:%s%s%s", getHostName(), port, contextPath, alias), "http");
		}
		serviceMap.put(alias,  new AssetContext(shell, alias));
	}
	
	public I40Component(int port, String contextPath) {
		this.port = port;
		this.contextPath = contextPath;
	}

	public void start() {
		if (! serviceMap.isEmpty()) {
			// TODO: decide what to do ...
		}
		//
		server = new Server(port);
		ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		handler.setContextPath(contextPath);
		server.setHandler(handler);
		
		// 
		for ( String alias : serviceMap.keySet()) {
			//
			ServletHolder holder = new ServletHolder(new ServletContainer(serviceMap.get(alias)));
			holder.setInitOrder(0);
			handler.addServlet(holder, "/"+alias+"/*");
		}
		try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void stop() {
		if (server != null && server.isRunning() ) {
			try {
				server.stop();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				server.destroy();
			}
		}
		
	}
	

}
