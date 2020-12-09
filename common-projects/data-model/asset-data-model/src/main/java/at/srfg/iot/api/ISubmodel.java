package at.srfg.iot.api;

import at.srfg.iot.aas.common.DirectoryEntry;
import at.srfg.iot.aas.common.HasDataSpecification;
import at.srfg.iot.aas.common.HasKind;
import at.srfg.iot.aas.common.HasSemantics;
import at.srfg.iot.aas.common.Identifiable;
import at.srfg.iot.aas.common.Qualifiable;
import at.srfg.iot.aas.common.Referable;
import at.srfg.iot.aas.common.SubmodelElementContainer;

public interface ISubmodel
		extends Referable, Identifiable, HasSemantics, HasKind, HasDataSpecification, DirectoryEntry, Qualifiable, SubmodelElementContainer {

}
