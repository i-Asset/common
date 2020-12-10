//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.03.17 at 11:56:48 AM EET 
//


package eu.nimble.service.model.ubl.despatchadvice;

import java.io.Serializable;
import eu.nimble.service.model.ubl.commonaggregatecomponents.*;
import eu.nimble.service.model.ubl.document.IDocument;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import eu.nimble.service.model.ubl.commonaggregatecomponents.CustomerPartyType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.DespatchLineType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.DocumentReferenceType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.OrderReferenceType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.ShipmentType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.SupplierPartyType;
import org.hibernate.annotations.Cascade;
import org.jvnet.hyperjaxb3.item.ItemUtils;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsDate;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XmlAdapterUtils;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for DespatchAdviceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DespatchAdviceType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}IssueDate"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Note" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OrderReference" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DespatchSupplierParty"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DeliveryCustomerParty"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Shipment" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DespatchLine" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AdditionalDocumentReference" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DespatchAdviceType", propOrder = {
    "id",
    "issueDate",
    "note",
    "orderReference",
    "despatchSupplierParty",
    "deliveryCustomerParty",
    "shipment",
    "despatchLine",
    "additionalDocumentReference"
})
@Entity(name = "DespatchAdviceType")
@Table(name = "DESPATCH_ADVICE_TYPE", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "ID"
    })
})
@Inheritance(strategy = InheritanceType.JOINED)
public class DespatchAdviceType
    implements Serializable, Equals, IDocument
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected String id;
    @XmlElement(name = "IssueDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar issueDate;
    @XmlElement(name = "Note", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<String> note;
    @XmlElement(name = "OrderReference", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    protected List<OrderReferenceType> orderReference;
    @XmlElement(name = "DespatchSupplierParty", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", required = true)
    protected SupplierPartyType despatchSupplierParty;
    @XmlElement(name = "DeliveryCustomerParty", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", required = true)
    protected CustomerPartyType deliveryCustomerParty;
    @XmlElement(name = "Shipment", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    protected ShipmentType shipment;
    @XmlElement(name = "DespatchLine", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", required = true)
    protected List<DespatchLineType> despatchLine;
    @XmlElement(name = "AdditionalDocumentReference", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    protected List<DocumentReferenceType> additionalDocumentReference;
    @XmlAttribute(name = "Hjid")
    protected Long hjid;
    protected transient List<DespatchAdviceTypeNoteItem> noteItems;

    @Override
	@Transient
    public String getDocumentId() {
        return id;
    }

    @Override
	@Transient
    public String getRequestDocumentId() {
        return null;
    }

    @Override
	@Transient
    public PartyType getSellerParty() {
        return despatchSupplierParty.getParty();
    }

    @Override
	@Transient
    public PartyType getBuyerParty() {
        return deliveryCustomerParty.getParty();
    }

    @Override
	@Transient
    public String getSellerPartyId() {
        return despatchSupplierParty.getParty().getPartyIdentification().get(0).getID();
    }

    @Override
	@Transient
    public List<PartyNameType> getSellerPartyName() {
        return despatchSupplierParty.getParty().getPartyName();
    }

    @Override
	@Transient
    public String getBuyerPartyId() {
        return deliveryCustomerParty.getParty().getPartyIdentification().get(0).getID();
    }

    @Override
	@Transient
    public List<PartyNameType> getBuyerPartyName() {
        return deliveryCustomerParty.getParty().getPartyName();
    }

    @Override
	@Transient
    public String getDocumentStatus() {
        return null;
    }

    @Override
	@Transient
    public List<ItemType> getItemTypes() {
        List<ItemType> itemTypes = new ArrayList<>();
        for (DespatchLineType despatchLineType : despatchLine) {
            itemTypes.add(despatchLineType.getItem());
        }
        return itemTypes;
    }

    @Override
	@Transient
    public List<DocumentReferenceType> getAdditionalDocuments() {
        return additionalDocumentReference;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "ID", length = 255)
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the issueDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    @Transient
    public XMLGregorianCalendar getIssueDate() {
        return issueDate;
    }

    /**
     * Sets the value of the issueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setIssueDate(XMLGregorianCalendar value) {
        this.issueDate = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the note property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNote().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    @Transient
    public List<String> getNote() {
        if (note == null) {
            note = new ArrayList<String>();
        }
        return this.note;
    }

    /**
     * 
     * 
     */
    public void setNote(List<String> note) {
        this.note = note;
    }

    /**
     * Gets the value of the orderReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderReferenceType }
     * 
     * 
     */
    @OneToMany(orphanRemoval = true,targetEntity = OrderReferenceType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "ORDER_REFERENCE_DESPATCH_ADV_0")
    public List<OrderReferenceType> getOrderReference() {
        if (orderReference == null) {
            orderReference = new ArrayList<OrderReferenceType>();
        }
        return this.orderReference;
    }

    /**
     * 
     * 
     */
    public void setOrderReference(List<OrderReferenceType> orderReference) {
        this.orderReference = orderReference;
    }

    /**
     * Gets the value of the despatchSupplierParty property.
     * 
     * @return
     *     possible object is
     *     {@link SupplierPartyType }
     *     
     */
    @ManyToOne(targetEntity = SupplierPartyType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "DESPATCH_SUPPLIER_PARTY_DESP_0")
    public SupplierPartyType getDespatchSupplierParty() {
        return despatchSupplierParty;
    }

    /**
     * Sets the value of the despatchSupplierParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierPartyType }
     *     
     */
    public void setDespatchSupplierParty(SupplierPartyType value) {
        this.despatchSupplierParty = value;
    }

    /**
     * Gets the value of the deliveryCustomerParty property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerPartyType }
     *     
     */
    @ManyToOne(targetEntity = CustomerPartyType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "DELIVERY_CUSTOMER_PARTY_DESP_0")
    public CustomerPartyType getDeliveryCustomerParty() {
        return deliveryCustomerParty;
    }

    /**
     * Sets the value of the deliveryCustomerParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerPartyType }
     *     
     */
    public void setDeliveryCustomerParty(CustomerPartyType value) {
        this.deliveryCustomerParty = value;
    }

    /**
     * Gets the value of the shipment property.
     * 
     * @return
     *     possible object is
     *     {@link ShipmentType }
     *     
     */
    @ManyToOne(targetEntity = ShipmentType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "SHIPMENT_DESPATCH_ADVICE_TYP_0")
    public ShipmentType getShipment() {
        return shipment;
    }

    /**
     * Sets the value of the shipment property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipmentType }
     *     
     */
    public void setShipment(ShipmentType value) {
        this.shipment = value;
    }

    /**
     * Gets the value of the despatchLine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the despatchLine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDespatchLine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DespatchLineType }
     * 
     * 
     */
    @OneToMany(orphanRemoval = true,targetEntity = DespatchLineType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "DESPATCH_LINE_DESPATCH_ADVIC_0")
    public List<DespatchLineType> getDespatchLine() {
        if (despatchLine == null) {
            despatchLine = new ArrayList<DespatchLineType>();
        }
        return this.despatchLine;
    }

    /**
     * 
     * 
     */
    public void setDespatchLine(List<DespatchLineType> despatchLine) {
        this.despatchLine = despatchLine;
    }

    /**
     * Gets the value of the additionalDocumentReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the additionalDocumentReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdditionalDocumentReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    @OneToMany(orphanRemoval = true,targetEntity = DocumentReferenceType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "ADDITIONAL_DOCUMENT_REFERENC_4")
    public List<DocumentReferenceType> getAdditionalDocumentReference() {
        if (additionalDocumentReference == null) {
            additionalDocumentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.additionalDocumentReference;
    }

    /**
     * 
     * 
     */
    public void setAdditionalDocumentReference(List<DocumentReferenceType> additionalDocumentReference) {
        this.additionalDocumentReference = additionalDocumentReference;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if ((object == null)||(this.getClass()!= object.getClass())) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final DespatchAdviceType that = ((DespatchAdviceType) object);
        {
            String lhsID;
            lhsID = this.getID();
            String rhsID;
            rhsID = that.getID();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "id", lhsID), LocatorUtils.property(thatLocator, "id", rhsID), lhsID, rhsID)) {
                return false;
            }
        }
        {
            XMLGregorianCalendar lhsIssueDate;
            lhsIssueDate = this.getIssueDate();
            XMLGregorianCalendar rhsIssueDate;
            rhsIssueDate = that.getIssueDate();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "issueDate", lhsIssueDate), LocatorUtils.property(thatLocator, "issueDate", rhsIssueDate), lhsIssueDate, rhsIssueDate)) {
                return false;
            }
        }
        {
            List<String> lhsNote;
            lhsNote = (((this.note!= null)&&(!this.note.isEmpty()))?this.getNote():null);
            List<String> rhsNote;
            rhsNote = (((that.note!= null)&&(!that.note.isEmpty()))?that.getNote():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "note", lhsNote), LocatorUtils.property(thatLocator, "note", rhsNote), lhsNote, rhsNote)) {
                return false;
            }
        }
        {
            List<OrderReferenceType> lhsOrderReference;
            lhsOrderReference = (((this.orderReference!= null)&&(!this.orderReference.isEmpty()))?this.getOrderReference():null);
            List<OrderReferenceType> rhsOrderReference;
            rhsOrderReference = (((that.orderReference!= null)&&(!that.orderReference.isEmpty()))?that.getOrderReference():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "orderReference", lhsOrderReference), LocatorUtils.property(thatLocator, "orderReference", rhsOrderReference), lhsOrderReference, rhsOrderReference)) {
                return false;
            }
        }
        {
            SupplierPartyType lhsDespatchSupplierParty;
            lhsDespatchSupplierParty = this.getDespatchSupplierParty();
            SupplierPartyType rhsDespatchSupplierParty;
            rhsDespatchSupplierParty = that.getDespatchSupplierParty();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "despatchSupplierParty", lhsDespatchSupplierParty), LocatorUtils.property(thatLocator, "despatchSupplierParty", rhsDespatchSupplierParty), lhsDespatchSupplierParty, rhsDespatchSupplierParty)) {
                return false;
            }
        }
        {
            CustomerPartyType lhsDeliveryCustomerParty;
            lhsDeliveryCustomerParty = this.getDeliveryCustomerParty();
            CustomerPartyType rhsDeliveryCustomerParty;
            rhsDeliveryCustomerParty = that.getDeliveryCustomerParty();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "deliveryCustomerParty", lhsDeliveryCustomerParty), LocatorUtils.property(thatLocator, "deliveryCustomerParty", rhsDeliveryCustomerParty), lhsDeliveryCustomerParty, rhsDeliveryCustomerParty)) {
                return false;
            }
        }
        {
            ShipmentType lhsShipment;
            lhsShipment = this.getShipment();
            ShipmentType rhsShipment;
            rhsShipment = that.getShipment();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "shipment", lhsShipment), LocatorUtils.property(thatLocator, "shipment", rhsShipment), lhsShipment, rhsShipment)) {
                return false;
            }
        }
        {
            List<DespatchLineType> lhsDespatchLine;
            lhsDespatchLine = (((this.despatchLine!= null)&&(!this.despatchLine.isEmpty()))?this.getDespatchLine():null);
            List<DespatchLineType> rhsDespatchLine;
            rhsDespatchLine = (((that.despatchLine!= null)&&(!that.despatchLine.isEmpty()))?that.getDespatchLine():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "despatchLine", lhsDespatchLine), LocatorUtils.property(thatLocator, "despatchLine", rhsDespatchLine), lhsDespatchLine, rhsDespatchLine)) {
                return false;
            }
        }
        {
            List<DocumentReferenceType> lhsAdditionalDocumentReference;
            lhsAdditionalDocumentReference = (((this.additionalDocumentReference!= null)&&(!this.additionalDocumentReference.isEmpty()))?this.getAdditionalDocumentReference():null);
            List<DocumentReferenceType> rhsAdditionalDocumentReference;
            rhsAdditionalDocumentReference = (((that.additionalDocumentReference!= null)&&(!that.additionalDocumentReference.isEmpty()))?that.getAdditionalDocumentReference():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "additionalDocumentReference", lhsAdditionalDocumentReference), LocatorUtils.property(thatLocator, "additionalDocumentReference", rhsAdditionalDocumentReference), lhsAdditionalDocumentReference, rhsAdditionalDocumentReference)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(null, null, object, strategy);
    }

    /**
     * Gets the value of the hjid property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    @Id
    @Column(name = "HJID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getHjid() {
        return hjid;
    }

    /**
     * Sets the value of the hjid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHjid(Long value) {
        this.hjid = value;
    }

    @Basic
    @Column(name = "ISSUE_DATE_ITEM")
    @Temporal(TemporalType.DATE)
    public Date getIssueDateItem() {
        return XmlAdapterUtils.unmarshall(XMLGregorianCalendarAsDate.class, this.getIssueDate());
    }

    public void setIssueDateItem(Date target) {
        setIssueDate(XmlAdapterUtils.marshall(XMLGregorianCalendarAsDate.class, target));
    }

    @OneToMany(orphanRemoval = true, targetEntity = DespatchAdviceTypeNoteItem.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "NOTE_ITEMS_DESPATCH_ADVICE_T_0")
    public List<DespatchAdviceTypeNoteItem> getNoteItems() {
        if (this.noteItems == null) {
            this.noteItems = new ArrayList<DespatchAdviceTypeNoteItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.note)) {
            this.note = ItemUtils.wrap(this.note, this.noteItems, DespatchAdviceTypeNoteItem.class);
        }
        return this.noteItems;
    }

    public void setNoteItems(List<DespatchAdviceTypeNoteItem> value) {
        this.note = null;
        this.noteItems = null;
        this.noteItems = value;
        if (this.noteItems == null) {
            this.noteItems = new ArrayList<DespatchAdviceTypeNoteItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.note)) {
            this.note = ItemUtils.wrap(this.note, this.noteItems, DespatchAdviceTypeNoteItem.class);
        }
    }

}