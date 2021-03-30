package at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;

@Entity
@Table(name="aas_submodel_element_file")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public class File extends DataElement<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="file_path", nullable = false)
	private String filePath;
	@Column(name="mime_type", nullable = false)
	private String mimeType;
	
	public File() {
		//
	}
	public File(SubmodelElementContainer container) {
		super(container);
	}
	/**
	 * Convenience constructor. Creates and assigns the {@link File} as
	 * a direct child element to the provided {@link SubmodelElementContainer}.
	 * @param idShort
	 * @param submodel
	 */
	public File(String idShort, SubmodelElementContainer submodel) {
		super(idShort, submodel);
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return filePath;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.filePath = value;
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
	public void setMimeType(String mime_type) {
		this.mimeType = mime_type;
	}
	@Override
	public Optional<Referable> asInstance() {
		if ( isInstance()) {
			return Optional.of(this);
		}
		File instance = new File();
		instance.setIdShort(getIdShort());
		instance.setKind(Kind.Instance);
		instance.setCategory(getCategory());
		instance.setDescription(getDescription());
		instance.setMimeType(getMimeType());
		instance.setSemanticElement(this);
		return Optional.of(instance);
	}	
	//@TODO: add value Id Reference
	

}
