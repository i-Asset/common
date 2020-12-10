package at.srfg.iot.common.datamodel.asset.aas.common;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import at.srfg.iot.common.datamodel.asset.aas.modeling.SubmodelElement;
import at.srfg.iot.common.datamodel.asset.api.ISubmodel;
import at.srfg.iot.common.datamodel.asset.api.ISubmodelElement;

public interface SubmodelElementContainer extends Referable {
	/** 
	 * Retrieve the submodel the container belongs to.
	 * 
	 * @return
	 */
	public ISubmodel getSubmodel();
	/**
	 * Obtain all direct submodel elements contained in this {@link SubmodelElementContainer}
	 * @return
	 */
	public List<ISubmodelElement> getSubmodelElements();
	/**
	 * Setter 
	 * @param elements
	 */
	public void setSubmodelElements(List<ISubmodelElement> elements);
	/**
	 * Add a new {@link SubmodelElement} to the container
	 * @param element
	 */
	public void addSubmodelElement(ISubmodelElement element);
	/**
	 * Remove a submodel element from it's container
	 * @param element
	 * @return
	 */
	public boolean removeSubmodelElement(ISubmodelElement element);
	
	/** 
	 * Direct access to a direct submodel element
	 * @param path
	 * @return
	 */
	default Optional<ISubmodelElement> getSubmodelElement(String idShort) {
		return getSubmodelElements().stream()
					.filter(new Predicate<ISubmodelElement>() {

						@Override
						public boolean test(ISubmodelElement t) {
							return t.getIdShort().equals(idShort);
						}})
					.findFirst();
	}
	
	/** 
	 * Direct access to a direct {@link SubmodelElement}, with type check
	 * @param path
	 * @see #getSubmodelElements()
	 * @return
	 */
	default <T extends ISubmodelElement> Optional<T> getSubmodelElement(String idShort, Class<T> clazz) {
		return getSubmodelElements().stream()
					.filter(new Predicate<ISubmodelElement>() {

						@Override
						public boolean test(ISubmodelElement t) {
							return t.getIdShort().equals(idShort) && clazz.isInstance(t);
						}})
					.findFirst()
					.flatMap(new Function<ISubmodelElement, Optional<T>>() {

						@Override
						public Optional<T> apply(ISubmodelElement t) {
							return Optional.of(clazz.cast(t));
						}
						
					});
	}
	default <T extends ISubmodelElement> List<T> getSubmodelElements(Class<T> clazz) {
		return getSubmodelElements().stream()
					.filter(new Predicate<ISubmodelElement>() {

						@Override
						public boolean test(ISubmodelElement t) {
							return clazz.isInstance(t);
						}})
					.map(new Function<ISubmodelElement, T>() {

						@Override
						public T apply(ISubmodelElement t) {
							return clazz.cast(t);
						}
						
					})
					.collect(Collectors.toList());
	}
	default <T extends ISubmodelElement> T newChildElement(String idShort, Class<T> clazz) {
		try {
			T instance = clazz.newInstance();
			instance.setIdShort(idShort);
			addSubmodelElement(instance);
			return instance;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalArgumentException("Error when creating child element of type:  " + clazz.getSimpleName());
		}
	}
}
