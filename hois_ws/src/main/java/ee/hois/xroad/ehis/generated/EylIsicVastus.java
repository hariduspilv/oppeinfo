
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for eylIsicVastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eylIsicVastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood"/&gt;
 *         &lt;element name="eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="synniKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="oppimineYld" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eylOppimine" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="oppimineKorg" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eylOppimine" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="opetamine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eylOpetamine" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eylIsicVastus", propOrder = {
    "isikukood",
    "eesnimi",
    "perenimi",
    "synniKp",
    "oppimineYld",
    "oppimineKorg",
    "opetamine"
})
public class EylIsicVastus {

    @XmlElement(required = true, nillable = true)
    protected String isikukood;
    @XmlElement(required = true, nillable = true)
    protected String eesnimi;
    @XmlElement(required = true, nillable = true)
    protected String perenimi;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar synniKp;
    protected List<EylOppimine> oppimineYld;
    protected List<EylOppimine> oppimineKorg;
    protected List<EylOpetamine> opetamine;

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the eesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEesnimi() {
        return eesnimi;
    }

    /**
     * Sets the value of the eesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEesnimi(String value) {
        this.eesnimi = value;
    }

    /**
     * Gets the value of the perenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerenimi() {
        return perenimi;
    }

    /**
     * Sets the value of the perenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerenimi(String value) {
        this.perenimi = value;
    }

    /**
     * Gets the value of the synniKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSynniKp() {
        return synniKp;
    }

    /**
     * Sets the value of the synniKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSynniKp(XMLGregorianCalendar value) {
        this.synniKp = value;
    }

    /**
     * Gets the value of the oppimineYld property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppimineYld property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppimineYld().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EylOppimine }
     * 
     * 
     */
    public List<EylOppimine> getOppimineYld() {
        if (oppimineYld == null) {
            oppimineYld = new ArrayList<EylOppimine>();
        }
        return this.oppimineYld;
    }

    /**
     * Gets the value of the oppimineKorg property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppimineKorg property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppimineKorg().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EylOppimine }
     * 
     * 
     */
    public List<EylOppimine> getOppimineKorg() {
        if (oppimineKorg == null) {
            oppimineKorg = new ArrayList<EylOppimine>();
        }
        return this.oppimineKorg;
    }

    /**
     * Gets the value of the opetamine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the opetamine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOpetamine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EylOpetamine }
     * 
     * 
     */
    public List<EylOpetamine> getOpetamine() {
        if (opetamine == null) {
            opetamine = new ArrayList<EylOpetamine>();
        }
        return this.opetamine;
    }

}
