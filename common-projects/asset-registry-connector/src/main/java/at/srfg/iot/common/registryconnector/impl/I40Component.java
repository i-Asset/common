package at.srfg.iot.common.registryconnector.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import at.srfg.iot.common.aas.IAssetModel;
import at.srfg.iot.common.datamodel.asset.aas.common.DirectoryEntry;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.IdType;
import at.srfg.iot.common.registryconnector.AssetComponent;

public class I40Component implements AssetComponent {
	
	private Server server;
	private final int port;
	private final String contextPath;
	
	private final AssetRegistry registry;
	
	private Map<String, AssetContext> serviceMap = new HashMap<String, AssetContext>();
	
	public I40Component(int port, String contextPath, AssetRegistry reg) {
		this.port = port;
		// contextPath must start with slash
		this.contextPath = contextPath.startsWith("/")? contextPath : "/"+contextPath;
		// required for registering the models on startup
		this.registry = reg;
	}

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
	public void serve(IAssetModel shell, String alias) {
		// method only allowed prior to startup
		if ( ! isStarted()) {
			if (!IdType.getType(alias).equals(IdType.IdShort)) {
				throw new IllegalArgumentException("Not a valid alias");
			}
			if (shell.getRoot() instanceof DirectoryEntry) {
				DirectoryEntry e = (DirectoryEntry)shell.getRoot();
				e.setEndpoint(0, String.format("http://%s:%s%s%s", getHostName(), port, contextPath, alias), "http");
			}
			AssetContext ctx = new AssetContext(shell, alias);
			serviceMap.put(alias,  ctx);
		}
		else {
			throw new IllegalStateException("Component already activated, cannot add a new model!");
		}
	}
	
	public boolean isStarted() {
		return server != null && server.isStarted();
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
			AssetContext ctx = serviceMap.get(alias);
			IAssetModel modelToRegister = ctx.getShell();
			// register the model und perform complete update (e.g. create all elements)
			registry.register(modelToRegister, true);
			
//			addContext(server, alias, serviceMap.get(alias));
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
				server = null;
			}
		}
		
	}
	

}
