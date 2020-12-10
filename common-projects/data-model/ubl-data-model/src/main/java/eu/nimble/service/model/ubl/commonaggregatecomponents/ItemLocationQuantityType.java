//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.30 at 09:30:40 AM MSK 
//


package eu.nimble.service.model.ubl.commonaggregatecomponents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.nimble.service.model.ubl.commonbasiccomponents.QuantityType;
import org.hibernate.annotations.Cascade;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for ItemLocationQuantityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemLocationQuantityType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MinimumQuantity"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ApplicableTerritoryAddress" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Price" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AllowanceCharge" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ApplicableTaxCategory" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemLocationQuantityType", propOrder = {
    "minimumQuantity",
    "applicableTerritoryAddress",
    "price",
    "allowanceCharge",
    "applicableTaxCategory"
})
@Entity(name = "ItemLocationQuantityType")
@Table(name = "ITEM_LOCATION_QUANTITY_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
public class ItemLocationQuantityType
    implements Serializable, Equals
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "MinimumQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected QuantityType minimumQuantity;
    @XmlElement(name = "ApplicableTerritoryAddress")
    protected List<AddressType> applicableTerritoryAddress;
    @XmlElement(name = "Price")
    protected PriceType price;
    @XmlElement(name = "AllowanceCharge")
    protected List<AllowanceChargeType> allowanceCharge;
    @XmlElement(name = "ApplicableTaxCategory")
    protected List<TaxCategoryType> applicableTaxCategory;
    @XmlAttribute(name = "Hjid")
    protected Long hjid;

    /**
     * Gets the value of the minimumQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link QuantityType }
     *     
     */
    @ManyToOne(targetEntity = QuantityType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "MINIMUM_QUANTITY_ITEM_LOCATI_0")
    public QuantityType getMinimumQuantity() {
        return minimumQuantity;
    }

    /**
     * Sets the value of the minimumQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *     
     */
    public void setMinimumQuantity(QuantityType value) {
        this.minimumQuantity = value;
    }

    /**
     * Gets the value of the applicableTerritoryAddress property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the applicableTerritoryAddress property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApplicableTerritoryAddress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AddressType }
     * 
     * 
     */
    @OneToMany(orphanRemoval = true,targetEntity = AddressType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "APPLICABLE_TERRITORY_ADDRESS_0")
    public List<AddressType> getApplicableTerritoryAddress() {
        if (applicableTerritoryAddress == null) {
            applicableTerritoryAddress = new ArrayList<AddressType>();
        }
        return this.applicableTerritoryAddress;
    }

    /**
     * 
     * 
     */
    public void setApplicableTerritoryAddress(List<AddressType> applicableTerritoryAddress) {
        this.applicableTerritoryAddress = applicableTerritoryAddress;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link PriceType }
     *     
     */
    @ManyToOne(targetEntity = PriceType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "PRICE_ITEM_LOCATION_QUANTITY_0")
    public PriceType getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceType }
     *     
     */
    public void setPrice(PriceType value) {
        this.price = value;
    }

    /**
     * Gets the value of the allowanceCharge property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allowanceCharge property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllowanceCharge().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AllowanceChargeType }
     * 
     * 
     */
    @OneToMany(orphanRemoval = true,targetEntity = AllowanceChargeType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "ALLOWANCE_CHARGE_ITEM_LOCATI_0")
    public List<AllowanceChargeType> getAllowanceCharge() {
        if (allowanceCharge == null) {
            allowanceCharge = new ArrayList<AllowanceChargeType>();
        }
        return this.allowanceCharge;
    }

    /**
     * 
     * 
     */
    public void setAllowanceCharge(List<AllowanceChargeType> allowanceCharge) {
        this.allowanceCharge = allowanceCharge;
    }

    /**
     * Gets the value of the applicableTaxCategory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the applicableTaxCategory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApplicableTaxCategory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TaxCategoryType }
     * 
     * 
     */
    @OneToMany(orphanRemoval = true,targetEntity = TaxCategoryType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "APPLICABLE_TAX_CATEGORY_ITEM_0")
    public List<TaxCategoryType> getApplicableTaxCategory() {
        if (applicableTaxCategory == null) {
            applicableTaxCategory = new ArrayList<TaxCategoryType>();
        }
        return this.applicableTaxCategory;
    }

    /**
     * 
     * 
     */
    public void setApplicableTaxCategory(List<TaxCategoryType> applicableTaxCategory) {
        this.applicableTaxCategory = applicableTaxCategory;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if ((object == null)||(this.getClass()!= object.getClass())) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final ItemLocationQuantityType that = ((ItemLocationQuantityType) object);
        {
            QuantityType lhsMinimumQuantity;
            lhsMinimumQuantity = this.getMinimumQuantity();
            QuantityType rhsMinimumQuantity;
            rhsMinimumQuantity = that.getMinimumQuantity();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "minimumQuantity", lhsMinimumQuantity), LocatorUtils.property(thatLocator, "minimumQuantity", rhsMinimumQuantity), lhsMinimumQuantity, rhsMinimumQuantity)) {
                return false;
            }
        }
        {
            List<AddressType> lhsApplicableTerritoryAddress;
            lhsApplicableTerritoryAddress = (((this.applicableTerritoryAddress!= null)&&(!this.applicableTerritoryAddress.isEmpty()))?this.getApplicableTerritoryAddress():null);
            List<AddressType> rhsApplicableTerritoryAddress;
            rhsApplicableTerritoryAddress = (((that.applicableTerritoryAddress!= null)&&(!that.applicableTerritoryAddress.isEmpty()))?that.getApplicableTerritoryAddress():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "applicableTerritoryAddress", lhsApplicableTerritoryAddress), LocatorUtils.property(thatLocator, "applicableTerritoryAddress", rhsApplicableTerritoryAddress), lhsApplicableTerritoryAddress, rhsApplicableTerritoryAddress)) {
                return false;
            }
        }
        {
            PriceType lhsPrice;
            lhsPrice = this.getPrice();
            PriceType rhsPrice;
            rhsPrice = that.getPrice();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "price", lhsPrice), LocatorUtils.property(thatLocator, "price", rhsPrice), lhsPrice, rhsPrice)) {
                return false;
            }
        }
        {
            List<AllowanceChargeType> lhsAllowanceCharge;
            lhsAllowanceCharge = (((this.allowanceCharge!= null)&&(!this.allowanceCharge.isEmpty()))?this.getAllowanceCharge():null);
            List<AllowanceChargeType> rhsAllowanceCharge;
            rhsAllowanceCharge = (((that.allowanceCharge!= null)&&(!that.allowanceCharge.isEmpty()))?that.getAllowanceCharge():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "allowanceCharge", lhsAllowanceCharge), LocatorUtils.property(thatLocator, "allowanceCharge", rhsAllowanceCharge), lhsAllowanceCharge, rhsAllowanceCharge)) {
                return false;
            }
        }
        {
            List<TaxCategoryType> lhsApplicableTaxCategory;
            lhsApplicableTaxCategory = (((this.applicableTaxCategory!= null)&&(!this.applicableTaxCategory.isEmpty()))?this.getApplicableTaxCategory():null);
            List<TaxCategoryType> rhsApplicableTaxCategory;
            rhsApplicableTaxCategory = (((that.applicableTaxCategory!= null)&&(!that.applicableTaxCategory.isEmpty()))?that.getApplicableTaxCategory():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "applicableTaxCategory", lhsApplicableTaxCategory), LocatorUtils.property(thatLocator, "applicableTaxCategory", rhsApplicableTaxCategory), lhsApplicableTaxCategory, rhsApplicableTaxCategory)) {
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

}