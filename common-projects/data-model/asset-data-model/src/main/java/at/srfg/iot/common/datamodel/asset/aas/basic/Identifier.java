package at.srfg.iot.common.datamodel.asset.aas.basic;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.IdType;
/**
 * Embeddable element storing the administrative 
 * information for {@link Identifiable} elements.
 *
 */
@Embeddable
public class Identifier {
	@Column(name="identifier")
	private String id;

	@Column(name="identifier_type")
	private IdType idType;

	public Identifier() {
		
	}
	public Identifier(String id) {
		// determine the id type
		this(IdType.getType(id), id);			
	
	}
	public Identifier(IdType idType, String id) {
		this.idType = idType;
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	public void setIdType(IdType idType) {
		this.idType = idType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
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
		Identifier other = (Identifier) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idType != other.idType)
			return false;
		return true;
	}
	
	public String toString() {
		return idType.name() + ": " + id;
	}
}
