package at.srfg.iot.aas.modeling;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import at.srfg.iot.aas.common.referencing.ReferableElement;


@Entity
@Table(name="aas_formula")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("Formula")
public class Formula extends Constraint {
	//bi-directional many-to-one association to Reference
	@ManyToOne
	@JoinColumn(name="depends_on_id")
	private ReferableElement dependsOn;


}
