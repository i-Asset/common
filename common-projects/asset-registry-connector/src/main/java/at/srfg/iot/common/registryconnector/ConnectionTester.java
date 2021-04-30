package at.srfg.iot.common.registryconnector;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.json.JSONObject;

import at.srfg.iot.common.aas.IAssetModel;
import at.srfg.iot.common.aas.IAssetModelListener;
import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Key;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.KeyElementsEnum;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;
import at.srfg.iot.common.datamodel.asset.aas.common.types.DataTypeEnum;
import at.srfg.iot.common.datamodel.asset.aas.common.types.DirectionEnum;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.DataElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Operation;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.SubmodelElementCollection;

public class ConnectionTester {

	public static void main(String[] args) {

		IAssetRegistry registry = IAssetRegistry.componentWithRegistry("http://localhost:8085");
		
		//
		registry.addModelListener(new IAssetModelListener() {

			@Override
			public void onEventElementCreate(String path, EventElement element) {
				System.out.println(String.format("Event %s - element created", path));
				
			}

			@Override
			public void onEventElementRemove(String path, EventElement element) {
				System.out.println(String.format("Event %s - element removed", path));
				
			}

			@Override
			public void onOperationCreate(String path, Operation element) {
				System.out.println(String.format("Operation %s - element created", path));
				
			}

			@Override
			public void onOperationRemove(String path, Operation element) {
				System.out.println(String.format("Operation %s - element removed", path));
				
			}

			@Override
			public void onPropertyCreate(String path, Property property) {
				System.out.println(String.format("Property %s - element created", path));
				property.setGetter(()->"Value of Property " + path + " "+ LocalDateTime.now());
				
			}

			@Override
			public void onPropertyRemove(String path, Property property) {
				System.out.println(String.format("Property %s - element removed", path));
				
			}

			@Override
			public void onValueChange(DataElement<?> element, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				System.out.println(String.format("Element %s - value changed: Old %s, New: %s", element.asReference().getPath(), oldValue, newValue));
				
			}
			

		});
		
//		registry.start(5000);
		/**
		 * connect an existing AAS or Submodel (loaded from file)
		 * 
		 * 
		 */
		IAssetModel beltInstance2 = registry.create("belt2", shell());
		/**
		 * connect an existing AAS 
		 */
		IAssetModel beltInstance = registry.create("belt",
			// Identifier of new Shell
			new Identifier("http://iasset.labor/belt"),
			// Identifier of Shell-Template
			new Identifier("http://iasset.salzburgresearch.at/labor/belt#aas"));
		// 
		/**
		 * connect the AAS INSTANCE with the local physical device, e.g. access the DEVICE's data
		 * 
		 * NOTE: only sample-code at this point 
		 */
		beltInstance.setFunction("operations/setSpeed", new Function<Map<String, Object>, Object>() {

				@Override
				public Object apply(Map<String, Object> t) {
					if (!t.containsKey("speed")) {
						throw new IllegalStateException("Missing parameter [speed]");
					}
					Optional<Property> speedProperty = beltInstance.getElement("properties/beltData/speed", Property.class);
					if ( speedProperty.isPresent()) {
						speedProperty.get().setValue(t.get("speed").toString());
					}
					beltInstance.setElementValue("properties/beltData/speed", t.get("speed").toString());
					return "Speed setting updated to the new value: " + t.get("speed");
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

//		
//		
//		// create the parameter map, the keys consist of the idShort's of the OperationVariable (inputVariable)
		Map<String,Object> params = new HashMap<String, Object>();
		// operation specifies a "speed" input parameter -> seee localhost:5000/belt/element/operations/setSpeed
		params.put("speed", 17.3d);
		// send a post request to the registry, check the stored endpoint and delegate the request to the device
		Map<String,Object> result = registry.invokeOperation(
				// asset model has shell as root
				beltInstance.getRoot().getIdentification(),
				// path to the operation 
				"operations/setSpeed",
				// 
				params);
		if ( result != null ) {
			System.out.println(result.get("result"));
		}
		/**
		 * Obtain the edge component
		 */
		AssetComponent edgeServer = registry.getComponent(5000);
		// add the distinct models to serve
		edgeServer.serve(beltInstance, "belt");
		AssetComponent edgeServer2 = registry.getComponent(5005);
		edgeServer2.serve(beltInstance2, "belt2");
		// start the edge component, e.g. activate the endpoint
		edgeServer.start();
		edgeServer2.start();
		/**
		 * Register the INSTANCE with the registry. Creates a copy of the instance in the repository
		 * 
		 */
//		registry.register(beltInstance);
		/**
		 * Register the INSTANCE with the registry. Creates a copy of the instance in the repository
		 * Problem at this point: the belt2Instance only holds references to it's "semanticId's"
		 * 
		 * TODO: implement a function to resolve the type information when required!
		 * 
		 */
//		registry.register(beltInstance2);
		//
		IAssetModel connected = registry.connect(beltInstance.getRoot().getIdentification());
		// connected must do at this point:
		// - resolve the reference for "properties"
		// - 
		Optional<Property> speedProperty = connected.getElement("properties/beltData/speed", Property.class);
		if ( speedProperty.isPresent()) {
			System.out.println(speedProperty.get().getValue());
		}
//		/**
//		 * Deactivate the service endpoint(s)
//		 */
		edgeServer.stop();
		edgeServer2.stop();
		
	}

	/**
	 * create a AssetAdministrationShell based on loaded example full AAS Json file
	 * @return returns constructed AAS to caller
	 */
	public static AssetAdministrationShell createAASTypeFromJSONFile()
	{
		byte[] bytes = new byte[0];
		try {
			URL resource = ConnectionTester.class.getClassLoader().getResource("ExampleAssetType.json");
			bytes = Files.readAllBytes(Paths.get(resource.toURI()));
		}
		catch (IOException e) { e.printStackTrace(); }
		catch (URISyntaxException e) {e.printStackTrace();}

		String jsonString = new String(bytes);
		return new AssetAdministrationShell(new JSONObject(jsonString));
	}
	
	
	/**
	 * create a model from scratch - assuming the type already exists ... all the elements are
	 * related to their semantic definition, e.g. corresponding element from the asset type 
	 * @return
	 */
	private static Identifiable shell() {

		AssetAdministrationShell aShell = new AssetAdministrationShell("http://iasset.labor/belt2");
		aShell.setIdShort("belt2");
		// let the shell be an instance of the type
		aShell.setDerivedFrom(new Reference("http://iasset.salzburgresearch.at/labor/belt#aas",
				KeyElementsEnum.AssetAdministrationShell));
		aShell.setDescription("de", "i-Asset Labor - Förderband");

		Submodel propertiesSubmodel = new Submodel("properties", aShell);
		
		propertiesSubmodel.setIdShort("properties");
		propertiesSubmodel.setKind(Kind.Instance);
		propertiesSubmodel.setDescription("de", "i-Asset Labor - Förderband");
		propertiesSubmodel.setKind(Kind.Instance);
		propertiesSubmodel.setSemanticId(
				// create reference
				new Reference(
						// add distinct keys
						Key.of("http://iasset.salzburgresearch.at/labor/belt#aas", KeyElementsEnum.AssetAdministrationShell),
						Key.of("properties", KeyElementsEnum.Submodel)
					)
				);
		// create the properties (arranged within a element collection named beltData)
		SubmodelElementCollection beltData = new SubmodelElementCollection("beltData", propertiesSubmodel);
		beltData.setDescription("de", "Datenelemente Förderband");
		// set the semantic type
		beltData.setSemanticId(
				// create reference
				new Reference(
						// add distinct keys
						Key.of("http://iasset.salzburgresearch.at/labor/belt#aas", KeyElementsEnum.AssetAdministrationShell),
						Key.of("properties", KeyElementsEnum.Submodel),
						Key.of("beltData", KeyElementsEnum.SubmodelElementCollection)
					)
				);

		Property distance = new Property("distance", beltData);
		distance.setDescription("de", "Zurückgelegte Distanz");
		distance.setValueQualifier(DataTypeEnum.DECIMAL);
		distance.setKind(Kind.Instance);
		distance.setCategory("iAsstLabor-Tester");
		distance.setSemanticId(
				// create reference
				new Reference(
						// add distinct keys
						Key.of("http://iasset.salzburgresearch.at/labor/belt#aas", KeyElementsEnum.AssetAdministrationShell),
						Key.of("properties", KeyElementsEnum.Submodel),
						Key.of("beltData", KeyElementsEnum.SubmodelElementCollection),
						Key.of("distance", KeyElementsEnum.Property)
					)
				);
		
		Property speed = new Property("speed", beltData);
		speed.setDescription("de", "Aktuelle Geschwindigkeit");
		speed.setValueQualifier(DataTypeEnum.DECIMAL);
		speed.setKind(Kind.Instance);
		speed.setCategory("iAsstLabor-Tester");
		speed.setSemanticId(
				// create reference
				new Reference(
						// add distinct keys
						Key.of("http://iasset.salzburgresearch.at/labor/belt#aas", KeyElementsEnum.AssetAdministrationShell),
						Key.of("properties", KeyElementsEnum.Submodel),
						Key.of("beltData", KeyElementsEnum.SubmodelElementCollection),
						Key.of("speed", KeyElementsEnum.Property)
					)
				);
		
		Property state = new Property("state", beltData);
		state.setDescription("de", "Geräte-Status (On/Off)");
		state.setValueQualifier(DataTypeEnum.BOOLEAN);
		state.setKind(Kind.Instance);
		state.setCategory("iAsstLabor-Tester");
		state.setSemanticId(
				// create reference
				new Reference(
						// add distinct keys
						Key.of("http://iasset.salzburgresearch.at/labor/belt#aas", KeyElementsEnum.AssetAdministrationShell),
						Key.of("properties", KeyElementsEnum.Submodel),
						Key.of("beltData", KeyElementsEnum.SubmodelElementCollection),
						Key.of("state", KeyElementsEnum.Property)
					)
				);
		
		Property direction = new Property("direction", beltData);
		direction.setDescription("de", "Geräte-Status (On/Off)");
		direction.setValueQualifier(DataTypeEnum.STRING);
		direction.setKind(Kind.Instance);
		direction.setCategory("iAsstLabor-Tester");
		direction.setSemanticId(
				// create reference
				new Reference(
						// add distinct keys
						Key.of("http://iasset.salzburgresearch.at/labor/belt#aas", KeyElementsEnum.AssetAdministrationShell),
						Key.of("properties", KeyElementsEnum.Submodel),
						Key.of("beltData", KeyElementsEnum.SubmodelElementCollection),
						Key.of("direction", KeyElementsEnum.Property)
					)
				);
			
		Submodel events = new Submodel("events", aShell);
		events.setDescription("de", "Event-Settings - Förderband");
		events.setKind(Kind.Instance);
		events.setSemanticId(
				// create reference
				new Reference(
						// add distinct keys
						Key.of("http://iasset.salzburgresearch.at/labor/belt#aas", KeyElementsEnum.AssetAdministrationShell),
						Key.of("events", KeyElementsEnum.Submodel)
					)
				);
		EventElement eventElement = new EventElement("stateEvent", events);
		eventElement.setDescription("de", "Status-Änderungen für Förderband");
		eventElement.setSemanticId(		
				// create reference to the type element
				new Reference(
						// add distinct keys
						Key.of("http://iasset.salzburgresearch.at/labor/belt#aas", KeyElementsEnum.AssetAdministrationShell),
						Key.of("events", KeyElementsEnum.Submodel),
						Key.of("stateEvent", KeyElementsEnum.EventElement)
					)
				);
		// the event should observe the state of the belt
		eventElement.setObservableElement(state);
		eventElement.setKind(Kind.Instance);
		// the event should "send" messages, e.g. publish the events
		eventElement.setDirection(DirectionEnum.Output);
		eventElement.setMessageTopic("must be filled");
		
		Submodel operations = new Submodel("operations", aShell);
		operations.setDescription("de", "Funktionen für Förderband");
		operations.setKind(Kind.Instance);
		operations.setSemanticId(
				// create reference
				new Reference(
						// add distinct keys
						Key.of("http://iasset.salzburgresearch.at/labor/belt#aas", KeyElementsEnum.AssetAdministrationShell),
						Key.of("operations", KeyElementsEnum.Submodel)
					)
				);
		
		Operation setSpeed = new Operation("setSpeed", operations);
		setSpeed.setDescription("de", "Geschwindigkeit einstellen");
		setSpeed.setSemanticId(
				// create reference
				new Reference(
						// add distinct keys
						Key.of("http://iasset.salzburgresearch.at/labor/belt#aas", KeyElementsEnum.AssetAdministrationShell),
						Key.of("operations", KeyElementsEnum.Submodel),
						Key.of("setSpeed", KeyElementsEnum.Operation)
					)
				);
		
		setSpeed.setFunction(new Function<Map<String,Object>, Object>() {

			@Override
			public Object apply(Map<String, Object> t) {
				Map<String, Object> result = new HashMap<>();
				result.put("result", "Success: Speed setting updated to the new value: " + t.get("speed"));
				return result;
			}
		});
		/*
		 * The settings of the operation (input and output-variables) should be taken from the operation's 
		 * "type" element  
		 */
//		OperationVariable speedVariable = new OperationVariable("speed", setSpeed, DirectionEnum.Input);
//		speedVariable.setDescription("de", "Input-Variable für setSpeed");
//		speedVariable.setKind(Kind.Instance);
//		
//
//		// value of variable must be a Type-Element??
//		speedVariable.setValue(speed);
//		speedVariable.setSemanticId(null);
//		speedVariable.setSemanticId(
//				// create reference
//				new Reference(
//						// add distinct keys
//						Key.of("http://iasset.salzburgresearch.at/labor/belt#aas", KeyElementsEnum.AssetAdministrationShell),
//						Key.of("operations", KeyElementsEnum.Submodel),
//						Key.of("setSpeed", KeyElementsEnum.Operation),
//						Key.of("speed", KeyElementsEnum.OperationVariable)
//					)
//				);

		
		return aShell;

	}
}
