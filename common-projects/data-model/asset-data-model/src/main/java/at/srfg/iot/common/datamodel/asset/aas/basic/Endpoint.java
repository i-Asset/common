package at.srfg.iot.common.datamodel.asset.aas.basic;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.common.datamodel.asset.aas.common.referencing.IdentifiableElement;

@Entity
@Table(name = "aas_endpoint")
public class Endpoint {
	@JsonIgnore
	@EmbeddedId
	private EndpointPK id;
	@Column(name="address",nullable = false)
	private String address;
	@Column(name="type", nullable = false)
	private String type;
	
	public Endpoint() {
		
	}
	public static Endpoint create(IdentifiableElement registration, Integer  index, String address, String type) {
		return new Endpoint(registration, index, address, type);
	}
	protected Endpoint(IdentifiableElement registration, Integer  index, String address, String type) {
		this.id = new EndpointPK(registration, index);
		this.address = address;
		this.type =  type;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
