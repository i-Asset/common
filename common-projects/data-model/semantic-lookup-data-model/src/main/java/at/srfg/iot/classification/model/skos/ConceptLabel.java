package at.srfg.iot.classification.model.skos;

import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.classification.model.ConceptBase;

@Entity
@Table(name = "concept_label")
public class ConceptLabel implements SKOSLabel {
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private ConceptBase concept;
	@Column(name = "language", nullable = false, length = 2)
	private Locale locale;
	@Column(name = "label_type")
	@Enumerated(EnumType.STRING)
	private LabelType labelType;
	@Column(name="label", nullable = false, length=1023)
	private String label;

	public ConceptLabel() {
		// default
	}
	public ConceptLabel(ConceptBase base, Locale locale, LabelType labelType, String label) {
		this.concept = base;
		this.locale = locale;
		this.labelType = labelType;
		this.label = label;
	}
	@Override
	public LabelType getLabelType() {
		return labelType;
	}

	@Override
	public void setLabelType(LabelType labelType) {
		this.labelType = labelType;

	}

	@Override
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}

}