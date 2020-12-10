package at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;

@Entity
@Table(name="aas_submodel_element_blob")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "model_element_id")
public class Blob extends DataElement<byte[]> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="blob_value")
	private byte[] byteValue;
	@Column(name="mime_type")
	private String mimeType;
	
	public Blob() {
		// default
	}
	public Blob(SubmodelElementContainer container) {
		super(container);
	}
	/**
	 * Convenience constructor. Creates and assigns the {@link Blob} as
	 * a direct child element to the provided {@link Submodel}.
	 * @param idShort
	 * @param submodel
	 */
	public Blob(String idShort, SubmodelElementContainer submodel) {
		super(idShort, submodel);
	}

	/**
	 * @return the value
	 */
	public byte[] getValue() {
		return byteValue;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(byte[] value) {
		this.byteValue = value;
	}
	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}
	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	@Override
	public Optional<Referable> asInstance(Referable parent) {
		if ( isInstance()) {
			return Optional.empty();
		}
		if (SubmodelElementContainer.class.isInstance(parent)) {
			Blob instance = new Blob(getIdShort(), SubmodelElementContainer.class.cast(parent));
			instance.setKind(Kind.Instance);
			instance.setCategory(getCategory());
			instance.setDescription(getDescription());
			instance.setMimeType(getMimeType());
			instance.setSemanticElement(this);
			return Optional.of(instance);
		}
			
		throw new IllegalStateException("Provided parent must be a SubmodelElementContainer");
	}
	
	// TODO: add value Id Reference
	

}
