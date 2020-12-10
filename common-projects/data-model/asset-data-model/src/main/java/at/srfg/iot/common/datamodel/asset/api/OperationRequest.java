package at.srfg.iot.common.datamodel.asset.api;

import java.util.Map;

import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;

public class OperationRequest {
	private final Reference operation;
	
	private final Map<String, Object> parameter;

	public OperationRequest(Reference refToOperation, Map<String, Object> parameter) {
		this.operation = refToOperation;
		this.parameter = parameter;
	}
	/**
	 * @return the operation
	 */
	public Reference getOperation() {
		return operation;
	}

	/**
	 * @return the parameter map
	 */
	public Map<String, Object> getParameter() {
		return parameter;
	}
	/**
	 * return the parameter by key
	 * @param key
	 * @return
	 */
	public Object getParameter(String key) {
		return parameter.get(key);
	}
	/**
	 * return the parameter by key and cast to the expected type
	 * @param <T>
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T getParameter(String key, Class<T> clazz) {
		Object o = parameter.get(key);
		if (o != null && clazz.isInstance(o)) {
			return clazz.cast(o);
		}
		return null;
	}
	

}
