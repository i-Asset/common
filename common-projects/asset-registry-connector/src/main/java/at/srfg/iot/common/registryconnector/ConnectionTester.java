package at.srfg.iot.common.registryconnector;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Key;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.KeyElementsEnum;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;
import at.srfg.iot.common.datamodel.asset.aas.common.types.DataTypeEnum;
import at.srfg.iot.common.datamodel.asset.aas.common.types.DirectionEnum;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Operation;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.OperationVariable;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.SubmodelElementCollection;
import at.srfg.iot.common.datamodel.asset.provider.IAssetProvider;
import at.srfg.iot.common.datamodel.asset.provider.impl.AssetModel;

public class ConnectionTester {

	public static void main(String[] args) {

		// component.registerWith(registry);
		IAssetRegistry registry = IAssetRegistry.connectWithRegistry("http://localhost:8085");
		
		
		/**
		 * create an INSTANCE of the Asset 
		 */
		IAssetProvider beltInstance = registry.fromType(
				// 
				new Identifier("http://iasset.labor/belt"),
				// 
				new Identifier("http://iasset.salzburgresearch.at/labor/belt#aas"));
		// use the idShort - possibly for an alias
		beltInstance.getRoot().setIdShort("belt");
		
		
		
		/**
		 * connect the AAS INSTANCE with the local physical device, e.g. access the DEVICE's data
		 * 
		 * NOTE: only sample-code at thispoint 
		 */
		beltInstance.setFunction("operations/setSpeed", new Function<Map<String, Object>, Object>() {

				@Override
				public Object apply(Map<String, Object> t) {
					if (!t.containsKey("speed")) {
						throw new IllegalStateException("Missing parameter [message]");
					}
					Map<String, Object> result = new HashMap<>();
					result.put("result", "Success: Speed setting updated to the new value: " + t.get("speed"));
					return result;
				}
			});
		// add a consumer function to the property
		beltInstance.setValueConsumer("properties/beltData/distance",
				// setter function, simply show the new value at System.out 
				(String t) -> System.out.println("Distance value change: " + t));
		// add a supplier function to the property
		beltInstance.setValueSupplier("properties/beltData/distance",
				// the getter function must return a string
				() -> "Distance belt so far read at timestamp: " + LocalDateTime.now().toString());

		
		
		
		
		
		/**
		 * Tell the Industry 4.0 Component to serve the INSTANCE with a given context 
		 * and start the component's endpoint
		 * 
		 * The AAS can be accessed from outside then .
		 */
		registry.serve(beltInstance, "belt");
		registry.start();


		
		/**
		 * Register the INSTANCE with the registry. Creates a copy of the instance in the repository
		 * 
		 */
		registry.register(beltInstance);
		// delete the shell from the repository


		/**
		 * Deactivate the service endpoint
		 */
		registry.stop();
	}
	
	
	
	
	
	
	private static IAssetProvider shell() {

		AssetAdministrationShell aShell = new AssetAdministrationShell("http://www.irgendwo.com");
		aShell.setIdShort("irgendwo");
		aShell.setDerivedFrom(new Reference("http://iasset.salzburgresearch.at/labor/belt",
				KeyElementsEnum.AssetAdministrationShell));
		Submodel sub1 = new Submodel("sub1", aShell);
		sub1.setIdShort("sub1");
		sub1.setKind(Kind.Instance);
		sub1.setDescription("de", "Operation Model");
		sub1.setSemanticId(
				new Reference("http://iasset.salzburgresearch.at/registry/cmms/operations", KeyElementsEnum.Submodel));

		Submodel sub2 = new Submodel("sub2", aShell);
		sub2.setIdShort("sub2");
		sub2.setKind(Kind.Instance);
		sub2.setDescription("de", "Info Model");
		sub2.setSemanticId(
				new Reference("http://iasset.salzburgresearch.at/registry/cmms/info", KeyElementsEnum.Submodel));

		SubmodelElementCollection cont1 = new SubmodelElementCollection("cont1", sub1);
		cont1.setDescription("de", "Container Level 1");

		SubmodelElementCollection cont2 = new SubmodelElementCollection("cont2", cont1);
		cont2.setDescription("de", "Container Level 2");

		Property prop = new Property("demo", cont2);
		prop.setDescription("de", "Property Demo");
		IAssetProvider model = new AssetModel(aShell);
		Property prop2 = new Property();
		prop2.setIdShort("later");
		prop2.setDescription("de", "Property Later");
		// provide a getter function
		prop2.setGetter(() -> LocalDateTime.now().toString());
		prop.setGetter(() -> LocalDate.now().plusWeeks(2).toString());
		// provide a setter function
		prop.setSetter((String t) -> System.out.println(t));

		model.setElement(prop.getParent(), prop2);
		// call the element value
		Object o = model.getElementValue(prop.asReference());
		model.setElementValue(prop.asReference(), "new Value");
//	    	
//	    	model.deleteElement(prop.asReference());
		Operation operation = new Operation("maintenanceHistory", cont1);
		operation.setDescription("de", "Test für eine ausführbare Methode");
		Property variable = new Property();
		variable.setParent(
				new Reference("http://iasset.salzburgresearch.at/registry/cmms/operations", KeyElementsEnum.Submodel));
		variable.setIdShort("maintenanceHistoryInputV1Value");
		variable.setKind(Kind.Type);
		variable.setDescription("de", "Typ-Defintion Input-Parameter 1 für Maintenance History");
		variable.setValueQualifier(DataTypeEnum.STRING);
		//
		OperationVariable input = new OperationVariable("maintenanceHistory", operation, DirectionEnum.Input);
		input.setIdShort("maintenanceHistory");
		input.setDescription("de", "Typ-Defintion Input-Parameter 1 für Maintenance History");
		input.setKind(Kind.Type);
		input.setValue(variable);
		//
		input.setSemanticElement(new Reference(
				Key.of("http://iasset.salzburgresearch.at/registry/cmms/operations", KeyElementsEnum.Submodel),
				Key.of("maintenanceHistoryInputV1", KeyElementsEnum.OperationVariable)));
		// provide a function for testing
		operation.setFunction(new Function<Map<String, Object>, Object>() {

			@Override
			public Object apply(Map<String, Object> t) {
				// TODO Auto-generated method stub
				if (!t.containsKey("maintenanceHistory")) {
					throw new IllegalStateException("Missing parameter [message]");
				}
				return t.get("message");
			}
		});

		Optional<Referable> refElem = model.getElement("sub1/cont1/cont2/demo");
		if (refElem.isPresent()) {
			model.deleteElement(refElem.get());
		}
		return new AssetModel(aShell);

	}
	private static IAssetProvider submodel() {
		Submodel subModel = new Submodel();
		subModel.setIdentification(new Identifier("http://www.otherplace.org"));
		subModel.setDescription("de", "Submodel");

		SubmodelElementCollection subCont1 = new SubmodelElementCollection("cont1", subModel);
		subCont1.setDescription("de", "Container Level 1");

		SubmodelElementCollection subCont2 = new SubmodelElementCollection("cont2", subCont1);
		subCont2.setDescription("de", "Container Level 2");

		Property submodelProperty = new Property("demo", subCont2);
		submodelProperty.setDescription("de", "Property from Submodel Demo");
		submodelProperty.setGetter(() -> LocalDateTime.now().toString());
		// provide a setter function
		submodelProperty.setSetter((String t) -> System.out.println(t));

		return new AssetModel(subModel);
	}

}
