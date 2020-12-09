package at.srfg.iot.api;

public interface IDataElement<T extends Object> extends ISubmodelElement {
	
	public T getValue();
	public void setValue(T value);

}
