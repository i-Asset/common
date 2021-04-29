package at.srfg.iot.common.datamodel.asset.aas.basic;

import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.common.datamodel.asset.aas.common.HasDataSpecification;
import at.srfg.iot.common.datamodel.asset.aas.common.HasKind;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.IdentifiableElement;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;
@Entity
@Table(name="aas_asset")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public class Asset extends IdentifiableElement implements Referable, Identifiable, HasKind, HasDataSpecification {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToMany
	@JoinTable(name = "aas_asset_data_spec", joinColumns = {
			@JoinColumn(name = "model_element_id") }, inverseJoinColumns = { @JoinColumn(name = "spec_element_id") })
	private List<ReferableElement> dataSpecification;
	/**
	 * the {@link Kind} of the element, either {@link Kind#Type}
	 * or {@link Kind#Instance}
	 */
	@Column(name = "kind", nullable = false)
	private Kind kind = Kind.Type;
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="aas_element_id")
	private AssetAdministrationShell assetAdministrationShell;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="identification_element_id")
	private Submodel assetIdentificationModel;
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="bom_element_id")
	private Submodel billOfMaterials;
	
	public Asset() {
		
	}
	public Asset(Identifier identification) {
		setIdentification(identification);
	}
	public Asset(Submodel identifyingSubmodel) {
		setAssetIdentificationModel(identifyingSubmodel);
	}
	/**
	 * @return the dataSpecification
	 */
	public List<ReferableElement> getDataSpecification() {
		return dataSpecification;
	}
	/**
	 * @param dataSpecification the dataSpecification to set
	 */
	public void setDataSpecification(List<ReferableElement> dataSpecification) {
		this.dataSpecification = dataSpecification;
	}
	/**
	 * @return the assetAdministrationShell
	 */
	public AssetAdministrationShell getAssetAdministrationShell() {
		return assetAdministrationShell;
	}
	/**
	 * @param assetAdministrationShell the assetAdministrationShell to set
	 */
	public void setAssetAdministrationShell(AssetAdministrationShell assetAdministrationShell) {
		this.assetAdministrationShell = assetAdministrationShell;
	}
	public Submodel getAssetIdentificationModel() {
		return assetIdentificationModel;
	}
	public void setAssetIdentificationModel(Submodel assetIdentificationModel) {
		this.assetIdentificationModel = assetIdentificationModel;
	}
	public Submodel getBillOfMaterials() {
		return billOfMaterials;
	}
	public void setBillOfMaterials(Submodel billOfMaterials) {
		this.billOfMaterials = billOfMaterials;
	}
	public Kind getKind() {
		return kind;
	}
	public void setKind(Kind assetKind) {
		this.kind = assetKind;
	}
	public Optional<Referable> asInstance(Referable parent) {
		if ( AssetAdministrationShell.class.isInstance(parent)) {
			AssetAdministrationShell shell = AssetAdministrationShell.class.cast(parent);
			Asset instance = new Asset();
			instance.setKind(Kind.Instance);
			shell.setAsset(instance);
			return Optional.of(instance);
		}
		throw new IllegalStateException("Parent element must be an AssetAdministrationShell");
	}
}
