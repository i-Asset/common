package at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Key;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.ReferableElement;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;
import at.srfg.iot.common.datamodel.asset.aas.common.types.DirectionEnum;
import at.srfg.iot.common.datamodel.asset.aas.modeling.SubmodelElement;
/**
 * Extension of {@link SubmodelElement} representing
 * an Event setting for asset (parts).
 * 
 * @author dglachs
 *
 */
@Entity
@Table(name="aas_submodel_element_event")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public class EventElement extends SubmodelElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EventElement() {
		super(); // required default constructor
	}
	public EventElement(SubmodelElementContainer container) {
		super(container);
	}
	
	/**
	 * Convenience constructor. Creates and assigns the {@link EventElement} as
	 * a direct child element to the provided {@link Submodel}.
	 * @param idShort
	 * @param submodel
	 */
	public EventElement(String idShort, SubmodelElementContainer submodel) {
		super(idShort, submodel);
	}

	/**
	 * Reference to the {@link ReferableElement}, which defines the scope of the event. Can be AAS, 
	 * Submodel, SubmodelElementCollection or SubmodelElement.
	 */
	@ManyToOne
	@JoinColumn(name="observed_element_id")
	private ReferableElement observableElement;
	
	@Column(name="direction", nullable = false)
	private DirectionEnum direction;
	
	/**
	 * specifies whether the element actively triggers events (<code>true</code>) or not (<code>false</code>) 
	 */
	@Column(name="active", nullable=false)
	private boolean active;
	
	@Column(name="message_topic", length=50, nullable = false)
	private String messageTopic;
	/**
	 * Information, which outer message infrastructure shall handle messages for the EventElement. 
	 * Refers to a Submodel, SubmodelElementCollection, which contains DataElements describing the 
	 * proprietary specification for the message broker. 
	 * <p>Note: for different message infrastructure, e.g. OPC UA or MQTT or AMQP, these 
	 * proprietary specification could be standardized by having respective Submodels.</p>
	 */
	@ManyToOne
	@JoinColumn(name="broker_element_id")
	private ReferableElement messageBroker;

	public DirectionEnum getDirection() {
		return direction;
	}
	public void setDirection(DirectionEnum direction) {
		this.direction = direction;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getMessageTopic() {
		return messageTopic;
	}
	public void setMessageTopic(String messageTopic) {
		this.messageTopic = messageTopic;
	}
	@JsonIgnore
	public ReferableElement getMessageBrokerElement() {
		return messageBroker;
	}
	public void setMessageBrokerElement(ReferableElement messageBroker) {
		this.messageBroker = messageBroker;
	}
	public void setMessageBroker(Reference referenceToBroker) {
		this.messageBroker = referenceToBroker;
	}
	public Reference getMessageBroker() {
		if ( getMessageBrokerElement()== null) {
			return null;
		}
		if ( getMessageBrokerElement() instanceof Reference) {
			return (Reference) getMessageBrokerElement();
		}
		return new Reference(getMessageBrokerElement());
	}
	
	@JsonIgnore
	public ReferableElement getObservableElement() {
		return observableElement;
	}
	@JsonIgnore
	public void setObservableElement(ReferableElement observedElement) {
		this.observableElement = observedElement;
	}
	@JsonIgnore
	@Deprecated
	private void joinObservableReference(Reference element) {
		if ( element != null) {
			Reference thisRef = asReference();
			// fixme
			List<Key> newKeys = new ArrayList<>();
			newKeys.add(thisRef.getFirstKey());
			Iterator<Key> iter = element.getPathIterator();
			while (iter.hasNext()) {
				newKeys.add(iter.next());
			}
			this.observableElement = new Reference(newKeys);
		}
		else {
			this.observableElement = null;
		}
	}
	@JsonIgnore
	public void setObservableElement(Referable referable) {
		if ( ReferableElement.class.isInstance(referable)) {
			setObservableElement(ReferableElement.class.cast(referable));
		}
	}

	public Reference getObservableReference() {
		if ( getObservableElement()== null) {
			return null;
		}
		if ( getObservableElement() instanceof Reference) {
			return (Reference) getObservableElement();
		}
		return new Reference(getObservableElement());
	}
	/**
	 * Setter for the reference 
	 * @param reference
	 */
	public void setObservableReference(Reference reference) {
		setObservableElement(reference);
	}
	@Override
	public Optional<Referable> asInstance(Referable parent) {
		if ( isInstance()) {
			Optional.empty();
		}
		if (SubmodelElementContainer.class.isInstance(parent)) {
			EventElement instance = new EventElement(getIdShort(), SubmodelElementContainer.class.cast(parent));
			instance.setKind(Kind.Instance);
			instance.setCategory(getCategory());
			instance.setDescription(getDescription());
			// hold the semantics to the parent
			instance.setSemanticElement(this);
			// event specific data
			instance.setMessageBroker(getMessageBroker());
			instance.setMessageTopic(getMessageTopic());
			// combine the first key of the instance element with the remainder of the type
			instance.joinObservableReference(getObservableReference());
			// 
			instance.setDirection(getDirection());
			instance.setActive(isActive());
			return Optional.of(instance);
		}
			
		throw new IllegalStateException("Provided parent must be a SubmodelElementContainer");
	}

}