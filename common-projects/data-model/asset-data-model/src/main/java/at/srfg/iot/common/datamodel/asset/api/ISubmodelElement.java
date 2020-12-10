package at.srfg.iot.common.datamodel.asset.api;

import at.srfg.iot.common.datamodel.asset.aas.common.HasDataSpecification;
import at.srfg.iot.common.datamodel.asset.aas.common.HasKind;
import at.srfg.iot.common.datamodel.asset.aas.common.HasSemantics;
import at.srfg.iot.common.datamodel.asset.aas.common.Qualifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;

public interface ISubmodelElement extends Referable, HasSemantics, HasKind, HasDataSpecification, Qualifiable {
	void setDerived(Boolean derived);
}
