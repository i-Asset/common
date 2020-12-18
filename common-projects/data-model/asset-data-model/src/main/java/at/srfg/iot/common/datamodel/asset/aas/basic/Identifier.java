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
	@Column(name="identifier", nullable = false)
	private String id;

	@Column(name="identifier_type", nullable = false)
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
	public boolean equals(Object other) {
		if (! (other instanceof Identifier)) {
			return false;
		}
		Identifier otherId = (Identifier)other;
		if ( otherId.id == null ) return false;
		if ( this.id == null) return false;
		return this.id.equalsIgnoreCase(otherId.id);
	}
}
