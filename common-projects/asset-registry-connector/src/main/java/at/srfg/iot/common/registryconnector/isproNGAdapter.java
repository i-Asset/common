//package at.srfg.iot.common.registryconnector;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
//import at.srfg.iot.common.datamodel.asset.provider.IAssetProvider;
//
//public class isproNGAdapter {
//
//	public static void main(String[] args) {
//
//		// component.registerWith(registry);
//		IAssetRegistry registry = IAssetRegistry.componentWithRegistry("http://localhost:8085")
//												.componentAtPort(5001);
//		
//		
//		/**
//		 * create an INSTANCE of the Asset 
//		 */
//		IAssetProvider cmmsInstance = registry.fromType(
//				// 
//				new Identifier("http://iasset.labor/isproNG"),
//				// 
//				new Identifier("http://iasset.salzburgresearch.at/registry/cmms"));
//		// use the idShort - possibly for an alias
//		cmmsInstance.getRoot().setIdShort("cmms");
//		
//		cmmsInstance.setValueConsumer("information/vendor", 	
//				(String t) -> System.out.println("vendor value change: " + t));
//		// add a consumer function to the property
//		cmmsInstance.setValueSupplier("information/vendor",
//				// the getter function must return a string
//				() -> "H+H Systems");
//		cmmsInstance.setValueSupplier("information/name",
//				// the getter function must return a string
//				() -> "isproNG");
//		
//		
//		/**
//		 * connect the AAS INSTANCE with the local physical device, e.g. access the DEVICE's data\
//		 * 
//		 * NOTE: only sample-code at thispoint 
//		 */
//		cmmsInstance.setFunction("operations/maintenanceHistory", new Function<Map<String, Object>, Object>() {
//
//				@Override
//				public Object apply(Map<String, Object> t) {
//					if (!t.containsKey("maintenanceHistoryInputV1")) {
//						throw new IllegalStateException("Missing parameter [maintenanceHistoryInputV1Value]");
//					}
//					Map<String, Object> result = new HashMap<>();
//					result.put("result", "An dieser Stelle muss von isproNG die History für das Asset lt. Parameter abgeholt werden! Param: " + t.get("maintenanceHistoryInputV1"));
//					return result;
//				}
//			});
//
//		
//		
//		
//		
//		
//		/**
//		 * Tell the Industry 4.0 Component to serve the INSTANCE with a given context 
//		 * and start the component's endpoint
//		 * 
//		 * The AAS can be accessed from outside then .
//		 */
//		registry.serve(cmmsInstance, "cmms");
//		registry.start();
//
//
//		
//		/**
//		 * Register the INSTANCE with the registry. Creates a copy of the instance in the repository
//		 * 
//		 */
//		registry.register(cmmsInstance);
//		// delete the shell from the repository
//
//
//		/**
//		 * Deactivate the service endpoint
//		 */
//		registry.stop();
//	}
//
//}
