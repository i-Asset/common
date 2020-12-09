package at.srfg.iot.api;

import at.srfg.iot.aas.common.HasDataSpecification;
import at.srfg.iot.aas.common.HasKind;
import at.srfg.iot.aas.common.HasSemantics;
import at.srfg.iot.aas.common.Qualifiable;
import at.srfg.iot.aas.common.Referable;

public interface ISubmodelElement extends Referable, HasSemantics, HasKind, HasDataSpecification, Qualifiable {
	void setDerived(Boolean derived);
}
