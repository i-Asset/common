package at.srfg.iot.common.solr.indexing.core.service.event;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.jena.vocabulary.XSD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import at.srfg.iot.common.solr.indexing.core.service.repository.PropertyRepository;
import at.srfg.iot.common.solr.model.model.common.ClassType;
import at.srfg.iot.common.solr.model.model.common.IPropertyAware;
import at.srfg.iot.common.solr.model.model.common.PropertyType;
import at.srfg.iot.common.solr.model.model.common.ValueQualifier;

@Component
public class PropertyAwareEventListener {

	@Autowired
	private PropertyRepository propRepo;

	/**
	 * Asynchronous event listener processing
	 * the removal of {@link IPropertyAware} objects
	 * @param event
	 */
	@Async
	@EventListener
	public void onPropertyAwareRemove(RemovePropertyAwareEvent event) {
		IPropertyAware item = event.getEventObject();
		
		Collection<String> existing = item.getProperties();
		existing.forEach(new Consumer<String>() {

			@Override
			public void accept(String t) {
				Optional<PropertyType> prop = propRepo.findById(t);
				if ( prop.isPresent()) {
					PropertyType pt =prop.get();
					boolean changed = false;
					if ( pt.getPropertyUsage(item.getCollection()).contains(item.getUri()) ) {
						pt.removePropertyUsage(item.getCollection(), item.getUri());
						changed = true;
					}
					if ( pt.getConceptClass().contains(item.getUri())) {
						pt.removeConceptClass(item.getUri());
						changed = true;
					}
					if ( changed )
						propRepo.save(pt);
				}
			}
		});
		
	}
	/**
	 * Synchronous event listener maintaining the link between 
	 * {@link ClassType} entries and their {@link PropertyType}s.
	 * @param event
	 */
	@EventListener
	public void onApplicationEvent(PropertyAwareEvent event) {

		IPropertyAware item = event.getEventObject();
		if (item.getPropertyMap() != null && !item.getPropertyMap().isEmpty()) {
			// find all properties based on idxField name
			
			List<PropertyType> existing = propRepo.findByIdIn(escapedKey(item.getPropertyMap().keySet()));
			// keep a map of properties to change
			Map<String, PropertyType> changed = new HashMap<String, PropertyType>();

			// check whether an existing property lacks a itemFieldName or a label
			existing.forEach(new Consumer<PropertyType>() {

				@Override
				public void accept(PropertyType c) {
					// assign the class to the property (if not yet set)
					
					boolean changeDetected = false;
					PropertyType change = item.getPropertyMap().get(c.getUri());

					for (String idxField : change.getItemFieldNames()) {
						if (!c.getItemFieldNames().contains(idxField)) {
							c.addItemFieldName(idxField);
							// add to changed with URI - to have it saved ...
							changeDetected = true;
						}
					}
					//
					if ( item instanceof ClassType) {
						if ( ! c.getConceptClass().contains(item.getUri())) {
							c.getConceptClass().add(item.getUri());
							changeDetected = true;
						}
					}
					if ( ! c.getPropertyUsage(item.getCollection()).contains(item.getUri())) {
						c.addPropertyUsage(item.getCollection(), item.getUri());
						changeDetected = true;
					}
					// on the localNaem
					if ( harmonizeLabels(c.getLabel(), change.getLabel())) {
						changeDetected = true;
					}
					if ( harmonizeLabels(c.getComment(), change.getComment()) ) {
						changeDetected = true;
					}
					if ( harmonizeLabels(c.getDescription(), change.getDescription()) ) {
						changeDetected = true;
					}
					// remove any existing property so that is not added twice
					if ( changeDetected ) {
						changed.put(c.getUri(), c);
					}
					// in any case remove the checked property (based on the key) from the item
					item.getPropertyMap().remove(c.getUri());
				}
			});
			// process the remaining properties - they are not yet indexed!
			item.getPropertyMap().forEach(new BiConsumer<String, PropertyType>() {

				@Override
				public void accept(String qualifier, PropertyType newProp) {
					String uri = newProp.getUri();
					// uri must be present and valid, create the new property
					if (! StringUtils.isEmpty(uri)) {
						PropertyType pt = new PropertyType();
						// how to specify uri, localName & nameSpace
						// 
						pt.setUri(newProp.getUri());
						pt.setNameSpace(newProp.getNameSpace());
						pt.setLocalName(newProp.getLocalName());
						pt.setItemFieldNames(newProp.getItemFieldNames());
						pt.setLabel(newProp.getLabel());
						pt.setComment(newProp.getComment());
						pt.setDescription(newProp.getDescription());
						if ( item instanceof ClassType) {
							pt.addConceptClass(item.getUri());
						}
						// keep the link to the issuing collection
						pt.addPropertyUsage(item.getCollection(), item.getUri());
						
						pt.setValueQualifier(
								newProp.getValueQualifier() != null ? newProp.getValueQualifier() : ValueQualifier.STRING);
						switch (pt.getValueQualifier()) {
						case BOOLEAN:
							pt.setRange(XSD.xboolean.getURI());
							break;
						case TEXT:
						case STRING:
							pt.setValueQualifier(ValueQualifier.STRING);
							pt.setRange(XSD.xstring.getURI());
							break;
						default:
							pt.setRange(XSD.xdouble.getURI());
							break;
						}
						// add the new custom property to the index
						changed.put(pt.getUri(), pt);
					}
				}
			});

			// save all changed & new properties
			for (PropertyType newPt : changed.values()) {
				propRepo.save(newPt);
			}
		}
	}
	/**
	 * Helper method 
	 * @param toAdd
	 * @param from
	 */
	private boolean harmonizeLabels(Map<String, String> toAdd, Map<String, String> from) {
		boolean changeDetected = false;
		if (toAdd != null && from != null) {
			for (String lang : from.keySet()) {
				String old = toAdd.get(lang);
				if ( old == null || !old.equals(from.get(lang))) {
					toAdd.put(lang, from.get(lang));
					changeDetected = true;
				}
			}
		}
		return changeDetected;
	}
	private Set<String> escapedKey(Set<String> idSet) {
		return idSet.stream().map(new Function<String, String>() {

			@Override
			public String apply(String t) {
				return String.format("\"%s\"", t);
			}
		})
		.collect(Collectors.toSet());
	}
}
