package at.srfg.iot.common.datamodel.asset.aas.common;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.common.datamodel.asset.aas.basic.AdministrativeInformation;
import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.IdType;

public interface Identifiable extends Referable {
	public AdministrativeInformation getAdministration();
	public void setAdministration(AdministrativeInformation administration);
	public void setIdentification(Identifier identification);
	/**
	 * Return the {@link Identifier} for the current {@link Identifiable} element.
	 * <p>
	 * If not yet specfied, the method will create an {@link Identifier} with {@link IdType#IdShort}
	 * and an arbitrary generated UUID value.
	 * </id>
	 * @return
	 */
	public Identifier getIdentification();
	@JsonIgnore
	default IdType getIdType() {
		if (getIdentification() != null) { 
			return getIdentification().getIdType();
		}
		return IdType.IdShort;
	}
	/**
	 * Return the identifier for the {@link Identifiable} element.
	 * <p>
	 * In case the identifiable element does not have a global
	 * identifier, e.g. has a id of {@link IdType#IdShort}, the 
	 * corresponding {@link #getIdShort()} is returned. 
	 * @return 
	 */
	@JsonIgnore
	default String getId() {
		if (getIdentification().getIdType().equals(IdType.IdShort)) {
			Referable parent = getParentElement();
			if ( Identifiable.class.isInstance(parent)) {
				Identifiable i = Identifiable.class.cast(parent);
				return i.getId()
						.concat("#")
						.concat(getIdShort());
			}
		}
		//
		return getIdentification().getId();
	}
	@JsonIgnore
	default void setId(String id) {
		Identifier identifier = new Identifier(id);
		if ( IdType.IdShort.equals(identifier.getIdType())) {
			// an identifiable requires an uniqe id value, an arbitrary idShort is not valid
			setIdentification(null);
			setIdShort(id);
		}
		else {
			setIdentification(identifier);
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
