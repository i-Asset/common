package at.srfg.iot.aas.common.referencing;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the multilanguage descriptions database table.
 * 
 */
@Entity
@Table(name="aas_referable_description")
public class ReferableDescription implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@EmbeddedId
	private ReferableDescriptionPK id;

	private String description;
	

	public ReferableDescription() {
	}
	protected ReferableDescription(ReferableElement modelElement, String language, String description) {
		this.id = new ReferableDescriptionPK(modelElement, language);
		this.description = description;
	}

	public void setLanguage(String lang) {
		this.id = new ReferableDescriptionPK(null, lang);
	}
	public String getLanguage() {
		return id.getLanguage();
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}