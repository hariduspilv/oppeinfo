
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for alusOppurOppimine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="alusOppurOppimine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ryhm" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kohaSuurus" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="oppimaAsumKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="klErivajadus" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="tugiteenus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}alusOppurTugiteenus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alusOppurOppimine", propOrder = {
    "ryhm",
    "kohaSuurus",
    "oppimaAsumKp",
    "klErivajadus",
    "tugiteenus"
})
public class AlusOppurOppimine {

    protected BigInteger ryhm;
    protected Double kohaSuurus;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar oppimaAsumKp;
    protected List<String> klErivajadus;
    protected List<AlusOppurTugiteenus> tugiteenus;

    /**
     * Gets the value of the ryhm property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRyhm() {
        return ryhm;
    }

    /**
     * Sets the value of the ryhm property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRyhm(BigInteger value) {
        this.ryhm = value;
    }

    /**
     * Gets the value of the kohaSuurus property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getKohaSuurus() {
        return kohaSuurus;
    }

    /**
     * Sets the value of the kohaSuurus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setKohaSuurus(Double value) {
        this.kohaSuurus = value;
    }

    /**
     * Gets the value of the oppimaAsumKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOppimaAsumKp() {
        return oppimaAsumKp;
    }

    /**
     * Sets the value of the oppimaAsumKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOppimaAsumKp(XMLGregorianCalendar value) {
        this.oppimaAsumKp = value;
    }

    /**
     * Gets the value of the klErivajadus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the klErivajadus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKlErivajadus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getKlErivajadus() {
        if (klErivajadus == null) {
            klErivajadus = new ArrayList<String>();
        }
        return this.klErivajadus;
    }

    /**
     * Gets the value of the tugiteenus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tugiteenus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTugiteenus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AlusOppurTugiteenus }
     * 
     * 
     */
    public List<AlusOppurTugiteenus> getTugiteenus() {
        if (tugiteenus == null) {
            tugiteenus = new ArrayList<AlusOppurTugiteenus>();
        }
        return this.tugiteenus;
    }

}
