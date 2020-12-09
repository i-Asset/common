package at.srfg.indexing.model.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
/**
 * Interface adding multi-language support to custom properties
 * <p>
 * The multilingual values are indexed in RDF-style with the @ sign as
 * delimiter. The <code>qualifier</code> are used the same way as with {@link ICustomPropertyAware}
 *
 * @author dglachs
 * @see ICustomPropertyAware
 */
public interface IMultiLingualPropertyAware extends ICustomPropertyAware {
	String MULTILANG_DELIM = "@";
	
	/**
	 * Add a new multilingual text property to the index. 
	 * 
	 */
	default void addMultiLingualProperty(String text, String lang, String ... qualifier) {
		addProperty(String.format("%s%s%s", text, MULTILANG_DELIM, lang), qualifier);
	}
	default void addMultiLingualProperty(String text, String lang, PropertyType meta, String ...qualifier ) {
		addProperty(String.format("%s%s%s", text, MULTILANG_DELIM, lang), meta, qualifier);
	}
	/**
	 * add a custom multilingual property to the index
	 * @param text
	 * @param locale
	 * @param qualifier
	 */
	default void addMultiLingualProperty(String text, Locale locale, String ...qualifier ) {
		addMultiLingualProperty(text, locale.getLanguage(), qualifier);
	}
	default void addMultiLingualProperty(String text, Locale locale, PropertyType meta, String ...qualifier ) {
		addMultiLingualProperty(text, locale.getLanguage(), meta, qualifier);
	}
	/**
	 * Obtain a list of multilingual property values
	 * @param qualifier The qualifier used when storing  the multi lingual property value
	 * @param locale The language code, such as <i>en</i>, <i>es</i>
	 * @return A list of multilingual property values for the desired languag, empty list when no value present
	 */
	default List<String> getMultiLingualProperties(Locale locale, String ...qualifier ) {
		return getMultiLingualProperties(locale.getLanguage(), qualifier);
	}
	/**
	 * Obtain the first value of the requested language (assuming there is only one value per language)
	 * @param locale
	 * @param qualifier
	 * @return
	 */
	default Optional<String> getMultiLingualProperty(Locale locale, String ...qualifier ) {
		return getMultiLingualProperty(locale.getLanguage(), qualifier);
	}
	/**
	 * Obtain a single multilingual property value
	 * @param language
	 * @param qualifier
	 * @return
	 */
	default Optional<String> getMultiLingualProperty(String language, String ...qualifier ) {
		Collection<String> v = getMultiLingualProperties(language, qualifier);
		if ( v!=null ) {
			return v.stream().findFirst();
		}
		return Optional.empty();
		
	}
	/**
	 * Retrieve a list of multilingual properties in the desired language
	 * @param language
	 * @param qualifier
	 * @return
	 */
	default List<String> getMultiLingualProperties(String language, String ... qualifier) {
		String key = DynamicName.getDynamicFieldPart(qualifier);
		// check the values 
		Collection<String> values = getCustomStringValues().get(key);
		if ( values != null && ! values.isEmpty() ) {
			return values.stream().filter(new Predicate<String>() {
				private String extractLanguage(String t) {
					int delim = t.lastIndexOf("@");
					if ( delim > 0 ) {
						return t.substring(delim+1);
					}
					return t;
				}
				@Override
				public boolean test(String t) {
					if ( language.equals(extractLanguage(t))) {
						return true;
					}
					return false;
				}})
			.map(new Function<String, String>() {
				private String extractValue(String t) {
					int delim = t.lastIndexOf("@");
					if ( delim > 0 && t.length()>delim+1) {
						return t.substring(0,delim);
					}
					return null;
				}
				@Override
				public String apply(String t) {
					return extractValue(t);
				}})
			.collect(Collectors.toList());
		}

		return new ArrayList<>();
	}

}
