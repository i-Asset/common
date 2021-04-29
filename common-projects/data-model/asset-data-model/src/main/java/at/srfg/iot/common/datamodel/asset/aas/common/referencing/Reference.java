package at.srfg.iot.common.datamodel.asset.aas.common.referencing;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;


/**
 * Helper class holding references to model elements.
 * <p>
 * Note: The Reference is not persisted although subclassing 
 * {@link ReferableElement}. That way, the {@link Reference}
 * may be used in API calls for referencing persistent
 * entities.
 * </p>  
 * 
 */
public class Reference extends ReferableElement implements Serializable {
	private static final long serialVersionUID = 1L;

	private final LinkedList<Key> keys = new LinkedList<Key>();
	public Reference(Key ...keys) {
		for (Key k : keys) {
			this.keys.add(k);
		}
	}
	public Reference(List<Key> keys) {
		for( Key k : keys ) {
			this.keys.add(k);
		}
	}
	public Reference() {
		// default constructor
	}
	public Reference(String identifier) {
		this(identifier,null);
	}
	public Reference(String identifier, KeyElementsEnum type) {
		Key key = Key.of(identifier, type);
		setKeys(Collections.singletonList(key));
	}
	public Reference(Reference parent, Referable to) {
		if ( parent != null ) {
			this.keys.addAll(parent.keys);
			this.keys.add(Key.of(to.getIdShort(), to.getModelType()));
		}
		else {
			this.keys.add(Key.of(to));
		}
	}
	public Reference(Referable to) {
		setIdShort(to.getIdShort());
		setCategory(to.getCategory());
		do {
			if (Reference.class.isInstance(to)) {
				Reference ref = Reference.class.cast(to);
				Iterator<Key> refKeyIter = ref.keys.descendingIterator();
				while (refKeyIter.hasNext()) {
					keys.add(0, refKeyIter.next());
				}
			}
			else {
				keys.add(0, Key.of(to));
			}
			// 
			
		} while ((to = getParentKey(to)) != null);
	}
	public Optional<Key> getKey(KeyElementsEnum type) {
		return getKeys().stream().filter(new Predicate<Key>() {

			@Override
			public boolean test(Key t) {
				return t.getType().equals(type);
			}
		}).findFirst();
	}
	private Referable getParentKey(Referable to) {
		return to.getParentElement();
//		if ( to.getParentElement() != null) {
//			if (to instanceof Identifiable) {
//				Identifiable identifiable = (Identifiable)to;
//				switch (identifiable.getIdType()) {
//				case IdShort:
//					break;
//				default:
//					return null;
//				}
//			}
//			return to.getParentElement();
//		}
//		// when identifiable use the direct reference when not IdShort
//		return null;
	}
	/**
	 * Override the method
	 */
	public String getIdShort() {
		if ( getLastKey().getIdType().equals(IdType.IdShort)) {
			return getLastKey().getValue();
		}
		return super.getIdShort();
	}
	/**
	 * @return the keys
	 */
	public List<Key> getKeys() {
		return keys;
	}
	@JsonIgnore 
	public Key getFirstKey() {
		if (keys != null && ! keys.isEmpty() ) {
			return keys.getFirst();
		}
		return null;
	}
	@JsonIgnore
	public boolean isLastKey(Key key) {
		return key.equals(getLastKey());
	}
	@JsonIgnore
	public Key getLastKey() {
		if (keys != null && ! keys.isEmpty() ) {
			return keys.getLast();
		}
		return null;
	}
	@JsonIgnore
	public Identifier getFirstIdentifier() {
		if (keys != null && ! keys.isEmpty() ) {
			return keys.get(0).asIdentifier();
		}
		return null;
	}
	/**
	 * Check whether the first key resolves to the same identifier
	 * @param root
	 * @return
	 */
	public boolean hasRoot(String root) {
		if (keys != null && ! keys.isEmpty() ) {
			return keys.get(0).getValue().equals(root);
		}
		return false;
	}
	/**
	 * Check whether the first key resolves to the provided {@link Identifiable}
	 * @param root The (expected) root 
	 * @return <code>true</code> when the first key points to the provided {@link Identifiable}, <code>false</code> otherwise.
	 */
	public boolean hasRoot(Identifiable identifiable) {
		Optional<Key> firstKey = getKey(identifiable.getModelType());
		if ( firstKey.isPresent()) {
			return firstKey.get().asIdentifier().equals(identifiable.getIdentification());
		}
		return false;

	}
	/**
	 * Check whether the reference (finally) points to an element of
	 * the requested Type.
	 * @param modelType 
	 * @return 
	 */
	public boolean hasTargetType(KeyElementsEnum modelType) {
		if (!keys.isEmpty()) {
			return keys.getLast().getType().equals(modelType);
		}
		return false;
	}
	/**
	 * Construct a path for the reference. 
	 * <p>The path consists of all concatenated {@link IdType#IdShort}
	 * entries in the reference. The first element in the Reference must point to 
	 * the root element and is therefore omitted.
	 * @return The path, defaults to an empty string, not <code>null</code>!
	 * @see Reference#getPathIterator()
	 */
	@JsonIgnore
	public String getPath() {
		Iterator<Key> iter = getPathIterator();
		String path = "";
		while (iter.hasNext()) {
			Key next = iter.next();
			
			path+=next.getValue();
			if ( iter.hasNext() ) {
				path+="/";
			}
		}
		return path;
	}
	/**
	 * Provide a {@link Iterator} 
	 * @return
	 */
	@JsonIgnore
	public Iterator<Key> getPathIterator() {
		if ( keys.size()> 1) {
			return keys.subList(1,keys.size()).iterator();
		}
		else {
			return Collections.emptyIterator();
		}
	}
	/**
	 * @param keys the keys to set
	 */
	public void setKeys(List<Key> keys) {
		this.keys.addAll(keys);
	}
	
	public static boolean isSameIdentifiable(Identifiable identifiable, Reference ref) {
		Optional<Key> firstKey = ref.getKey(identifiable.getModelType());
		if ( firstKey.isPresent()) {
			return firstKey.get().asIdentifier().equals(identifiable.getIdentification());
		}
		return false;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((keys == null) ? 0 : keys.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
//		if (!super.equals(obj))
//			return false;
		if (getClass() != obj.getClass())
			return false;
		Reference other = (Reference) obj;
		if (keys == null) {
			if (other.keys != null)
				return false;
		} else if (!keys.equals(other.keys))
			return false;
		return true;
	}

	public String toString() {
		return getModelType()+": " + (getFirstKey()==null ? "" : getFirstKey().getValue() + "/") + getPath();
	}

}