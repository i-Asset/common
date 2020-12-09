//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.03.05 at 08:49:53 AM MSK 
//


package eu.nimble.service.model.ubl.commonaggregatecomponents;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.nimble.service.model.ubl.commonbasiccomponents.QuantityType;
import org.hibernate.annotations.Cascade;
import org.jvnet.hyperjaxb3.item.ItemUtils;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for ReceiptLineType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReceiptLineType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RejectedQuantity" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RejectReason" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Item" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReceiptLineType", propOrder = {
    "id",
    "rejectedQuantity",
    "rejectReason",
    "item"
})
@Entity(name = "ReceiptLineType")
@Table(name = "RECEIPT_LINE_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
public class ReceiptLineType
    implements Serializable, Equals
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected String id;
    @XmlElement(name = "RejectedQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected QuantityType rejectedQuantity;
    @XmlElement(name = "RejectReason", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<String> rejectReason;
    @XmlElement(name = "Item")
    protected ItemType item;
    @XmlAttribute(name = "Hjid")
    protected Long hjid;
    protected transient List<ReceiptLineTypeRejectReasonItem> rejectReasonItems;

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
     * Gets the value of the rejectedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link QuantityType }
     *     
     */
    @ManyToOne(targetEntity = QuantityType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "REJECTED_QUANTITY_RECEIPT_LI_0")
    public QuantityType getRejectedQuantity() {
        return rejectedQuantity;
    }

    /**
     * Sets the value of the rejectedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *     
     */
    public void setRejectedQuantity(QuantityType value) {
        this.rejectedQuantity = value;
    }

    /**
     * Gets the value of the rejectReason property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rejectReason property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRejectReason().add(newItem);
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
    public List<String> getRejectReason() {
        if (rejectReason == null) {
            rejectReason = new ArrayList<String>();
        }
        return this.rejectReason;
    }

    /**
     * 
     * 
     */
    public void setRejectReason(List<String> rejectReason) {
        this.rejectReason = rejectReason;
    }

    /**
     * Gets the value of the item property.
     * 
     * @return
     *     possible object is
     *     {@link ItemType }
     *     
     */
    @ManyToOne(targetEntity = ItemType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "ITEM_RECEIPT_LINE_TYPE_HJID")
    public ItemType getItem() {
        return item;
    }

    /**
     * Sets the value of the item property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemType }
     *     
     */
    public void setItem(ItemType value) {
        this.item = value;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if ((object == null)||(this.getClass()!= object.getClass())) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final ReceiptLineType that = ((ReceiptLineType) object);
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
            QuantityType lhsRejectedQuantity;
            lhsRejectedQuantity = this.getRejectedQuantity();
            QuantityType rhsRejectedQuantity;
            rhsRejectedQuantity = that.getRejectedQuantity();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "rejectedQuantity", lhsRejectedQuantity), LocatorUtils.property(thatLocator, "rejectedQuantity", rhsRejectedQuantity), lhsRejectedQuantity, rhsRejectedQuantity)) {
                return false;
            }
        }
        {
            List<String> lhsRejectReason;
            lhsRejectReason = (((this.rejectReason!= null)&&(!this.rejectReason.isEmpty()))?this.getRejectReason():null);
            List<String> rhsRejectReason;
            rhsRejectReason = (((that.rejectReason!= null)&&(!that.rejectReason.isEmpty()))?that.getRejectReason():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "rejectReason", lhsRejectReason), LocatorUtils.property(thatLocator, "rejectReason", rhsRejectReason), lhsRejectReason, rhsRejectReason)) {
                return false;
            }
        }
        {
            ItemType lhsItem;
            lhsItem = this.getItem();
            ItemType rhsItem;
            rhsItem = that.getItem();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "item", lhsItem), LocatorUtils.property(thatLocator, "item", rhsItem), lhsItem, rhsItem)) {
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

    @OneToMany(orphanRemoval = true, targetEntity = ReceiptLineTypeRejectReasonItem.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "REJECT_REASON_ITEMS_RECEIPT__0")
    public List<ReceiptLineTypeRejectReasonItem> getRejectReasonItems() {
        if (this.rejectReasonItems == null) {
            this.rejectReasonItems = new ArrayList<ReceiptLineTypeRejectReasonItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.rejectReason)) {
            this.rejectReason = ItemUtils.wrap(this.rejectReason, this.rejectReasonItems, ReceiptLineTypeRejectReasonItem.class);
        }
        return this.rejectReasonItems;
    }

    public void setRejectReasonItems(List<ReceiptLineTypeRejectReasonItem> value) {
        this.rejectReason = null;
        this.rejectReasonItems = null;
        this.rejectReasonItems = value;
        if (this.rejectReasonItems == null) {
            this.rejectReasonItems = new ArrayList<ReceiptLineTypeRejectReasonItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.rejectReason)) {
            this.rejectReason = ItemUtils.wrap(this.rejectReason, this.rejectReasonItems, ReceiptLineTypeRejectReasonItem.class);
        }
    }

}
