package at.srfg.iot.common.solr.model.model.solr.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.data.solr.core.mapping.SolrDocument;

import at.srfg.iot.common.solr.model.model.common.IClassType;
/**
 * Annotation for specifying joins in the SOLR domain
 * classes. 
 * <p>
 * Example:
 * <pre>
 * <code>@SolrJoin(joinedType=ClassType.class, <i>joinedField="id", joinName={"class", "classification"}</i>)</code>
 * <code>@Indexed(name="usage")</code>
 * <code>private Collection&ltString&gt classUsage;</code>
 * </pre>
 * The <i>mandatory</i> <code><b>joinedType</b></code> points to the domain class annotated with {@link SolrDocument}, 
 * thus providing the name of the joined collection, in this example {@link IClassType#COLLECTION} (resolves to <code>concept_class</code>).
 * </p><p>
 * The <i>optional</i> <code><b>joinedName</b></code> usually points to the field annotated with <code>@Id</code>, e.g. the 
 * primary id of the collection. If not present, the element is extracted from the <code>joinedType</code>. 
 * </p><p>
 * The <i>optional</i> <code><b>joinName</b></code> provides additional names for use as a join indicator. In the example,
 * the terms <code>class.en_label:SOLR*</code>, <code>classification.usage:SOLR*</code> 
 * and <code>concept_class.usage:SOLR*</code> are equivalent and will resolve to the following join expression:
 * <pre>
 * {!join to=usage from=id fromIndex=concept_class}en_label:SOLR*
 * </pre>
 * If <code>joinName</code> is not provided, only the name of the joined collection is accepted as join indicator.  
 * </p>
 * 
 * 
 * @author dglachs
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SolrJoin {
	/**
	 * Denotes the class annotated with {@link SolrDocument}
	 * representing the target collection of the join! The
	 * {@link SolrDocument#collection()} must link to the target 
	 * collection used as <b>fromIndex=collectionName</b>.
	 * @return
	 */
	Class<?> joinedType() default Object.class;
	/**
	 * Specifies the <b>from=id_field</b> for the join, this is 
	 * usually the field annotated with <code>@Id</code> in the 
	 * {@link SolrJoin#joinedType()}.
	 * When not set, the respective field is detected from
	 * the {@link #joinedType()}.
	 * @return
	 */
	String joinedField() default "";
	/**
	 * Provide additional names for initiating a join in the query. 
	 * <p>Usually, the join is initiated with the target collection's name as
	 * extracted from the {@link #joinedType()}. To allow for additional 
	 * join names in a query, this field may provide multiple
	 * alternate names. E.g an 
	 * query expression <code><b>manufacturer</b>.en_label:*a*</code>
	 * searches for a {@link SolrJoin} annotation with the join name <b>manufacturer</b>
	 * although the collection is named <code>party</code>.
	 * </p>
	 * @return
	 */
	String[] joinName() default "";
}
