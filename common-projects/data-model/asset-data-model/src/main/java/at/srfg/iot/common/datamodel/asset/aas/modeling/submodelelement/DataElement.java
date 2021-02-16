package at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement;

import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.modeling.SubmodelElement;
import at.srfg.iot.common.datamodel.asset.api.IDataElement;
/**
 * A data element is a submodel element that is not 
 * further composed out of other submodel elements.
 * <p>
 * Subclasses specifiy the data type of the element</p> 
 *
 * @param <T> The DataType of the Data Element in the
 * database, e.g. <code>String</code> for {@link Property}, <code>byte[]</code> for {@link Blob} etc.
 */
public abstract class DataElement<T> extends SubmodelElement implements IDataElement<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DataElement() {
		super();
	}
	public DataElement(SubmodelElementContainer container) {
		super(container);
	}
	public DataElement(String idShort, SubmodelElementContainer container) {
		super(idShort, container);
	}
	public abstract T getValue();
	public abstract void setValue(T value);

}
