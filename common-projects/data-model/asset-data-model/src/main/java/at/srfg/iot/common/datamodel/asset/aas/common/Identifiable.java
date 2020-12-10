package at.srfg.iot.common.datamodel.asset.aas.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.common.datamodel.asset.aas.basic.AdministrativeInformation;
import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.IdType;

public interface Identifiable extends Referable {
	public AdministrativeInformation getAdministration();
	public void setAdministration(AdministrativeInformation administration);
	public void setIdentification(Identifier identification);
	public Identifier getIdentification();
	@JsonIgnore
	default IdType getIdType() {
		if (getIdentification() != null) { 
			return getIdentification().getIdType();
		}
		return IdType.IdShort;
	}
	@JsonIgnore
	default String getId() {
		if (getIdentification() != null) {
			return getIdentification().getId();
		}
		return "";
	}
	default void setId(String id) {
		if ( getIdentification() == null) {
			setIdentification(new Identifier(id));
		}
		else {
			// detect the type
			getIdentification().setIdType(IdType.getType(id));
			// set the id
			getIdentification().setId(id);
		}
	}
	default void setVersion(String version) {
		if ( getAdministration() == null) {
			setAdministration(new AdministrativeInformation());
		}
		getAdministration().setVersion(version);
	}
	default void setRevision(String revision) {
		if ( getAdministration() == null) {
			setAdministration(new AdministrativeInformation());
		}
		getAdministration().setRevision(revision);
	}

}
