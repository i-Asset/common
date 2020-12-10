package at.srfg.iot.common.datamodel.asset.aas.common.referencing;

import at.srfg.iot.common.datamodel.asset.aas.basic.Asset;
import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.GlobalReference;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.AssetAdministrationShellDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.basic.directory.SubmodelDescriptor;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.dictionary.ConceptDescription;
import at.srfg.iot.common.datamodel.asset.aas.dictionary.ConceptDictionary;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Blob;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.EventElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.File;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Operation;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.OperationVariable;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.ReferenceElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.RelationshipElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.SubmodelElementCollection;

public enum KeyElementsEnum {
	GlobalReference(GlobalReference.class),
	AccessPermissionRule(null),
	Blob(Blob.class),
	ConceptDictionary(ConceptDictionary.class),
	ConceptDescription(ConceptDescription.class),
	DataElement(null),
	File(File.class),
	EventElement(EventElement.class),
	Operation(Operation.class),
	OperationVariable(OperationVariable.class),
	Property(Property.class),
	ReferenceElement(ReferenceElement.class),
	RelationshipElement(RelationshipElement.class),
	Submodel(Submodel.class),
	SubmodelElement(null),
	SubmodelElementCollection(SubmodelElementCollection.class),
	View(null),
	Asset(Asset.class),
	MultiLanguageProperty(null),
	AssetAdministrationShell(AssetAdministrationShell.class),
	// Descriptors
	SubmodelDescriptor(SubmodelDescriptor.class),
	AssetAdministrationShellDescriptor(AssetAdministrationShellDescriptor.class),
	// non persistent
	Reference(Reference.class),
	;
	private Class<? extends Referable> clazz;
	KeyElementsEnum(Class<? extends Referable> clazz) {
		this.clazz =clazz;
	}
	public Class<? extends Referable> getElementClass() {
		return this.clazz;
	}
	
}
