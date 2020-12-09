package at.srfg.iot.aas.common.referencing;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import at.srfg.iot.aas.basic.AdministrativeInformation;
import at.srfg.iot.aas.basic.Asset;
import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.common.Identifiable;
import at.srfg.iot.aas.dictionary.ConceptDescription;
/**
 * Abstract base class representing {@link Identifiable} elements.
 * <p>
 * Any {@link Identifiable} element provides an {@link Identifier} as
 * well as {@link AdministrativeInformation}. The {@link Identifier}
 * must be globally unique, thus it is not allowed to have duplicates. 
 * </p>
 * Subclasses are {@link AssetAdministrationShell}, {@link Submodel},
 * {@link Asset} and {@link ConceptDescription}.
 * <p>
 * </p>
 * @see ReferableElement
 * @author dglachs
 *
 */
@Entity
@Table(name="aas_identifiable", uniqueConstraints = {
		@UniqueConstraint(name = "identifier_ak", columnNames = {"identifier_type", "identifier"})
})
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public abstract class IdentifiableElement extends ReferableElement implements Identifiable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Embedded
	private AdministrativeInformation administration;
	@Embedded
	private Identifier identification;

	public void setIdType(IdType idType) {
		if ( identification == null) {
			identification = new Identifier();
		}
		this.identification.setIdType(idType);
	}
	/**
	 * @return the administration
	 */
	public AdministrativeInformation getAdministration() {
		return administration;
	}
	/**
	 * @param administration the administration to set
	 */
	public void setAdministration(AdministrativeInformation administration) {
		this.administration = administration;
	}
	/**
	 * @return the identification
	 */
	public Identifier getIdentification() {
		return identification;
	}
	/**
	 * @param identification the identification to set
	 */
	public void setIdentification(Identifier identification) {
		this.identification = identification;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((administration == null) ? 0 : administration.hashCode());
		result = prime * result + ((identification == null) ? 0 : identification.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdentifiableElement other = (IdentifiableElement) obj;
		if (administration == null) {
			if (other.administration != null)
				return false;
		} else if (!administration.equals(other.administration))
			return false;
		if (identification == null) {
			if (other.identification != null)
				return false;
		} else if (!identification.equals(other.identification))
			return false;
		return super.equals(obj);
	}


}
