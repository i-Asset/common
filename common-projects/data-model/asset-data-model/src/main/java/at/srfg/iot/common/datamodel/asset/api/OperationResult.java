package at.srfg.iot.common.datamodel.asset.api;

import java.time.LocalDateTime;
import java.util.Map;

public class OperationResult {
	private int status;
	private LocalDateTime time;
	private Map<String, Object> result;
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the time
	 */
	public LocalDateTime getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	/**
	 * @return the result
	 */
	public Map<String, Object> getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}
}
