package at.srfg.iot.aas.common.referencing;

import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.common.Identifiable;
import at.srfg.iot.aas.common.Referable;

public class Key {
	private String value;
	private IdType idType;
	private boolean local;
	private KeyElementsEnum type;
	
	public static Key of(Referable referable) {
		// when current referable is an identifiable and no parent present 
		if ( referable.getParentElement() == null && Identifiable.class.isInstance(referable)) {
				
			Identifiable i = (Identifiable) referable;
			Key key = new Key();
			key.setLocal(true);
			key.setIdType(i.getIdType());
			key.setValue(i.getId());
			if ( i.getIdType() == IdType.IRI && i.getModelType() == KeyElementsEnum.GlobalReference) {
				key.setLocal(false);
			}
			key.setType(referable.getModelType());
			return key;
		}
		// otherwise use the IdShort as key
		else {
			Key key = new Key();
			key.setLocal(true);
			key.setIdType(IdType.IdShort);
//			key.setType(referable.get);
			key.setValue(referable.getIdShort());
			key.setType(referable.getModelType());
			return key;
		}
	}
	public static Key of(String id, KeyElementsEnum modelType) {
		Key key = new Key();
		Identifier identifier = new Identifier(id);
		key.setValue(identifier.getId());
		key.setIdType(identifier.getIdType());
		key.setType(modelType);
		return key;
	}
	public static Key of(String elementType, String id, IdType idType) {
		Key key = new Key();
		Identifier identifier = new Identifier(id);
		key.setValue(identifier.getId());
		key.setIdType(identifier.getIdType());
		
		try {
			key.setType(KeyElementsEnum.valueOf(elementType));
		} catch (Exception e) {
			key.setType(KeyElementsEnum.GlobalReference);
		}
		if ( key.getIdType() == IdType.IRI && key.getType() == KeyElementsEnum.GlobalReference) {
			key.setLocal(false);
		}
		else {
			key.setLocal(true);
		}
		return key;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String id) {
		this.value = id;
	}
	/**
	 * @return the idType
	 */
	public IdType getIdType() {
		return idType;
	}
	/**
	 * @param idType the idType to set
	 */
	public void setIdType(IdType keyType) {
		this.idType = keyType;
	}
	/**
	 * @return the local
	 */
	public boolean isLocal() {
		return local;
	}
	/**
	 * @param local the local to set
	 */
	public void setLocal(boolean local) {
		this.local = local;
	}
	/**
	 * @return the type
	 */
	public KeyElementsEnum getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(KeyElementsEnum type) {
		this.type = type;
	}
	public Identifier asIdentifier() {
		return new Identifier(value);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Key other = (Key) obj;
		if (idType != other.idType)
			return false;
		if (type != other.type)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}
