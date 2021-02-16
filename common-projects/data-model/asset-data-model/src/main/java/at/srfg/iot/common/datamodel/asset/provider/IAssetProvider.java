package at.srfg.iot.common.datamodel.asset.provider;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.DataElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Operation;
import at.srfg.iot.common.datamodel.asset.api.IAssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.api.ISubmodel;

/**
 * Model provider for a single IAsset Component.
 * 
 * The methods allow the manipulation of an {@link AssetAdministrationShell} 
 * 
 * @author dglachs
 *
 */
public interface IAssetProvider {
	public IAssetAdministrationShell getShell();
	
	public Identifiable getRoot();
	/**
	 * Retrieve the {@link Submodel} by it's idShort from the component
	 * @param idShort
	 * @return
	 */
	public Optional<ISubmodel> getSubmodel(String idShort);
	/**
	 * create a new {@link ISubmodel} within the component
	 * @param submodel
	 */
	public void setSubmodel(ISubmodel submodel);
	
	/**
	 * Retrieve the full element (including sub-elements) 
	 * 
	 * @param reference
	 * @return
	 */
	public Optional<Referable> getElement(Reference reference);
	/**
	 * Retrieve the value of the referenced element. 
	 * 
	 * @param reference Reference pointing to a {@link DataElement}
	 * @return
	 */
	public Object getElementValue(Reference reference);
	/**
	 * Update the value of an existing element. 
	 * @param element The reference to the element whose value should be updated
	 * @param value The new value of the element
	 * @return
	 */
	public Referable setElementValue(Reference element, Object value);
	/**
	 * Create or replace an element
	 * @param parent Reference to the element containing the newly provided element
	 * @param element The element to add
	 * @return
	 */
	public Referable setElement(Reference parent, Referable element);
	/**
	 * Set the new value of the referenced element.
	 * @param element
	 * @param value
	 * @return
	 */
	public Referable setElement(String submodelIdentifier, String path, Referable element);
	/**
	 * Convenience method to delete an object from the model. The {@link Referable} must 
	 * have proper parent element in order to resolve it by it's reference (see {@link Referable#asReference()})
	 * @param referable The element to remove from the model
	 * @return
	 */
	public boolean deleteElement(Referable referable);
	/**
	 * Execute the referenced operation
	 * @param reference Reference pointing to an {@link Operation}
	 * @param parameter Map holding the parameter values for each {@link Operation#getIn()}
	 * @return 
	 */
	public Map<String, Object> execute(Reference reference, Map<String, Object> parameter);
	/**
	 * Retrieve an element by it's path
	 * @param path
	 * @return
	 */
	public Optional<Referable> getElement(String path);
	/**
	 * 
	 * @param <T>
	 * @param path
	 * @param clazz
	 * @return
	 */

	<T extends Referable> Optional<T> getElement(String path, Class<T> clazz);
	<T extends Referable> Optional<T> getElement(String submodelIdShort, String path, Class<T> clazz);
	void setValueConsumer(String pathToProperty, Consumer<String> consumer);
	void setValueSupplier(String pathToProperty, Supplier<String> supplier);
	void setFunction(String pathToOperation, Function<Map<String, Object>, Object> function);
	void addModelListener(IAssetModelListener listener);
}