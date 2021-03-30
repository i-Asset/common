package at.srfg.iot.common.datamodel.asset.aas.basic.directory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import at.srfg.iot.common.datamodel.asset.aas.basic.Endpoint;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.common.DirectoryEntry;
import at.srfg.iot.common.datamodel.asset.aas.common.HasKind;
import at.srfg.iot.common.datamodel.asset.aas.common.HasSemantics;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.IdentifiableElement;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;

public class SubmodelDescriptor extends IdentifiableElement implements DirectoryEntry, HasSemantics, HasKind {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Referable semanticElement;
	
	private Kind kind;

	public SubmodelDescriptor() {
		// required default constructor
	}
	public SubmodelDescriptor(Submodel forModel) {
		setIdShort(forModel.getIdShort());
		// TODO: deal with idShort!!
		setIdentification(forModel.getIdentification());
		setDescription(forModel.getDescription());
		setEndpoints(forModel.getEndpoints());
		setSemanticId(forModel.getSemanticId());
		setKind(forModel.getKind());
	}
	
	private Map<Integer, Endpoint> endpoints;

	public Map<Integer, Endpoint> getEndpoints() {
		return endpoints;
	}
	public void setEndpoints(Map<Integer, Endpoint> endpoints) {
		if ( endpoints != null) {
			this.endpoints = new HashMap<Integer, Endpoint>();
			for (Integer i : endpoints.keySet()) {
				this.endpoints.put(i, Endpoint.create(this, i, endpoints.get(i).getAddress(), endpoints.get(i).getType()));
			}
		}
	}
	@Override
	public Referable getSemanticElement() {
		return semanticElement;
	}
	@Override
	public void setSemanticElement(Referable semanticId) {
		this.semanticElement = ReferableElement.class.cast(semanticId);
		
	}
	@Override
	public Kind getKind() {
		return kind;
	}
	@Override
	public void setKind(Kind kind) {
		this.kind = kind;
		
	}
	@Override
	public Optional<Referable> asInstance() {
		throw new IllegalStateException("Wrong usage!");
	}
}
