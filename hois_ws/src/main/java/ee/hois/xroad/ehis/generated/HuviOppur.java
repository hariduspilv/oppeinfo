
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for huviOppur complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="huviOppur"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood"/&gt;
 *         &lt;element name="klEmakeel" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisRiikType" minOccurs="0"/&gt;
 *         &lt;element name="klOppekeel" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisRiikType" minOccurs="0"/&gt;
 *         &lt;element name="oppimaAsumiseKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="klOppevorm" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisRiikType"/&gt;
 *         &lt;element name="oppekavaKoodid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}huviOppekavaKoodid"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "huviOppur", propOrder = {
    "isikukood",
    "klEmakeel",
    "klOppekeel",
    "oppimaAsumiseKp",
    "klOppevorm",
    "oppekavaKoodid"
})
public class HuviOppur {

    @XmlElement(required = true)
    protected String isikukood;
    protected String klEmakeel;
    protected String klOppekeel;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar oppimaAsumiseKp;
    @XmlElement(required = true)
    protected String klOppevorm;
    @XmlElement(required = true)
    protected HuviOppekavaKoodid oppekavaKoodid;

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
     * Gets the value of the klEmakeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlEmakeel() {
        return klEmakeel;
    }

    /**
     * Sets the value of the klEmakeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlEmakeel(String value) {
        this.klEmakeel = value;
    }

    /**
     * Gets the value of the klOppekeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOppekeel() {
        return klOppekeel;
    }

    /**
     * Sets the value of the klOppekeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOppekeel(String value) {
        this.klOppekeel = value;
    }

    /**
     * Gets the value of the oppimaAsumiseKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOppimaAsumiseKp() {
        return oppimaAsumiseKp;
    }

    /**
     * Sets the value of the oppimaAsumiseKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOppimaAsumiseKp(XMLGregorianCalendar value) {
        this.oppimaAsumiseKp = value;
    }

    /**
     * Gets the value of the klOppevorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOppevorm() {
        return klOppevorm;
    }

    /**
     * Sets the value of the klOppevorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOppevorm(String value) {
        this.klOppevorm = value;
    }

    /**
     * Gets the value of the oppekavaKoodid property.
     * 
     * @return
     *     possible object is
     *     {@link HuviOppekavaKoodid }
     *     
     */
    public HuviOppekavaKoodid getOppekavaKoodid() {
        return oppekavaKoodid;
    }

    /**
     * Sets the value of the oppekavaKoodid property.
     * 
     * @param value
     *     allowed object is
     *     {@link HuviOppekavaKoodid }
     *     
     */
    public void setOppekavaKoodid(HuviOppekavaKoodid value) {
        this.oppekavaKoodid = value;
    }

}
