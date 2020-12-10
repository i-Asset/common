package at.srfg.iot.common.datamodel.asset.aas.basic.directory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import at.srfg.iot.common.datamodel.asset.aas.basic.Asset;
import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Endpoint;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.common.DirectoryEntry;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.IdentifiableElement;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;

public class AssetAdministrationShellDescriptor extends IdentifiableElement implements DirectoryEntry {

	public AssetAdministrationShellDescriptor() {
		// default constructor
	}
	public AssetAdministrationShellDescriptor(AssetAdministrationShell forShell) {
		setIdShort(forShell.getIdShort());
		setIdentification(forShell.getIdentification());
		setDescription(forShell.getDescription());
		setEndpoints(forShell.getEndpoints());
		setDerivedFrom(forShell.getDerivedFrom());
		// descriptor holds Asset 
		setAsset(forShell.getAsset());
		// descriptor holds SubmodelDescriptors
		setSubmodels(getSubmodelDescriptors(forShell));
	}
	private Collection<SubmodelDescriptor> getSubmodelDescriptors(AssetAdministrationShell forShell) {
		Collection<SubmodelDescriptor> list = new ArrayList<SubmodelDescriptor>();
		// obtain all children of type "Submodel"
		for (Submodel model : forShell.getChildElements(Submodel.class)) {
			SubmodelDescriptor subDesc = new SubmodelDescriptor(model);
			Optional<Endpoint> ep = forShell.getFirstEndpoint();
			if ( ep.isPresent()) {
				// TODO: check the construction of the path 
				subDesc.setEndpoint(0, ep.get().getAddress()+"/element/" + model.getIdShort(), ep.get().getType());
			}
			list.add(subDesc);
		}
		return list;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Asset asset;
	
	private Reference derivedFrom;
	
	private Collection<SubmodelDescriptor> submodels;
	
	private Map<Integer, Endpoint> endpoints;
	public Map<Integer, Endpoint> getEndpoints() {
		return endpoints;
	}
	public void setEndpoints(Map<Integer, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}
	public Asset getAsset() {
		return asset;
	}
	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	public Collection<SubmodelDescriptor> getSubmodels() {
		return submodels;
	}
	public void setSubmodels(Collection<SubmodelDescriptor> submodels) {
		this.submodels = submodels;
	}
	/**
	 * @return the derivedFrom
	 */
	public Reference getDerivedFrom() {
		return derivedFrom;
	}
	/**
	 * @param derivedFrom the derivedFrom to set
	 */
	public void setDerivedFrom(Reference derivedFrom) {
		this.derivedFrom = derivedFrom;
	}
}
