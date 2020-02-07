
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for otsus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="otsus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood"/&gt;
 *         &lt;element name="otsusNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="otsusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="soovitusKehtivusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="soovitused" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}soovitused"/&gt;
 *         &lt;element name="kommentaar" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}innoveKommentaar" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "otsus", propOrder = {
    "isikukood",
    "otsusNr",
    "otsusKp",
    "soovitusKehtivusKp",
    "soovitused",
    "kommentaar"
})
public class Otsus {

    @XmlElement(required = true)
    protected String isikukood;
    @XmlElement(required = true)
    protected String otsusNr;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar otsusKp;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar soovitusKehtivusKp;
    @XmlElement(required = true)
    protected Soovitused soovitused;
    protected String kommentaar;

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
     * Gets the value of the otsusNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtsusNr() {
        return otsusNr;
    }

    /**
     * Sets the value of the otsusNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtsusNr(String value) {
        this.otsusNr = value;
    }

    /**
     * Gets the value of the otsusKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOtsusKp() {
        return otsusKp;
    }

    /**
     * Sets the value of the otsusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOtsusKp(XMLGregorianCalendar value) {
        this.otsusKp = value;
    }

    /**
     * Gets the value of the soovitusKehtivusKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSoovitusKehtivusKp() {
        return soovitusKehtivusKp;
    }

    /**
     * Sets the value of the soovitusKehtivusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSoovitusKehtivusKp(XMLGregorianCalendar value) {
        this.soovitusKehtivusKp = value;
    }

    /**
     * Gets the value of the soovitused property.
     * 
     * @return
     *     possible object is
     *     {@link Soovitused }
     *     
     */
    public Soovitused getSoovitused() {
        return soovitused;
    }

    /**
     * Sets the value of the soovitused property.
     * 
     * @param value
     *     allowed object is
     *     {@link Soovitused }
     *     
     */
    public void setSoovitused(Soovitused value) {
        this.soovitused = value;
    }

    /**
     * Gets the value of the kommentaar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKommentaar() {
        return kommentaar;
    }

    /**
     * Sets the value of the kommentaar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKommentaar(String value) {
        this.kommentaar = value;
    }

}
