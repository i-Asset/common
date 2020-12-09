package at.srfg.iot.aas.common.types;

import java.util.Date;

public enum DataTypeEnum {
	STRING(String.class), 
	INTEGER(Integer.class),
	DECIMAL(Double.class),
	DATE_TIME(Date.class),
	BOOLEAN(Boolean.class),
	;
	private Class<?> valueType;
	DataTypeEnum(Class<?> valueType) {
		this.valueType = valueType;
	}
}
