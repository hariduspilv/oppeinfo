
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eylOpetamine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eylOpetamine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oasNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oasId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="oasRegnr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ametikohad" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eylOpetamineAmetikoht" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eylOpetamine", propOrder = {
    "oasNimetus",
    "oasId",
    "oasRegnr",
    "ametikohad"
})
public class EylOpetamine {

    @XmlElement(required = true, nillable = true)
    protected String oasNimetus;
    @XmlElement(required = true, nillable = true)
    protected BigInteger oasId;
    @XmlElement(required = true, nillable = true)
    protected String oasRegnr;
    protected List<EylOpetamineAmetikoht> ametikohad;

    /**
     * Gets the value of the oasNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOasNimetus() {
        return oasNimetus;
    }

    /**
     * Sets the value of the oasNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOasNimetus(String value) {
        this.oasNimetus = value;
    }

    /**
     * Gets the value of the oasId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOasId() {
        return oasId;
    }

    /**
     * Sets the value of the oasId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOasId(BigInteger value) {
        this.oasId = value;
    }

    /**
     * Gets the value of the oasRegnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOasRegnr() {
        return oasRegnr;
    }

    /**
     * Sets the value of the oasRegnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOasRegnr(String value) {
        this.oasRegnr = value;
    }

    /**
     * Gets the value of the ametikohad property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ametikohad property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAmetikohad().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EylOpetamineAmetikoht }
     * 
     * 
     */
    public List<EylOpetamineAmetikoht> getAmetikohad() {
        if (ametikohad == null) {
            ametikohad = new ArrayList<EylOpetamineAmetikoht>();
        }
        return this.ametikohad;
    }

}