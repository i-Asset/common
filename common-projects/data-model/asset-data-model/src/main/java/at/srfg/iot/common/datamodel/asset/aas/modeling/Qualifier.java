package at.srfg.iot.common.datamodel.asset.aas.modeling;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import at.srfg.iot.common.datamodel.asset.aas.common.HasSemantics;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;

@Entity
@Table(name = "aas_qualifier")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("Constraint")
public class Qualifier extends Constraint implements HasSemantics {

	@Column(name="qualifier_type")
	private String qualifierType;

	@Column(name="qualifier_value")
	private String qualifierValue;
	
	@ManyToOne
	@JoinColumn(name="qualifier_value_id")
	private ReferableElement qualifierValueId;
	
	@ManyToOne
	@JoinColumn(name = "semantic_id")
	private ReferableElement semanticId;

	public Optional<Referable> getSemanticElement() {
		return Optional.ofNullable(semanticId);
	}

	public void setSemanticElement(Referable semanticId) {
		this.semanticId = ReferableElement.class.cast(semanticId);
	}

}
