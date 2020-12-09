package at.srfg.iot.aas.security;

import java.util.List;

import at.srfg.iot.aas.common.Qualifiable;
import at.srfg.iot.aas.common.referencing.ReferableElement;
import at.srfg.iot.aas.modeling.Constraint;

public class AccessPermissionRule extends ReferableElement implements Qualifiable {
	/**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private List<SubjectAttribute> subjectAttributes;
	private PermissionsPerObject permissionsPerObject;
	private List<Constraint> qualifier;

	@Override
	public List<Constraint> getQualifier() {
		return qualifier;
	}

	@Override
	public void setQualifier(List<Constraint> qualifier) {
		this.qualifier = qualifier;
	}

}
