package at.srfg.iot.api;

import java.util.Optional;

import at.srfg.iot.aas.common.DirectoryEntry;
import at.srfg.iot.aas.common.HasDataSpecification;
import at.srfg.iot.aas.common.Identifiable;
import at.srfg.iot.aas.common.Referable;
import at.srfg.iot.aas.common.referencing.Reference;

public interface IAssetAdministrationShell extends Referable, Identifiable, HasDataSpecification, DirectoryEntry {

	/**
	 * Create a 
	 * @return
	 */
	Reference getDerivedFrom();
	/**
	 * get the reference to the parent AAS
	 * @param reference
	 */
	void setDerivedFrom(Reference reference);
	/**
	 * 
	 * @param model
	 */
	void addSubmodel(ISubmodel model);
	boolean removeSubmodel(ISubmodel submodel);
	/**
	 * Obtain a submodel based on it's (local) identifier
	 * @param idShort
	 * @return
	 */
	Optional<ISubmodel> getSubmodel(String idShort);

}
