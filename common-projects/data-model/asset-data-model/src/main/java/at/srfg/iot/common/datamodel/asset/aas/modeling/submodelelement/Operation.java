package at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;
import at.srfg.iot.common.datamodel.asset.aas.common.types.DirectionEnum;
import at.srfg.iot.common.datamodel.asset.aas.modeling.SubmodelElement;

@Entity
@Table(name="aas_submodel_element_operation")
@Inheritance(strategy = InheritanceType.JOINED) 
@PrimaryKeyJoinColumn(name="model_element_id")
public class Operation extends SubmodelElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	
	@Transient
	@JsonIgnore
	private Function<Map<String,Object>, Object> function;

	public Operation() {
		// default
	}
	public Operation(SubmodelElementContainer container) {
		super(container);
	}
	/**
	 * Convenience constructor. Creates and assigns the {@link Operation} as
	 * a direct child element to the provided {@link SubmodelElementContainer}.
	 * @param idShort
	 * @param submodel
	 */
	public Operation(String idShort, SubmodelElementContainer submodel) {
		super(idShort, submodel);
	}
	public void addOperationVariable(OperationVariable variable) {
		variable.setParentElement(this);
		addChildElement(variable);
	}
	/**
	 * Retrieve a {@link OperationVariable} by it's idShort
	 * @param idShort
	 * @return
	 */
	public Optional<OperationVariable> getOperationVariable(String idShort) {
		return getChildElement(idShort, OperationVariable.class);
	}
	public void addChildElement(Referable child) {
		if ( OperationVariable.class.isInstance(child)) {
			getChildElements().add(OperationVariable.class.cast(child));
		}
	}
	/**
	 * @return the in
	 */
	public List<OperationVariable> getIn() {
		if ( getChildElements().size()>0) {
			return getChildElements().stream()
					.filter(new Predicate<ReferableElement>() {
	
						@Override
						public boolean test(ReferableElement t) {
							if ( t instanceof OperationVariable) {
								return ((OperationVariable)t).getDirection() == DirectionEnum.Input;
							};
							return false;
						}
					})
					.map(new Function<ReferableElement, OperationVariable>() {
	
						@Override
						public OperationVariable apply(ReferableElement t) {
							return OperationVariable.class.cast(t);
						}
						
					})
					.collect(Collectors.toList());
		}
		return new ArrayList<OperationVariable>();
	}
	/**
	 * @param in the in to set
	 */
	public void setIn(List<OperationVariable> in) {
		for (OperationVariable i : in) {
			addChildElement(i);
		}

	}
	/**
	 * @return the out
	 */
	public List<OperationVariable> getOut() {
		if ( getChildElements().size()>0) {
			return getChildElements().stream()
					.filter(new Predicate<ReferableElement>() {
	
						@Override
						public boolean test(ReferableElement t) {
							if ( t instanceof OperationVariable) {
								return ((OperationVariable)t).getDirection() == DirectionEnum.Output;
							};
							return false;
						}
					})
					.map(new Function<ReferableElement, OperationVariable>() {
	
						@Override
						public OperationVariable apply(ReferableElement t) {
							return OperationVariable.class.cast(t);
						}
						
					})
					.collect(Collectors.toList());
		}
		return new ArrayList<OperationVariable>();
	}
	/**
	 * @param out the out to set
	 */
	public void setOut(List<OperationVariable> out) {
		this.getChildElements().addAll(out);
	}
	public Map<String,Object> invoke(Map<String,Object> params) {
		// OPTIONALLY validate in & out
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("timestamp", LocalDateTime.now());
		if ( function!= null) {
			boolean ok = true;
			if ( !getIn().isEmpty()) {
				ok = getIn().stream().allMatch(new Predicate<OperationVariable>() {

					@Override
					public boolean test(OperationVariable t) {
						return params.containsKey(t.getIdShort());
					}
				});
			}
			if ( ok ) {
				Object result = function.apply(params);
				resultMap.put("result", result);
				resultMap.put("success", true);
				return resultMap;
			}
			else {
				resultMap.put("error", "Not all parameters have been provided");
				resultMap.put("error", "Not all parameters have been provided");
				resultMap.put("success", false);
				return resultMap;
			}
		}
		resultMap.put("error", "No function has been provided");
		resultMap.put("success", false);
		return resultMap;
	}
	/**
	 * @param function the function to set
	 */
	public void setFunction(Function<Map<String, Object>, Object> function) {
		this.function = function;
	}
	
	@Override
	public Optional<Referable> asInstance(Referable parent) {
		if ( isInstance()) {
			return Optional.empty();
		}
		if (SubmodelElementContainer.class.isInstance(parent)) {
			Operation instance = new Operation(getIdShort(), SubmodelElementContainer.class.cast(parent));
			instance.setKind(Kind.Instance);
			instance.setCategory(getCategory());
			instance.setDescription(getDescription());
			instance.setSemanticElement(this);
			for (OperationVariable variable : getChildElements(OperationVariable.class)) {
				variable.asInstance(instance);
			}
			return Optional.of(instance);
		}
			
		throw new IllegalStateException("Provided parent must be a SubmodelElementContainer");
	}	

}
